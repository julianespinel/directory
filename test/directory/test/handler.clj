(ns directory.test.handler
  (:use clojure.test
        ring.mock.request  
        directory.handler
        midje.sweet)
  (:require directory.mongomanager))

(def redirection-message "Hello you have been redirected.")

(facts "About the main app routes"

       (fact "Get to / redirects to /redirect-url"
             (app-routes {:uri "/" :request-method :get}) 
             => (contains {:status 302 :headers {"Location" "/redirect-url"}}))

       (fact (str "Get to /redirect-url returns " redirection-message)
             (app-routes {:uri "/redirect-url" :request-method :get})
             => (contains {:status 200 :body redirection-message}))

       (fact "Register a new service with no POST payload returns 400"
             (app-routes {:uri "/services" :request-method :post})
             => (contains {:status 400}))

       (fact "Get to a not valid route returns 404"
             (app-routes {:uri "/not-valid" :request-method :get})
             => (contains {:status 404})))
