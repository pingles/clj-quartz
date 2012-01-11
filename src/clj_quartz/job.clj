(ns clj-quartz.job
  (:import [org.quartz.spi JobFactory TriggerFiredBundle]
           [org.quartz Scheduler]))

(defn- job-factory
  []
  (proxy [JobFactory] []
    (newJob [^TriggerFiredBundle bundle ^Scheduler scheduler])))