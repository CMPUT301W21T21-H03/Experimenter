package com.DivineInspiration.experimenter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactPersonInfoTest {

    private ContactPersonInfo mockContactPersonInfo(String address, int phoneNumber, String cityName) {
        return new ContactPersonInfo(address, phoneNumber, cityName);
    }

    @Test
    void getAddress() {
        ContactPersonInfo mock = mockContactPersonInfo("100 Old St.", 1234, "Regina");

        assertEquals("10 Old St.", mock.getAddress());
    }

    @Test
    void setAddress() {
    }

    @Test
    void getPhoneNumber() {
    }

    @Test
    void setPhoneNumber() {
    }

    @Test
    void getCityName() {
    }

    @Test
    void setCityName() {
    }
}