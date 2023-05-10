(ns htmx-demo.core
  (:gen-class)
  (:require
   htmx-demo.server
   [mount.core :as mount]))


(defn start []
  (mount/start))


(defn stop []
  (mount/stop))


(defn -main
  [& args]
  (start))
