(ns zooniverse-live.websocket
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]))

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
