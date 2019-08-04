(ns book-api.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [book-api.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[book-api started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[book-api has shut down successfully]=-"))
   :middleware wrap-dev})
