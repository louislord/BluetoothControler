package com.example.bloothcontroler.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bloothcontroler.service.BluetoothDataIOServer;
import com.example.bloothcontroler.service.DataMessage;

/**
 * @author Hanwenhao
 * @date 2020/4/10
 * @Description
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public abstract class BaseBCViewModel extends ViewModel {
    private BluetoothDataIOServer mText;

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


}
