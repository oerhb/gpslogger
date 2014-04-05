package com.mendhak.gpslogger.views;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import com.mendhak.gpslogger.R;
import com.mendhak.gpslogger.common.Session;

/**
 * Created by mendhak on 31/03/14.
 */
public class GpsBigFragment extends GenericViewFragment implements View.OnClickListener {

    View rootView;


    @Override
    public void onClick(View view) {

    }

    public void setCurrentlyLogging(boolean loggingStatus) {
//        TextView txt = (TextView)rootView.findViewById(R.id.textViewLocation);
//        txt.setText("Started logging " + String.valueOf(loggingStatus));
    }

    public void setLocationInfo(Location currentLocationInfo) {
        if(currentLocationInfo != null){
            TextView txtLat = (TextView)rootView.findViewById(R.id.textViewLat);
            txtLat.setText(String.valueOf(currentLocationInfo.getLatitude()));

            TextView txtLong = (TextView)rootView.findViewById(R.id.textViewLong);
            txtLong.setText(String.valueOf(currentLocationInfo.getLongitude()));
        }

    }



    public static final GpsBigFragment newInstance() {

        GpsBigFragment fragment = new GpsBigFragment();
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


        setCurrentlyLogging(Session.isStarted());
        setLocationInfo(Session.getCurrentLocationInfo());



//        Button btnStart = (Button) rootView.findViewById(R.id.buttonStart);
//        btnStart.setText("Found it");
//        btnStart.setOnClickListener(this);

//            textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getArguments().getString("parent_message"));

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

    }

    @Override
    public void onDetach() {
        super.onDetach();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);


        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

    }

}
