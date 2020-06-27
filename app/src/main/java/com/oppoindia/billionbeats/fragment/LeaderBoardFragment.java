package com.oppoindia.billionbeats.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import com.oppoindia.billionbeats.ApplicationInit;
import com.oppoindia.billionbeats.R;
import com.oppoindia.billionbeats.adapter.CustomListAdapter;
import com.oppoindia.billionbeats.data.LeaderBoard;
import com.oppoindia.billionbeats.model.GlobalConst;
import com.oppoindia.billionbeats.ui.login.Util;


public class LeaderBoardFragment extends Fragment {

    public LeaderBoardFragment() {
        // Required empty public constructor
    }
    private ListView listView;
    private List<LeaderBoard> itemsList;
    private CustomListAdapter adapter;
    public static LeaderBoardFragment newInstance(String param1, String param2) {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board, container, false);


        itemsList = new ArrayList<>();

        adapter = new CustomListAdapter(getActivity(),itemsList);

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
       // recyclerView.setItemAnimator(new DefaultItemAnimator());

        if(Util.isInternetAvailable(ApplicationInit.getAppContext())){
            fetchStoreItems();
        }else{
            String response = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext()).getString("leaderboard", "");
            List<LeaderBoard> items = new Gson().fromJson(response.toString(), new TypeToken<List<LeaderBoard>>() {
            }.getType());


            itemsList.clear();
            LeaderBoard leader=new LeaderBoard();
            leader.setName("Name");
            leader.setRank("Rank");

            items.add(0,leader);

            itemsList.addAll(items);
            adapter.notifyDataSetChanged();
            Toast.makeText(ApplicationInit.getAppContext(), "No Internet Connection.", Toast.LENGTH_LONG).show();
        }



        return view;
    }

    private void fetchStoreItems() {
        String email = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext()).getString("email", "girish@gmail.com");

        JsonArrayRequest request = new JsonArrayRequest(GlobalConst.APIURL+"leaderboardmob?email="+email,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {

                            Toast.makeText(getActivity(), "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }
                       // Log.e("RESPONSE", "Response: " + response.toString());
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationInit.getAppContext());
                        sharedPreferences.edit().putString("leaderboard",response.toString()).commit();

                        List<LeaderBoard> items = new Gson().fromJson(response.toString(), new TypeToken<List<LeaderBoard>>() {
                        }.getType());

                        LeaderBoard leader=new LeaderBoard();
                        leader.setName("Name");
                        leader.setRank("Rank");

                        items.add(0,leader);

                        itemsList.clear();
                        itemsList.addAll(items);
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                error.printStackTrace();
               Log.e("ERROR", "Error: " + error.toString());
               // Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        })

        {


        };



        ApplicationInit.getAppContext().addToRequestQueue(request);
    }




}
