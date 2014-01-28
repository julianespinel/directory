(ns directory.test.mongomanager

  (:use clojure.test
        midje.sweet)
  (:require [monger.core :as monger]
            [directory.mongomanager :as momanager])
  (:import [directory.microservice Microservice]))

(def basic-microservice-map {:serviceName "service1", :host "host1", :port "port1", :protocol "protocol1", :prefix "prefix1"})
(def updated-microservice-map {:serviceName "service1", :host "localhost", :port "3000", :protocol "http", :prefix "stats"})

(def new-microservice (Microservice.
                        (:serviceName basic-microservice-map)
                        (:host basic-microservice-map)
                        (:port basic-microservice-map)
                        (:protocol basic-microservice-map)
                        (:prefix basic-microservice-map)))

(def updated-microservice (Microservice.
                        (:serviceName updated-microservice-map)
                        (:host updated-microservice-map)
                        (:port updated-microservice-map)
                        (:protocol updated-microservice-map)
                        (:prefix updated-microservice-map)))

(def testdb-name "testdb")

(defn set-testdb-as-current-db [] (momanager/change-default-db testdb-name))
(defn drop-testdb [] (monger/use-db! testdb-name)(monger.collection/remove "services"))

; Execute what it is inside the do statement before every fact. Like a setup.
(with-state-changes [(before :facts (do (drop-testdb) (set-testdb-as-current-db)))]

  (facts "About the mongomanager functions"

         (fact "get-current-db-name returns the name of the database the module is using rigth now."
               (momanager/get-current-db-name) => testdb-name)

         (facts "change-default-db changes the default db and now uses a diferent db."

                (fact "Changes the current database if the new db name is not nil."
                      (momanager/change-default-db "test")
                      (momanager/get-current-db-name) => "test")
                (fact "Does not change the current database if the new db name is nil or an empty string"
                      (momanager/change-default-db "directorydb")
                      (momanager/change-default-db "")
                      (momanager/get-current-db-name) => "directorydb"))

         (fact "register-service registers a new microservice into the database."
               ; Add the _id field that mongo generates.
               (momanager/register-service new-microservice) => (contains (assoc basic-microservice-map :_id anything)))

         (fact "get-all-services returns all the services registered into the database."
               (momanager/register-service new-microservice)
               (momanager/register-service new-microservice)
               (momanager/register-service new-microservice)
               (count (momanager/get-all-services)) => 3)
         
         (fact "get-service-by-name returns all the services that match the given name."
               (momanager/register-service new-microservice)
               (momanager/get-service-by-name (:serviceName new-microservice)) => 
               (contains (assoc basic-microservice-map :_id anything)))
         
         (fact "handle-write-result returns ok if the given map contains an error message equal to nil, 
              otherwise it returns error."
               (momanager/handle-write-result {:err "error message"}) => "error"
               (momanager/handle-write-result {:err nil}) => "ok")
         
         (fact "update-service-by-name updates the service with a given name."
               (momanager/register-service new-microservice)
               (momanager/update-service-by-name (:serviceName new-microservice) updated-microservice) => "ok"
               (momanager/get-service-by-name (:serviceName updated-microservice)) => 
               (contains (assoc updated-microservice-map :_id anything)))
         
         (fact "delete-service-by-name deletes a service with a given name."
               (momanager/register-service new-microservice)
               (count (momanager/get-all-services)) => 1
               (momanager/delete-service-by-name (:serviceName new-microservice))
               (count (momanager/get-all-services)) => 0)))
