(ns zooniverse-live.dev
  (:require
   [zooniverse-live.core]
   [zooniverse-live.data-init]
   [figwheel.client :as fw]))

(fw/start {
           :websocket-url "ws://localhost:3449/figwheel-ws"
           :on-jsload (fn []
                        (zooniverse-live.data-init/data-init zooniverse-live.core/app-state))})
