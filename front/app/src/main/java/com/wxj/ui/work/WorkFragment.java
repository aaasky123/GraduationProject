package com.wxj.ui.work;

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

import com.google.android.material.tabs.TabLayout;
import com.wxj.R;

import java.util.ArrayList;

public class WorkFragment extends Fragment {

    private WorkViewModel workViewModel;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View contextView;
    ArrayList fragmentList=new ArrayList<Fragment>();
    String[] temp={"进行中","已完结","新建"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contextView=inflater.inflate(R.layout.fragment_work,container,false);
        tabLayout=contextView.findViewById(R.id.tablayout);
        viewPager=contextView.findViewById(R.id.viewPager);
        return contextView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MPagerAdapter mPagerAdapter=new MPagerAdapter(getChildFragmentManager());
        initFragment();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(mPagerAdapter);
    }

    private void initFragment(){
        fragmentList.add(new OnWorkingFragment());
        fragmentList.add(new FinishedWorkFragment());
        fragmentList.add(new NewWorkFragment());
    }

    class MPagerAdapter extends FragmentPagerAdapter{

        public MPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return (Fragment) fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        //返回tablayout的标题文字;
        @Override
        public CharSequence getPageTitle(int position) {
            return temp[position];
        }
    }

}