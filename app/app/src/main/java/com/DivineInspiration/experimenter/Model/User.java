package com.DivineInspiration.experimenter.Model;

import org.jetbrains.annotations.NotNull;

public class User {
    private String userId;
    private String userName;
    private String description;
    private UserContactInfo contactInfo;

    /**
     * Constructor for the user
     * @param username
     * username of user
     * @param userId
     * unique user ID of user
     * @param contactUserInfo
     * contact info of user
     * @param description
     * user description
     */
    public User(String username, String userId, UserContactInfo contactUserInfo, String description) {
        this.userId = userId;
        this.userName = username;
        this.contactInfo = contactUserInfo;
        this.description = description;
    }

    /**
     * Default constructor when initializing the user
     * @param userId
     * ID of user
     */
    public User(String userId) {
        this.userId = userId;
        userName = "Anonymous";
        contactInfo = new UserContactInfo();
        description = "";
    }

    /**
     * Mock object constructor for testing purposes
     */
    public User() {
        userId = "defaultId";
        userName = "UserName";
        contactInfo = new UserContactInfo();
        description = "";
    }

    /**
     * Gets contact info of user
     * @return
     * The contact info of the user
     */
    public UserContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets new contact info of person
     * @param contactInfo
     * new contact for person
     */
    public void setContactInfo(UserContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * Gets name of user
     * @return
     * name of user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the name of the user
     * @param userName
     * new name of user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets ID of user
     * @return
     * unique ID of user
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns a formatted user string
     * @return
     * pretty formatted string
     */
    @NotNull
    @Override
    public String toString(){
        return String.format("User Name: %s, Id: %s", userName, userId);
    }

    /**
     * Gets the description/about of user
     * @return
     * the user about
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the user description
     * @param: newDescription:String (the new user description)
     */
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
}