(ns clj-quartz.example.simple
  (:use [clj-quartz.scheduler :only (create-scheduler metadata)]))

(def defaults {"org.quartz.scheduler.instanceName" "Testing"
               "org.quartz.threadPool.threadCount" "1"
               "org.quartz.jobStore.class" "org.quartz.simpl.RAMJobStore"})

(def scheduler (create-scheduler defaults))