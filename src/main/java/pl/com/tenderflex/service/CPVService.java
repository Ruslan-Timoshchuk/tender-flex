package pl.com.tenderflex.service;

import java.util.List;
import pl.com.tenderflex.payload.response.CPVresponse;

public interface CPVService {

    List<CPVresponse> getAllCPVs();

}
