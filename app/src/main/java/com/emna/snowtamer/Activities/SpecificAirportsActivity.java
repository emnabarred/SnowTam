package com.emna.snowtamer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.emna.snowtamer.Adapters.RecyclerViewAdapter;
import com.emna.snowtamer.Models.Airport;
import com.emna.snowtamer.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpecificAirportsActivity extends AppCompatActivity {
    private final String JSON_URL = "https://gist.githubusercontent.com/tdreyno/4278655/raw/7b0762c09b519f40397e4c3e100b097d861f5588/airports.json" ;
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<Airport> lair ;
    private RecyclerView recyclerView ;
    List<String> airs= new ArrayList<>(5);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airports);
        Bundle extras = getIntent().getExtras();
        String p1=extras.getString("P1");
        airs.add(p1);
        String p2=extras.getString("P2");
        airs.add(p2);
        String p3=extras.getString("P3");
        airs.add(p3);
        String p4=extras.getString("P4");
        airs.add(p4);
        String p5=extras.getString("P5");
        airs.add(p5);
        recyclerView = findViewById(R.id.recyclerviewid);
        getAirList();


    }

    private void getAirList() {

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                lair = new ArrayList<>(5);
                JSONObject jsonObject  = null ;
                for (String a : airs) {
                for (int i = 0 ; i < response.length(); i++ ) {
                    try {
                        jsonObject = response.getJSONObject(i) ;
                        Airport airport = new Airport() ;



                            if(jsonObject.getString("name").equals(a) && !a.isEmpty()){

                            airport.setUrlimage("https://source.unsplash.com/1600x900/?" + jsonObject.getString("name") + " Airport");
                            airport.setName(jsonObject.getString("name"));
                            airport.setCountry(jsonObject.getString("country"));
                            airport.setIcao(jsonObject.getString("icao"));
                            airport.setLat(jsonObject.getString("lat"));
                            airport.setLon(jsonObject.getString("lon"));
                            lair.add(airport);

                        }








                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                }
               // lair = lair.subList(0, airs.size());
               setuprecyclerview(lair);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(SpecificAirportsActivity.this);
        requestQueue.add(request) ;

    }



    private void setuprecyclerview(List<Airport> lair) {


        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,lair) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }
}

