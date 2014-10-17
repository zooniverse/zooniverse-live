(ns zooniverse-live.project-list
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om-tools.dom :as dom :include-macros true]
            [cljs.core.async :refer [<! put! chan]]))


(defn put-to-chan
  [chan & args]
  (fn [e]
    (apply put! chan args)))

(defn project
  "Om component for new project"
  [[project {:keys [color display_name enabled]}] owner]
  (reify
    om/IRenderState
    (render-state [this {:keys [disable color-chan]}]
      (dom/li {:className (name project) :style {:color color}} 
              display_name
              (dom/div {:className "editor"}
               (dom/label "Enabled: "
                          (dom/input {:type "checkbox" :checked enabled :onChange (put-to-chan disable project)}))
               (dom/label "Color: "
                          (dom/input {:type "color" :value color :onChange (fn [e] (put! color-chan [project (.-value (.-target e))]))})))))))

(defn project-list
  "Om component for new project-list"
  [data owner]
  (reify
    om/IInitState
    (init-state [_]
      {:disable (chan)
       :edit-mode (chan)
       :color (chan)})
    om/IWillMount
    (will-mount [_]
      (let [disable (om/get-state owner :disable)
            edit-mode (om/get-state owner :edit-mode)
            color (om/get-state owner :color)]
        (go-loop [project (<! disable)]
          (om/transact! data #(update-in % [:projects project :enabled] not))
          (recur (<! disable)))
        (go-loop [[project c] (<! color)]
          (om/transact! data #(update-in % [:projects project] assoc :color c))
          (recur (<! color)))
        (go-loop [mode (<! edit-mode)]
          (om/transact! data :edit-mode (fn [mode] (not mode)))
          (recur (<! edit-mode)))))
    om/IRenderState
    (render-state [this {:keys [disable edit-mode color]}]
      (dom/div
       (dom/div {:className "projects-title"}
                (dom/h1 "Projects")
                (dom/button {:type "button" :onClick (put-to-chan edit-mode true)} "Edit"))
       (dom/ul {:className (when (:edit-mode data) "edit")}
               (om/build-all project
                             (filter #(or (:edit-mode data) (:enabled (second %))) (:projects data))
                             {:init-state {:disable disable :color-chan color}}))))))
