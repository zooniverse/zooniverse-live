(ns zooniverse-live.classifiers-view
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]))

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
