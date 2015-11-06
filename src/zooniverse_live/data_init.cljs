(ns zooniverse-live.data-init
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [<! >! chan]])
  (:require-macros [cljs.core.async.macros :refer [go-loop go]]))

(enable-console-print!)

(def project-blacklist #{"hard_cell" "cancer_gene_runner"})

(def classifier-keynum (atom 0))

(defn randint
  [min max]
  (.floor js/Math (-> (.random js/Math)
                      (* (- max min))
                      (+ min))))

(defn random-color
  ([pastel]
     (let [color (map #(apply randint %1) (repeat 3 [0 255]))]
       (apply str "#" (map #(.toString (.floor js/Math (/ (+ %1 %2) 2)) 16)
                           color pastel))))
  ([] (random-color [255 255 255])))

(defn enabled-projects
  [{:keys [projects]}]
  (select-keys projects
               (for [[k v] projects
                     :when (:enabled v)]
                 k)))

(def str->clj
  (comp #(js->clj % :keywordize-keys true) #(.parse js/JSON %)))

(defn format-msgs
  [app]
  (comp (map #(.-data %))
        (map trim-newline)
        (filter #(not (or (= %  "Heartbeat") (= % "Stream Start"))))
        (map str->clj)
        (map #(assoc % :keynum (swap! classifier-keynum inc)))
        (filter #(contains? (enabled-projects @app) (keyword (:project %))))))

(defn request
  [url callback-fn & {:keys [type] :or {type "GET"}}]
  (let [xhr (js/XMLHttpRequest.)]
    (set! (.-onreadystatechange xhr)
          (fn [_] (when (and (= (.-readyState xhr) 4) (.-response xhr))
                    (callback-fn (str->clj (.-response xhr))))))
    (doto xhr
      (.open type url true)
      (.setRequestHeader "Accept" "application/json")
      (.send))))

(defn project-list->map
  [projects]
  (->> (map #(assoc % :color (random-color [120 120 120])) projects)
       (map #(assoc % :enabled true))
       (filter #(not (contains? project-blacklist (:name %))))
       (reduce #(assoc %1 (keyword (:name %2)) %2) {})))

(defn initial-load
  [app]
  (let [classifications-fn (fn [response] (swap! app assoc :classifications response))
        projects-fn (fn [response] (swap! app assoc :projects (project-list->map response)))]
    (request "https://api.zooniverse.org/projects/list" projects-fn)))

(defn merge-color
  [projects {:keys [project] :as classification}]
  (assoc classification :color (get-in projects [(keyword project) :color])))

(defn data-init
  [app]
  (initial-load app)
  (let [socket-chan (chan 1 (format-msgs app))
        socket (js/WebSocket. "ws://event.zooniverse.org/classifications")]
    (set! (.-onmessage socket) (fn [msg] (go (>! socket-chan msg))))
    (go-loop [msg (<! socket-chan)]
      #_(println (:project msg))
      (swap! app update-in [:classifications] conj (merge-color (:projects @app) msg))
      (recur (<! socket-chan)))))
