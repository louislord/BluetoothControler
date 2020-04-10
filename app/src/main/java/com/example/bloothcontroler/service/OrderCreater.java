package com.example.bloothcontroler.service;

/**
 * @author Hanwenhao
 * @date 2020/4/10
 * @Description
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class OrderCreater {
    public static byte[] getOrder(byte[] bytes){
        int crc = CRCUtil.getCRC(bytes);
        byte low = (byte) (crc & 0x00FF);
        byte high = (byte) ((crc & 0xFF00) >> 8);
        byte[] order = new byte[bytes.length + 2];
        System.arraycopy(bytes,0,order,0,bytes.length);
        order[order.length - 2] = high;
        order[order.length - 1] = low;
        return order;
    }
}
