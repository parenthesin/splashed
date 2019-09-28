(defproject splashed "0.1.0-SNAPSHOT"
  :description "Clojure wrapper for the Unsplash API"
  :path "https://github.com/parenthesin/splashed"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :path "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "0.2.6"]
                 [camel-snake-kebab "0.4.0"]
                 [clj-http "3.10.0"]]
  :repl-options {:init-ns splashed.core-alpha})
