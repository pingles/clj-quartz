(ns clj-quartz.test.triggers
  (:import [org.quartz CronTrigger]
           [org.quartz.impl.calendar BaseCalendar]
           [java.util Date])
  (:use [clojure.test]
        [clj-quartz.triggers] :reload))

(deftest creating-cron-trigger
  (is (instance? CronTrigger (cron-trigger "* * * * * ?")))
  (let [trigger (cron-trigger "* * * * * ?")]
    (is (= ["seconds: *"
            "minutes: *"
            "hours: *"
            "daysOfMonth: *"
            "months: *"
            "daysOfWeek: ?"
            "lastdayOfWeek: false"
            "nearestWeekday: false"
            "NthDayOfWeek: 0"
            "lastdayOfMonth: false"
            "years: *"]
           (clojure.string/split (.getExpressionSummary trigger) #"\n"))))
  (is (= nil
         (.getNextFireTime (cron-trigger "* * * * * ?"))))
  (let [trigger (cron-trigger "* * * * * ?")]
    (is (instance? Date
                   (.computeFirstFireTime trigger
                                          (BaseCalendar. ))))))