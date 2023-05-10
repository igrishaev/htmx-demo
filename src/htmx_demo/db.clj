(ns htmx-demo.db
  (:refer-clojure :exclude [update]))


(def ^:private -storage
  (atom {}))


(defn get-by-id [id]
  (-> -storage deref (get id)))


(defn list-all []
  (-> -storage deref vals))


(defn del-by-id [id]
  (swap! -storage dissoc id))


(defn insert [entry]
  (let [id (str (random-uuid))
        item (assoc entry :id id)]
    (swap! -storage assoc id item)
    item))


(defn udpate [id entry]
  (swap! -storage clojure.core/update id merge entry))


(defn reset []
  (swap! -storage empty))
