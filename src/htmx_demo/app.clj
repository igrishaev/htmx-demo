(ns htmx-demo.app
  (:require
   [htmx-demo.routes :as routes]
   [ring.middleware.resource :refer [wrap-resource]]
   [ring.middleware.json :refer [wrap-json-body
                                 wrap-json-response]]
   [ring.middleware.keyword-params :refer [wrap-keyword-params]]
   [ring.middleware.params :refer [wrap-params]]))


(def app
  (-> routes/routes
      (wrap-keyword-params)
      (wrap-params)
      (wrap-resource "static")
      (wrap-json-body {:keywords? true})
      (wrap-json-response)))
