(ns quil-dojo.core
	(:require [quil.core :as q :include-macros true]))

(enable-console-print!)

(def state (atom []))

(defn add-piece [x y width height]
  (swap! state conj {:x x :y y :width width :height height :colour [(rand-int 255) (rand-int 255) (rand-int 255)]}))

(defn draw-pieces []
  (doseq [piece @state]
    (let [{:keys [x y width height colour]} piece]
      (apply q/fill colour)
      (q/rect x y width height))))

(defn remove-pieces [state x y]
  (let [new-state (into [] (remove #(and (or (< x (- (:x %) (:width %)))
                                             (> x (+ (:x %) (:width %))))
                                         (or (< y (- (:y %) (:height %)))
                                             (> y (+ (:y %) (:height %))))) state))]
    new-state))

(defn click []
  (let [x (q/mouse-x)
        y (q/mouse-y)]
    (swap! state remove-pieces x y)))

(defn grow [state]
  (into [] (map #(assoc % :width (+ (:width %) 1) :height (+ (:height %) 1)) state)))

(defn add-growable-shape []
 (add-piece (rand-int 500) (rand-int 500) 50 50) )

(defn draw []
	(q/background 255)
	(q/fill 0)
        (draw-pieces)
        (add-growable-shape)
        (swap! state grow))


(defn setup []
  (q/frame-rate 1)
  (add-growable-shape)
  (draw-pieces))


(q/defsketch quil-dojo
	:draw draw
	:host "quil-dojo"
	:size [600 600]
        :mouse-pressed click
        :setup setup)

;;DONE - add a growable ellipse at the start 
;;DONE - Add more growable ellipses over time.
;; DONE - Make the growable ellipses colourful
;; When we get an X Y click coordinate we need to work out
;; - which one(s) have we clicked?



;; start putting ellipses on screen. 
;; - The ellipses will grow
;; - over time we will add more that also grow.
;; when you click they will be removed
;; if the ellipse fills the screen you lose

;; Q: How can we determine if it's filled the screen?
;; When would it be called?
;; How would it work it out?
