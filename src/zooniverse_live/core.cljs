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

(om/root project-list app-state
         {:target (. js/document (getElementById "projects"))})

(om/root classifiers-view app-state
         {:target (. js/document (getElementById "classifications"))})

(data-init app-state)
