(ns directory.main
  "The main file of the application."
  (:gen-class)
  (:use [ring.middleware.json])
  (:require [org.httpkit.server :as http-kit]
            [compojure.handler :as handler]
            [directory.services.microserviceApi :as microserviceApi]))

(def app
  (-> (handler/api microserviceApi/api-routes)
      (wrap-json-body)
      (wrap-json-response)))

(defn start-server []
  (http-kit/run-server app {:port 4001}))

(defn -main [& args] 
  (start-server))
