package com.DivineInspiration.experimenter.Model;

public class UserContactInfo {
    private String address;
    private int phoneNumber;
    private String cityName;
    private String email;


    /**
     * Contact person info constructor
     * @param address
     * physical address of person
     * @param phoneNumber
     * phone number of person
     * @param cityName
     * city of person
     */
    public UserContactInfo(String address, int phoneNumber, String cityName, String email) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cityName = cityName;
        this.email = email;
    }


    /**
     * Default empty constructor for a person's contact
     */
    public UserContactInfo() {
        this.address = "";
        this.phoneNumber = 0;
        this.cityName = "";
        this.email = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets address of person
     * @return
     * contact address of person
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address of person
     * @param address
     * contact address of person
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets phone number
     * @return phone number
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number
     * @param phoneNumber
     * Contact phone number
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets city name
     * @return city name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets city name
     * @param cityName
     * City name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
