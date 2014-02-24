(ns directory.infrastructure.translator
  "This module provides functions to translate regular maps to well defined data structures."
  (:require [directory.datastructures.microservice])
  (:import [directory.datastructures.microservice Microservice]))

(defn get-microservice-from-map 
  "Gets a string map and return a Microservice. It should be a string map, not a key map." 
  [string-map] (Microservice. 
                  (get string-map "serviceName")
                  (get string-map "host")
                  (get string-map "port")
                  (get string-map "protocol")
                  (get string-map "prefix")))
