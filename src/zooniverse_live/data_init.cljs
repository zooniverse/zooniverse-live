(ns zooniverse-live.data-init
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [<! >! chan]])
  (:require-macros [cljs.core.async.macros :refer [go-loop go]]))


(enable-console-print!)

(defn enabled-projects
  [{:keys [projects]}]
  (select-keys projects (for [[k v] projects :when (:enabled v)] k)))

(defn json
  [str]
  (.parse js/JSON str))

(def str->clj
  (comp #(js->clj % :keywordize-keys true) json))

(defn format-msgs
  [app]
  (comp (map #(.-data %))
        (map trim-newline)
        (filter #(not (or (= %  "Heartbeat") (= % "Stream Start"))))
        (map str->clj)
        (filter #(contains? (enabled-projects @app) (keyword (:project %))))))

(defn request
  [url callback-fn & {:keys [type clojurize] :or {type "GET" clojurize true}}]
  (let [xhr (js/XMLHttpRequest.)
        convert-fn (if clojurize str->clj json)]
    (set! (.-onreadystatechange xhr)
          (fn [_] (when (and (= (.-readyState xhr) 4) (.-response xhr))
                    (callback-fn (convert-fn (.-response xhr))))))
    (doto xhr
      (.open type url true)
      (.setRequestHeader "Accept" "application/json")
      (.send))))

(defn project-list->map
  [projects]
  (->>  (map #(assoc % :color "#222") projects)
        (map #(assoc % :enabled true))
        (reduce #(assoc %1 (keyword (:name %2)) %2) {})))

(defn initial-load
  [app]
  (let [classifications-fn (fn [response] (swap! app assoc :classifications response))
        projects-fn (fn [response] (swap! app assoc :projects (project-list->map response)))
        map-fn (fn [response] (swap! app assoc :map-data response))]
    (request "https://api.zooniverse.org/projects/list" projects-fn)
    (request "./resources/countries.geo.json" map-fn :clojurize false)
    (request "http://event.zooniverse.org/classifications/galaxy_zoo?per_page=7" classifications-fn)))

(defn data-init
  [app]
  (initial-load app)
  (let [socket-chan (chan 1 (format-msgs app))
        socket (js/WebSocket. "ws://event.zooniverse.org/classifications")]
    (set! (.-onmessage socket) (fn [msg] (go (>! socket-chan msg))))
    (go-loop [msg (<! socket-chan)]
      (swap! app update-in [:classifications] conj msg)
      (recur (<! socket-chan)))))
