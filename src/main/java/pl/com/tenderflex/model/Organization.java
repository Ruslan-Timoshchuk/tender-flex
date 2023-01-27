package pl.com.tenderflex.model;

public class Organization {

    private Integer id;
    private String name;
    private String nationalRegistrationNumber;
    private Country country;
    private String city;
    private ContactPerson contactPerson; 
    
    public Organization(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalRegistrationNumber() {
        return nationalRegistrationNumber;
    }

    public void setNationalRegistrationNumber(String nationalRegistrationNumber) {
        this.nationalRegistrationNumber = nationalRegistrationNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }
}