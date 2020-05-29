(defproject hs-test "0.1.0-SNAPSHOT"
  :description "Health Samurai test task for junior clojure developer"
  :url "https://github.com/octoalex/hs-clojure"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-jetty-adapter "1.8.1"]
                 [ring/ring-defaults "0.3.2"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.7.11"]
                 [org.postgresql/postgresql "42.1.3"]]
  :plugins [[lein-ring "0.12.5"]]
  :main ^:skip-aot hs-test.web
  :uberjar-name "hs-test-standalone.jar"
  :ring {:handler hs-test.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
