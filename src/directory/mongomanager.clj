(ns directory.mongomanager
  (:require [monger.core :as monger]
            [monger.collection :as mc]))

(monger/connect! { :host "localhost" })
(monger/set-db! (monger/get-db "directorydb"))

(defn register-user [user]
  (println "*********** hello from clojure: " user)
  (mc/insert "services" user))
