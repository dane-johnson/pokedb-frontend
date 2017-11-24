(ns pokedb-frontend.background
  (:require [reagent.core :as reagent :refer [atom]]))

(def num-pokes 50)

(defn place-rand-poke
  []
  [(rand-int 600) (rand-int (.-innerWidth js/window)) (- (rand-int (.-innerHeight js/window)))])

(def pokemons (atom (vec (repeatedly num-pokes place-rand-poke))))

(defn update-poke
  [poke]
  (if (> (poke 2) (+ (.-innerHeight js/window) 100))
    (place-rand-poke)
    (update poke 2 (partial + 5))))

(defn update-pokes!
  []
  (swap! pokemons #(map update-poke %))) 

(defn pokemon-img [number x y]
  [:div {:style {:width 100 :height 100 :overflow "hidden"
                 :position "absolute" :top y :left x}}
   [:img {:src "/img/pokesprites.png"
          :style {:margin-left (* -96 (mod number 31))
                  :margin-top  (* -96 (quot number 31))}}]])

(defn poke-div
  []
  [:div {:style {:position "absolute" :top 0 :left 0 :z-index -1}}
   (map-indexed #(with-meta (apply pokemon-img %2) {:key %1}) @pokemons)])

(defn scrolling-pokes []
  [:div
   [poke-div]])

(defn animate
  []
  (do
    (update-pokes!)
    (js/setTimeout animate 10)))

(js/setTimeout animate 10)
