package com.Polynt.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Polynt.R;
import com.Polynt.utils.LogUtil;

/**
 * Created by Alex on 6/3/2015.
 */
public class TradeNameContainerFragment extends BaseContainerFragment{
    private boolean mIsViewInited;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.container_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d("ProductNameContainerFragment onActivityCreated");
        if (!mIsViewInited){
            mIsViewInited = true;
            initView();
        }
    }

    private void initView(){
        replaceFragment(new TradeNameFragment(), false);
    }
}
