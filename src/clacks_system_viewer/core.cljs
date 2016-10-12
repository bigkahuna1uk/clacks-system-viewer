(ns clacks-system-viewer.core
  (:require
   #_[om.core :as om :include-macros true]
   [sablono.core :as sab :include-macros true])
  (:require-macros
   [devcards.core :as dc :refer [defcard deftest]]))

(enable-console-print!)

;; Simple content can be created without a card frame (heading) by not including
;; a name for a card.  In this case the card simply displays the text of the string.
;; This text will process markdown symbols such as # for heading styles
(defcard
  "# A Clacks Tower system visualised by DevCards")

;; A card can include simple text or html content.
;; The sablono library generates html for you, using Clojure keywords & vectors
;; to represent the structure of the html you require.
(defcard introduction
  (sab/html [:div
             [:h1 "Overview of the Clacks System Viewer"]
             [:p "Using the Sablono library makes it much easier to create your html
                  structure without having to manage html tags"]
             [:p "The inital design will be to define a devcard for each clacks tower
                  in the system"]]))


;; Define data to display in the cards representing a snapshot of the clacks towers

(def tower-001
  {:row1 [1 1 0 0]
   :row2 [0 1 1 0]})

(def tower-002
  {:row1 [1 1 0 0]
   :row2 [0 1 1 0]})

(def tower-003
  {:row1 [1 1 0 0]
   :row2 [0 1 1 0]})


;; Define cards to represent several towers in the Clacks system.

(defcard first-tower
  (sab/html [:div
             [:h1 "First clacks Tower"]
             [:p "A simple devcard showing the raw 1's and 0's from a Clacks Tower One"]
             [:div (tower-001 :row1)]
             [:div (tower-001 :row2)]]))

(defcard second-tower
  (sab/html [:div
             [:h1 "Second clacks Tower"]
             [:p "A simple devcard showing the raw 1's and 0's from a Clacks Tower Two"]
             [:div (tower-002 :row1)]
             [:div (tower-002 :row2)]]))

(defcard third-tower
  (sab/html [:div
             [:h1 "First clacks Tower"]
             [:p "A simple devcard showing the raw 1's and 0's from a Clacks Tower Three"]
             [:div (tower-003 :row1)]
             [:div (tower-003 :row2)]]))


(defn main []
  ;; conditionally start the app based on whether the #main-app-area
  ;; node is on the page
  (if-let [node (.getElementById js/document "main-app-area")]
    (.render js/ReactDOM (sab/html [:div "This is working"]) node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html

