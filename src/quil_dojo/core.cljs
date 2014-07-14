(ns quil-dojo.core
	(:require [quil.core :as q :include-macros true]))

(def state (atom []))

(defn mouse []
 ; (q/background 255)
 ; (q/fill 0)
  (doseq [[ind capt fn] [;[0 "mouse-button" q/mouse-button]
                         ;[1 "mouse-pressed?" q/mouse-pressed?]
                        ; [2 "mouse-x" q/mouse-x]
                        ; [3 "mouse-y" q/mouse-y]
                       ;  [4 "pmouse-x" q/pmouse-x]
                        ; [5 "pmouse-y" q/pmouse-y]
                         ]]
   ; (q/text (str capt " " (fn)) 100 (+ (* 20 ind) 20))
    (q/ellipse 200 200 105 105)))

(defn add-piece [x y width height]
  (swap! state conj {:x x :y y :width width :height height :colour [(rand-int 255) (rand-int 255) (rand-int 255)]}))

(defn draw-pieces []
  (doseq [piece @state]
    (let [{:keys [x y width height colour]} piece]
      (apply q/fill colour)
      (q/ellipse x y width height))))

(defn click []
  (let [x (q/mouse-x)
        y (q/mouse-y)]
     (add-piece x y 50 50)))

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
  (q/frame-rate 10)
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
;; Make the growable ellipses colourful

;; start putting ellipses on screen. 
;; - The ellipses will grow
;; - over time we will add more that also grow.
;; when you click they will be removed
;; if the ellipse fills the screen you lose

;; Q: How can we determine if it's filled the screen?
;; When would it be called?
;; How would it work it out?
