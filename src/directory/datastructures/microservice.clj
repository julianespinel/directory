(ns directory.datastructures.microservice
  "Define the microservice data structure.")

(defrecord Microservice [serviceName host port protocol prefix])
