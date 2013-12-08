(ns directory.test.handler

  (:use clojure.test
        ring.mock.request  
        directory.handler
        midje.sweet)
  (:require [directory.mongomanager :as momanager]
            [directory.translator :as translator]
            [cheshire.core :refer :all]))

(def redirection-message "Hello you have been redirected.")

(def json-service "{\"service-name\": \"service1\", \"host\": \"host1\", \"port\": \"port1\", 
                  \"protocol\": \"protocol1\", \"prefix\": \"prefix1\"}")

(defn translate-from-json-to-map [json-string] (parse-string json-string))
(defn translate-from-map-to-service [generic-map] (translator/get-microservice-from-map generic-map))

(def new-microservice (translate-from-map-to-service (translate-from-json-to-map json-service)))

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

       (fact "Register a new service with a proper payload return 201"
            ; Translates the JSON String to a clojure map. (This is done by compojure in "(wrap-json-body)").
             (app-routes {:uri "/services" :request-method :post :body (translate-from-json-to-map json-service)}) 
             => (contains {:status 201})
             (provided (momanager/register-service new-microservice)
                       => (translate-from-map-to-service (translate-from-json-to-map json-service)) :times 1))

       (fact "Get to a not valid route returns 404"
             (app-routes {:uri "/not-valid" :request-method :get})
             => (contains {:status 404})))
