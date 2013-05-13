package com.clojurista.cljquartz;

import java.util.Set;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import clojure.lang.IFn;

public class ClojureFnJob implements Job {
    private static Logger log = LoggerFactory.getLogger(ClojureFnJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info(String.format("Executing job: %s", context.getJobDetail().getKey()));

        if (context.getMergedJobDataMap().size() > 0) {
            Set<String> keys = context.getMergedJobDataMap().keySet();
            for (String key : keys) {
                Object val = context.getMergedJobDataMap().get(key);
                log.info("jobDataMap entry: " + key + " = " + val);
            }
        }

        IFn fn = (IFn)context.getMergedJobDataMap().get(":fn");
        Object result = fn.invoke(context.getMergedJobDataMap(), context);
    }
}
