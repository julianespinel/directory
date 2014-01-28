(defproject directory "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-json "0.2.0"]
                 [compojure "1.1.6"]
                 [com.novemberain/monger "1.7.0"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler directory.handler/app
         :stacktraces? true
         :auto-reload true
         :auto-refresh? true}
  :profiles
  {:dev {:plugins [[lein-midje "3.1.1"]]
         :dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]
                        [midje "1.6.0"]
                        [cheshire "5.2.0"]]}})
