(ns zooniverse-live.world-map
  (:require [om.core :as om :include-macros true]
            [clojure.string :refer [trim-newline]]
            [om.dom :as dom :include-macros true]
            [zooniverse-live.data-init :refer [request]]
            [cljs.core.async :refer [chan <! >!]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(defn world-map
  "Om component for new world-map"
  [app owner]
  (reify
    om/IRender
    (render [_]
      (dom/svg #js {:width 800 :height 400}
               (dom/rect nil "blah")))))
