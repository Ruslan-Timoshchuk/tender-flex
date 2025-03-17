package pl.com.tenderflex.exception;

@SuppressWarnings("serial")
public class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException(Integer userId, Integer tenderId) {
        super(String.format("Offer not found for user-id %s and tender-id %s", userId, tenderId));
    }

}