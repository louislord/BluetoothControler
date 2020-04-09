package com.example.bloothcontroler.ui.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.example.bloothcontroler.R;
import com.example.bloothcontroler.base.BaseRcvAdapter;
import com.example.bloothcontroler.databinding.ItemDeviceBinding;

/**
 * @author Hanwenhao
 * @date 2020/4/9
 * @Description
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class DeviceListAdapter extends BaseRcvAdapter<BluetoothDevice, ItemDeviceBinding> {
    public DeviceListAdapter(Context context) {
        super(context, R.layout.item_device);
    }

    @Override
    public void handleItem(BluetoothDevice bluetoothDevice, ItemDeviceBinding binding, int position) {
        binding.tvName.setText(bluetoothDevice.getName() == null ? bluetoothDevice.getAddress() : bluetoothDevice.getName());
    }
}
