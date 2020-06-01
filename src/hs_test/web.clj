(ns hs-test.web
  (:require [compojure.core :refer [defroutes]]
            [ring.adapter.jetty :as ring]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [hs-test.routes :as routes]
            [hs-test.db :as schema]
            [ring.middleware.cors :refer [wrap-cors]])
  (:gen-class))

(reset! routes/db-state schema/connection)
(defroutes routes
  #'routes/app-routes)

(def application  
  (->
   #'routes
   (wrap-defaults api-defaults)
   (wrap-cors :access-control-allow-origin [#".*"]
               :access-control-allow-methods [:get :post])))

(defn start [port]
  (ring/run-jetty application {:port port
                               :join? false}))

(defn -main []
  (schema/migrate @routes/db-state)
  (let [port (Integer. (or (System/getenv "PORT") "3000"))]
    (start port)))