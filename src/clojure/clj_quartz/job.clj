(ns clj-quartz.job
  (:import [org.quartz.spi JobFactory TriggerFiredBundle]
           [org.quartz.impl JobDetailImpl]
           [org.quartz Scheduler JobDetail JobKey]))


;; A job is just a map with a name and a fn to
;; execute when the job runs

;; {:name "Test Job" :fn (fn [_] (println "Hello!"))}

;; the function will receive a map of all the job
;; information from the TriggerFiredBundle


;; Jobs are scheduled with a JobDetail instance
;; this is a map of some job data, the job itself
;; and identity:

;; {:name "unique name" :group "reporting"
;;  :data {:first-val "Hello, world!"}
;;  :job {:name "Test Job"
;;        :fn (fn [_])}}

(defn create-job-detail
  "Creates the Quartz JobDetail object."
  [m]
  (proxy [JobDetail] []
    (getKey [] (JobKey. (:name m) (:group m)))
    (getClass [] )))

(defn- job-factory
  []
  (proxy [JobFactory] []
    (newJob [^TriggerFiredBundle bundle ^Scheduler scheduler])))