# splashed
Clojure wrapper for the Unsplash API
=======

Clojure wrapper for the [Unsplash API](https://unsplash.com/documentation)

## Setup

First you need to create a unsplash developer account and register an application, you can check [here](https://unsplash.com/documentation#creating-a-developer-account) for more information.

After you created your application you can get your access key [here](https://unsplash.com/oauth/applications/).

Add the following dependency to your project.clj file:

```
[org.parenthesin/splashed "0.1.0"]
```

## Usage

```
(ns your.app.namespace.core
  (:require [org.parenthesin.splashed.core :as splashed]))

;; Bellow are some samples of how use this wrapper

;; Get a single page from the list of all photos.
(splashed/photos-list {} {:access-key "YOUR-APP-ACCESS-KEY"})

;; Retrieve a single photo by id
(splashed/photos-by-id "tYnFSqELn54" {:access-key "YOUR-APP-ACCESS-KEY"}

;; Retrieve a single random photo, given optional filters.
(splashed/photos-random {} {:access-key "YOUR-APP-ACCESS-KEY"})

;; Retrieve total number of downloads, views and likes of a single photo by id, as well as the historical breakdown of these stats in a specific timeframe (default is 30 days).
(splashed/photos-statistics-by-id "tYnFSqELn54" {} {:access-key "YOUR-APP-ACCESS-KEY"})

;; To abide by the API guidelines, you need to trigger a GET request to this endpoint every time your application performs a download of a photo. 
(splashed/photos-download-by-id "tYnFSqELn54" {:access-key "YOUR-APP-ACCESS-KEY"})

;; Get a single page of five photos results for a query.
(splashed/search-photos {:query "corgi" :per-page 5 :page 1} {:access-key "YOUR-APP-ACCESS-KEY"})

;; Get a single page of collection results for a query.
(splashed/search-collections {:query "corgi"} {:access-key "YOUR-APP-ACCESS-KEY"})

;; Get a single page of user results for a query.
(splashed/search-users {:query "rafael delboni"} {:access-key "YOUR-APP-ACCESS-KEY"})

;; Get a single page from the list of all collections.
(splashed/collections-list {} {:access-key "YOUR-APP-ACCESS-KEY"})

;; Get a single page from the list of featured collections with only 2 items per page.
(splashed/collections-featured {:page 1 :per-page 2} {:access-key "YOUR-APP-ACCESS-KEY"})

;; Retrieve a single collection by the collection id.
(splashed/collections-by-id 1410291 {:access-key "YOUR-APP-ACCESS-KEY"})

;; Retrieve a collection’s photos by the collection id
(splashed/collections-photos-by-id 1410291 {:access-key "YOUR-APP-ACCESS-KEY"})

;; Retrieve a list of collections related to this one id.
(splashed/collections-related-by-id 1410291 {:access-key "YOUR-APP-ACCESS-KEY"})
```

## License

Copyright © 2019 Parenthesin

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
