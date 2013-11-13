(ns directory.mongomanager
  (:use [clojure.string :only (split)])
  (:require [monger.core :as monger]
            [monger.collection :as mc] 
            [monger.json]))

(monger/connect! { :host "localhost" })
(monger/set-db! (monger/get-db "directorydb"))

(defn register-service [service]
    (mc/insert-and-return "services" service))

(defn get-all-services []
  (mc/find-maps "services"))

(defn get-service-by-name [service-name]
  (mc/find-maps "services" { :service-name service-name }))

(defn update-service-by-name [service-name service]
  (mc/update "services" { :service-name service-name } service :upsert false))
