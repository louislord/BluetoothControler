package com.example.bloothcontroler;

import com.example.bloothcontroler.service.CRCUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testCRC(){
        byte[] bytes = new byte[]{0x01,0x04,0x30,0x01,0x00,0x01};
        System.out.println(Integer.toHexString(CRCUtil.getCRC(bytes)));
        byte[] bytes2 = new byte[]{0x01,0x04,0x30,0x01,0x00,0x01,0x0a,0x6f};
        System.out.println(Integer.toHexString(CRCUtil.checkCRC(bytes2)));
    }
}