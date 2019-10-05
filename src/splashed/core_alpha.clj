(ns splashed.core-alpha
  (:require [camel-snake-kebab.core :as csk]
            [clj-http.client :as client]
            [camel-snake-kebab.extras :as cske]
            [clojure.data.json :as json]))

;; Configuration/bindings

(def default-url "https://api.unsplash.com/")

;; Request fns

(defn parse-resp
  [{:keys [headers status body]}]
  (json/read-str body :key-fn keyword))

(defmulti req! :method)

(defmethod req! :get
  [{:keys [path base-url params options]}]
  (-> (client/get (str (or base-url default-url) path) 
                  {:query-params (cske/transform-keys
                                   csk/->snake_case_keyword
                                   params)
                   :headers {"Authorization" (->> options
                                                  :access-key
                                                  (str "Client-ID "))}})
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
  [{:keys [page per-page order-by] :as params} options]
  (req! {:method :get :path "photos" :params params :options options})) 

;; Search
(defn- search
  [sub-path params options]
  (req! {:method :get :path (str "search/" sub-path) :params params :options options}))

(defn search-photos
  [{:keys [query page per-page collections orientation] :as params} options]
  (search "photos" params options))

(defn search-collections
  [{:keys [query page per-page] :as params} options]
  (search "collections" params options))

(defn search-users
  [{:keys [query page per-page] :as params} options]
  (search "users" params options))

;; Collections

;; Stats
(defn- stats
  [sub-path options]
  (req! {:method :get :path (str "stats/" sub-path) :options options}))

(defn stats-total
  [options]
  (stats "total" options))

(defn stats-month
  [options]
  (stats "month" options))

;; Tests

;(photos {})

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
