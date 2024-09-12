package pl.com.tenderflex.service.impl;

import static java.time.LocalDate.*;
import static pl.com.tenderflex.model.EUserTenderStatus.*;
import static java.util.Arrays.*;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TenderStatusRepository;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.TenderStatus;
import pl.com.tenderflex.service.TenderStatusService;

@RequiredArgsConstructor
public class TenderStatusServiceImpl implements TenderStatusService {

    private final TenderStatusRepository tenderStatusRepository;

    @Override
    public Tender assignNewUserStatus(Tender tender) {
        tender.setUserTenderStatuses(new HashSet<>(asList(tenderStatusRepository
              .create(TenderStatus
                        .builder()
                        .user(tender.getContractor())
                        .tender(tender)
                        .status(TENDER_IN_PROGRESS)
                        .lastUpdated(now())
                        .build()))));
        return tender;
    }
}