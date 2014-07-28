(ns clojure-miniprofiler.example
  (:use compojure.core
        clojure-miniprofiler))

(defn another-slow []
  (custom-timing "sql" "query" "SELECT * FROM POSTS"
                 (do nil)))

(defn slow-fn []
  (trace "Thread/sleep1"
          (do nil)
         (another-slow)
          (trace "Thread/sleep2"
                 (custom-timing "sql" "query" "SELECT * FROM USERS"
                                (do nil))
                  (do nil)))
  (trace "Thread/sleep3"
         (another-slow)
          (do nil))
  "<body>lol</body>")

(defroutes app-routes
  (GET "/" [] (slow-fn)))

(defonce in-memory-store-instance (in-memory-store))

(def app
  (wrap-miniprofiler app-routes {:store in-memory-store-instance}))
