(ns zooniverse-live.core
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(defn update-state
  [app]
  (fn [msg]
    (println (.-data msg))
    (let [msg (trim-newline (.-data msg))]
      (when-not (= msg "Heartbeat")
        (swap! app update-in [:classifiers] conj ((js->clj (.parse js/JSON msg)) "project"))))))

(defn connect-websocket
  [app]
  (let [socket (js/WebSocket. "ws://event.zooniverse.org/classifications")]
    (set! (.-onmessage socket) (update-state app))
    socket))

(def app-state (atom {:classifiers []}))

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

(om/root classifiers-view app-state
         {:target (. js/document (getElementById "app"))})

(connect-websocket app-state)
