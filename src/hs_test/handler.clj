(ns hs-test.handler
  (:require  [hs-test.routes :refer [app-routes]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


(def app
  (wrap-defaults app-routes site-defaults))
