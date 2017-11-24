(ns pokedb-frontend.ajax
  (:require [ajax.core :refer [GET PUT]]))

(def ^:dynamic *api-url* "http://localhost:5000")

(defn trainers-handler
  [res]
  (print (first (cljs.reader/read-string res))))

(defn trainers-error-handler
  [err]
  (print err))

(defn get-trainers
  []
  (GET (str *api-url*  "/trainers")
       {:handler trainers-handler
        :error-handler trainers-error-handler}))
