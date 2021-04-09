package com.DivineInspiration.experimenter.Model;

import org.jetbrains.annotations.NotNull;

public class User {
    private String userId;
    private String userName;
    private String description;
    private UserContactInfo contactInfo;

    /**
     * User constructor
     * @param username:String
     * @param contactUserInfo:UserContactInfo
     * @param description:String
     */
    public User(String username, String userId, UserContactInfo contactUserInfo, String description) {
        this.userId = userId;
        this.userName = username;
        this.contactInfo = contactUserInfo;
        this.description = description;
    }

    /**
     * Default constructor when initializing the user
     * @param userId:String
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
     * @return contactInfo:UserContactInfo (Contact person class of user)
     */
    public UserContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets new contact info of person
     * @param contactInfo:UserContactInfo (new contact info)
     */
    public void setContactInfo(UserContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * Gets name of user
     * @return username:String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets username
     * @param userName:String
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets ID of user
     * @return userID:String
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns a formatted user string
     * @return formatted string
     */
    @NotNull
    @Override
    public String toString(){
        return String.format("User Name: %s, Id: %s", userName, userId);
    }

    /**
     * Gets the User description
     * @return description:String (the user description)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the user description
     * @param newDescription:String (the new user description)
     */
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
}