(ns pokedb-frontend.core
  (:require [reagent.core :as reagent :refer [atom]]
            [pokedb-frontend.ajax :refer [trainers get-trainers set-age
                                          pokemons get-pokemons delete-pokemon]]
            [pokedb-frontend.background :refer [scrolling-pokes]]))

(enable-console-print!)
(get-trainers)

(defn on-age-change
  [e]
  (let [age (cljs.reader/read-string (-> e .-target .-value))
        number (cljs.reader/read-string (-> e .-target .-name))]
    (do
      (set-age number age)
      (swap! trainers assoc-in [number :age]  age))))

(defn trainers-div
  [[number t]]
  (with-meta [:p (:name t)
              [:input {:type "number"
                       :min 1
                       :max 255
                       :value (:age t)
                       :name number
                       :on-change on-age-change}]
              [:button {:class "btn btn-primary"
                        :on-click #(get-pokemons number)}
               "Show Pokemon"]]
    {:key number}))

(defn trainers-view []
  [:div.col-sm (map trainers-div @trainers)])

(defn on-delete-pokemon
  [no]
  (do
    (delete-pokemon no)
    (swap! pokemons dissoc no)))

(defn pokemons-div
  [[number p]]
  (with-meta [:p (or (:nickname p) (:speciesname p))
              [:button {:class "btn btn-danger"
                        :on-click #(on-delete-pokemon number)}
                                   "Delete"]]
    {:key number}))

(defn pokemons-view []
  [:div.col-sm (map pokemons-div @pokemons)])

(defn foreground
  [& children]
  [:div.container-fluid [:div.row children]])

(defn page []
  [:div [scrolling-pokes] [foreground [trainers-view] [pokemons-view]]])

(reagent/render-component [page]
                          (. js/document (getElementById "app")))
