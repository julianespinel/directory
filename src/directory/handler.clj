(ns directory.handler
  "Http server handler. Define the routes and the functions that each route have to execute."
  (:use compojure.core
        ring.middleware.json)
  (:require [ring.util.response :as resp]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [directory.mongomanager :as momanager]
            [directory.translator :as translator]
            [cheshire.core :refer :all]))

(def ok-status "200")
(def not-found-error "404")

(defn register-service
  "Register a new service"
  [generic-map] (let [micro-service (translator/get-microservice-from-map generic-map) 
                      create-result (momanager/register-service micro-service)] 
                  (if (not (empty? create-result)) { :status 201 } { :status 500 })))

(defn body-is-null
  "Checks that the parameter is not a null or empty map"
  [body] (empty? body))

(defroutes api-routes
  "Define the application routes"
  (GET "/" [] (resp/resource-response "index.html" {:root "public"}))
  (GET "/redirect" [] (resp/redirect "/redirect-url"))
  (GET "/redirect-url" [] "Hello you have been redirected.")
  
  (GET "/services" [] (momanager/get-all-services))
  ; Http status code 400: bad request
  (POST "/services" { body :body } (if (not (body-is-null body)) (register-service body) { :status 400 }))
  (GET "/services/:serviceName" [serviceName] (generate-string (momanager/get-service-by-name serviceName)))

  (PUT "/services/:serviceName" { params :params, body :body }
       (if (not (body-is-null body))
       (let [serviceName (:serviceName params) service (translator/get-microservice-from-map body)] 
         (momanager/update-service-by-name serviceName service))
       { :status 500 }))

  (DELETE "/services/:serviceName" [serviceName] 
          (momanager/delete-service-by-name serviceName))
  
  (route/resources "/")
  (route/not-found not-found-error))

(def app
  (-> (handler/api api-routes)
      (wrap-json-body)
      (wrap-json-response)))
