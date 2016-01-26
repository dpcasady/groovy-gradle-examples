package com.cmt

import static org.quartz.CronScheduleBuilder.cronSchedule
import static org.quartz.JobBuilder.newJob
import static org.quartz.TriggerBuilder.newTrigger

import org.quartz.CronTrigger
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.SchedulerMetaData
import org.quartz.impl.StdSchedulerFactory
import groovy.util.logging.Slf4j

import java.util.Date

@Slf4j
class CronTriggerExample {

    static void main(String... args) {
        Scheduler scheduler = scheduleJob()

        try {
          // Wait 1 minute so that we can execute some jobs
          Thread.sleep(60000L)
        } catch (Exception e) { }

        scheduler.shutdown(true)

        SchedulerMetaData metaData = scheduler.metaData
        log.info "Executed ${metaData.numberOfJobsExecuted} jobs"
    }

    /**
     * Schedule a new job to run every second.
     */
    static Scheduler scheduleJob() {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler()

        // Create the job
        JobDetail job = newJob(SimpleJob)
            .withIdentity("SimpleJob", "job")
            .build()

        // Schedule the job
        String cronExpression = "* * * * * ?"
        CronTrigger trigger = newTrigger()
            .withIdentity("CronTrigger", "trigger")
            .withSchedule(cronSchedule(cronExpression))
            .build()

        Date date = scheduler.scheduleJob(job, trigger)
        log.info "${job.key.name} has been scheduled to run at: ${date}, and repeat based on expression: ${trigger.cronExpression}"

        // Jobs will only run once the scheduler has been started
        scheduler.start()

        return scheduler
    }

}
