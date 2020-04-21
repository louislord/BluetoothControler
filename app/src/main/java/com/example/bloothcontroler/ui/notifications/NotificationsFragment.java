package com.example.bloothcontroler.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bloothcontroler.R;
import com.example.bloothcontroler.databinding.FragmentNotificationsBinding;
import com.example.bloothcontroler.service.DataMessage;
import com.example.bloothcontroler.service.OrderCreater;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_notifications, container, false);
        notificationsViewModel.getText().observe(this, new Observer<DataMessage>() {
            @Override
            public void onChanged(@Nullable DataMessage s) {
                handleMessage(s);
            }
        });

//        notificationsViewModel.startCover(OrderCreater.setDefault(),3000);
        return binding.getRoot();
    }

    private void handleMessage(DataMessage message) {
        if (null != message) {
            switch (message.what) {
                case DataMessage.RECEVED_IV_DATA:
                    int[] data = message.getData();
                    if (data.length > 5){
                        binding.tvVoc.setText(String.valueOf(notificationsViewModel.getValue(data[0])));
                        binding.tvIrr.setText(String.valueOf(notificationsViewModel.getValue(data[1])));
                        binding.tvTemp.setText(String.valueOf(notificationsViewModel.getValue(data[2])));
                        binding.edPm.setText(String.valueOf(notificationsViewModel.getValue(data[3],0.01)));
                        binding.edVoc.setText(String.valueOf(notificationsViewModel.getValue(data[4],0.01)));
                        binding.edIsc.setText(String.valueOf(notificationsViewModel.getValue(data[5],0.01)));
                    }
                    break;
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationsViewModel.isBTConnected()){
                    double pamx = Double.parseDouble(binding.edPm.getText().toString());
                    double vmp = Double.parseDouble(binding.edVoc.getText().toString());
                    double imp = Double.parseDouble(binding.edIsc.getText().toString());

                    int p = (int) (pamx * 10);
                    int v = (int) (vmp * 10);
                    int i = (int) (imp * 10);
                    byte[] editOrder = OrderCreater.getWriteDataOrder(OrderCreater.T_of_Pm,3,
                            p,
                            v,
                            i
                    );
                    notificationsViewModel.sendOrder(editOrder);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        notificationsViewModel.setReady(true);
        notificationsViewModel.sendOrder(OrderCreater.generalReadOrder(OrderCreater.Voc_of_String,6));
    }

    @Override
    public void onStop() {
        super.onStop();
        notificationsViewModel.setReady(false);
    }
}