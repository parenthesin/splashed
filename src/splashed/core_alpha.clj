(ns splashed.core-alpha
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

;; Configuration/bindings

(def default-url "https://api.unsplash.com/")

;; Request fns

(defn parse-resp
  [{:keys [headers status body]}]
  (json/read-str body :key-fn keyword))

(defmulti req! :method)

(defmethod req! :get
  [{:keys [path base-url params]}]
  (-> (client/get (str (or base-url default-url) path) 
                 {:query-params params
                  :headers {"Authorization" "Client-ID YOUR_ACCESS_KEY"}})
      (parse-resp)))   

;; Authorization

;; Oauth - authenticate a unsplash user
(defn token
  [client-id]
  (req! {:method :post 
         :base-url "https://unsplash.com/" 
         :path "oauth/token"
         :client-id client-id}))

;; Users

;; Photos
(defn photos
  [{:keys [page per-page order-by] :as params}]
  (req! {:method :get :path "photos" :params params})) 
 

;; Search

;; Collections

;; Stats

;; Tests

;(photos {})

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
