(ns zooniverse-live.world-map
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]))

(enable-console-print!)

;;; colors
(def ocean-color "hsla(226, 100%, 27%, 1)")
(def land-color "hsla(235, 15%, 30%, 1)")
(def points-color "hsla(0, 100%, 50%, 1)")

(defn parse-int [s]
  (.parseInt js/window s 10))

(defn ctx
  [canvas]
  (.getContext canvas "2d"))

(defn lat->canvas-y
  [lat canvas]
  (let [adj-lat  (+ (* 1 lat) 90)
        height (parse-int (.-height canvas))]
    (cond
      (= adj-lat 0) height
      (> adj-lat 180) 0
      :else (- height (* adj-lat(/ height 180))))))

(defn long->canvas-x
  [long canvas]
  (let [adj-long (+ (* 1 long) 180)
        width (parse-int (.-width canvas))]
    (cond
      (= adj-long 0) 0
      (> adj-long 360) width
      :else (* adj-long (/ width 360)))))

(defn draw-ocean!
  [canvas]
  (let [ctx (ctx canvas)]
    (set! (.-fillStyle ctx) ocean-color)
    (.fillRect ctx
               0
               0
               (parse-int (.-width canvas))
               (parse-int (.-height canvas)))))

(defn draw-polygon!
  [ctx canvas pts]
  (.beginPath ctx)
  (doseq [pt pts]
    (let [lat (lat->canvas-y (second pt) canvas)
          long (long->canvas-x (first pt) canvas)]
      (.lineTo ctx long lat)))
  (.closePath ctx)
  (.fill ctx)
  (.stroke ctx))

(defn draw-land!
  [canvas map-data]
  (let [ctx (ctx canvas)
        countries map-data]
    (set! (.-fillStyle ctx) land-color)
    (doseq [{:keys [properties geometry]} countries]
      (condp = (:type geometry)
        "Polygon" (draw-polygon! ctx canvas (apply concat (:coordinates geometry)))
        "MultiPolygon" (doseq [pts (apply concat (:coordinates geometry))]
                         (draw-polygon! ctx canvas pts))))))

(defn draw-classifiers!
  [classifications canvas]
  (let [ctx (ctx canvas)
        pi (.-PI js/Math)
        radius 2] ;; px
    (set! (.-fillStyle ctx) points-color)
    (doseq [{:keys [latitude longitude]} classifications]
      (let [x (lat->canvas-y latitude canvas)
            y (long->canvas-x longitude canvas)]
        (.beginPath ctx)
        (.arc ctx y x radius (* 2 pi) false)
        (.fill ctx)))))

(defn world-map
  "Draws a world map and plots classifications on it"
  [data owner]
  (reify
    om/IDidMount
    (did-mount [_]
      (let [canvas (om/get-node owner "c")]
        (draw-land! canvas (:map-data data))))
    om/IWillUpdate
    (will-update [_ next-props next-state]
      (draw-classifiers! (:classifications data) (om/get-node owner "c")))
    om/IRender
    (render [_]
      (dom/canvas {:className "world-map" :ref "c" :width 1800 :height 900}
                  "Your browser does not support the canvas element"))))
