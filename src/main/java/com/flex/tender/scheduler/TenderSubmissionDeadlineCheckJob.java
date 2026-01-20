package com.flex.tender.scheduler;

import static com.flex.tender.model.enumeration.ETenderStatus.TENDER_IN_PROGRESS;
import static java.time.LocalDate.now;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.flex.tender.service.TenderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TenderSubmissionDeadlineCheckJob implements Job {

    private final TenderService tenderService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        tenderService.closeActiveWithExpiredSubmission(TENDER_IN_PROGRESS, now());
    }

}