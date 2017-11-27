(ns pokedb-frontend.ajax
  (:require [ajax.core :refer [GET PUT DELETE]]
            [reagent.core :as reagent :refer [atom]]))

(def ^:dynamic *api-url* "http://localhost:5000")

(defn atom-handler
  [a key]
  (fn [res]
    (reset! a (let [vals (cljs.reader/read-string res)]
                (zipmap (map #(get % key) vals) vals)))))

(defn atom-getter
  [endpoint handler]
  (fn []
    (GET (str *api-url* endpoint)
         {:handler handler})))

;;;;;;;;;; TRAINERS ;;;;;;;;;;

(def trainers (atom {}))

(def trainers-handler (atom-handler trainers :number))

(def get-trainers (atom-getter "/trainers" trainers-handler))

(defn set-age
  [no age]
  (PUT (str *api-url* "/trainer/" no)
       {:params {:age age}
        :format :raw}))

;;;;;;;;;; POKEMON ;;;;;;;;;;

(def pokemons (atom {}))

(def pokemons-handler (atom-handler pokemons :number))

(defn get-pokemons
  [no]
  (GET (str *api-url* "/pokemon/" no)
       {:handler pokemons-handler}))

(defn delete-pokemon
  [no]
  (DELETE (str *api-url* "/pokemon/" no)))

;;;;;;;;;; SPECIES ;;;;;;;;;;

(def species (atom {}))

(def species-handler (atom-handler species :name))

(def get-species (atom-getter "/species" species-handler))

