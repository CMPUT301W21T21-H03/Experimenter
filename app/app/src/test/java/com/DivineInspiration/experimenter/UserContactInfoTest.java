package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Model.UserContactInfo;
import android.util.Log;
import com.google.firebase.installations.FirebaseInstallations;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

public class UserContactInfoTest {

    private UserContactInfo mockContactPersonInfo(String cityName, String email) {
        return new UserContactInfo(cityName, email);
    }

    @Test
    public void getEmailTest() {
        UserContactInfo mock = mockContactPersonInfo("Edmonton", "1234@gmail.com");

        assertEquals("1234@gmail.com", mock.getEmail());
    }

    @Test
    public void setEmailTest() {
        UserContactInfo mock = mockContactPersonInfo("Edmonton", "1234@gmail.com");
        assertEquals("1234@gmail.com", mock.getEmail());

        mock.setEmail("abcd@gmail.com");
        assertEquals("abcd@gmail.com", mock.getEmail());
    }

    @Test
    public void getCityName() {
        UserContactInfo mock = mockContactPersonInfo("Edmonton", "1234@gmail.com");

        assertEquals("Edmonton", mock.getCityName());
    }

    @Test
    public void setCityName() {
        UserContactInfo mock = mockContactPersonInfo("Edmonton", "1234@gmail.com");
        assertEquals("Edmonton", mock.getCityName());

        mock.setCityName("Calgary");
        assertEquals("Calgary", mock.getCityName());
    }


    
}