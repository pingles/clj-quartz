(defproject clj-quartz/clj-quartz "0.0.3" 
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.quartz-scheduler/quartz "2.1.7"]]
  :source-paths ["src/clojure"]
  :profiles {:dev {:dependencies [[org.slf4j/slf4j-simple "1.6.1"]]}
             :test {:dependencies [[org.slf4j/slf4j-nop "1.6.1"]]}}
  :java-source-paths ["src/java"]
  :min-lein-version "2.0.0"
  :description "")
