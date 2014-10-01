(ns zooniverse-live.core
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

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

(defn update-state
  [app]
  (fn [msg]
    (println (.-data msg))
    (let [msg (trim-newline (.-data msg))]
      (when-not (some #{msg} ["Heartbeat" "String Start"])
        (swap! app update-in [:classifiers] conj (js->clj (.parse js/JSON msg)))))))

(defn connect-websocket
  [app]
  (let [socket (js/WebSocket. "ws://event.zooniverse.org/classifications")]
    (set! (.-onmessage socket) (update-state app))
    socket))

(def app-state (atom {:classifiers [] :projects {:galaxy_zoo {:color "orange" :name "Galaxy Zoo"}
                                                 :planet_hunter {:color "blue" :name "Planet Hunters"}}}))

(defn classifier-view
  [classifier owner]
  (reify
    om/IRender
    (render [this]
      (dom/li nil classifier))))

(defn classifiers-view
  [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
               (dom/h1 nil "Classifiers")
               (apply dom/ul nil
                      (om/build-all classifier-view  (:classifiers app)))))))

(om/root project-list app-state
         {:target (. js/document (getElementById "app"))})

(connect-websocket app-state)
