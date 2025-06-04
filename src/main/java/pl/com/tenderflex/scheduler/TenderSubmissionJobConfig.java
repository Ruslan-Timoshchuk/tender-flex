package pl.com.tenderflex.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenderSubmissionJobConfig {

    @Bean
    public JobDetail tenderDeadlineCheckJobDetail() {
        return JobBuilder
                 .newJob()
                 .ofType(TenderSubmissionDeadlineCheckJob.class)
                 .storeDurably()
                 .withIdentity("TenderSubmissionDeadlineCheckJob")  
                 .withDescription("Checks expired tender submission deadlines")
                 .build();
    }
    
    @Bean
    public Trigger tenderDeadlineTrigger() {
        return TriggerBuilder
                 .newTrigger()
                 .forJob(tenderDeadlineCheckJobDetail())
                 .withIdentity("TenderDeadlineTrigger")
                 .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                 .build();
    }
        
}