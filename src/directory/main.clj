(ns directory.main
  "The main file of the application."
  (:gen-class)
  (:use ring.middleware.json)
  (:require [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]
            [directory.services.microserviceApi :as microserviceApi]))

(def app
  (-> (handler/api microserviceApi/api-routes)
      (wrap-json-body)
      (wrap-json-response)))

(defn start-server []
  (jetty/run-jetty app {:port 4001 :join? false}))

(defn -main [& args] 
  (start-server))
