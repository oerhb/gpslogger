package com.mendhak.gpslogger.views;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mendhak.gpslogger.R;
import com.mendhak.gpslogger.common.Session;
import com.mendhak.gpslogger.views.component.ToggleComponent;

/**
 * Created by oceanebelle on 03/04/14.
 */
public class GpsDetailedViewFragment extends GenericViewFragment {

    private ToggleComponent toggleComponent;
    private View rootView;

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

        rootView = inflater.inflate(R.layout.fragment_detailed_view, container, false);

        // Toggle the play and pause views.
        toggleComponent = ToggleComponent.getBuilder()
                .addOnView(rootView.findViewById(R.id.detailedview_play))
                .addOffView(rootView.findViewById(R.id.detailedview_stop))
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

        return rootView;
    }

    @Override
    public void SetLocation(Location locationInfo) {
        if (locationInfo == null)
        {
            return;
        }

        TextView tvLatitude = (TextView) rootView.findViewById(R.id.detailedview_lat_text);
        TextView tvLongitude = (TextView) rootView.findViewById(R.id.detailedview_lon_text);
        TextView tvDateTime = (TextView) rootView.findViewById(R.id.detailedview_datetime_text);

        TextView tvAltitude = (TextView) rootView.findViewById(R.id.detailedview_altitude_text);

        TextView txtSpeed = (TextView) rootView.findViewById(R.id.detailedview_speed_text);

        TextView txtSatellites = (TextView) rootView.findViewById(R.id.detailedview_satellites_text);
        TextView txtDirection = (TextView) rootView.findViewById(R.id.detailedview_direction_text);
        TextView txtAccuracy = (TextView) rootView.findViewById(R.id.simpleview_txtAccuracy);
        TextView txtTravelled = (TextView) rootView.findViewById(R.id.detailedview_travelled_text);
        TextView txtTime = (TextView) rootView.findViewById(R.id.detailedview_duration_text);
        String providerName = locationInfo.getProvider();
    }

    @Override
    public void SetSatelliteCount(int count) {

    }

    @Override
    public void SetLoggingStarted() {
        toggleComponent.SetEnabled(false);
    }

    @Override
    public void SetLoggingStopped() {
        toggleComponent.SetEnabled(true);
    }
}
