package com.mendhak.gpslogger.views;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import com.mendhak.gpslogger.R;
import com.mendhak.gpslogger.common.Session;
import com.mendhak.gpslogger.common.Utilities;

import java.text.NumberFormat;

/**
 * Created by mendhak on 31/03/14.
 */
public class GpsBigViewFragment extends GenericViewFragment implements View.OnTouchListener {

    View rootView;



    public static final GpsBigViewFragment newInstance() {
        GpsBigViewFragment fragment = new GpsBigViewFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("a_number", 1);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_big_view, container, false);

        TextView txtLat = (TextView)rootView.findViewById(R.id.bigview_text_lat);
        txtLat.setOnTouchListener(this);

        TextView txtLong = (TextView)rootView.findViewById(R.id.bigview_text_long);
        txtLong.setOnTouchListener(this);

        SetLocation(Session.getCurrentLocationInfo());

        //Toast.makeText(getActivity().getApplicationContext(), R.string.bigview_taptotoggle, Toast.LENGTH_LONG).show();

        return rootView;
    }

    @Override
    public void SetLocation(Location locationInfo) {
        TextView txtLat = (TextView)rootView.findViewById(R.id.bigview_text_lat);
        TextView txtLong = (TextView)rootView.findViewById(R.id.bigview_text_long);

        if(locationInfo != null){
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(6);

            txtLat.setText(String.valueOf(nf.format(locationInfo.getLatitude())));

            txtLong.setText(String.valueOf(nf.format(locationInfo.getLongitude())));
        }
        else
        {
            txtLat.setText(R.string.bigview_taptotoggle);
        }
    }

    @Override
    public void SetSatelliteCount(int count) {

    }

    @Override
    public void SetLoggingStarted() {
        TextView txtLat = (TextView)rootView.findViewById(R.id.bigview_text_lat);
        TextView txtLong = (TextView)rootView.findViewById(R.id.bigview_text_long);
        txtLat.setText("");
        txtLong.setText("");
    }

    @Override
    public void SetLoggingStopped() {

    }

    @Override
    public void SetStatusMessage(String message) {

    }

    @Override
    public void SetFatalMessage(String message) {

    }

    @Override
    public void OnFileNameChange(String newFileName) {

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            Utilities.LogDebug("Big frame - onTouch event");
            requestToggleLogging();
            return true;
        }

        return false;

    }
}
