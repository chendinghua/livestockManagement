package com.kymjs.app.base_res.utils.view.viewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ReceivePagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> views =  new ArrayList<>();

    Fragment currentFragment;
    public ReceivePagerAdapter(FragmentManager fm, ArrayList<Fragment> views) {
        super(fm);
       this.views.addAll(views);

    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Fragment getItem(int position) {


       // return PageFragment.newInstance(position + 1, position == getCount() - 1);

     /* if(  views.get(position ) instanceof  ReceiveProductScanOperationFragment){
           ReceiveProductScanOperationFragment receiveProductScanOperationFragment = (ReceiveProductScanOperationFragment)views.get(position );
                if(receiveProductScanOperationFragment!=null) {
                    receiveProductScanOperationFragment.initLoadingData();
                }
      }*/

        return  views.get(position);
        //return new ReceiveProductPrintOperationFragment();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }


    public Fragment getCurrentFragment() {
        return currentFragment;
    }

}