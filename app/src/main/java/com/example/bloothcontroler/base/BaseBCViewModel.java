package com.example.bloothcontroler.base;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bloothcontroler.service.BluetoothDataIOServer;
import com.example.bloothcontroler.service.DataMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Hanwenhao
 * @date 2020/4/10
 * @Description
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public abstract class BaseBCViewModel extends ViewModel {
    private BluetoothDataIOServer mText;
    private String TAG = "BaseBCViewModel";
    public boolean isBTConnected (){
        return mText.isConnected();
    }

    protected BaseBCViewModel(){
        mText = BluetoothDataIOServer.getInstance();
    }

    public LiveData<DataMessage> getText() {
        return mText;
    }

    public void sendOrder(byte[] order){
        if (null != mText){
            mText.sendOrder(order);
        }
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (isReadyToSend && order.length > 0
//                    && isBTConnected()
            ){
                Log.w(TAG,"sendCover:" + Arrays.toString(order));
                sendOrder(order);
            }
        }
    };

    private boolean isReadyToSend;

    public void setReady(boolean isReadyToSend){
        this.isReadyToSend = isReadyToSend;
    }


    private Timer timer;
    private byte[] order;

    public void startCover(byte[] order,long period){
        this.order = order;
        if (timer == null){
            timer = new Timer();
        }
        timer.schedule(timerTask,0,period);
    }

    public double getValue(int data){
        return getValue(data,0.1);
    }

    public double getValue(int data,double rate){
        BigDecimal b = new BigDecimal(data).multiply(new BigDecimal(rate)).setScale(1, RoundingMode.HALF_UP);
        return b.doubleValue();
    }
}
