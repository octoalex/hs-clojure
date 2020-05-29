(ns hs-test.web
  (:require [compojure.core :refer [defroutes]]
            [ring.adapter.jetty :as ring]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hs-test.routes :as routes]
            [hs-test.views.layout :as layout]
            [hs-test.db :as schema])
  (:gen-class))

(defroutes routes
  routes/app-routes
  (route/resources "/")
  (route/not-found (layout/not-found-page)))

(def application (wrap-defaults routes site-defaults))

(defn start [port]
  (ring/run-jetty application {:port port
                               :join? false}))

(defn -main []
  (schema/migrate)
  (let [port (Integer. (or (System/getenv "PORT") "3000"))]
    (start port)))