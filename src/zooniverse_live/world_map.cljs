(ns zooniverse-live.world-map
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]
            [zooniverse-live.data-init :refer [request]]))

(def geo-json-url "./resources/countries.geo.json")

(defn geo-json-data []
  (request geo-json-url #(.log js/console "got data") :clojurize false))

(defn world-map []
  "Om component for new world-map"
  [app owner]
  (reify
    om/IWillMount
    (will-mount [_]
      (geo-json-data))
    om/IRender
    (render [_]
      (dom/svg #js {:width 800 :height 400}
               (dom/rect nil "blah")))))
