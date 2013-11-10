(ns directory.handler
  (:use compojure.core
        ring.middleware.json)
  (:require [ring.util.response :as resp]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [directory.mongomanager :as momanager]))

(defn ok-status [] "200")
(defn get-service [id] (str "Hello service: " id))
(defn not-found-error [] "404")

(defn register-service [service]
  (momanager/register-service service))

(defroutes app-routes
  (GET "/" [] (resp/redirect "/redirect-url"))
  (GET "/redirect-url" [] "Hello you have been redirected.")

  (GET "/services" [] (momanager/get-all-services))
  (GET "/services/:id" [id] (get-service id))
  (POST "/services" { body :body } (register-service body))
  (GET "/unknown" [] (not-found-error))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (wrap-json-body)
      (wrap-json-response)))
