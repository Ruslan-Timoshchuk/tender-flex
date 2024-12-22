package pl.com.tenderflex.companyprofile.payload;

import org.mapstruct.Mapper;
import pl.com.tenderflex.companyprofile.model.ContactPerson;

@Mapper(componentModel = "spring")
public interface ContactPersonMapper {

    ContactPersonResponse toResponse(ContactPerson contactPerson);
    
}