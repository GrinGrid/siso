package net.gringrid.siso.fragments;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.gringrid.siso.R;

/**
 * Created by choijiho on 16. 12. 23..
 */
public class SisoStaticViewFragment extends Fragment {

    public int mViewId;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;

    public SisoStaticViewFragment() {
        // Required empty public constructor
    }

    public SisoStaticViewFragment(int viewId){
        mViewId = viewId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mInflater = inflater;
        mContainer = container;
        return inflater.inflate(mViewId, container, false);
    }

    public void setView(int viewId){
        mViewId = viewId;
        mInflater.inflate(mViewId, mContainer, false);
    }
}

