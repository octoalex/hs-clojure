(ns hs-test.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [hs-test.handler :refer [app]]
            [hs-test.routes :refer [db-state]]
            [hs-test.db :as schema]))

(reset! db-state schema/connection-test)




(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))
  (testing "table exist"
    (if-not (true? (schema/migrated? @db-state)) (schema/migrate @db-state) nil)
    (is (= (schema/migrated? @db-state) true)))
  
  (testing "empty patients list from api"
      (let [response (app (mock/request :get "/api/patients"))]
        (is (= (:status response) 200))
        (is (= (:body response) "[]")))
  )
  
  (testing "search user by name "
    (let [response (app (mock/request :get "/api/patients?name=Alex"))]
      (is (= (:status response) 200))
      (is (= (:body response) "[]"))))
  
  (testing "adding patient"
    (app (->
          (mock/request :post "/api/patient")
          (mock/body {:full_name "Foo Bar"
                      :gender 1
                      :birthday "1995-01-14"
                      :address "Moscow"
                      :policy_number "1234353465"})))
     (let [response (app (mock/request :get "/api/patients"))]
       (is (= (:status response) 200))
       (is (= (:body response) "[{\"id\":1,\"full_name\":\"Foo Bar\",\"gender\":1,\"birthday\":\"1995-01-14\",\"address\":\"Moscow\",\"policy_number\":\"1234353465\"}]"))))
  
  (testing "search user by name 111"
    (let [response (app (mock/request :get "/api/patients?name=Foo"))]
     (is (= (:status response) 200))
     (is (= (:body response) "[{\"id\":1,\"full_name\":\"Foo Bar\",\"gender\":1,\"birthday\":\"1995-01-14\",\"address\":\"Moscow\",\"policy_number\":\"1234353465\"}]"))))
  
  (testing "search user by name dsfsdfsdf"
    (let [response (app (mock/request :get "/api/patients?name=Alex"))]
      (is (= (:status response) 200))
      (is (= (:body response) "[{\"id\":1,\"full_name\":\"Foo Bar\",\"gender\":1,\"birthday\":\"1995-01-14\",\"address\":\"Moscow\",\"policy_number\":\"1234353465\"}]"))))
  
  (testing "update user"
     (app (->
          (mock/request :post "/api/patient")
          (mock/body {:patient-id "1"
                      :full_name "Foo Bar Buzz"
                      :gender 1
                      :birthday "1995-01-14"
                      :address "Moscow"
                      :policy_number "1234353465"})))
     (let [response (app (mock/request :get "/api/patients"))]
       (is (= (:status response) 200))
       (is (= (:body response) "[{\"id\":1,\"full_name\":\"Foo Bar Buzz\",\"gender\":1,\"birthday\":\"1995-01-14\",\"address\":\"Moscow\",\"policy_number\":\"1234353465\"}]"))))
   (testing "delete patient"
     (app (mock/request :get "/api/patientd/1"))
     (let [response (app (mock/request :get "/api/patients"))]
       (is (= (:status response) 200))
       (is (= (:body response) "[]"))))
)
