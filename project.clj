(defproject zooniverse-live "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122"]
                 [prismatic/om-tools "0.3.12"]
                 [lein-figwheel "0.4.1"]
                 [org.clojure/core.async "0.2.371"]
                 [org.omcljs/om "0.8.8"]]

  :plugins [[lein-cljsbuild "1.1.1-SNAPSHOT"]
            [lein-figwheel "0.4.1"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"]
  :cljsbuild {
              :builds [{:id "dev"
                        :source-paths ["src" "dev_src"]
                        :compiler {:output-to "resources/public/js/compiled/zooniverse_live.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :optimizations :none
                                   :main zooniverse-live.dev
                                   :asset-path "js/compiled/out"
                                   :source-map true
                                   :source-map-timestamp true
                                   :cache-analysis true }}
                       {:id "min"
                        :source-paths ["src"]
                        :compiler {:output-to "resources/public/js/compiled/zooniverse_live.js"
                                   :main zooniverse-live.core
                                   :optimizations :advanced
                                   :pretty-print false}}]}

  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.10"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}

  :figwheel {
             :http-server-root "public" ;; default and assumes "resources"
             :server-port 3449 ;; default
             :css-dirs ["resources/public/css"] ;; watch and update CSS
             ;; Start an nREPL server into the running figwheel process
             :nrepl-port 7888
             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is simple ring servers, if this
             ;; doesn't work for you just run your own server :)
             ;; :ring-handler hello_world.server/handler
             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"
             ;; if you want to disable the REPL
             ;; :repl false
             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             })
