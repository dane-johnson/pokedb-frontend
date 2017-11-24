(ns pokedb-frontend.core
  (:require [reagent.core :as reagent :refer [atom]]
            [pokedb-frontend.ajax :refer [get-trainers]]
            [pokedb-frontend.background :refer [scrolling-pokes]]))

(enable-console-print!)

(print (get-trainers))

(reagent/render-component [scrolling-pokes]
                          (. js/document (getElementById "app")))
