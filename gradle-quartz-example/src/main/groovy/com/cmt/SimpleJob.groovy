package com.cmt

import java.util.Date
import groovy.util.logging.Slf4j
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobKey

@Slf4j
class SimpleJob implements Job {

    /**
     * This job simply prints out its job name and the date and time
     * that it's running.
     */
    void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.jobDetail.key
        log.info "JobKey: ${jobKey}, executing at: ${new Date()}"
    }

}
