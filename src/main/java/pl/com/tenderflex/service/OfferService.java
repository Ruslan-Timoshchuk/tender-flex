package pl.com.tenderflex.service;

import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.dto.OfferDetailsRequest;

public interface OfferService {

    void createOffer(MultipartFile document, OfferDetailsRequest offerDetailsRequest, Integer bidderId);

}