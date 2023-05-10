(ns htmx-demo.routes
  (:require
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :as route]))


(defroutes routes
  (GET "/" request {:status 200 :body "hello"}))
