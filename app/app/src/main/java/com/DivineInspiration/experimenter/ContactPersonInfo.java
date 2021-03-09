package com.DivineInspiration.experimenter;

public class ContactPersonInfo {
    private String address;
    private int phoneNumber;
    private String cityName;

    /**
     * Contact person info constructor
     * @param address
     * physical address of person
     * @param phoneNumber
     * phone number of person
     * @param cityName
     * city of person
     */
    public ContactPersonInfo(String address, int phoneNumber, String cityName) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cityName = cityName;
    }

    /**
     * Default empty constructor for a person's contact
     */
    public ContactPersonInfo() {
        this.address = "";
        this.phoneNumber = 0;
        this.cityName = "";
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
