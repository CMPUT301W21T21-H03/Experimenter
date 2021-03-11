package com.DivineInspiration.experimenter;

import android.util.Log;

import com.DivineInspiration.experimenter.Model.ContactPersonInfo;
import com.google.firebase.installations.FirebaseInstallations;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

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
        ContactPersonInfo mock = mockContactPersonInfo("100 Old St.", 1234, "Regina");

        mock.setAddress("99 Kerd Dr.");
        assertEquals("99 Kerd Dr.", mock.getAddress());
    }

    @Test
    public void getPhoneNumber() {
        ContactPersonInfo mock = mockContactPersonInfo("100 Old St.", 1224, "Regina");

        assertEquals(1224, mock.getPhoneNumber());
    }

    @Test
    public void setPhoneNumber() {
        ContactPersonInfo mock = mockContactPersonInfo("100 Old St.", 1274, "Regina");

        mock.setPhoneNumber(90000043);
        assertEquals(90000043, mock.getPhoneNumber());
    }

    @Test
    public void getCityName() {
        ContactPersonInfo mock = mockContactPersonInfo("100 Old St.", 1274, "Regina");

        assertEquals("Regina", mock.getCityName());
    }

    @Test
    public void setCityName() {
        ContactPersonInfo mock = mockContactPersonInfo("100 Old St.", 1274, "Regina");

        mock.setCityName("Edmonton");
        assertEquals("Edmonton", mock.getCityName());
    }

    public static class TestInstallation {
        public static long testInstallation(){
            final AtomicLong output = new AtomicLong(0);
            final AtomicBoolean done = new AtomicBoolean(false);
            FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
                Log.d("stuff","entering get Id");
                if(task.isSuccessful()){
                    Log.d("stuff",task.getResult() + "||||||||||||||| Task is successful");
                    output.set(333);
                }
                else{
                    Log.d("stuff","task failed");
                }
                Log.d("stuff","existing getId");
                done.set(true);
            });
            Log.d("stuff", "done?    "+ String.valueOf(done.get()));
    //        while(!done.get()){
    //            try {
    //                Thread.sleep(25L);
    //            } catch (InterruptedException e) {
    //                e.printStackTrace();
    //            }
    //        };
            return output.get();
        }
    }
}