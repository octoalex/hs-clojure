(ns hs-test.routes
  (:require
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :as route]
   [hs-test.controllers :as c]
   [hs-test.views.layout :as layout]
   [clojure.java.io :as io]))

(defonce db-state (atom nil))


(defroutes app-routes
  (route/resources "/")
  (GET "/" [] (io/resource "public/index.html"))
  (GET "/patients" [] (c/index @db-state))
  (GET "/new" [] (c/new @db-state))
  (GET "/delete/:id" [id] (c/delete @db-state id))
  (GET "/edit/:id" [id] (c/edit @db-state id))
  (POST "/edit" request (c/save @db-state request))
  (POST "/create" patient (c/create @db-state patient))
  (GET "/api/patients" [] (c/api-patients @db-state))
  (POST "/api/patient" request (c/api-patient @db-state request))
  (GET "/api/patientd/:id" [id] (c/api-patient-delete @db-state id))
  (route/not-found (layout/not-found-page)))
