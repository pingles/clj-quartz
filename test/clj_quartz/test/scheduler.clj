(ns clj-quartz.test.scheduler
  (:import [java.util.concurrent CountDownLatch]
           [org.quartz JobKey SimpleTrigger])
  (:use [clojure.test]
        [clj-quartz.scheduler :only (add-job delete-job job-keys group-names trigger triggers)]
        [clj-quartz.job :only (create-job-detail)]
        [clj-quartz.test-utils :only (with-test-scheduler)] :reload))

(deftest jobs
  (with-test-scheduler scheduler
    (add-job scheduler (create-job-detail {:f (fn [_ _])
                                           :name "name"
                                           :group "group"}))
    (is (= 1 (count (group-names scheduler))))
    (is (= '("group") (group-names scheduler)))
    (let [job (first (job-keys scheduler "group"))]
      (is (= "group" (:group job)))
      (is (= "name" (:name job))))
    (delete-job scheduler {:name "name"
                           :group "group"})
    (is (= 0 (count (group-names scheduler))))
    (is (= 0 (count (job-keys scheduler "group"))))))

(deftest list-executing-jobs
  (let [latch (CountDownLatch. 1)
        result (atom [])
        execute-fn (fn [x _] (do (.countDown latch)
                                (swap! result conj x)))]
    (with-test-scheduler scheduler
      (add-job scheduler (create-job-detail {:f execute-fn
                                             :name "name"
                                             :group "group"}))
      (trigger scheduler (JobKey. "name" "group"))
      (.await latch))
    (is (= 1
           (count @result)))))

(deftest listing-triggers
  (with-test-scheduler scheduler
    (add-job scheduler (create-job-detail {:f identity
                                           :name "name"
                                           :group "group"}))
    (trigger scheduler (JobKey. "name" "group"))
    (let [ts (triggers scheduler (JobKey. "name" "group"))]
      (is (= 1 (count ts)))
      (is (instance? SimpleTrigger (first ts))))))
