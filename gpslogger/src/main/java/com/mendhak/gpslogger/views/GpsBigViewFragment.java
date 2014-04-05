package com.mendhak.gpslogger.views;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import com.mendhak.gpslogger.R;
import com.mendhak.gpslogger.common.Session;
import com.mendhak.gpslogger.common.Utilities;

/**
 * Created by mendhak on 31/03/14.
 */
public class GpsBigViewFragment extends GenericViewFragment implements View.OnTouchListener {

    View rootView;


    public void setCurrentlyLogging(boolean loggingStatus) {
//        TextView txt = (TextView)rootView.findViewById(R.id.textViewLocation);
//        txt.setText("Started logging " + String.valueOf(loggingStatus));
    }


    public static final GpsBigViewFragment newInstance() {
        GpsBigViewFragment fragment = new GpsBigViewFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("a_number",1);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_big_view, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        TextView txtLat = (TextView)rootView.findViewById(R.id.textViewLat);
        txtLat.setOnTouchListener(this);

        TextView txtLong = (TextView)rootView.findViewById(R.id.textViewLong);
        txtLong.setOnTouchListener(this);

        setCurrentlyLogging(Session.isStarted());
        SetLocation(Session.getCurrentLocationInfo());

        return rootView;
    }

    @Override
    public void SetLocation(Location locationInfo) {
        if(locationInfo != null){
            TextView txtLat = (TextView)rootView.findViewById(R.id.textViewLat);
            txtLat.setText(String.valueOf(locationInfo.getLatitude()));

            TextView txtLong = (TextView)rootView.findViewById(R.id.textViewLong);
            txtLong.setText(String.valueOf(locationInfo.getLongitude()));
        }
    }

    @Override
    public void SetSatelliteCount(int count) {

    }

    @Override
    public void SetLoggingStarted() {

    }

    @Override
    public void SetLoggingStopped() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Utilities.LogDebug("Big frame - onTouch event");
        requestToggleLogging();
        return true;
    }
}
