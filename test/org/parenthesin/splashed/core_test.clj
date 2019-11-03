(ns org.parenthesin.splashed.core-test
  (:require [clojure.test :as test]
            [org.parenthesin.splashed.core :as core]))

(test/deftest default-url-test
  (test/testing core/default-url
    (test/is (= core/default-url "https://api.unsplash.com/"))))
