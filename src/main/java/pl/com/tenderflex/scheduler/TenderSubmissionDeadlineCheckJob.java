package pl.com.tenderflex.scheduler;

import static java.time.LocalDate.now;
import static pl.com.tenderflex.model.enums.ETenderStatus.TENDER_IN_PROGRESS;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.service.TenderService;

@Component
@RequiredArgsConstructor
public class TenderSubmissionDeadlineCheckJob implements Job {

    private final TenderService tenderService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        tenderService.closeActiveWithExpiredSubmission(TENDER_IN_PROGRESS, now());
    }

}