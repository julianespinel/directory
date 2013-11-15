(ns directory.microservice
  "Define the microservice data structure.")

(defrecord Microservice [service-name host port protocol prefix])
