package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.Model.UserContactInfo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private User mockUser(String username, String userId, UserContactInfo contactUserInfo, String description) {
        User user = new User(username, userId, contactUserInfo, description);
        return user;
    }

    private UserContactInfo mockContactInfo() {
        return new UserContactInfo("Edmonton", "abcd@gmail.com");
    }

    @Test
    public void getContactInfoTest() {
        UserContactInfo contactInfo = mockContactInfo();
        User user = mockUser("Sheldon", "XDC23ABC9K", contactInfo, "Test Description");
        assertEquals(contactInfo, user.getContactInfo());
    }

    @Test
    public void setContactInfoTest() {
        UserContactInfo contactInfo = mockContactInfo();
        User user = mockUser("Sheldon", "XDC23ABC9K", contactInfo, "Test Description");
        assertEquals(contactInfo, user.getContactInfo());

        UserContactInfo newContactInfo = new UserContactInfo("Calgary", "1234@gmail.com");
        user.setContactInfo(newContactInfo);
        assertEquals(newContactInfo, user.getContactInfo());
    }

    @Test
    public void getUserNameTest() {
        User user = mockUser("Sheldon","XDC23ABC9K", mockContactInfo(), "Test Description");
        assertEquals("Sheldon", user.getUserName());
    }

    @Test
    public void setUserNameTest() {
        User user = mockUser("Sheldon","XDC23ABC9K", mockContactInfo(), "Test Description");
        assertEquals("Sheldon", user.getUserName());

        user.setUserName("Bob");
        assertEquals("Bob", user.getUserName());
    }

    @Test
    public void getUserIdTest() {
        User user = mockUser("Sheldon","XDC23ABC9K", mockContactInfo(), "Test Description");
        assertEquals("XDC23ABC9K", user.getUserId());
    }

    @Test
    public void toStringTest() {
        User user = mockUser("Sheldon","XDC23ABC9K", mockContactInfo(), "Test Description");
        assertEquals("User Name: Sheldon, Id: XDC23ABC9K", user.toString());
    }

    @Test
    public void getDescription() {
        User user = mockUser("Sheldon","XDC23ABC9K", mockContactInfo(), "Test Description");
        assertEquals("Test Description", user.getDescription());
    }

    @Test
    public void setDescription() {
        User user = mockUser("Sheldon","XDC23ABC9K", mockContactInfo(), "Test Description");
        assertEquals("Test Description", user.getDescription());

        String newDescription = "New Test Description";
        user.setDescription(newDescription);
        assertEquals(newDescription, user.getDescription());
    }
}
