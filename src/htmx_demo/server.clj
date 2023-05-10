(ns htmx-demo.server
  (:require
   [htmx-demo.app :as app]
   [mount.core :as mount]
   [org.httpkit.server :as server]))


(mount/defstate ^{:on-reload :noop} server
  :start
  (server/run-server app/app
                     {:port 18088
                      :thread 4
                      :legacy-return-value? false})

  :stop
  (server/server-stop! server))
