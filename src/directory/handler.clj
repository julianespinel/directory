(ns directory.handler
  (:use compojure.core
        ring.middleware.json)
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
  (POST "/users" { body :body } (println body))
  (GET "/unknown" [] (not-found-error))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (wrap-json-body)
      (wrap-json-response)))
