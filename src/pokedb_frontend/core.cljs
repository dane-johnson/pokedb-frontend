(ns pokedb-frontend.core
  (:require [reagent.core :as reagent :refer [atom]]
            [pokedb-frontend.ajax :refer [trainers get-trainers]]
            [pokedb-frontend.background :refer [scrolling-pokes]]))

(enable-console-print!)
(get-trainers)

(defn trainers-view []
  [:div (map #(with-meta [:p (:name %)] {:key (:number %)}) @trainers)])

(defn page []
  [:div [scrolling-pokes] [trainers-view]])

(reagent/render-component [page]
                          (. js/document (getElementById "app")))
