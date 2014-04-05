package com.mendhak.gpslogger.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mendhak.gpslogger.R;
import com.mendhak.gpslogger.common.Session;
import com.mendhak.gpslogger.views.component.ToggleComponent;

/**
 * Created by oceanebelle on 03/04/14.
 */
public class GpsDetailedViewFragment extends GenericViewFragment {

    public static final GpsDetailedViewFragment newInstance() {

        GpsDetailedViewFragment fragment = new GpsDetailedViewFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("a_number",1);

        fragment.setArguments(bundle);
        return fragment;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Inflates the detailed layout

        View mainView = inflater.inflate(R.layout.fragment_detailed_view, container, false);

        // Toggle the play and pause views.
        ToggleComponent.getBuilder()
                .addOnView(mainView.findViewById(R.id.detailed_play))
                .addOffView(mainView.findViewById(R.id.detailed_stop))
                .setDefaultState(!Session.isStarted())
                .addHandler(new ToggleComponent.ToggleHandler() {
                    @Override
                    public void onStatusChange(boolean status) {
                        if (status) {
                            requestStartLogging();
                        } else {
                            requestStopLogging();
                        }
                    }
                })
                .build();

        return mainView;
    }
}
