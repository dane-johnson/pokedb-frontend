(ns pokedb-frontend.add-pokemon
  (:require [reagent.core :as reagent :refer [atom]]
            [pokedb-frontend.ajax :refer [species get-species]]))

(get-species)

(def selected-trainer (atom nil))

(defn species-display
  [[name, species]]
  (with-meta [:option {:value name} (clojure.string/capitalize name)] {:key name}))

(defn species-spinner
  []
  [:select
   (map species-display @species)])

(defn add-pokemon-modal
  []
  [:div#add-pokemon-modal {:class "modal fade"}
   [:div.modal-dialog
    [:div.modal-content
     [:div.modal-header "Add Pokemon"]
     [:div.modal-body (str @selected-trainer) [species-spinner]]]]])
