(ns clj-quartz.test.job
  (:import [org.quartz JobDetail])
  (:use [clojure.test]
        [clj-quartz.job] :reload))

(deftest creating-job-details
  (is (instance? JobDetail
                 (create-job-detail {:name "Job Name"
                                     :group "Group Name"
                                     :f (fn [_])}))))
