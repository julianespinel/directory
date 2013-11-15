(ns directory.translator
  "This module provides functions to translate regular maps to well defined data structures."
  (:require [directory.microservice])
  (:import [directory.microservice Microservice]))

(defn get-microservice-from-map 
  "Gets a regular map and return a Microservice." 
  [generic-map] (Microservice. 
                  (get generic-map "service-name")
                  (get generic-map "host")
                  (get generic-map "port")
                  (get generic-map "protocol")
                  (get generic-map "prefix")))
