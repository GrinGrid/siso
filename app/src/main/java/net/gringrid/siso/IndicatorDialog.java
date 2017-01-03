package net.gringrid.siso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;
import net.gringrid.siso.views.SisoIndicator;


/**
 * Setting : content, button
 *      Content : layout inflate
 *      Button : color, Text, listener, tag, order
 */
public class IndicatorDialog extends DialogFragment{

    /**
     * Interface definition for a callback to be invoked when a view is clicked.
     */
    public interface UseIndicator{
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        void showIndicator();
        void hideIndicator();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SisoIndicator sisoIndicator;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view  = layoutInflater.inflate(R.layout.loading, null);
        sisoIndicator = (SisoIndicator)view.findViewById(R.id.id_indicator);

        builder.setView(view);
        sisoIndicator.show();
        return builder.create();
    }

}
