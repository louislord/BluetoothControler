package com.example.bloothcontroler.service;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * @author Hanwenhao
 * @date 2020/4/10
 * @Description
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class BluetoothDataIOServer extends MutableLiveData<String> {

    private BluetoothDataIOServer(){

    }
    private static class SingleHolder{
        static BluetoothDataIOServer server = new BluetoothDataIOServer();
    }
    public static BluetoothDataIOServer getInstance(){
        return SingleHolder.server;
    }

    public void initWithSocket(BluetoothSocket socket){
        if (null != socket){
            if (bluetoothIOThread == null){
                bluetoothIOThread = new BluetoothIOThread(socket);
                bluetoothIOThread.start();
            } else {
                bluetoothIOThread.cancel();
                bluetoothIOThread.init(socket);
            }
        }

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (null != bluetoothIOThread){
            bluetoothIOThread.cancel();
        }
    }


    public void sendOrder(String order){
        if (bluetoothIOThread != null && order != null){
            bluetoothIOThread.write(order.getBytes());
        }
    }

    private BluetoothIOThread bluetoothIOThread;

    private class BluetoothIOThread extends Thread {
        private BluetoothSocket socket;
        private InputStream inputStream;
        private OutputStream outputStream;
        String TAG = "BluetoothIOThread";
        BluetoothIOThread(BluetoothSocket socket) {
            init(socket);
        }

        void init(BluetoothSocket socket){
            this.socket = socket;
            InputStream input = null;
            OutputStream output = null;

            try {
                input = socket.getInputStream();
                output = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.inputStream = input;
            this.outputStream = output;
        }


        public void run() {
//            StringBuilder recvText = new StringBuilder();
            byte[] buff = new byte[1024];
            int bytes;
            Bundle tmpBundle = new Bundle();
            Message tmpMessage = new Message();
            tmpBundle.putString("notice", "连接成功");


            while (true) {
                try {
                    bytes = inputStream.read(buff);
                    if (bytes == 0){
                        continue;
                    }
                    byte[] data = new byte[bytes];
                    System.arraycopy(buff, 0, data, 0, bytes);
                    if (CRCUtil.checkCRC(data) == 0){
                        Log.w(TAG,"data check ok");
                    }
                    String str = new String(buff, "ISO-8859-1");
                    str = str.substring(0, bytes);

                    // 收到数据
                    //Log.e("read", str);
//                    if (!str.endsWith("#")) {
//                        recvText.append(str);
//                        continue;
//                    }
//                    recvText.append(str.substring(0, str.length() - 1)); // 去除'#'

                    postValue(str);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
