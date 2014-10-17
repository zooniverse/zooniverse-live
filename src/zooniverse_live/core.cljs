(ns zooniverse-live.core
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]
            [zooniverse-live.data-init :refer [data-init]]
            [zooniverse-live.classifiers-view :refer [classifiers-view]]
            [zooniverse-live.project-list :refer [project-list]]))

(enable-console-print!)

(def app-state (atom {:classifications []
                      :projects {}
                      :edit-mode false}))

(om/root project-list app-state
         {:target (. js/document (getElementById "projects"))})

(om/root classifiers-view app-state
         {:target (. js/document (getElementById "classifications"))})

(data-init app-state)
