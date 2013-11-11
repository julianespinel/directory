(ns directory.mongomanager
  (:use [clojure.string :only (split)])
  (:require [monger.core :as monger]
            [monger.collection :as mc]
            [directory.microservice]
            [monger.json])
  (:import [directory.microservice Microservice]))


(monger/connect! { :host "localhost" })
(monger/set-db! (monger/get-db "directorydb"))

(defn get-microservice-from-map [generic-map]
  (Microservice. 
    (get generic-map "name")
    (get generic-map "host")
    (get generic-map "port")
    (get generic-map "protocol")
    (get generic-map "prefix")))

(defn register-service [service]
  (let [microservice (get-microservice-from-map service)] 
    (mc/insert-and-return "services" microservice)))

(defn get-all-services []
  (mc/find-maps "services"))
