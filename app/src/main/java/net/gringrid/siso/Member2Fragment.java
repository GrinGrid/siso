package net.gringrid.siso;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class Member2Fragment extends Fragment implements View.OnClickListener {

    TextView id_tv_next_btn;

    public Member2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member2, container, false);
    }



    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        super.onResume();
    }

    @Override
    public void onClick(View v) {

        Member3Fragment fragment = new Member3Fragment();

        switch (v.getId()){
            case  R.id.id_tv_next_btn:
                ((BaseActivity)getActivity()).setFragment(fragment, "무료 회원가입", 0);
                break;

        }
    }

}
