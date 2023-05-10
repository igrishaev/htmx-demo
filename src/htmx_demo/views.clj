(ns htmx-demo.views
  (:require
   [htmx-demo.db :as db]
   [hiccup.core :as hiccup]))


(defn make-item-id [id]
  (format "item-%s" id))


(defn item-sort-button [field title]
  [:span
   [:a.sort
    {:hx-get (format "/htmx/list?sort=%s" (name field))
     :hx-target "#items"
     :hx-swap "innerHTML"}
    title]
   [:a.sort
    {:hx-get (format "/htmx/list?sort=%s&reverse=true" (name field))
     :hx-target "#items"
     :hx-swap "innerHTML"}
    "(reverse)"]])


(defn item-header []
  [:div.item.header
   [:div.fname (item-sort-button :fname "First Name")]
   [:div.lname (item-sort-button :lname "Last Name")]
   [:div.email (item-sort-button :email "Email")]
   [:div.age (item-sort-button :age "Age")]
   [:div.edit ""]
   [:div.delete ""]])


(defn item-delete-button [id]
  [:a.delete
   {:hx-post (format "/htmx/delete?id=%s" id)
    :hx-confirm "Are you sure you want to delete that item?"
    :hx-target (str "#" (make-item-id id))
    :hx-swap "delete"}
   "Delete"])


(defn item-row [item]
  (let [{:keys [id
                fname
                lname
                email
                age]}
        item]
    [:div.item {:id (make-item-id id)}
     [:div.fname fname]
     [:div.lname lname]
     [:div.email email]
     [:div.age age]
     [:div.edit [:a "Edit"]]
     [:div.delete (item-delete-button id)]]))


(defmacro response [& nodes]
  `{:status 200
    :headers {"content-type" "text/html"}
    :body (hiccup/html ~@nodes)})


(defn handler-edit-form [r]
  )


(defn handler-edit-ok [r]
  )


(defn handler-create [{:keys [params]}]
  (let [item (db/insert params)]
    (response (item-row item))))


(defn handler-delete [{:keys [params]}]
  (let [{:keys [id]}
        params]
    (db/del-by-id id)
    (response nil)))


(defn handler-update [r]
  )


(defn handler-edit [r]
  )


(defn handler-list [request]
  (let [sort-field
        (some-> request :params :sort keyword)

        reverse?
        (some-> request :params :reverse some?)

        items
        (cond->> (db/list-all)

          sort-field
          (sort-by sort-field)

          reverse?
          (reverse))]

    (response
     (for [item items]
       (item-row item)))))


(defn item-form []
  [:form
   {:hx-post "/htmx/create"
    :hx-target "#items"
    :hx-swap "beforeend"}

   [:div.form-item
    [:div.label
     [:label {:for :fname} "First Name"]]
    [:div
     [:input {:name :fname}]]]

   [:div.form-item
    [:div.label
     [:label {:for :lname} "Last Name"]]
    [:div
     [:input {:name :lname}]]]

   [:div.form-item
    [:div.label
     [:label {:for :email} "Email Address"]]
    [:div
     [:input {:name :email :type "email"}]]]

   [:div.form-item
    [:div.label
     [:label {:for :age} "Age"]]
    [:div
     [:input {:name "age" :type "number"}]]]

   [:div.form-item
    [:button {:type :submit}
     "Create"]]])


(defn handler-index [request]

  (let [items (db/list-all)]

    (response
     [:html {:lang "en"}
      [:head
       [:meta {:charset "utf-8"}]
       [:title "Hello"]
       [:link {:href "/css/style.css" :rel "stylesheet"}]
       [:script {:src "https://unpkg.com/htmx.org@1.8.5"
                 :crossorigin "anonymous"}]]
      [:body
       [:div#content
        [:div.items-wrapper
         [:div#items-header
          (item-header)]
         [:div#items
          (for [item items]
            (item-row item))]]
        [:div#item-form
         (item-form)]]]])))


(defn item-edit []
  )
