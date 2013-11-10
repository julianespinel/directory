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
