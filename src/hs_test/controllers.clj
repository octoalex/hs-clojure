(ns hs-test.controllers
  (:require
   [ring.util.response :refer [redirect]]
   [hs-test.db :as db]
   [hs-test.views.patient :as view]
   [hs-test.views.layout :as layout]
   [clojure.data.json :as json])
  (:import [java.time LocalDate]))
 
(defn parse-date [string]
   (LocalDate/parse string))


(extend-type java.sql.Date
  json/JSONWriter
  (-write [date out]
    (json/-write (str date) out)))


(extend-type java.sql.Timestamp
  json/JSONWriter
  (-write [date out]
    (json/-write (str date) out)))




(defn api-patients
  [conn]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (json/write-str (db/all conn))})

(defn api-patient-delete
  [conn id]
  (db/delete conn id))

(defn api-patient [conn request]
   (let [patient {:full_name (get-in request [:form-params "full_name"])
                  :gender (Integer/parseInt (get-in request [:form-params "gender"]))
                  :birthday (parse-date (get-in request [:form-params "birthday"]))
                  :address (get-in request [:form-params "address"])
                  :policy_number (get-in request [:form-params "policy_number"])}]
     (if (not-empty (get-in request [:form-params "patient-id"]))
       (do
         (db/save-patient conn (get-in request [:form-params "patient-id"]) patient)
         (redirect "/"))
      (db/create conn patient))
     (redirect "/")))

(defn index 
  [conn]
  (view/index (db/all conn)))

(defn new
  []
  (view/new))


(defn create
  [conn request]
  (let [patient {:full_name (get-in request [:form-params "full_name"])
                 :gender  (Integer/parseInt (get-in request [:form-params "gender"]))
                 :birthday (parse-date (get-in request [:form-params "birthday"]))
                 :address (get-in request [:form-params "address"])
                 :policy_number (get-in request [:form-params "policy_number"])}]
    (if (not-empty (:full_name patient))
      (do
        (db/create conn patient)
        (redirect "/patients"))
     (redirect "/new"))))


(defn delete
  [conn id]
  (db/delete conn id)
  (redirect "/patients"))


(defn save [conn request]
   (let [patient {:full_name (get-in request [:form-params "full_name"])
                  :gender (Integer/parseInt (get-in request [:form-params "gender"]))
                  :birthday (parse-date (get-in request [:form-params "birthday"]))
                  :address (get-in request [:form-params "address"])
                  :policy_number (get-in request [:form-params "policy_number"])}]
     (if (not-empty (:full_name patient))
       (do
         (db/save-patient conn (get-in request [:form-params "patient-id"]) patient)
         (redirect "/patients"))
       (redirect (str "/edit/" (:id patient))))))

(defn edit
  [conn id]
  (let [patient (db/get-patient conn id)]
    (if (not-empty (:full_name patient))
      (view/edit patient)
      (layout/not-found-page))
    ))
