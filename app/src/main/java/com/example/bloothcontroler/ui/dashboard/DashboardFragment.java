package com.example.bloothcontroler.ui.dashboard;

import android.app.AlertDialog;
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
import com.example.bloothcontroler.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard,container,false);
        binding.setModel(dashboardViewModel);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.tvType.setOnClickListener(this);
        binding.tvGlass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvType:
                showSetDialog(TYPE);
                break;
            case R.id.tvGlass:
                showSetDialog(GLASS);
                break;
        }
    }

    private static final int TYPE = 1;
    private static final int GLASS = 2;
    private void showSetDialog(final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_setlanguage, null);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        ListView listView = view.findViewById(R.id.language_content_list);

        final String[] languages;
        if (type == TYPE){
            languages = new String[]{"Poly","Mono"};
        } else {
            languages = new String[]{"Single","Double"};
        }

        listView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, languages));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type == TYPE){
                    dashboardViewModel.mType.set(languages[position]);
                } else {
                    dashboardViewModel.mGlass.set(languages[position]);
                }
                dialog.dismiss();
            }
        });

    }
}