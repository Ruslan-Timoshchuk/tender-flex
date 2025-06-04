package pl.com.tenderflex.scheduler;

import static java.time.LocalDate.now;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.enums.EContractStatus;
import pl.com.tenderflex.service.ContractService;
import pl.com.tenderflex.service.OfferService;

@Component
@RequiredArgsConstructor
public class ContractSigningDeadlineCheckJob implements Job {

    private final ContractService contractService;
    private final OfferService offerService;

    @Override
    @Transactional
    public void execute(JobExecutionContext context) throws JobExecutionException {
        contractService.findAll(EContractStatus.PENDING_SIGNATURE).stream()
                .filter(contract -> contract.getSignedDate().isBefore(now())).forEach(contract -> {
                    Offer offer = offerService.findById(contract.getOffer().getId());
                    offerService.handleOnSigningDeadlinePassed(offer);
                    contractService.handleOnSigningDeadlinePassed(contract);
                });
    }

}