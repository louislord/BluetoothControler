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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bloothcontroler.R;
import com.example.bloothcontroler.databinding.FragmentNotificationsBinding;
import com.example.bloothcontroler.service.DataMessage;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private String[] titles = new String[]{"PV1", "PV2", "PV3", "PV4"};
    private List<Fragment> fragmentList = new ArrayList<>();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        tabLayout = root.findViewById(R.id.tab_layout);
        viewPager = root.findViewById(R.id.view_pager);

        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<DataMessage>() {
            @Override
            public void onChanged(@Nullable DataMessage s) {
//                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        for (String title : titles) {
            fragmentList.add(LineChartFragment.getInstance(title));
        }
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getContext(), fragmentList, getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        for (String title : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

}