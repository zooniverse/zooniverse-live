(ns zooniverse-live.world-map
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]
            [zooniverse-live.data-init :refer [request]]))

(defn geojson->svg
  [geojson width height]
  "converts geojson to a vector of svg paths"
  (let [g2svg (.geojson2svg js/window #js {:width width :height height})]
    (js->clj (.convert g2svg geojson))))

(defn world-map
  "Om component for new world-map"
  [{:keys [map-data]} owner]
  (reify
    om/IRender
    (render [_]
      (dom/svg #js {:width 800 :height 400}
               (dom/rect nil "blah")))))
