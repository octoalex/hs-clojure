(ns hs-test.db
   (:require [clojure.java.jdbc :as sql]))

 (def connection-test {:dbtype "postgresql"
                  :dbname "hs_test"
                  :host "localhost"
                  :user "narkotroll"
                  :password "elfenlied1,"})
 
 
 (def connection {:dbtype "postgresql"
             :dbname "hs"
             :host "localhost"
             :user "narkotroll"
             :password "elfenlied1,"})
 
 
(defn migrated? [conn]
  (-> (sql/query conn
                 [(str "select count(*) from information_schema.tables "
                       "where table_name='patients'")])
      first :count pos?))

(defn migrate [conn]
  (when (not (migrated? conn))
    (print "Creating database structure...\n") (flush)
    (sql/db-do-commands conn
                        (sql/create-table-ddl
                         :patients
                         [[:id :serial "PRIMARY KEY"]
                         [:full_name :varchar "NOT NULL"]
                         [:gender :int "NOT NULL"]
                         [:birthday :date "NOT NULL"]
                         [:address :text "NOT NULL"]
                         [:policy_number :varchar "NOT NULL"] 
                         [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]]))
    (println " done \n")))


(defn all [conn]
  (into [] (sql/query conn ["select id,full_name,gender,birthday,address,policy_number from patients order by id desc"])))

(defn create [conn patient]
  (sql/insert! conn :patients patient))

(defn delete [conn id] 
  (sql/delete! conn :patients ["id = ?" (Integer/parseInt id)]))

(defn get-patient [conn id]
  (sql/query conn ["select * from patients where id = ?" (Integer/parseInt id)] {:result-set-fn first }))

(defn save-patient [conn id patient]
  (sql/update! conn :patients patient ["id  = ? "  (Integer/parseInt id)]))