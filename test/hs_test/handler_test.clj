(ns hs-test.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [hs-test.handler :refer [app]]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
