package com.flex.tender.scheduler;

import static java.time.LocalDate.now;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.flex.tender.model.Offer;
import com.flex.tender.model.enumeration.EContractStatus;
import com.flex.tender.service.ContractService;
import com.flex.tender.service.OfferService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContractSigningDeadlineCheckJob implements Job {

    private final ContractService contractService;
    private final OfferService offerService;

    @Override
    @Transactional
    public void execute(JobExecutionContext context) throws JobExecutionException {
        contractService.findAll(EContractStatus.PENDING_SIGNATURE).stream()
                .filter(contract -> contract.getSignedDeadline().isBefore(now())).forEach(contract -> {
                    Offer offer = offerService.findById(contract.getOffer().getId());
                    offerService.handleOnSigningDeadlinePassed(offer);
                    contractService.handleOnSigningDeadlinePassed(contract);
                });
    }

}