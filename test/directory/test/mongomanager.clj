(ns directory.test.mongomanager

  (:use clojure.test
        midje.sweet)
  (:require [directory.mongomanager :as momanager]))

(facts "About the mongomanager functions"

       (fact "get-current-db-name returns the name of the database the module is using rigth now."
             (momanager/get-current-db-name) => "directorydb")
       
       (facts "change-default-db Changes the default db and now uses a diferent db."

              (fact "Changes the current database if the new db name is not nil."
                    (momanager/change-default-db "test")
                    (momanager/get-current-db-name) => "test")
              (fact "Does not change the current database if the new db name is nil or an empty string"
                    (momanager/change-default-db "testdb")
                    (momanager/change-default-db "")
                    (momanager/get-current-db-name) => "testdb")))
