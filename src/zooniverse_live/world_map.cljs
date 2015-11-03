(ns zooniverse-live.world-map
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]))

(enable-console-print!)

;; SVG dimensions
(def height 900)
(def width 1600)

(defn parse-int [s]
  (.parseInt js/window s 10))

(defn lat->svg-y
  [lat]
  (let [adj-lat (+ lat 90)]
    (cond
      (= adj-lat 0) height
      (> adj-lat 180) 0
      :else (- height (* adj-lat (/ height 180))))))

(defn long->svg-x
  [long]
  (let [adj-long (+ (* 1 long) 180)]
    (cond
      (= adj-long 0) 0
      (> adj-long 360) width
      :else (* adj-long (/ width 360)))))

(defn country-path
  [pts owner]
  (reify
    om/IRender
    (render [_]
      (let [[first-pt & pts] (map (fn [[long lat]]
                                    (str (long->svg-x long)
                                         " "
                                         (lat->svg-y lat)))
                                  pts)]
        (dom/path {:d (apply str "M" first-pt (map #(str " L" %) pts))})))))

(defn draw-land
  [{:keys [properties geometry]} owner]
  (reify
    om/IRender
    (render [_]
      (dom/g {:className (str "country " (:name properties))}
            (condp = (:type geometry)
              "Polygon" (om/build country-path (apply concat (:coordinates geometry)))
              "MultiPolygon" (om/build-all country-path (apply concat (:coordinates geometry))))))))

(defn classifiers
  [{:keys [color project latitude longitude]} owner]
  (reify
    om/IRender
    (render [_]
      (dom/circle {:className (str "classifier-loc " project)
                   :r 5
                   :cy (lat->svg-y latitude)
                   :cx (long->svg-x longitude)
                   :fill color}))))

(defn world-map
  "Draws a world map and plots classifications on it"
  [{:keys [showing] :as data} owner]
  (reify
    om/IRender
    (render [_]
      (dom/svg {:className (str "world-map" (when (:classifiers showing) " small"))
                :ref "c"
                :viewBox (str "0 0" " " width " " height)}
               (om/build-all draw-land (:map-data data))
               (dom/g {:className "classifiers"}
                     (om/build-all classifiers (take-last 100 (:classifications data))))))))
