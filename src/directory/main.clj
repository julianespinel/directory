(ns directory.main
  "The main file of the application."
  (:use ring.middleware.json)
  (:require [directory.services.microserviceApi :as microserviceApi]
            [compojure.handler :as handler]))

(def app
  (-> (handler/api microserviceApi/api-routes)
      (wrap-json-body)
      (wrap-json-response)))
