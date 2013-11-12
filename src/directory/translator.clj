(ns directory.translator
  (:require [directory.microservice])
  (:import [directory.microservice Microservice]))

(defn get-microservice-from-map [generic-map]
  (Microservice. 
    (get generic-map "service-name")
    (get generic-map "host")
    (get generic-map "port")
    (get generic-map "protocol")
    (get generic-map "prefix")))
