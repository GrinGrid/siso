package net.gringrid.siso.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.gringrid.siso.R;
import net.gringrid.siso.adapter.SitterListSisoAdapter;
import net.gringrid.siso.models.SitterList;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SitterListSIsoFragment extends Fragment {

    private SitterListSisoAdapter mAdapter;
    private List<SitterList> mList;
    private ListView id_lv;

    public static SitterListSIsoFragment newInstance(int page, int titleRscId){
        SitterListSIsoFragment sitterListSIsoFragment = new SitterListSIsoFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putInt("someTitle", titleRscId);
        sitterListSIsoFragment.setArguments(args);
        return sitterListSIsoFragment;
    }

    public SitterListSIsoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mList = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_sitter_list_siso, container, false);
    }

    @Override
    public void onResume() {
        for(int i=0;i<100;i++){
            SitterList item = new SitterList();
            item.name = "이순자 시터(23세)";
            item.brief = "전직 유치원 교사!  아이들과 잘 놀아요!아이들과 잘 놀아요!";
            item.addr1 = "경기도 > 읜왕시 > 내손도옹  0.4km";
            mList.add(item);
        }
        mAdapter = new SitterListSisoAdapter(getContext(), mList);
        id_lv = (ListView)getView().findViewById(R.id.id_lv);
        id_lv.setAdapter(mAdapter);
        super.onResume();
    }
}
