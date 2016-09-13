package net.gringrid.siso;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gringrid.siso.fragments.Member2Fragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class MemberActivityFragment extends Fragment {

    private static final String TAG = "jiho";

    public MemberActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member, container, false);
    }

    @Override
    public void onResume() {
        TextView id_tv_test = (TextView)getView().findViewById(R.id.id_tv_test);
        id_tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Member2Fragment member1Fragment = new Member2Fragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment, member1Fragment).addToBackStack(null).commit();
            }
        });
        super.onResume();
    }
}
