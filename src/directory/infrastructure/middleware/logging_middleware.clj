(ns directory.infrastructure.middleware.logging-middleware)

(defn log-request [app request]
  (let [http-verb (name (:request-method request)) uri (:uri request)]
    (println "http-verb:" http-verb "-> uri:" uri)
    (app request)))

(defn simple-logggin-middleware [app]
  (fn [request] (log-request app request)))
