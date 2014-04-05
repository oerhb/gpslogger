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

import java.text.NumberFormat;

/**
 * Created by oceanebelle on 03/04/14.
 */
public class GpsSimpleViewFragment extends GenericViewFragment implements SensorEventListener {

    Context context;

    SensorManager sensorManager;
    Compass myCompass;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagneticField;
    private float[] valuesAccelerometer;
    private float[] valuesMagneticField;
    private float[] matrixR;
    private float[] matrixI;
    private float[] matrixValues;

    private View rootView;

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
        ToggleComponent.getBuilder()
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

        if(getActivity() != null){
            this.context = getActivity().getApplicationContext();

            TableRow rowRose = (TableRow) rootView.findViewById(R.id.rowRose);
            myCompass = (Compass) rootView.findViewById(R.id.mycompass);


            sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
            sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

            valuesAccelerometer = new float[3];
            valuesMagneticField = new float[3];

            matrixR = new float[9];
            matrixI = new float[9];
            matrixValues = new float[3];
        }


        return rootView;
    }

    @Override
    public void onResume() {
        sensorManager.registerListener(this,
                sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorMagneticField,
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    public void onPause() {
        sensorManager.registerListener(this,
                sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorMagneticField,
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                for (int i = 0; i < 3; i++) {
                    valuesAccelerometer[i] = event.values[i];
                }
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                for (int i = 0; i < 3; i++) {
                    valuesMagneticField[i] = event.values[i];
                }
                break;
        }

        boolean success = SensorManager.getRotationMatrix(
                matrixR,
                matrixI,
                valuesAccelerometer,
                valuesMagneticField);

        if (success) {
            SensorManager.getOrientation(matrixR, matrixValues);

            double azimuth = Math.toDegrees(matrixValues[0]);
            double pitch = Math.toDegrees(matrixValues[1]);
            double roll = Math.toDegrees(matrixValues[2]);


            if(myCompass != null){
                myCompass.update(matrixValues[0]);
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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
        txtAccuracy.setText(getString(R.string.accuracy_within, nf.format(locationInfo.getAccuracy()),
                getString(R.string.meters)));

    }

    @Override
    public void SetSatelliteCount(int count) {
        TextView txtSatelliteCount = (TextView)rootView.findViewById(R.id.txtSatelliteCount);
        txtSatelliteCount.setText(String.valueOf(count));
    }

    @Override
    public void SetLoggingStarted() {

    }

    @Override
    public void SetLoggingStopped() {
        TextView txtSatelliteCount = (TextView)rootView.findViewById(R.id.txtSatelliteCount);
        txtSatelliteCount.setText("-");
    }
}
