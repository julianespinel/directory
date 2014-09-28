(defproject directory "0.1.0-SNAPSHOT"
  :description "Directory is a system that allow us to register new microservices of our architecture."
  :url "https://github.com/julianespinel/directory"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-json "0.2.0"]
                 [http-kit "2.1.18"]
                 [compojure "1.1.6"]
                 [com.novemberain/monger "1.7.0"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler directory.main/app
         :stacktraces? true
         :auto-reload true
         :auto-refresh? true}
  :profiles
  {:dev {:plugins [[lein-midje "3.1.3"]]
         :dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]
                        [midje "1.6.2"]
                        [cheshire "5.2.0"]]}}
  :main directory.main)
