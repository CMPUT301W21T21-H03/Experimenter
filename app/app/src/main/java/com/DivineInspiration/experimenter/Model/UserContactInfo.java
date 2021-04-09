package com.DivineInspiration.experimenter.Model;

public class UserContactInfo {
    private String cityName;
    private String email;

    /**
     * Contact person info constructor
     * @param cityName :String (city of person)
     * @param email :String (email of person)
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
     * @return email:String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the user email
     * @param email :String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets city name of the user
     * @return cityName :String
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets a new city name for the user
     * @param cityName :String
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}