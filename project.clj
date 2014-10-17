(defproject zooniverse-live "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.7.0-alpha1"]
                 [org.clojure/clojurescript "0.0-2342"]
                 [prismatic/om-tools "0.3.6"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [om "0.7.1"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]

  :source-paths ["src"]

  :cljsbuild { 
              :builds [{:id "zooniverse-live"
                        :source-paths ["src"]
                        :compiler {
                                   :output-to "zooniverse_live.js"
                                   :output-dir "out"
                                   :optimizations :none
                                   :source-map true}}]})
