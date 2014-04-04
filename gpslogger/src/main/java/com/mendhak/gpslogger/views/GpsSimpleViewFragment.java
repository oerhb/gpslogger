package com.mendhak.gpslogger.views;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import com.mendhak.gpslogger.R;

/**
 * Created by oceanebelle on 03/04/14.
 */
public class GpsSimpleViewFragment extends Fragment implements SensorEventListener {

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

    public GpsSimpleViewFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Inflates the simple layout

        View mainView = inflater.inflate(R.layout.fragment_simple_view, container, false);

        TableRow rowRose = (TableRow) mainView.findViewById(R.id.rowRose);
        myCompass = (Compass) mainView.findViewById(R.id.mycompass);


        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        valuesAccelerometer = new float[3];
        valuesMagneticField = new float[3];

        matrixR = new float[9];
        matrixI = new float[9];
        matrixValues = new float[3];

//        myCompass.update((float)Math.toRadians(-221));

        return mainView;
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
}