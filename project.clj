(defproject clj-quartz/clj-quartz "0.0.1-SNAPSHOT" 
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 [org.quartz-scheduler/quartz "2.1.2"]]
  :source-paths ["src/clojure"]
  :profiles {:dev {:dependencies [[org.slf4j/slf4j-nop "1.6.1"]]}}
  :java-source-paths ["src/java"]
  :min-lein-version "2.0.0"
  :description "")
