(defproject hs-test "0.1.1-SNAPSHOT"
  :description "Health Samurai test task for junior clojure developer"
  :url "https://github.com/octoalex/hs-clojure"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.758"]
                 [compojure "1.6.1"]
                 [ring/ring-jetty-adapter "1.8.1"]
                 [ring/ring-defaults "0.3.2"]
                 [hiccup "1.0.5"]
                 [ring-cors "0.1.13"]
                 [org.clojure/java.jdbc "0.7.11"]
                 [org.postgresql/postgresql "42.1.3"]
                 [reagent "1.0.0-alpha2"]
                 [cljs-ajax "0.7.5"]]
  :plugins [[lein-ring "0.12.5"]]
  :main hs-test.web
  :uberjar-name "patients.jar"
  :ring {:handler hs-test.handler/app}
  :hooks [leiningen.cljsbuild]
  :profiles
  {:dev {:plugins [[lein-cljsbuild "1.1.8"]
                   [lein-figwheel "0.5.20"]]
         :dependencies [[figwheel-sidecar "0.5.20"]
                        [im.chit/vinyasa "0.2.0"]
                        [javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]
         :cljsbuild {:builds {
                              :dev {
                               :source-paths ["src/"]
                               :figwheel true
                               :jar true
                               :compiler {
                                          :main "hs-test.core"
                                          :asset-path "js/out"
                                          :output-to "resources/public/js/app.js"
                                          :output-dir "resources/public/js/out"
                                          :optimizations :none
                                          :pretty-print true}}
                              :prod {
                                    :source-paths ["src/"]
                                    :compiler {
                                                :output-to "resources/public/js/app.js"
                                                :optimizations :advanced
                                                :pretty-print false
                                               }}}
                     :clean-targets ^{:protect false} ["resources/public/js"
                                                       :target-path]}}})
