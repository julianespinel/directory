(ns app.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [defroutes GET]]))

(defn ok-status [] "200")
(defn not-found-error [] "404")

(defroutes app
  (GET "/" [] (ok-status))
  (GET "/user/:id" [id] (str "Hello user: " id))
  (GET "/unknown" [] (not-found-error)))

(run-jetty #'app {:port 3000})
