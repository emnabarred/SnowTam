package com.emna.snowtamer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class AirportsActivity extends AppCompatActivity {
    private final String JSON_URL = "https://gist.githubusercontent.com/tdreyno/4278655/raw/7b0762c09b519f40397e4c3e100b097d861f5588/airports.json" ;
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<Airport> lair;
    private RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airports);
        lair = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);
        getAirList();
    }


    private void getAirList() {

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject  = null ;

                for (int i = 0 ; i < response.length(); i++ ) {

                    try {
                        jsonObject = response.getJSONObject(i) ;
                        Airport airport = new Airport() ;
                        airport.setCountry(jsonObject.getString("country"));
                        airport.setIcao(jsonObject.getString("icao"));
                        airport.setLat(jsonObject.getString("lat"));
                        airport.setLon(jsonObject.getString("lon"));

                        if (jsonObject.getString("name").endsWith("Airport") == false){
                            airport.setUrlimage("https://source.unsplash.com/1600x900/?"+jsonObject.getString("name")+" Airport");
                            airport.setName(jsonObject.getString("name")+" Airport");
                        }else{
                            airport.setUrlimage("https://source.unsplash.com/1600x900/?"+jsonObject.getString("name"));
                            airport.setName(jsonObject.getString("name"));

                        }
                        lair.add(airport);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                setuprecyclerview(lair);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        requestQueue = Volley.newRequestQueue(AirportsActivity.this);
        requestQueue.add(request) ;

    }




    private void setuprecyclerview(List<Airport> lair) {


        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,lair) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }
}
