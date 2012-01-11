# clj-quartz

A Clojure library for scheduling and running jobs using the [Quartz Scheduler](http://quartz-scheduler.org/).

## Usage

You can see more information in the [example](https://github.com/pingles/clj-quartz/blob/master/src/clj_quartz/example/simple.clj).

    (def defaults {"org.quartz.scheduler.instanceName" "Testing"
                   "org.quartz.threadPool.threadCount" "1"
                   "org.quartz.jobStore.class" "org.quartz.simpl.RAMJobStore"})

    (def scheduler (create-scheduler defaults))
    
    (start scheduler)
    
    (println (metadata scheduler))
    
    (shutdown scheduler)

## License

Copyright &copy; 2012 Paul Ingles.

Distributed under the Eclipse Public License, the same as Clojure.
