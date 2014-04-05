package com.mendhak.gpslogger.views;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import com.mendhak.gpslogger.R;
import com.mendhak.gpslogger.common.Session;
import com.mendhak.gpslogger.views.component.ToggleComponent;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oceanebelle on 03/04/14.
 */
public class GpsSimpleViewFragment extends GenericViewFragment  {

    Context context;

    SensorManager sensorManager;
    Compass myCompass;


    private View rootView;
    private ToggleComponent toggleComponent;

    public static final GpsSimpleViewFragment newInstance() {

        GpsSimpleViewFragment fragment = new GpsSimpleViewFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("a_number",1);

        fragment.setArguments(bundle);
        return fragment;


    }

    public GpsSimpleViewFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Inflates the simple layout

        rootView = inflater.inflate(R.layout.fragment_simple_view, container, false);

        // Toggle the play and pause.
        toggleComponent.getBuilder()
                .addOnView(rootView.findViewById(R.id.simple_play))
                .addOffView(rootView.findViewById(R.id.simple_stop))
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

        if(Session.hasValidLocation()){
            SetLocation(Session.getCurrentLocationInfo());
        }


        if(getActivity() != null){
            this.context = getActivity().getApplicationContext();
            myCompass = (Compass) rootView.findViewById(R.id.mycompass);

        }


        return rootView;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
    }



    @Override
    public void SetLocation(Location locationInfo) {

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(3);

        EditText txtLatitude = (EditText)rootView.findViewById(R.id.simple_lat_text);
        txtLatitude.setText(String.valueOf(locationInfo.getLatitude()));

        EditText txtLongitude = (EditText)rootView.findViewById(R.id.simple_lon_text);
        txtLongitude.setText(String.valueOf(locationInfo.getLongitude()));

        TextView txtAccuracy = (TextView)rootView.findViewById(R.id.txtAccuracy);
        txtAccuracy.setText(nf.format(locationInfo.getAccuracy()) +  getString(R.string.meters));

        TextView txtAltitude = (TextView)rootView.findViewById(R.id.txtAltitude);
        txtAltitude.setText(nf.format(locationInfo.getAltitude()) + getString(R.string.meters));


        float speed = locationInfo.getSpeed();
        String unit;
        if (speed > 0.277)
        {
            speed = speed * 3.6f;
            unit = getString(R.string.kilometers_per_hour);
        }
        else
        {
            unit = getString(R.string.meters_per_second);
        }

        TextView txtSpeed = (TextView)rootView.findViewById(R.id.txtSpeed);
        txtSpeed.setText(String.valueOf(nf.format(speed)) + unit + "\n"
                + String.valueOf(Math.round(locationInfo.getBearing()))
                + getString(R.string.degree_symbol) );
        if(myCompass != null){
            myCompass.update((float)Math.toRadians(-1 * locationInfo.getBearing()));
        }

        TextView txtDuration = (TextView)rootView.findViewById(R.id.txtDuration);


        long startTime = Session.getStartTimeStamp();
        Date d = new Date(startTime);
        long currentTime = System.currentTimeMillis();
        String duration = getInterval(startTime, currentTime);

        txtDuration.setText(duration);

    }

    private String getInterval(long startTime, long endTime)
    {
        StringBuffer sb = new StringBuffer();
        long diff = endTime - startTime;
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if (diffDays > 0)
        {
            sb.append(diffDays + " days ");
        }
        if (diffHours > 0)
        {
            sb.append(String.format("%02d", diffHours)+":");
        }
        sb.append(String.format("%02d", diffMinutes)+":");
        sb.append(String.format("%02d", diffSeconds));
        return sb.toString();
    }

    @Override
    public void SetSatelliteCount(int count) {
        TextView txtSatelliteCount = (TextView)rootView.findViewById(R.id.txtSatelliteCount);
        txtSatelliteCount.setText(String.valueOf(count));
    }

    @Override
    public void SetLoggingStarted() {
        toggleComponent.setEnabled(true);
    }

    @Override
    public void SetLoggingStopped() {
        TextView txtSatelliteCount = (TextView)rootView.findViewById(R.id.txtSatelliteCount);
        txtSatelliteCount.setText("-");

        toggleComponent.setEnabled(false);
    }
}
