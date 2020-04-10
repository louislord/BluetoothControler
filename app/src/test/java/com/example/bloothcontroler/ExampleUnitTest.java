package com.example.bloothcontroler;

import com.example.bloothcontroler.service.CRCUtil;
import com.example.bloothcontroler.service.OrderCreater;

import org.junit.Test;

import java.util.Arrays;

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
        byte[] bytes2 = OrderCreater.getOrder(bytes);
        System.out.println(Arrays.toString(bytes2));
    }
}