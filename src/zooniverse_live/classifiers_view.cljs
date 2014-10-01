(ns zooniverse-live.classifiers-view
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]))

(defn location
  [city country]
  (if city
    (str city ", " country)
    country))

(defn image-src
  [project]
  (str "/images/" project ".jpg"))

(defn merge-color
  [projects]
  (fn [{:keys [project] :as classification}]
    (assoc classification :color (get-in projects [(keyword project) :color]))))

(defn classifications-slice
  [{:keys [classifications projects]}]
  (map (merge-color projects) (take-last 7 classifications)))

(defn classifier-view
  [{:keys [project color country_name city_name] :as classification} owner]
  (reify
    om/IRender
    (render [this]
      (dom/li #js {:className "classifier-view-item" :style #js {:background color}}
              (dom/div #js {:className project}
                       (dom/h2 nil (location city_name country_name))
                       (dom/img #js {:src (image-src project)} nil))))))

(defn classifiers-view
  [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
               (dom/h1 nil "Classifiers")
               (apply dom/ul #js {:className "classifiers-view"}
                      (om/build-all classifier-view (classifications-slice app)))))))
