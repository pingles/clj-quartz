(ns clj-quartz.test.execution
  (:use [clojure.test]
        [clj-quartz.scheduler :only (started?)]
        [clj-quartz.test-utils :only (with-test-scheduler)]))

(deftest test-scheduler-is-started-in-test
  (with-test-scheduler scheduler
    (is (true? (started? scheduler)))))
