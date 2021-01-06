package com.hxtx.udp.cron;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Quartz定时任务
 *
 * @author sunweihong
 * @date 2021/1/6 18:48
 **/
public class QuartzScheduler {
    public static void main(String[] args) {
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();
            JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1").build();
            CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(cronSchedule("0/10 * * * * ?")).build();
            Date ft = sched.scheduleJob(job, trigger);
            System.out.println(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "+ trigger.getCronExpression());
            JobDetail job2 = newJob(SimpleJob2.class).withIdentity("job2", "group2").build();
            CronTrigger trigger2 = newTrigger().withIdentity("trigger2", "group2").withSchedule(cronSchedule("0/15 * * * * ?")).build();
            Date ft2 = sched.scheduleJob(job2, trigger2);
            System.out.println(job2.getKey() + " has been scheduled to run at: " + ft2 + " and repeat based on expression: "+ trigger2.getCronExpression());
            sched.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
