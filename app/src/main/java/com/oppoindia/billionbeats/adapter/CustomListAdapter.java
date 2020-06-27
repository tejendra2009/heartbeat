package com.oppoindia.billionbeats.adapter;

import java.util.List;


import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oppoindia.billionbeats.R;
import com.oppoindia.billionbeats.data.LeaderBoard;

public class CustomListAdapter extends BaseAdapter{
    private Context activity;
    private List<LeaderBoard> couponItems;

    private LayoutInflater inflater;
    public CustomListAdapter(Context activity, List<LeaderBoard> data) {
        // TODO Auto-generated constructor stub
        this.activity=activity;
        this.couponItems=data;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return couponItems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return couponItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        if(inflater==null)
            inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView=inflater.inflate(R.layout.list_row,null,true);
        int lastpostion = couponItems.size() -1;

        int lastposition1=couponItems.size() -2;

        TextView rank=(TextView)convertView.findViewById(R.id.rank);
        TextView name=(TextView)convertView.findViewById(R.id.name);

        LeaderBoard c = couponItems.get(position);
        if(c.getRank()!=null){
String rankValue=String.valueOf(c.getRank());}

String nameValue=c.getName();
nameValue="               "+nameValue;
  /*      if(position==0){
            name.setGravity(Gravity.CENTER_HORIZONTAL);
String namevalue1=c.getName();
namevalue1="     "+namevalue1+"             ";
            name.setText(namevalue1);

            name.setTextSize(22);
            rank.setVisibility(View.INVISIBLE);
            name.setTypeface(null, Typeface.BOLD);
        }*/
  if(position==0){
            String nameValue1=c.getName();
            nameValue1=" "+nameValue1;
    rank.setTypeface(null, Typeface.BOLD);
    rank.setText(c.getRank());
    rank.setTextSize(18);
    name.setText(nameValue1);
    name.setTextSize(18);

    name.setTypeface(null, Typeface.BOLD);
}

else if(position==lastposition1){
rank.setText("");
name.setText("");

}
else if(position==lastpostion){
    rank.setTypeface(null, Typeface.BOLD);
    rank.setText(c.getRank());
rank.setTextSize(15);
nameValue="         "+c.getName();
    name.setText(nameValue);
    name.setTextSize(15);
    name.setTypeface(null, Typeface.BOLD);
}else{
   rank.setText(c.getRank());

        name.setText(nameValue);
}



        return convertView;
    }
   /* @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return true;
    }*/

}
