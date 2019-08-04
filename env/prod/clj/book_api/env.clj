(ns book-api.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[book-api started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[book-api has shut down successfully]=-"))
   :middleware identity})
