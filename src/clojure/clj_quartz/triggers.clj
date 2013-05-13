(ns clj-quartz.triggers
  (:import [org.quartz CronExpression CronScheduleBuilder TriggerBuilder]))

(defn cron-trigger
  "Builds a trigger using the cron expression. See http://quartz-scheduler.org/api/2.1.7/org/quartz/CronExpression.html for more information.
   Example: (every second of every day) \"* * * * * ?\""
  [cron-expression]
  (let [schedule (CronScheduleBuilder/cronSchedule (CronExpression. cron-expression))
        trigger-builder (TriggerBuilder/newTrigger)]
    (.withSchedule trigger-builder schedule)
    (.build trigger-builder)))