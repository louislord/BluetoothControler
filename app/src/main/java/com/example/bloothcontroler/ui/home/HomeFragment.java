package com.example.bloothcontroler.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bloothcontroler.R;
import com.example.bloothcontroler.databinding.FragmentHomeBinding;
import com.example.bloothcontroler.service.BluetoothDataIOServer;
import com.example.bloothcontroler.service.OrderCreater;
import com.example.bloothcontroler.ui.BluetoothActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
                binding.txBluetooth.setText(s);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.txBluetooth.setOnClickListener(this);
        binding.tvDefault.setOnClickListener(this);
        binding.tvPVModel.setOnClickListener(this);
        binding.tvStop.setOnClickListener(this);
    }

    private boolean isStart;

    private void showSetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_setlanguage, null);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        ListView listView = view.findViewById(R.id.language_content_list);

        final String[] languages;

        languages = new String[]{"PV1","PV2","PV3","PV4"};


        listView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, languages));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.tvPVModel.setText(languages[position]);
                homeViewModel.sendOrder(OrderCreater.startCircle(position));
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvDefault:
                homeViewModel.sendOrder(OrderCreater.setDefault());
                break;
            case R.id.tvPVModel:
                showSetDialog();
                break;
            case R.id.tvStop:
                homeViewModel.sendOrder(OrderCreater.startOrStop(isStart));
                if (isStart){
                    isStart = false;
                    binding.tvStop.setText("Stop");
                } else {
                    isStart = true;
                    binding.tvStop.setText("Start");
                }
                break;
            case R.id.txBluetooth:
                Intent intent = new Intent(getContext(), BluetoothActivity.class);
                startActivity(intent);
                break;
        }
    }
}