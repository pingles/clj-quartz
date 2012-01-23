(ns clj-quartz.test.scheduler
  (:use [clojure.test]
        [clj-quartz.scheduler :only (add-job delete-job job-keys group-names)]
        [clj-quartz.job :only (create-job-detail)]
        [clj-quartz.test-utils :only (with-test-scheduler)] :reload))

(deftest jobs
  (with-test-scheduler scheduler
    (add-job scheduler (create-job-detail {:f (fn [_])
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
