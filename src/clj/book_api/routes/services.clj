(ns book-api.routes.services
  (:require
   [reitit.swagger :as swagger]
   [reitit.swagger-ui :as swagger-ui]
   [reitit.ring.coercion :as coercion]
   [reitit.coercion.spec :as spec-coercion]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.multipart :as multipart]
   [reitit.ring.middleware.parameters :as parameters]
   [book-api.middleware.formats :as formats]
   [book-api.middleware.exception :as exception]
   [ring.util.http-response :refer :all]
   [clojure.java.io :as io]
   [book-api.db.core :as db]))

(defn service-routes []
  ["/api"
   {:coercion spec-coercion/coercion
    :muuntaja formats/instance
    :swagger {:id ::api}
    :middleware [;; query-params & form-params
                 parameters/parameters-middleware
                 ;; content-negotiation
                 muuntaja/format-negotiate-middleware
                 ;; encoding response body
                 muuntaja/format-response-middleware
                 ;; exception handling
                 exception/exception-middleware
                 ;; decoding request body
                 muuntaja/format-request-middleware
                 ;; coercing response bodys
                 coercion/coerce-response-middleware
                 ;; coercing request parameters
                 coercion/coerce-request-middleware
                 ;; multipart
                 multipart/multipart-middleware]}

   ;; swagger documentation
   ["" {:no-doc true
        :swagger {:info {:title "my-api"
                         :description "https://cljdoc.org/d/metosin/reitit"}}}

    ["/swagger.json"
     {:get (swagger/create-swagger-handler)}]

    ["/api-docs/*"
     {:get (swagger-ui/create-swagger-ui-handler
            {:url "/api/swagger.json"
             :config {:validator-url nil}})}]]

   ["/ping"
    {:get (constantly (ok {:message "hahahaha"}))}]

   ["/books"
    {:get
     {:handler
      (fn [_]
        {:status 200
         :body (let [books (db/get-books)
                     historys (map (fn [h]
                                     (if (= 1 (:已经归还 h))
                                       (assoc h :已经归还 true)
                                       (assoc h :已经归还 false)))
                                   (db/get-historys))
                     history-groups (group-by :book-id historys)]
                 (map
                  (fn [book]
                    (assoc book :history (history-groups (:id book))))
                  books)
                 )})}}]

   ["/history"
    {:post
     {:parameters {:body any?}
      :handler
      (fn [{{:keys [:book-id :借书人 :借书日期 :还书日期 :评价 :已经归还]} :body-params}]
        (db/insert-history {:book-id book-id
                            :borrower-name 借书人
                            :start-time 借书日期
                            :end-time 还书日期
                            :comments 评价})
        {:status 200
         :body (db/get-last-history {:book-id book-id})})}}]

   ["/return"
    {:post
     {:parameters {:body any?}
      :handler
      (fn [{{:keys [rowid]} :body-params}]
        (db/udpate-history {:rowid rowid})
        {:status 200
         :body {:good 1}
         })}}]

   ])
