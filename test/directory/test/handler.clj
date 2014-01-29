(ns directory.test.handler

  (:use clojure.test
        ring.mock.request  
        directory.handler
        midje.sweet)
  (:require [directory.mongomanager :as momanager]
            [directory.translator :as translator]
            [cheshire.core :refer :all]))

(def redirection-message "Hello you have been redirected.")

(def json-service "{\"serviceName\": \"service1\", \"host\": \"host1\", \"port\": \"port1\", 
                  \"protocol\": \"protocol1\", \"prefix\": \"prefix1\"}")

(defn translate-from-json-to-map [json-string] (parse-string json-string))
(defn translate-from-map-to-service [generic-map] (translator/get-microservice-from-map generic-map))

(def new-microservice (translate-from-map-to-service (translate-from-json-to-map json-service)))

(facts "About the main app routes"

       (fact "Get to /redirect redirects to /redirect-url"
             (api-routes {:uri "/redirect" :request-method :get}) 
             => (contains {:status 302 :headers {"Location" "/redirect-url"}}))

       (fact (str "Get to /redirect-url returns " redirection-message)
             (api-routes {:uri "/redirect-url" :request-method :get})
             => (contains {:status 200 :body redirection-message}))

       (fact "Register a new service with no POST payload returns 400"
             (api-routes {:uri "/services" :request-method :post})
             => (contains {:status 400}))

       (fact "Register a new service with a proper payload return 201"
             ; Translates the JSON String to a clojure map. (This is done by compojure in "(wrap-json-body)").
             (api-routes {:uri "/services" :request-method :post :body (translate-from-json-to-map json-service)}) 
             => (contains {:status 201})
             (provided (momanager/register-service new-microservice)
                       => (translate-from-map-to-service (translate-from-json-to-map json-service)) :times 1))

       (fact "Get to /services returns a list of services."
             (api-routes {:uri "/services" :request-method :get})
             => (contains {:status 200 :body  (list new-microservice)})
             (provided (momanager/get-all-services) => (list new-microservice)))

       (fact "Get to /services/:serviceName returns the service with the given name."
             (api-routes {:uri (str "/services/" (:serviceName new-microservice)) :request-method :get})
             => (contains {:status 200 :body (generate-string new-microservice)})
             (provided (momanager/get-service-by-name "service1") => new-microservice))

       (fact "Put to /services/:serviceName with no body payload, returns 500."
             (api-routes {:uri (str "/services/" (:serviceName new-microservice)) 
                          :request-method :put}) => (contains {:status 500}))

       (fact "Put to /services/:serviceName returns the result of the update operation."
             (api-routes {:uri (str "/services/" (:serviceName new-microservice)) :request-method :put
                          :body (translate-from-json-to-map json-service)})
             => (contains {:status 200 :body "ok"})
             (provided (momanager/update-service-by-name "service1" new-microservice) => "ok"))

       (fact "Delete to /services/:serviceName returns the result of the delete operation."
             (api-routes {:uri (str "/services/" (:serviceName new-microservice)) :request-method :delete})
             => (contains {:status 200 :body "ok"})
             (provided (momanager/delete-service-by-name "service1") => "ok"))

       (fact "Get to a not valid route returns 404"
             (api-routes {:uri "/not-valid" :request-method :get})
             => (contains {:status 404})))
