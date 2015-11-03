(ns zooniverse-live.main-header
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]))

(defn main-header
  [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/div {:className "main-header"}
               (dom/div {:className "title"}
                (dom/img {:src "/images/zoonilogo.svg" :alt "Z"})
                (dom/h1 "Zooniverse" (dom/span " Live")))
               (dom/button
                {:onClick (fn [e] (om/transact! app #(update-in % [:showing :classifiers] not)))}
                "Classifiers")
               (dom/button
                {:onClick (fn [e] (om/transact! app #(update-in % [:showing :projects] not)))}
                "Project Legend")))))


