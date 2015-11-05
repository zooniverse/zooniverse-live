(ns zooniverse-live.core
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]
            [zooniverse-live.main-header :refer [main-header]]
            [zooniverse-live.data-init :refer [data-init]]
            [zooniverse-live.classifiers-view :refer [classifiers-view]]
            [zooniverse-live.world-map :refer [world-map]]
            [zooniverse-live.project-list :refer [project-list]]))

(enable-console-print!)

(defonce app-state (atom {:classifications []
                          :projects {}
                          :showing {:classifiers false
                                    :projects false
                                    :header-buttons false}
                          :map-data (js->clj (.-features (.-mapdata js/window)) :keywordize-keys true)
                          :edit-mode false}))

(om/root main-header app-state
         {:target (. js/document (getElementById "main-header"))})

(om/root project-list app-state
         {:target (. js/document (getElementById "projects"))})

(om/root classifiers-view app-state
         {:target (. js/document (getElementById "classifications"))})

(om/root world-map app-state
         {:target (. js/document (getElementById "map"))})

(data-init app-state)
