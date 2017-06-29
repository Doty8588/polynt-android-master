package com.Polynt.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.Polynt.R;
import com.Polynt.utils.LogUtil;

/**
 * Created by Alex on 6/2/2015.
 */
public class BaseContainerFragment extends Fragment{

    public void replaceFragment(Fragment fragment, boolean addToBackStack){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if(addToBackStack)
            transaction.addToBackStack(null);
        transaction.replace(R.id.container_framelayout, fragment);
        transaction.commit();
        getChildFragmentManager().executePendingTransactions();
    }

    public boolean popFragment(){
        LogUtil.d("Pop fragment:" + getChildFragmentManager().getBackStackEntryCount());
        boolean isPop = false;
        if(getChildFragmentManager().getBackStackEntryCount() > 0){
            isPop = true;
            getChildFragmentManager().popBackStack();
        }
        return isPop;
    }

}
