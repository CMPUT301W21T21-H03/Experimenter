package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Model.IdGen;

import org.junit.Test;
import static org.junit.Assert.*;
public class TestIdGen {

    @Test
    public void baseConversionTest(){
        long l = 1679616L;
        assertEquals("10000", IdGen.base10To36(l));
        assertEquals(1679616L, IdGen.base36To10(IdGen.base10To36(l)));
        long x = 123456L;
        assertEquals("2N9C", IdGen.base10To36(x));
        assertEquals(123456, IdGen.base36To10("2N9C"));
        assertNotEquals(123, IdGen.base36To10("2N9C"));
        assertNotEquals("ABCD", IdGen.base10To36(x));

        //TODO not sure if need negative/zero test but we currently fail them
//        long f = 0L;
//        assertEquals("0", IdGen.base10To36(f));
//        assertEquals(0, IdGen.base36To10("0"));
//
//        long y = -152L;
//        assertEquals("-48", IdGen.base10To36(y));
//        assertEquals(-152, IdGen.base36To10("-48"));

    }

    @Test
    public void testId(){
        //TODO not sure how to test this.
        //This might help https://generate.plus/en/base64

        //Log.d("stuff", String.valueOf(IdGen.genUserId()));
    }
}
