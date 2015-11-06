(ns zooniverse-live.main-header
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]))

(defn toggle-showing
  [key app-state]
  (om/transact! app-state #(update-in % [:showing key] not)))

(defn main-header
  [{:keys [showing] :as app} owner]
  (reify
    om/IRender
    (render [this]
      (dom/div {:className (str "main-header" (when (:header-buttons showing) " open"))}
               (dom/div {:className "title"}
                        (dom/img {:src "./images/zoonilogo.svg"})
                        (dom/h1 "Zooniverse" (dom/span " Live")))

               (dom/button
                {:type "button"
                 :className "menu-icon"
                 :onClick #(toggle-showing :header-buttons app)}
                (dom/img {:src "./images/show-menu.svg" :alt "Z"}))

               (dom/div {:className "menu-items"}
                        (dom/button
                         {:type "button"
                          :onClick #(toggle-showing :classifiers app)}
                         (count (:classifications app)) " Classifiers")
                        (dom/button
                         {:type "button"
                          :onClick #(toggle-showing :projects app)}
                         "Project Legend"))))))
