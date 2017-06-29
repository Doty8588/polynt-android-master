package com.Polynt.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.Polynt.HomeActivity;

/**
 * Created by Alex on 6/3/2015.
 */
public class BaseFragment extends Fragment{

    public String BILLING_TAG = "BillingProcessor";

    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity)getActivity()).getSupportActionBar().show();
        setBackVisbility(true);
        getHomeActivity().supportInvalidateOptionsMenu();
    }

    protected void setTitle(String title){
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(title);
    }

    protected void setTitle(int resId){
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(resId);
    }

    protected void setBackVisbility(boolean show){
        if (((AppCompatActivity)getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(show);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        }

        if (!show){

        }
    }

    protected void loadingStarted(){
        if (mProgressDialog != null && mProgressDialog.isShowing())
            loadingFinished();

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
    }

    protected void loadingFinished(){
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected HomeActivity getHomeActivity(){
        return ((HomeActivity)getActivity());
    }
}
