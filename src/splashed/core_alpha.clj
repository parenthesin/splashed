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
(defn- users
  [sub-path username options]
  (req! {:method :get :path (str "users/" username sub-path) :options options}))

(defn users-profile
  [username options]
  (users nil username options))

(defn users-portfolio
  [username options]
  (users "/portfolio" username options))

(defn users-photos
  [username options]
  (users "/photos" username options))

(defn users-likes
  [username options]
  (users "/likes" username options))

(defn users-collections
  [username options]
  (users "/collections" username options))

(defn users-statistics
  [username options]
  (users "/statistics" username options))

;; Photos
(defn photos
  [{:keys [page per-page order-by] :as params} options]
  (req! {:method :get :path "photos" :params params :options options})) 

;; Search

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
