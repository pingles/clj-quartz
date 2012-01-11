(ns clj-quartz.scheduler
  (:import [org.quartz.impl StdSchedulerFactory]
           [org.quartz.spi JobFactory TriggerFiredBundle]
           [org.quartz Scheduler])
  (:use [clj-quartz.core :only (as-properties)]))

(defn create-scheduler
  "See defaults for examples."
  [config]
  (.getScheduler (doto (StdSchedulerFactory.)
                   (.initialize (as-properties config)))))

(defn start
  [^Scheduler scheduler]
  (.start scheduler))

(defn shutdown
  [^Scheduler scheduler]
  (.shutdown scheduler))

(defn metadata
  [^Scheduler scheduler]
  (let [data (.getMetaData scheduler)]
    {:job-store (.getJobStoreClass data)
     :scheduler (.getSchedulerClass data)
     :instance-id (.getSchedulerInstanceId data)
     :name (.getSchedulerName data)
     :summary (.getSummary data)
     :thread-pool (.getThreadPoolClass data)
     :thread-pool-size (.getThreadPoolSize data)
     :version (.getVersion data)
     :standby-mode (.isInStandbyMode data)
     :shutdown (.isShutdown data)
     :started (.isStarted data)}))

(defn job-factory
  []
  (proxy [JobFactory] []
    (newJob [^TriggerFiredBundle bundle ^Scheduler scheduler])))
