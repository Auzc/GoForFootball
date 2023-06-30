package com.example.bar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bar.R;
import com.google.android.material.tabs.TabLayout;
//import com.tencent.qcloud.tuikit.tuicontact.classicui.pages.TUIContactFragment;

import java.util.ArrayList;
import java.util.List;

public class MessagesFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        // Set icons for each tab
        int[] tabIcons = {R.drawable.chatroom, R.drawable.friends};
        for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());

        adapter.addFragment(new ChatRoomFragment(), R.drawable.chatroom);
        adapter.addFragment(new FindFragment(),  R.drawable.friends);

        viewPager.setAdapter(adapter);
    }

    private static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        //private final List<String> fragmentTitleList = new ArrayList<>();
        private final List<Integer> fragmentIconList = new ArrayList<>();

        public Adapter(FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragmentList.get(position);
            int iconResId = fragmentIconList.get(position);
            Bundle args = new Bundle();
            args.putInt("icon_res_id", iconResId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment,  int iconResId) {
            fragmentList.add(fragment);

            fragmentIconList.add(iconResId);
        }

//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return fragmentTitleList.get(position);
//        }
    }
}
