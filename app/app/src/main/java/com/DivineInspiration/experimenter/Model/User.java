package com.DivineInspiration.experimenter.Model;

import org.jetbrains.annotations.NotNull;

public class User {
    private String userId;
    private String userName;
    private String description;
    private UserContactInfo contactInfo;

    /**
     * User contructor
     * @param username
     * name of the person
     * @param contactUserInfo
     * contact info on that person
     */
    public User(String username, String userId, UserContactInfo contactUserInfo, String description) {
        this.userId = userId;
        this.userName = username;
        this.contactInfo = contactUserInfo;
        this.description = description;
    }


    /**
     * Default constructor when initing user
     * @param userId
     * userID
     */
    public User(String userId){
        this.userId = userId;
        userName = "UserName";
        contactInfo = new UserContactInfo();
        description = "";

    }

    /**
     * Default constructor when no arguments are given, mostly for testing
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
     * Contact person class of user
     */
    public UserContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets new contact info of person
     * @param contactInfo
     * new contact info
     */
    public void setContactInfo(UserContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * Gets name of user
     * @return
     * username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets username
     * @param userName
     * username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets ID of user
     * @return
     * user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns a formatted user string
     * @return
     * formatted string
     */
    @NotNull
    @Override
    public String toString(){
        return String.format("User Name: %s, Id: %s", userName, userId);
    }

    /**
     * Gets the User description
     * @return
     * the user description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the user description
     * @param newDescription
     * the new user description
     */
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
}
