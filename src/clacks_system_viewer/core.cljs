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
  "# A Clacks Tower system visualised by DevCards

Based on the [Two-Zero DevCards example](https://github.com/bhauman/devcards/blob/master/example_src/devdemos/two_zero.cljs)
and using the [Two-Zero CSS styles](https://github.com/bhauman/devcards/blob/master/example-resources/public/devcards/css/two-zero.css).

This project was created with the Leiningen devcards template: `lein new devcards clacks-system-viewer`

The project template sets up the project.clj file for a ClojureScript project and includes the
[Sablono Library](https://github.com/r0man/sablono) to generate html from Clojure code.")

;; A card can include simple text or html content.
;; The sablono library generates html for you, using Clojure keywords & vectors
;; to represent the structure of the html you require.
(defcard introduction
  (sab/html
   [:div
    [:h1 "Overview of the Clacks System Viewer"]
    [:p "The Clacks System Viewer displays the light patterns from all of the
         Clacks Towers in the system as they pass through a message."]
    [:p "Each Clacks Tower is represented by a grid of cells, 4 cells wide
         and 2 cells high."]
    [:p "Each cell represents either a 1 or 0 value.  If the light in a cell
         is off, that represents a 0.  If the light is on, that represents a 1."]

    [:p "Using the "
        [:a {:href "https://github.com/r0man/sablono"} "Sablono Library"]
        " makes it much easier to create your html structure without having to
         manage html tags.  Instead you create a vector with the first element
         being a keyword representation of an html tag (eg. :h1, :p, :div)."]
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


(def clacks-systems ["Ankh-Morpork" "Sto Plains" "Ramtops" "The Unnamed Continent" "Genua" "Uberwald"])

(defcard ankh-morpork-tower
  (sab/html [:div
             [:h1 "Ankh-Morpork Tower"]
             [:p "Tower located on the outskirts of Ankh-Morpork on Nap Hill"]
             [:p "Using basic styles from the 2048 devcards example, displaying a part of the clacks message"]
              [:div.cells
               [:div {:class "cell xpos-1 ypos-1"} (first (tower-001 :row1))]
               [:div {:class "cell xpos-1 ypos-2"} (first (tower-001 :row2))]]]))

(defcard second-tower
  (sab/html [:div
             [:h1 "Sto Plains Tower"]
             [:p "Using basic styles from the 2048 devcards example, displaying the whole clacks code"]
             [:p "I'm still figuring out the 2048 style sheet..."]
             [:div.cells
               [:div {:class "cell xpos-1 ypos-1"} (nth (tower-002 :row1) 0)]
               [:div {:class "cell xpos-2 ypos-1"} (nth (tower-002 :row1) 1)]
               [:div {:class "cell xpos-3 ypos-1"} (nth (tower-002 :row1) 2)]
               [:div {:class "cell xpos-4 ypos-1"} (nth (tower-002 :row1) 3)]]
              [:div.cells
               [:div {:class "cell xpos-1 ypos-2"} (nth (tower-002 :row2) 0)]
               [:div {:class "cell xpos-2 ypos-2"} (nth (tower-002 :row2) 1)]
               [:div {:class "cell xpos-3 ypos-2"} (nth (tower-002 :row2) 2)]
               [:div {:class "cell xpos-4 ypos-2"} (nth (tower-002 :row2) 3)]]]))


(defcard what-is-a-clacks-tower
  "The ground floor is a storeroom, the second contains an office, a kitchen and, in out-of-the-way towers, a bunkroom. The top floor contains the controls, two chairs face identical control boards on either side, each connected to the panels on the opposite side. There is a keyboard, levers, and pedals. ")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Generating a board - code taken from devcard examples

#_(def to-pixel-pos
(def cell-size 106)
(def cell-border 15)

(def to-pixel-pos
  (fn [x] (+ cell-border (* x (+ cell-size cell-border)))))

(defn pixel-pos [pos]
  (str (to-pixel-pos pos) "px"))


(defn board-cell [{:keys [top left id v highlight reveal]}]
  (let [translate (str "translate3d("
                       (pixel-pos left) ","
                       (pixel-pos top) ", 0px)")]
    (sab/html
     [:div.cell-pos { :style { "-webkit-transform" translate
                              "-moz-transform" translate
                              "transform" translate }
                     :key (str (name id)) }
      [:div { :class (str "cell cell-num-" v
                          (when highlight " highlight")
                          (when reveal " reveal")) } v]])))


;; Create background cells of the board, 4 cells width, 2 cells high.
(defn background-cells []
  (for [top (range 2) left (range 4)]
    (sab/html
     [:div.cell-pos.cell-empty
      { :style { :top (pixel-pos top)
                :left (pixel-pos left)}}])))


(defn game-board [data]
  (sab/html
   [:div.board-area
    [:div.background (background-cells)]
    [:div.cells (map board-cell data)]]))

;; Generate an empty Clacks Tower display
;; This does currently generate a warning in the javascript console as each cell does not have a unique id
;; Warning: Each child in an array or iterator should have a unique "key" prop.
#_(defcard board-style
  "## Board Style
  Let's start by creating the style for the board.
  The board is a 4x2 board. It will have one container with 8
  absolutely positioned cells in it. These cells will mark where the
  potential locations for the game tiles."
  (game-board []))


;; Example game board with different coloured cells from 2048 example
(defcard board-with-cells
  "### Cell Style
   Then we'll work on the style for the cells. The hard part is
   getting the colors and the font sizes correct."
  (game-board [{ :top 0 :left 0 :v 2 :id :t1}
               { :top 0 :left 1 :v 4 :id :t2}
               { :top 0 :left 2 :v 8 :id :t3}
               { :top 0 :left 3 :v 16 :id :t4}
               { :top 1 :left 0 :v 32 :id :t5}
               { :top 1 :left 1 :v 64 :id :t6}
               { :top 1 :left 2 :v 128 :id :t7}
               { :top 1 :left 3 :v 256 :id :t8}]))

(defcard tower-001-display
  "### Cell Style
   Then we'll work on the style for the cells. The hard part is
   getting the colors and the font sizes correct."
  (game-board [{ :top 0 :left 0 :v 1 :id :t1}
               { :top 0 :left 1 :v 1 :id :t2}
               { :top 0 :left 2 :v 0 :id :t3}
               { :top 0 :left 3 :v 1 :id :t4}
               { :top 1 :left 0 :v 0 :id :t5}
               { :top 1 :left 1 :v 0 :id :t6}
               { :top 1 :left 2 :v 1 :id :t7}
               { :top 1 :left 3 :v 1 :id :t8}]))

(defcard tower-002-display
  "### Cell Style
   Then we'll work on the style for the cells. The hard part is
   getting the colors and the font sizes correct."
  (game-board [{ :top 0 :left 0 :v 1 :id :t1}
               { :top 0 :left 1 :v 1 :id :t2}
               { :top 0 :left 2 :v 0 :id :t3}
               { :top 0 :left 3 :v 1 :id :t4}
               { :top 1 :left 0 :v 0 :id :t5}
               { :top 1 :left 1 :v 0 :id :t6}
               { :top 1 :left 2 :v 1 :id :t7}
               { :top 1 :left 3 :v 1 :id :t8}]))

(defcard clacks-tower-display-off
  (game-board [{ :top 0 :left 0 :v nil :id :clack1}
               { :top 0 :left 1 :v nil :id :clack2}
               { :top 0 :left 2 :v nil :id :clack3}
               { :top 0 :left 3 :v nil :id :clack4}
               { :top 1 :left 0 :v nil :id :clack5}
               { :top 1 :left 1 :v nil :id :clack6}
               { :top 1 :left 2 :v nil :id :clack7}
               { :top 1 :left 3 :v nil :id :clack8}]))

(defcard clacks-tower-fault
  (game-board [{ :top 0 :left 0 :v "X" :id :clack1}
               { :top 0 :left 1 :v "X" :id :clack2}
               { :top 0 :left 2 :v "X" :id :clack3}
               { :top 0 :left 3 :v "X" :id :clack4}
               { :top 1 :left 0 :v "X" :id :clack5}
               { :top 1 :left 1 :v "X" :id :clack6}
               { :top 1 :left 2 :v "X" :id :clack7}
               { :top 1 :left 3 :v "X" :id :clack8}]))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; A simple JavaScript interop example

(defonce observed-atom
  (let [a (atom 0)]
    (js/setInterval (fn [] (swap! observed-atom inc)) 1000)
    a))

(defcard atom-observing-card observed-atom)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defn main []
  ;; conditionally start the app based on whether the #main-app-area
  ;; node is on the page
  (if-let [node (.getElementById js/document "main-app-area")]
    (.render js/ReactDOM (sab/html [:div "This is working"]) node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html

