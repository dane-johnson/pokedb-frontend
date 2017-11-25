(ns pokedb-frontend.ajax
  (:require [ajax.core :refer [GET PUT]]
            [reagent.core :as reagent :refer [atom]]))

(def ^:dynamic *api-url* "http://localhost:5000")

(def trainers (atom {}))

(defn trainers-handler
  [res]
  (reset! trainers
          (let [ts (cljs.reader/read-string res)]
            (zipmap (map #(get % :number) ts) ts))))

(defn trainers-error-handler
  [err]
  (print "I am in error"))

(defn get-trainers
  []
  (GET (str *api-url*  "/trainers")
       {:handler trainers-handler
        :error-handler trainers-error-handler}))

(defn set-age
  [no age]
  (PUT (str *api-url* "/trainer/" no)
       {:params {:age age}
        :format :raw}))
