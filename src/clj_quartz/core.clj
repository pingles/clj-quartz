(ns clj-quartz.core
  (:import [java.util Properties]))

(defn as-properties
  [m]
  (let [p (Properties.)]
    (doseq [[k v] m]
      (.setProperty p (name k) (name v)))
    p))

