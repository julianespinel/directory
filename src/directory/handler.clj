(ns directory.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [directory.mongomanager :as momanager]))

(defn ok-status [] "200")
(defn get-user-message [id] (str "Hello user: " id))
(defn not-found-error [] "404")

(defn register-user [user]
  (momanager/register-user user))

(defroutes app-routes
  (GET "/" [] "Hello World")
  
  (GET "/" [] (ok-status))
  (GET "/user/:id" [id] (get-user-message id))
  (POST "/user" request (println request))
  (GET "/unknown" [] (not-found-error))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
