(defproject directory "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-json "0.2.0"]
                 [compojure "1.1.6"]
                 [com.novemberain/monger "1.6.0"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler directory.handler/app
         :auto-reload? true}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
