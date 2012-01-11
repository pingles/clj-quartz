(ns clj-quartz.test.execution
  (:import [java.util.concurrent CountDownLatch]
           [org.quartz.impl.triggers SimpleTriggerImpl])
  (:use [clojure.test]
        [clj-quartz.job :only (create-job-detail)]
        [clj-quartz.scheduler :only (started? schedule)]
        [clj-quartz.test-utils :only (with-test-scheduler)] :reload))

(def run-immediately-trigger (SimpleTriggerImpl. ))

(deftest test-scheduler-is-started-in-test
  (with-test-scheduler scheduler
    (is (true? (started? scheduler)))))

(defn conj-data-job
  [atom]
  {:name "Add Data Job"
   :fn (fn [x]
         (swap! atom conj x))})

(deftest test-job-execution-in-scheduler
  (let [latch (CountDownLatch. 1)
        result (atom [])
        execute-fn (fn [_] (swap! conj "Hello"))]
    (with-test-scheduler scheduler
      (schedule scheduler (create-job-detail {:job conj-data-job
                                              :name "unique name"
                                              :group "group name"}))
      (.await latch))
    (is (= 1
           (count @result)))))