package com.DivineInspiration.experimenter;

import org.junit.Test;
import static org.junit.Assert.*;

public class ContactPersonInfoTest {

    private ContactPersonInfo mockContactPersonInfo(String address, int phoneNumber, String cityName) {
        return new ContactPersonInfo(address, phoneNumber, cityName);
    }

    @Test
    public void getAddress() {
        ContactPersonInfo mock = mockContactPersonInfo("100 Old St.", 1234, "Regina");

        assertEquals("100 Old St.", mock.getAddress());
    }

    @Test
    public void setAddress() {
    }

    @Test
    public void getPhoneNumber() {
    }

    @Test
    public void setPhoneNumber() {
    }

    @Test
    public void getCityName() {
    }

    @Test
    public void setCityName() {
    }
}