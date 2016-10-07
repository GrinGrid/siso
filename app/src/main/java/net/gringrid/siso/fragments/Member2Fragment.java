package net.gringrid.siso.fragments;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.R;
import net.gringrid.siso.models.Personal;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Member2Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "jiho";

    Personal mPersonal;
    Gson mGson;
    TextView id_tv_next_btn;

    SisoEditText id_et_name;
    SisoEditText id_et_birth;

    public Member2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        String memberStr = SharedData.getInstance(getContext()).getGlobalDataString(SharedData.PERSONAL);

        mGson = new Gson();
        if ( memberStr != null ){
            mPersonal = mGson.fromJson(memberStr, Personal.class);
        }else{
            mPersonal = new Personal();
        }
        Log.d(TAG, "onCreate: Personal.getUserType : "+mPersonal.user_type);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_member2, container, false);
        setScrollControl(view);
        return view;
    }

    /**
     * 소프트키보드에 따라 스크롤 컨트롤
     */
    private void setScrollControl(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);

                int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
                final ScrollView id_sv = (ScrollView)view.findViewById(R.id.id_sv);
                // softkey visible
                if ( heightDiff > 500 ){
                    id_sv.scrollTo(0, 500);
                    Log.d(TAG, "onGlobalLayout: softkey");
                }else{
                    id_sv.scrollTo(0, 0);
                }
            }
        });

    }


    @Override
    public void onResume() {
        id_tv_next_btn = (TextView) getView().findViewById(R.id.id_tv_next_btn);
        id_tv_next_btn.setOnClickListener(this);

        id_et_name = (SisoEditText) getView().findViewById(R.id.id_et_name);
        id_et_birth = (SisoEditText) getView().findViewById(R.id.id_et_birth);

//        id_et_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Log.d(TAG, "onFocusChange: name");
//            }
//        });
//
//        id_et_birth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if ( hasFocus ){
//                    setInputMode();
//                }
//                Log.d(TAG, "onFocusChange: birth");
//            }
//        });

        super.onResume();
    }

    private void setInputMode() {
//        final ScrollView id_sv = (ScrollView)getView().findViewById(R.id.id_sv);
//        id_sv.scrollTo(0, 500);
//        id_sv.post(new Runnable() {
//            @Override
//            public void run() {
//                id_sv.scrollTo(0, -1000);
//            }
//        });
    }

    @Override
    public void onClick(View v) {

        Member3Fragment fragment = new Member3Fragment();

        switch (v.getId()) {
            case R.id.id_tv_next_btn:
                if ( SharedData.DEBUG_MODE ) {
                    id_et_name.setInput("최지호");
                    id_et_birth.setInput("19801022");
                }
                String errorMsg = getInvalidInputMessage();
                if (errorMsg != null) {
                    Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
                    return;
                }
                mPersonal.name = id_et_name.getText().toString();
                mPersonal.birth_date = Integer.parseInt(id_et_birth.getText().toString());
                SharedData.getInstance(getContext()).insertGlobalData(SharedData.PERSONAL, mGson.toJson(mPersonal));
                ((BaseActivity) getActivity()).setFragment(fragment, R.string.member_title);
                break;

        }
    }

    public String getInvalidInputMessage() {

        if (TextUtils.isEmpty(id_et_name.getText())){
            return "이름을 입력해주시기 바랍니다.";
        }else if(TextUtils.isEmpty(id_et_birth.getText())){
            return "생년월일을 정확히 입력해주시기 바랍니다.";
        }
        return null;
    }
}
