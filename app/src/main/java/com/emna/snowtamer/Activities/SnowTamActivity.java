package com.emna.snowtamer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.emna.snowtamer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class SnowTamActivity extends AppCompatActivity {
    private String api = "ec8a18a0-1641-11ea-ab2e-bf2aa2669a71";
    private String oaci = "";
    private String name = "";
    private String URL = "https://v4p4sz5ijk.execute-api.us-east-1.amazonaws.com/anbdata/states/notams/notams-realtime-list?api_key=";
    private String JSON_URL;
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private TextView snowtam;
    private ToggleButton decode;
    String snowtamresult = "";
    String decodedsnowtam = "";
    String newLine = System.getProperty("line.separator");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snowtam);
        snowtam = findViewById(R.id.snowtam);
        decode = findViewById(R.id.decode);
        decode.setTextOff("Decode");
        decode.setTextOn("Raw SnowTam");
        Intent i= getIntent();
        Bundle b = i.getExtras();
        if(b!=null)
        {
            oaci = (String) b.get("icao");
            name = (String) b.get("name");
        }
        JSON_URL = URL + api + "&format=json&criticality=&locations="+oaci+"&qstring=&states=&ICAOonly=";
        jsonrequest();
        decode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    decoder(snowtamresult);
                } else {
                    setupRawSnowTam(snowtamresult);
                }
            }
        });
    }

    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject  = null ;

                for (int i = 0 ; i < response.length(); i++ ) {


                    try {

                        jsonObject = response.getJSONObject(i);
                        if(jsonObject.getString("all").contains("SNOW")){

                            snowtamresult = jsonObject.getString("all");
                            setupRawSnowTam(snowtamresult);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setupRawSnowTam(snowtamresult);


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(SnowTamActivity.this);
        requestQueue.add(request) ;


    }

    private void setupRawSnowTam(String json) {
        snowtam.setText(json);
        if (json == ""){
            snowtam.setText("No SnowTam");
        }
        snowtam.setMovementMethod(new ScrollingMovementMethod());
    }

    private void decoder(String rawsnowtam){

        decodedsnowtam = "";

        String[] infos = rawsnowtam.trim().split("\\)");
        for (int i = 1; i < infos.length; i++) {

            if (stringLastCharacter(infos[i - 1]).equals("A")) {
                decodedsnowtam = decodedsnowtam +  newLine +"A :   " + name;
            }

            if (stringLastCharacter(infos[i - 1]).equals("B")) {
                String time = stringWithoutLastCharacter(infos[i]);
                String month = time.substring(0,2);
                String day = time.substring(2,4);
                String hour = time.substring(4,6);
                String mins = time.substring(6,8);
                decodedsnowtam = decodedsnowtam +  newLine +"B :   " + month+"/"+day+"  At  "+ hour+":"+mins;

            }

            if (stringLastCharacter(infos[i - 1]).equals("C")) {
                    decodedsnowtam = decodedsnowtam +  newLine+"C :  Runway "  +stringWithoutLastCharacter(infos[i]);
            }

            if (stringLastCharacter(infos[i - 1]).equals("E")) {
                decodedsnowtam = decodedsnowtam +  newLine+"E :   "  +stringWithoutLastCharacter(infos[i]);
            }

            if (stringLastCharacter(infos[i - 1]).equals("F")) {

                String f =stringWithoutLastCharacter(infos[i]);
                decodedsnowtam = decodedsnowtam +  newLine+"F :   ";

                String[] farray = rawsnowtam.trim().split("\\/");
                for (int j = 1; j < farray.length; j++) {
                    if (stringLastCharacter(farray[j - 1]).equals("0")){
                        decodedsnowtam = decodedsnowtam +"CLEAR AND DRY"+"/";
                    }
                    if (stringLastCharacter(farray[j - 1]).equals("1")){
                        decodedsnowtam = decodedsnowtam +"DAMP"+"/";
                    }
                    if (stringLastCharacter(farray[j - 1]).equals("2")){
                        decodedsnowtam = decodedsnowtam +"WET or WATER PATCHES"+"/";
                    }
                    if (stringLastCharacter(farray[j - 1]).equals("3")){
                        decodedsnowtam = decodedsnowtam +"RIME OR FROST COVERED"+"/";
                    }
                    if (stringLastCharacter(farray[j - 1]).equals("4")){
                        decodedsnowtam = decodedsnowtam +"DRY SNOW"+"/";
                    }
                    if (stringLastCharacter(farray[j - 1]).equals("5")){
                        decodedsnowtam = decodedsnowtam +"WET SNOW"+"/";
                    }
                    if (stringLastCharacter(farray[j - 1]).equals("6")){
                        decodedsnowtam = decodedsnowtam +"SLUSH"+"/";
                    }
                    if (stringLastCharacter(farray[j - 1]).equals("7")){
                        decodedsnowtam = decodedsnowtam +"ICE"+"/";
                    }
                    if (stringLastCharacter(farray[j - 1]).equals("8")){
                        decodedsnowtam = decodedsnowtam +"COMPACTED OR ROLLED SNOW"+"/";
                    }
                    if (stringLastCharacter(farray[j - 1]).equals("9")){
                        decodedsnowtam = decodedsnowtam +"FROZEN RUTS OR RIDGES"+"/";
                    }

                }
                decodedsnowtam = stringWithoutLastCharacter(decodedsnowtam);

            }

            if (stringLastCharacter(infos[i - 1]).equals("G")) {

                decodedsnowtam = decodedsnowtam +  newLine+"G :    MEAN DEPTH"  ;
                String[] garray = stringWithoutLastCharacter(infos[i]).trim().split("\\/");
                decodedsnowtam = decodedsnowtam + "DEPTH Threshold: " + garray[0] +"mm / Mid runway: "+ garray[1]+"mm / Roll out: "+garray[2];
            }

            if (stringLastCharacter(infos[i - 1]).equals("H")) {
                decodedsnowtam = decodedsnowtam +  newLine+"H :   BRAKING ACTION" ;
                String[] Harray = stringWithoutLastCharacter(infos[i]).trim().split("\\/");
                decodedsnowtam = decodedsnowtam + " Threshold: " + Harray[0] +"mm / Mid runway: "+ Harray[1]+"mm / Roll out: "+Harray[2];
            }

            if (stringLastCharacter(infos[i - 1]).equals("N")) {
                decodedsnowtam = decodedsnowtam +  newLine+"N :   " +stringWithoutLastCharacter(infos[i]);
            }

            if (stringLastCharacter(infos[i - 1]).equals("D")) {
                decodedsnowtam = decodedsnowtam +  newLine+"D :   " +stringWithoutLastCharacter(infos[i]);
            }

            if (stringLastCharacter(infos[i - 1]).equals("S")) {

                String time = stringWithoutLastCharacter(infos[i]);
                String month = time.substring(0,2);
                String day = time.substring(2,4);
                String hour = time.substring(4,6);
                String mins = time.substring(6,8);
                decodedsnowtam = decodedsnowtam +  newLine+"S :   NEXT OBSERVATION " +day+"/"+month + " At "+hour+":"+mins+" UTC";
            }

            if (stringLastCharacter(infos[i - 1]).equals("T")) {
                decodedsnowtam = decodedsnowtam +  newLine+"T :   "  +stringWithoutLastCharacter(infos[i]);
            }



        //houni
        }
        snowtam.setText(decodedsnowtam);
    }


    static public String stringLastCharacter(String myString) {
        String bit = myString.substring(myString.length() - 1);
        return bit;
    }

    static public String stringWithoutLastCharacter(String myString) {
        String bit = myString.substring(0, myString.length() - 1).trim();
        return bit;
    }
}
