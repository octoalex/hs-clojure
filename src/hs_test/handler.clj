(ns hs-test.handler
  (:require  [hs-test.routes :refer [app-routes db-state]]
             [hs-test.db :refer [connection]]
             [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
             [ring.middleware.cors :refer [wrap-cors]]
             ))
(reset! db-state connection)

(def app
  (->
   #'app-routes
   (wrap-defaults api-defaults)
   (wrap-cors :access-control-allow-origin [#".*"]
              :access-control-allow-methods [:get :post])))
