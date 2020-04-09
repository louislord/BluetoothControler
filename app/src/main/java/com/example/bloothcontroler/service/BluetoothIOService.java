package com.example.bloothcontroler.service;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.widget.ScrollView;

import com.example.bloothcontroler.ui.BluetoothActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothIOService extends Thread {
    private  BluetoothSocket socket;
    private  InputStream inputStream;
    private  OutputStream outputStream;

    public BluetoothIOService(BluetoothSocket socket) {
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
        StringBuilder recvText = new StringBuilder();
        byte[] buff = new byte[1024];
        int bytes;

        Bundle tmpBundle = new Bundle();
        Message tmpMessage = new Message();
        tmpBundle.putString("notice", "连接成功");


        while (true) {
            try {
                bytes = inputStream.read(buff);
                if (bytes == 0){
                    return;
                }
                String str = new String(buff, "ISO-8859-1");
                str = str.substring(0, bytes);

                // 收到数据，单片机发送上来的数据以"#"结束，这样手机知道一条数据发送结束
                //Log.e("read", str);
                if (!str.endsWith("#")) {
                    recvText.append(str);
                    continue;
                }
                recvText.append(str.substring(0, str.length() - 1)); // 去除'#'

                Bundle bundle = new Bundle();
                Message message = new Message();

                bundle.putString("recv", recvText.toString());
                recvText.replace(0, recvText.length(), "");
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
