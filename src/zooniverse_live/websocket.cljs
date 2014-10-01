(ns zooniverse-live.websocket
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [<! >! chan]])
  (:require-macros [cljs.core.async.macros :refer [go-loop go]]))


(enable-console-print!)
(def str->clj
  (comp #(js->clj % :keywordize-keys true) #(.parse js/JSON %)))

(def format-msgs
  (comp (map #(.-data %))
        (map trim-newline)
        (filter #(not (some (apply set %) ["Heartbeat" "Stream Start" ""])))
        (map str->clj)))

(defn request
  [url callback-fn & {:keys [type] :or {type "GET"}}]
  (let [xhr (js/XMLHttpRequest.)]
    (set! (.-onreadystatechange xhr)
          (fn [_] (when-not (empty? (.-response xhr))
                    (callback-fn (str->clj (.-response xhr))))))
    (doto xhr
      (.open type url true)
      (.setRequestHeader "Accept" "application/json")
      (.send))))

(defn initial-load
  [app]
  (let [callback-fn (fn [response] (swap! app assoc :classifications response))]
    (request "http://event.zooniverse.org/classifications/galaxy_zoo?per_page=7" callback-fn)))

(defn connect-websocket
  [app]
  (initial-load app)
  (let [socket-chan (chan 1 format-msgs)
        socket (js/WebSocket. "ws://event.zooniverse.org/classifications")]
    (set! (.-onmessage socket) (fn [msg] (go (>! socket-chan msg))))
    (go-loop [msg (<! socket-chan)]
      (swap! app update-in [:classifications] conj msg)
      (recur (<! socket-chan)))))
