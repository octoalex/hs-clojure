(ns hs-test.db
   (:require [clojure.java.jdbc :as sql]
             ))

 (def connection {:dbtype "postgresql"
             :dbname "hs_test"
             :host "localhost"
             :user "narkotroll"
             :password "elfenlied1,"})
 
 
(defn migrated? []
  (-> (sql/query connection
                 [(str "select count(*) from information_schema.tables "
                       "where table_name='patients'")])
      first :count pos?))

(defn migrate []
  (when (not (migrated?))
    (print "Creating database structure...\n") (flush)
    (sql/db-do-commands connection
                        (sql/create-table-ddl
                         :patients
                         [[:id :serial "PRIMARY KEY"]
                         [:full_name :varchar "NOT NULL"]
                         [:gender :boolean "NOT NULL"]
                         [:birthday :date "NOT NULL"]
                         [:address :text "NOT NULL"]
                         [:policy_number :varchar "NOT NULL"] 
                         [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]]))
    (println " done \n")))


(defn all []
  (into [] (sql/query connection ["select * from patients order by id desc"])))

(defn create [patient]
  (sql/insert! connection :patients patient))

(defn delete [id] 
  (sql/delete! connection :patients ["id = ?" (Integer/parseInt id)]))

(defn get-patient [id]
  (sql/query connection ["select * from patients where id = ?" (Integer/parseInt id)] {:result-set-fn first }))

(defn save-patient [id patient]
  (sql/update! connection :patients patient ["id  = ? "  (Integer/parseInt id)]))
