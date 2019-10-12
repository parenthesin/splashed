(ns splashed.core-alpha
  (:require [camel-snake-kebab.core :as csk]
            [clj-http.client :as client]
            [camel-snake-kebab.extras :as cske]
            [clojure.data.json :as json]))

;; Configuration/bindings

(def default-url "https://api.unsplash.com/")

;; Request fns

(defn with-authentication
  [bearer skip-auth options request]
  (if skip-auth
    request
    (merge-with into request {:headers {"Authorization"
                                        (if bearer
                                          (str "Bearer " bearer)
                                          (->> options
                                               :access-key
                                               (str "Client-ID ")))}})))

(defn parse-resp
  [{:keys [headers status body]}]
  (json/read-str body :key-fn keyword))

(defmulti req! :method)

(defmethod req! :get
  [{:keys [path base-url params skip-auth bearer options]}]
  (->>
    {:query-params (cske/transform-keys
                     csk/->snake_case_keyword
                     params)}
    (with-authentication bearer skip-auth options)
    (client/get (str (or base-url default-url) path))
    parse-resp))

(defmethod req! :post
  [{:keys [path base-url body skip-auth bearer options]}]
  (->>
    {:body (-> (cske/transform-keys
                 csk/->snake_case_keyword
                 body)
               json/write-str)
     :content-type :json}
    (with-authentication bearer skip-auth options)
    (client/post (str (or base-url default-url) path))
    parse-resp))

;; Authorization

;; Oauth - authenticate a unsplash user
(defn token
  [auth-code options]
  (req! {:method :post
         :base-url "https://unsplash.com/"
         :path "oauth/token"
         :options options
         :content-type :json
         :body {:client-id (:client-id options)
                :client-secret (:client-secret options)
                :redirect-uri (:redirect-uri options)
                :code auth-code
                :grant-type "authorization_code"}
         :skip-auth false}))

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
(defn- photos
  [sub-path params options]
  (req! {:method :get :path (str "photos/" sub-path) :params params :options options}))

(defn photos-list
  [{:keys [page per-page order-by] :as params} options]
  (photos nil params options))

(defn photos-curated
  [{:keys [page per-page order-by] :as params} options]
  (photos "curated" params options))

(defn photos-by-id
  [id options]
  (photos id nil options))

(defn photos-random
  [{:keys [collections featured username query orientation count] :as params} options]
  (photos "random" params options))

(defn photos-statistics-by-id
  [id {:keys [resolution quantity] :as params} options]
  (photos (str id "/statistics") params options))

(defn photos-download-by-id
  [id options]
  (photos (str id "/download") nil options))

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
