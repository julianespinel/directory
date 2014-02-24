(ns directory.test.infrastructure.translator

  (:use clojure.test
        ring.mock.request
        directory.datastructures.microservice
        midje.sweet)
  (:require [directory.infrastructure.translator :as translator])
  (:import [directory.datastructures.microservice Microservice]))

(def string-map {"serviceName" "service1", "host" "host1", "port" "port1", "protocol" "protocol1", "prefix" "prefix1"})

(facts "About the translator functions"
       
       (fact "get-microservice-from-map translates a generic map into a Microservice"
             (translator/get-microservice-from-map string-map)
             => (Microservice. "service1" "host1" "port1" "protocol1" "prefix1")))
