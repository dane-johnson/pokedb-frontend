(ns pokedb-frontend.add-pokemon
  (:require [reagent.core :as reagent :refer [atom]]
            [pokedb-frontend.ajax :refer [species get-species get-pokemons
                                          add-pokemon]]))

(get-species)

(def selected-trainer (atom nil))

(defn species-display
  [[name, species]]
  (with-meta [:option {:value name} (clojure.string/capitalize name)] {:key name}))

(defn species-spinner
  [props]
  [:select props
   (map species-display @species)])

(def new-pokemon-info (atom {}))

(defn update-in-info
  [key]
  (fn [e]
    (swap! new-pokemon-info assoc key (-> e .-target .-value))))

(defn on-add-pokemon
  []
  (do
    (if (= (set (keys @new-pokemon-info)) #{:speciesname :nickname :attack :defense})
      (add-pokemon (assoc @new-pokemon-info :trainernumber @selected-trainer))
      (js/alert "Error, please enter all data."))
    (js/setTimeout #(get-pokemons @selected-trainer) 2000)))

(defn add-pokemon-modal
  []
  [:div#add-pokemon-modal {:class "modal fade"}
   [:div.modal-dialog
    [:div.modal-content
     [:div.modal-header "Add Pokemon"]
     [:div.modal-body
      [:p "Nickname:" [:input {:type "tex"
                               :on-change (update-in-info :nickname)}]]
      [:p "Species: " [species-spinner {:on-change (update-in-info :speciesname)}]]
      [:p "Attack: " [:input {:type "number" :min 0
                              :on-change (update-in-info :attack)}]]
      [:p "Defense: " [:input {:type "number" :min 0
                               :on-click (update-in-info :defense)}]]
      [:button.btn {:class "btn-primary"
                    :on-click on-add-pokemon
                    :data-toggle "modal"
                    :data-target "#add-pokemon-modal"}
       "Submit"]]]]])
