(ns app.core
  (:require [liberator.core :refer [resource defresource]]
            [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [defroutes ANY]]))

(defn ok-status [] "200")

(defroutes app
  (ANY "/" [] (ok-status)))

(run-jetty #'app {:port 3000})
