package com.DivineInspiration.experimenter.Model;

public class UserContactInfo {
    private String cityName;
    private String email;

    /**
     * Contact person info constructor
     * @param cityName
     * name of the city of the user
     * @param email
     * email of the user
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

    /**
     * Gets the user email
     * @return
     * email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the user email
     * @param email
     * email of user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets city name
     * @return
     * city name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets city name
     * @param cityName
     * name of the city
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}