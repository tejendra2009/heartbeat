package com.oppoindia.billionbeats.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import com.oppoindia.billionbeats.Measure;
import com.oppoindia.billionbeats.R;


public class HeartFragment extends Fragment {

    Button buttonBPM;
    public static final int REQUEST_CODE = 101;

    private String[] neededPermissions = new String[]{Manifest.permission.CAMERA};


    public static HeartFragment newInstance(String param1, String param2) {
        HeartFragment fragment = new HeartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //above part is to determine which fragment is in your frame_container



    }




    private void takeHeartBeat() {
        Intent intent;
        intent = new Intent(getActivity(), Measure.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heart, container, false);
        // Inflate the layout for this fragment
        buttonBPM = (Button) view.findViewById(R.id.start);




        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.start:
                        if(checkPermission()){
                        takeHeartBeat();}else{
                            requestPermissions(neededPermissions);
                        }
                        break;


                }
            }
        };

        buttonBPM.setOnClickListener(handler);

        return view;
    }
    private boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            ArrayList<String> permissionsNotGranted = new ArrayList<>();
            for (String permission : neededPermissions) {
                if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNotGranted.add(permission);
                }
            }
            if (permissionsNotGranted.size() > 0) {
                boolean shouldShowAlert = false;
                for (String permission : permissionsNotGranted) {
                    shouldShowAlert = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission);
                }
                if (shouldShowAlert) {
                  //  showPermissionAlert(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]));
                } else {
                    requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]));
                }
                return false;
            }
        }
        return true;
    }

   /* private void showPermissionAlert(final String[] permissions) {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(R.string.permission_required);
        alertBuilder.setMessage(R.string.permission_message);
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {




            }

        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }*/

    private void requestPermissions(String[] permissions) {
       ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("result",String.valueOf(requestCode)+" "+permissions[0]+" "+String.valueOf(grantResults.length));
        if(requestCode == REQUEST_CODE){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                takeHeartBeat();
            }
            else{
                Toast.makeText(getActivity(), "Camera require for this feature", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
