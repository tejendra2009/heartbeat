package com.oppoindia.billionbeats.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;


import com.commonsware.cwac.provider.StreamProvider;

import com.oppoindia.billionbeats.ApplicationInit;
import com.oppoindia.billionbeats.R;


public class ShareFragment extends Fragment {
    private static final String AUTHORITY =
            "com.oppoindia.billionbeats";
    private static final Uri PROVIDER =
            Uri.parse("content://" + AUTHORITY);
    private static final String[] ASSET_PATHS = {
            "assets/video_new.mp4",


    };
    private static final String[] PROJECTION = {
            OpenableColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.MIME_TYPE,
            OpenableColumns.SIZE
    };
    Button shareButton;

    int i = 0;

    public ShareFragment() {
        // Required empty public constructor
    }

    public static ShareFragment newInstance(String param1, String param2) {
        ShareFragment fragment = new ShareFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // String number = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("LAST_MEASURE", "0");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        shareButton = view.findViewById(R.id.share);


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    sendAsset(v , 0);

            }
        });

        /*imgClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1;
                activateBorder(i);
            }
        });
        imgClick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 2;
                activateBorder(i);
            }
        });*/
        return view;
    }




    /*private void activateBorder(int i) {
        if (i == 1) {
            imgView1.setVisibility(View.VISIBLE);
            imgClick1.setVisibility(View.GONE);
            imgView2.setVisibility(View.GONE);
            imgClick2.setVisibility(View.VISIBLE);
        } else if (i == 2) {
            imgClick2.setVisibility(View.GONE);
            imgView2.setVisibility(View.VISIBLE);
            imgView1.setVisibility(View.GONE);
            imgClick1.setVisibility(View.VISIBLE);

        }
    }*/

    public void sendAsset(View v ,int file) {

        String path = ASSET_PATHS[file];

        Intent share = new Intent(Intent.ACTION_SEND);

        String extension = null;

        int i = path.lastIndexOf('.');


        if (i > 0) {

            extension = path.substring(i + 1);

        }


        if (extension != null) {

            share.setType(

                    MimeTypeMap.getSingleton().getMimeTypeFromExtension(

                            extension));

        }


        share.putExtra(Intent.EXTRA_STREAM, getUri(file));
        String refferal_code_value = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext()).getString("refferal", "");
      //  String shareBody = " Download the OPPO Billion Beats App to share your heartbeat."+" Use referral code "+refferal_code_value+" at Sign Up. #OPPO #BillionBeats "+"http://tiny.cc/kzbi7y ";
String shareBody="Team India needs you! Download the OPPO Billion Beats app to share your heartbeat. Use referral code " +refferal_code_value+ " at Sign-Up. #OPPO #BillionBeats http://bit.ly/OPPOBillionBeats ";

        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        share.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(share, "Share BillionBeats"));

    }

    private Uri getUri(int i) {

        String path = ASSET_PATHS[i];


        return (PROVIDER

                .buildUpon()

                .appendPath(StreamProvider.getUriPrefix(AUTHORITY))

                .appendPath(path)

                .build());

    }

}