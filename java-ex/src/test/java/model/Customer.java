package model;

/**
 * Created by Alex on 01.12.2016.
 */
public class Customer {

    private String taxId;
    private String company;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String postcode;
    private String city;
    private String country;
    private String province;

    public String getProvince() {
        return province;
    }

    public Customer withProvince(String province) {
        this.province = province;
        return this;
    }

    private String email;
    private String phone;
    private boolean newsletterSubscribed;
    private String password;

    public String getTaxId() {
        return taxId;
    }

    public Customer withTaxId(String taxId) {
        this.taxId = taxId;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public Customer withCompany(String company) {
        this.company = company;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress1() {
        return address1;
    }

    public Customer withAddress1(String address1) {
        this.address1 = address1;
        return this;
    }

    public String getAddress2() {
        return address2;
    }

    public Customer withAddress2(String address2) {
        this.address2 = address2;
        return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public Customer withPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Customer withCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Customer withCountry(String country) {
        this.country = country;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Customer withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Customer withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public boolean isNewsletterSubscribed() {
        return newsletterSubscribed;
    }

    public Customer withNewsletterSubscribed(boolean newsletterSubscribed) {
        this.newsletterSubscribed = newsletterSubscribed;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Customer withPassword(String password) {
        this.password = password;
        return this;
    }
}
