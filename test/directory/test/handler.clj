(ns directory.test.handler
  (:use clojure.test
        ring.mock.request  
        directory.handler))

(deftest test-app
  (testing "Index"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 302)) ; Redirect to /redirect-url
      (is (= (:body response) ""))))

  (testing "Redirect url"
    (let [response (app (request :get "/redirect-url"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello you have been redirected."))))

  (testing "Register service no payload"
    (let [response (app (request :post "/services"))]
      (is (= (:status response) 400)))) ; Bad request, the body payload is missing.
  
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
