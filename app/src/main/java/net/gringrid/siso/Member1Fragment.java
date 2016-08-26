package net.gringrid.siso;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;



/**
 * A simple {@link Fragment} subclass.
 */
public class Member1Fragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "jiho";
    LinearLayout ll_parent;
    LinearLayout ll_sitter;

    public Member1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member1, container, false);
    }

    @Override
    public void onResume() {
        ll_parent = (LinearLayout) getView().findViewById(R.id.ll_parent);
        ll_sitter = (LinearLayout) getView().findViewById(R.id.ll_sitter);

        ll_parent.setOnClickListener(this);
        ll_sitter.setOnClickListener(this);

        super.onResume();
    }

    @Override
    public void onClick(View v) {

        Member2Fragment fragment = new Member2Fragment();

        switch (v.getId()){
            case  R.id.ll_parent:
                ((BaseActivity)getActivity()).setFragment(fragment, "무료 회원가입", 0);
                break;
            case  R.id.ll_sitter:
                ((BaseActivity)getActivity()).setFragment(fragment, "무료 회원가입", 0);
                break;

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "fragments onOptionsItemSelected: ");
        return super.onOptionsItemSelected(item);

    }

}
