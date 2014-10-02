(ns zooniverse-live.core
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]
            [zooniverse-live.data-init :refer [data-init]]
            [zooniverse-live.classifiers-view :refer [classifiers-view]]
            [zooniverse-live.project-list :refer [project-list]]
            [zooniverse-live.world-map :refer [world-map]]))

(enable-console-print!)

(def app-state (atom {:classifications []
                      :projects {}
                      :map-data (js-obj)}))

(defn app-component
  "Om component for new app-component"
  [data owner]
  (reify
    om/IRender
    (render [_]
      (dom/div nil
               (om/build world-map data)
               (om/build project-list data)
               (om/build classifiers-view data)))))

(om/root app-component app-state
         {:target (. js/document (getElementById "app"))})

(data-init app-state)
