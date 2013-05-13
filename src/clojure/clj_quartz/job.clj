(ns clj-quartz.job
  (:import [org.quartz.spi JobFactory TriggerFiredBundle]
           [org.quartz.impl JobDetailImpl]
           [org.quartz Scheduler JobBuilder JobDetail JobKey JobDataMap]
           [com.clojurista.cljquartz ClojureFnJob]))


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

(defn- create-fn-data
  "Creates params suitable for serialising Clojure jobs
   in Quartz's JobDataMap to integrate with the ClojureFnJob."
  [f]
  {:fn f})

(defn- create-job-data-map
  [m]
  (let [jobdata (JobDataMap. )]
    (doseq [[n v] m]
      (.put jobdata (str n) v))))

(defn create-job-detail
  "Creates the Quartz JobDetail object."
  [{:keys [name group data f]}]
  (let [fn-data (create-fn-data f)
        job-detail (-> (JobBuilder/newJob)
                       (.ofType ClojureFnJob)
                       (.storeDurably)
                       (.withIdentity name group)
                       (.build))]
    (doseq [[n v] (merge data fn-data)]
      (.put (.getJobDataMap job-detail) (str n) v))
    job-detail))

(defn- job-factory
  []
  (proxy [JobFactory] []
    (newJob [^TriggerFiredBundle bundle ^Scheduler scheduler])))