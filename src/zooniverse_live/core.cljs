(ns zooniverse-live.core
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]
            [zooniverse-live.websocket :refer [connect-websocket]]
            [zooniverse-live.classifiers-view :refer [classifiers-view]]
            [zooniverse-live.project-list :refer [project-list]]))

(enable-console-print!)

(def app-state (atom {:classifications []
                      :projects {:galaxy_zoo {:color "orange" :project-name "Galaxy Zoo"}
                                 :planet_hunter {:color "blue" :project-name "Planet Hunters"}}}))


(defn app-component
  "Om component for new app-component"
  [data owner]
  (reify
    om/IRender
    (render [_]
      (dom/div nil
               (om/build project-list data)
               (om/build classifiers-view data)))))

(om/root app-component app-state
         {:target (. js/document (getElementById "app"))})

(connect-websocket app-state)
