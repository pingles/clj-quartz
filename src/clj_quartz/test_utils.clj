(ns clj-quartz.test-utils
  (:use [clj-quartz.scheduler :only (create-scheduler shutdown start started?)]))

(def defaults {"org.quartz.scheduler.instanceName" "Testing"
               "org.quartz.threadPool.threadCount" "1"
               "org.quartz.jobStore.class" "org.quartz.simpl.RAMJobStore"})

(def ^:dynamic *scheduler* nil)

(defmacro with-test-scheduler
  [scheduler & body]
  `(let [~scheduler (create-scheduler defaults)]
     (try (do (start ~scheduler)
              ~@body)
          (finally (shutdown ~scheduler)))))
