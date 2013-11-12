(ns directory.handler
  (:use compojure.core
        ring.middleware.json)
  (:require [ring.util.response :as resp]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [directory.mongomanager :as momanager]
            [directory.translator :as translator]))

(defn ok-status [] "200")
(defn not-found-error [] "404")

(defn register-service [service]
  (momanager/register-service service))

(defroutes app-routes
  (GET "/" [] (resp/redirect "/redirect-url"))
  (GET "/redirect-url" [] "Hello you have been redirected.")
  
  (POST "/services" { body :body } (register-service (translator/get-microservice-from-map body)))
  (GET "/services" [] (momanager/get-all-services))
  (GET "/services/:service-name" [service-name] (momanager/get-service-by-name service-name))
  
  (GET "/unknown" [] (not-found-error))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (wrap-json-body)
      (wrap-json-response)))
