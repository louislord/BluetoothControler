package com.example.bloothcontroler;

import android.util.Log;

import com.example.bloothcontroler.service.CRCUtil;
import com.example.bloothcontroler.service.DataMessage;
import com.example.bloothcontroler.service.OrderCreater;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
//        byte[] bytes = new byte[]{0x01,0x04,0x30,0x01,0x00,0x01};
//        System.out.println(Integer.toHexString(CRCUtil.getCRC(bytes)));
//        byte[] bytes2 = OrderCreater.generalReadOrder(30001,23);
//        int high = (bytes2[2] & 0xFFFF) << 8;
//        int low = bytes2[3];
//        int lastAddress = high + low;
//        System.out.println(Arrays.toString(bytes2));
//        System.out.println(high);
//        System.out.println(low);
//        System.out.println(lastAddress);

//        byte[] order = new byte[7];
//        order[0] = 0x01;//从机地址 默认0x01
//        order[1] = 0x04;
//        order[2] = 4;
//        int num1 = 12;
//        int num2 = -23;
//        byte highRA = (byte) ((num1 & 0xFF00) >> 8);
//        byte lowRA = (byte) (num1 & 0x00FF);
//        order[3] = highRA;
//        order[4] = lowRA;
//        byte highRN = (byte) ((num2 & 0xFF00) >> 8);
//        byte lowRN = (byte) (num2 & 0x00FF);
//        order[5] = highRN;
//        order[6] = lowRN;

        byte[] data = create(17);
        System.out.println(Arrays.toString(data));
        // 校验数据
        if (CRCUtil.checkCRC(data) == 0){
                    int datasize = data[2];
                    byte[] receivedData = new byte[datasize];
                    System.arraycopy(data, 3, receivedData, 0, datasize);
                    DataMessage message = new DataMessage();
                    message.setData(receivedData);
                    message.setDataSize(datasize);
            System.out.println(Arrays.toString(message.getData()));
        }

//        BigDecimal b = new BigDecimal(-8897).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP);
//        System.out.println(b.doubleValue() + "%");
        System.out.println(Arrays.toString(OrderCreater.getWriteDataOrder(OrderCreater.Pamx,2,12,-23)));
    }

    private byte[] create(int datanum){
        byte[] order = new byte[3 + datanum * 2];
        order[0] = 0x01;//从机地址 默认0x01
        order[1] = 0x04;
        order[2] = (byte) (datanum * 2);
        int[] nums = new int[datanum];

        for (int i = 0;i< datanum;i++){
            int num1 = (int) (100 * Math.random());
            nums[i] = num1;
            byte highRA = (byte) ((num1 & 0xFF00) >> 8);
            byte lowRA = (byte) (num1 & 0x00FF);
            order[3 + 2*i] = highRA;
            order[3 + 2*i + 1] = lowRA;
        }
        System.out.println(Arrays.toString(nums));
        return OrderCreater.getOrder(order);
    }
}