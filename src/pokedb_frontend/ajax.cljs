(ns pokedb-frontend.ajax
  (:require [ajax.core :refer [GET PUT]]
            [reagent.core :as reagent :refer [atom]]))

(def ^:dynamic *api-url* "http://localhost:5000")

(def trainers (atom (list)))

(defn trainers-handler
  [res]
  (reset! trainers (cljs.reader/read-string res)))

(defn trainers-error-handler
  [err]
  (print "I am in error"))

(defn get-trainers
  []
  (GET (str *api-url*  "/trainers")
       {:handler trainers-handler
        :error-handler trainers-error-handler}))
