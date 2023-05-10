(ns htmx-demo.routes
  (:require
   [htmx-demo.views :as views]
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :as route]))


(defroutes routes
  (GET  "/"            request (views/handler-index request))
  (POST "/htmx/create" request (views/handler-create request))
  (POST "/htmx/delete" request (views/handler-delete request))
  (POST "/htmx/update" request (views/handler-update request))
  (GET  "/htmx/edit"   request (views/handler-edit-form request))
  (POST "/htmx/edit"   request (views/handler-edit-ok request))
  (GET  "/htmx/list"   request (views/handler-list request)))
