package pl.com.tenderflex.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.dto.OfferDetailsResponse;
import pl.com.tenderflex.dto.OfferResponse;
import pl.com.tenderflex.dto.OfferDetailsRequest;

public interface OfferService {

    void createOffer(MultipartFile document, OfferDetailsRequest offerDetailsRequest, Integer bidderId);

    List<OfferResponse> getOffersByContractor(Integer contractorId);

    OfferDetailsResponse getById(Integer offerId);

    List<OfferResponse> getOffersByBidder(Integer bidderId);

}