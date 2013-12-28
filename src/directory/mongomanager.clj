(ns directory.mongomanager
  "This module is responsible of providing the functions needed to communicate with mongodb."
  (:use [clojure.string :only (split)])
  (:require [monger.core :as monger]
            [monger.collection :as mc] 
            [monger.json]))

(monger/connect! { :host "localhost" })
(monger/use-db! "directorydb")

(defn get-current-db-name
  "Returns the name of the database it is using now."
    [] (.getName (monger/current-db))) ; Call the getName() method from the underlying java mongo driver.

(defn change-default-db
  "Changes the default db: directorydb, for the database with the name given as argument."
  [db-name] (if-not (clojure.string/blank? db-name) (monger/use-db! db-name)))

(defn handle-write-result
  "Replaces the default mongodb write result for a more meaningful answer."
  [write-result] (if (not (= (:err write-result) nil)) "error" "ok"))

(defn register-service 
  "Register a new service into mongo." 
  [service] (mc/insert-and-return "services" service))

(defn get-all-services 
  "Returns all the services stored in mongo as clojure maps."
  [] (mc/find-maps "services"))

(defn get-service-by-name 
  "Return a service with the given name."
  [service-name] (mc/find-maps "services" { :service-name service-name }))

(defn update-service-by-name 
  "Replaces the service with the given name, for the second argument (a microservice)."
  [service-name service] (handle-write-result (mc/update "services" { :service-name service-name } service)))

(defn delete-service-by-name 
  "Delete the service with the given name."
  [service-name] (handle-write-result (mc/remove "services" { :service-name service-name })))
