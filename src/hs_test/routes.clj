(ns hs-test.routes
  (:require
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :as route]
   [hs-test.controllers :as c]
   [hs-test.views.layout :as layout]))

(defroutes app-routes
  (GET "/" [] (c/index))
  (GET "/new" [] (c/new))
  (GET "/delete/:id" [id] (c/delete id))
  (GET "/edit/:id" [id] (c/edit id))
  (POST "/edit" request (c/save request))
  (POST "/create" patient (c/create patient))
  (route/not-found (layout/not-found-page)))