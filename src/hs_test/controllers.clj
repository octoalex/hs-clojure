(ns hs-test.controllers
  (:require
   [ring.util.response :refer [redirect]]
   [hs-test.db :as db]
   [hs-test.views.patient :as view]
   [hs-test.views.layout :as layout]
   )
  (:import [java.time LocalDate]))
 
(defn parse-date [string]
   (LocalDate/parse string))

(defn index 
  []
  (view/index (db/all)))

(defn new
  []
  (view/new))


(defn create
  [request]
  (let [patient {:full_name (get-in request [:form-params "full_name"])
                 :gender (if (= "1" (get-in request [:form-params "gender"])) true false)
                 :birthday (parse-date (get-in request [:form-params "birthday"]))
                 :address (get-in request [:form-params "address"])
                 :policy_number (get-in request [:form-params "policy_number"])}]
    (if (not-empty (:full_name patient))
      (do
        (db/create patient)
        (redirect "/"))
     (redirect "/new"))))


(defn delete
  [id]
  (db/delete id)
  (redirect "/"))


(defn save [request]
   (let [patient {:full_name (get-in request [:form-params "full_name"])
                  :gender (if (= "1" (get-in request [:form-params "gender"])) true false)
                  :birthday (parse-date (get-in request [:form-params "birthday"]))
                  :address (get-in request [:form-params "address"])
                  :policy_number (get-in request [:form-params "policy_number"])}]
     (if (not-empty (:full_name patient))
       (do
         (db/save-patient (get-in request [:form-params "patient-id"]) patient)
         (redirect "/"))
       (redirect (str "/edit/" (:id patient))))))

(defn edit
  [id]
  (let [patient (db/get-patient id)]
    (if (not-empty (:full_name patient))
      (view/edit patient)
      (layout/not-found-page))
    ))
