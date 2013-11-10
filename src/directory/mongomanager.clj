(ns directory.mongomanager
  (:require [monger.core :as monger]
            [monger.collection :as mc]))

(monger/connect! { :host "localhost" })
(monger/set-db! (monger/get-db "directorydb"))

(defn register-service [user]
  (mc/insert-and-return "services" user))

(defn get-all-services []
  (println (mc/find-maps "services")))
