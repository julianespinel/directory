(ns directory.handler
  "Http server handler. Define the routes and the functions that each route have to execute."
  (:use compojure.core
        ring.middleware.json)
  (:require [ring.util.response :as resp]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [directory.mongomanager :as momanager]
            [directory.translator :as translator]))

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

(defroutes app-routes
  "Define the application routes"
  (GET "/" [] (resp/redirect "/redirect-url"))
  (GET "/redirect-url" [] "Hello you have been redirected.")
  
  ; Http status code 400: bad request
  (POST "/services" { body :body } (if (not (body-is-null body)) (register-service body) { :status 400 }))
  (GET "/services" [] (momanager/get-all-services))
  (GET "/services/:service-name" [service-name] (momanager/get-service-by-name service-name))

  (PUT "/services/:service-name" { params :params, body :body } 
       (let [service-name (:service-name params) service (translator/get-microservice-from-map body)] 
         (momanager/update-service-by-name service-name service)))

  (DELETE "/services/:service-name" [service-name] 
          (momanager/delete-service-by-name service-name))

  (route/resources "/")
  (route/not-found not-found-error))

(def app
  (-> (handler/api app-routes)
      (wrap-json-body)
      (wrap-json-response)))
