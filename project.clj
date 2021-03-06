(defproject anansi "0.0.1"
  :description "a reference server implementation of the ceptr platform"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 ]
  :dev-dependencies [[swank-clojure "1.2.1"]
                      [autodoc "0.7.1" :exclusions [org.clojure/clojure-contrib 
                                                    org.clojure/clojure]]
;;                      [org.clojars.mjul/lein-cuke "1.1.0"]
                     ]
  :autodoc {:name "Anansi", :page-title "Anansi Docs"
            :description ""
            :copyright "Documentation: CC; Code: Eclipse Public License"}
  :main anansi.core)
