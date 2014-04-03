package com.mendhak.gpslogger.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import com.mendhak.gpslogger.R;

/**
 * Created by oceanebelle on 03/04/14.
 */
public class GpsSimpleViewFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Inflates the simple layout

        View mainView = inflater.inflate(R.layout.fragment_simple_view, container, false);

        TableRow rowRose = (TableRow)mainView.findViewById(R.id.rowRose);
        Compass myCompass = (Compass)mainView.findViewById(R.id.mycompass);
        myCompass.update((float)Math.toRadians(-221));

        return mainView;
    }


}
