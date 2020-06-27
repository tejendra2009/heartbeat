package com.oppoindia.billionbeats.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.oppoindia.billionbeats.ApplicationInit;
import com.oppoindia.billionbeats.R;
import com.oppoindia.billionbeats.ui.login.Util;


public class CalendarFragment extends Fragment implements View.OnTouchListener {

    TextView textview1, textview2;
String text;
    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        textview1 = (TextView) view.findViewById(R.id.text1);
        textview2 = (TextView) view.findViewById(R.id.text2);

        CalendarView simpleCalendarView = (CalendarView) view.findViewById(R.id.simpleCalendarView);

        text=Util.getProperty("5030", ApplicationInit.getAppContext());
        textview1.setText(text);
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                month=month+1;
                String text1="";
                String text2="";
              text=  Util.getProperty(String.valueOf(month+"0"+day), ApplicationInit.getAppContext());
              if(text.contains("##")){
                   text1=text.split("##")[0];
                   text2=text.split("##")[1];
                  textview1.setText( text1);
                  textview2.setText(text2);
              }else{
                  textview2.setText("");
                  textview1.setText( text);
              }


               // Toast.makeText(getActivity(), String.valueOf(year+" "+"0"+month+" "+day), Toast.LENGTH_SHORT).show();
            }
        });

        // get the reference of CalendarView
        // long selectedDate = simpleCalendarView.getDate();
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
     /*   //new Util().onTouchButton(v, event,R.id.may30);
        switch (v.getId()){
            case R.id.may30:
                Toast.makeText(getActivity(), "*******Touch on 30 ******", Toast.LENGTH_SHORT).show();
             return false;
            case R.id.may31:
                Toast.makeText(getActivity(), "*******Touch on 31 ******", Toast.LENGTH_SHORT).show();
            case R.id.june1:
                Toast.makeText(getActivity(), "*******Touch on june1 ******", Toast.LENGTH_SHORT).show();
                return false;

        }*/

        return false;//false indicates the event is not consumed
    }
}