package com.example.bloothcontroler.service;

/**
 * @author Hanwenhao
 * @date 2020/4/13
 * @Description
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class DataMessage {
    public static final int CONNECT_STATUS = 1;
    public static final int RECEVED_STATUS_DATA = 2;
    public static final int RECEVED_SETTING_DATA = 3;
    public static final int RECEVED_IV_DATA = 4;
    public int what;
    private int[] data;
    private int dataSize;
    private int registerAddress;

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(int registerAddress) {
        this.registerAddress = registerAddress;
    }
}
