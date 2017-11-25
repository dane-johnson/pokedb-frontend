(ns pokedb-frontend.core
  (:require [reagent.core :as reagent :refer [atom]]
            [pokedb-frontend.ajax :refer [trainers get-trainers set-age]]
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
  (with-meta [:p (:name t) [:input {:type "number"
                                    :min 1
                                    :max 255
                                    :value (:age t)
                                    :name number
                                    :on-change on-age-change}]]
    {:key number}))

(defn trainers-view []
  [:div (map trainers-div @trainers)])

(defn page []
  [:div [scrolling-pokes] [trainers-view]])

(reagent/render-component [page]
                          (. js/document (getElementById "app")))
