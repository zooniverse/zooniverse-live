(ns zooniverse-live.classifiers-view
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om-tools.dom :as dom :include-macros true]))

(defn location
  [city country code]
  (if city
    (str city ", " code)
    country))

(defn image-src
  [project]
  (str "/images/" project ".jpg"))

(defn classifications-slice
  [{:keys [classifications]}]
  (take-last 7 classifications))

(defn classifier-view
  [{:keys [project color country_code country_name city_name]} owner]
  (reify
    om/IRender
    (render [this]
      (dom/li {:className "classifier-view-item" :style {:background color}}
              (dom/div {:className "classification" :style {:border-color color}}
                       (dom/h2 (location city_name country_name country_code))
                       (dom/img {:src (image-src project)}))))))

(defn classifiers-view
  [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/div (dom/h1 "Classifiers")
               (dom/ul {:className "classifiers-view"}
                       (om/build-all classifier-view (classifications-slice app)))))))
