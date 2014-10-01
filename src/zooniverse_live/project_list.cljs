(ns zooniverse-live.project-list
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]))

(defn project
  "Om component for new project"
  [[project {:keys [color name]}] owner]
  (reify
    om/IRender
    (render [_]
      (dom/li #js {:className project :style #js {:color color}} name))))

(defn project-list
  "Om component for new project-list"
  [data owner]
  (reify
    om/IRender
    (render [_]
      (dom/div nil 
               (dom/h1 nil "Project List"
                       (apply dom/ul nil
                               (om/build-all project (:projects data))))))))
