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

(defn main []
  ;; conditionally start the app based on whether the #main-app-area
  ;; node is on the page
  (if-let [node (.getElementById js/document "main-app-area")]
    (.render js/ReactDOM (sab/html [:div "This is working"]) node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html

