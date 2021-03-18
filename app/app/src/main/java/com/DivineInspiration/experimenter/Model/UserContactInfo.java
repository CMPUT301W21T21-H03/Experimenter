package com.DivineInspiration.experimenter.Model;

public class UserContactInfo {

    private int phoneNumber;
    private String cityName;
    private String email;


    /**
     * Contact person info constructor
     * physical address of person
     * phone number of person
     * @param cityName
     * city of person
     */
    public UserContactInfo(String cityName, String email) {


        this.cityName = cityName;
        this.email = email;
    }


    /**
     * Default empty constructor for a person's contact
     */
    public UserContactInfo() {
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
