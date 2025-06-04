package pl.com.tenderflex.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractSigningDeadlineCheckJobConfig {

    @Bean
    public JobDetail signingDeadlineCheckJobDetail() {
        return JobBuilder.newJob().ofType(ContractSigningDeadlineCheckJob.class)
          .storeDurably()
          .withIdentity("ContractSigningDeadlineCheckJob")  
          .withDescription("Checks expired signing contract deadlines")
          .build();
    }
    
    @Bean
    public Trigger deadlineTrigger() {
        return TriggerBuilder.newTrigger()
            .forJob(signingDeadlineCheckJobDetail())
            .withIdentity("ContractDeadlineTrigger")
            .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
            .build();
    }
    
}