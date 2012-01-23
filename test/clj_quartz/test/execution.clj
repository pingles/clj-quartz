(ns clj-quartz.test.execution
  (:import [java.util.concurrent CountDownLatch]
           [org.quartz.impl.triggers SimpleTriggerImpl])
  (:use [clojure.test]
        [clj-quartz.job :only (create-job-detail)]
        [clj-quartz.scheduler :only (started? schedule)]
        [clj-quartz.test-utils :only (with-test-scheduler)] :reload))

(defn trigger-now
  "m is the job detail map"
  [{:keys [name group]}]
  (doto (SimpleTriggerImpl. )
    (.setName "Test Trigger")
    (.setGroup group)
    (.setJobName name)
    (.setJobGroup group)
    (.setRepeatCount 0)
    (.setRepeatInterval 1000)
    (.setStartTime (java.util.Date.))))

(deftest test-scheduler-is-started-in-test
  (with-test-scheduler scheduler
    (is (true? (started? scheduler)))))

(deftest test-job-execution-in-scheduler
  (let [latch (CountDownLatch. 1)
        result (atom [])
        execute-fn (fn [x] (do (.countDown latch)
                              (swap! result conj x)))]
    (with-test-scheduler scheduler
      (schedule scheduler
                (create-job-detail {:f execute-fn
                                    :name "unique name"
                                    :group "group name"
                                    :data {:a "Hello"}})
                (trigger-now {:name "unique name" :group "group name"}))
      (.await latch))
    (is (= 1
           (count @result)))))