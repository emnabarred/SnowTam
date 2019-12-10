package com.emna.snowtamer.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.emna.snowtamer.Adapters.ListViewAdapter;
import com.emna.snowtamer.Adapters.RecyclerViewAdapter;
import com.emna.snowtamer.Models.Airport;
import com.emna.snowtamer.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private final String JSON_URL =
            "https://gist.githubusercontent.com/tdreyno/4278655/raw/7b0762c09b519f40397e4c3e100b097d861f5588/airports.json";
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private TextView jsontext;
    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    List<Airport> airportList= new ArrayList<Airport>();
    Button valider;
    Button valider2;
    boolean existe=false;
    int compt=0;
    Toast toast;
    LinearLayout layout;
    List<String> listAdd = new ArrayList<String>();
    private ArrayList<Airport> arraylist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout=(LinearLayout) findViewById(R.id.layout);
        valider=findViewById(R.id.valider);
        valider2=findViewById(R.id.valider2);
//jsonrequest();
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityspec();
            }
        });
        valider2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityall();
            }
        });

/* airportList = new String[]{"Orly", "Charles de Gaulle", "Beviaux",
"Frankfort", "Carthage", "Monastir"};*/
// Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);
/* for (int i = 0; i < airportList.length; i++) {
Airport air = new Airport(airportList[i]);
// Binds all strings into an array
arraylist.add(air);
}
// Pass results to ListViewAdapter Class
adapter = new ListViewAdapter(this, arraylist);
// Binds the Adapter to the ListView
list.setAdapter(adapter);*/
        jsonrequest();
// Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);
//editsearch.setQuery("aaa",true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int k=0;
                for(Airport a: airportList){
                    if(a.getName().contains(editsearch.getQuery().toString())){
                        if(k==id) {
                            editsearch.setQuery(a.getName(), true);
                        }
                        k++;
                    }}
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        for(Airport a: airportList){
            if(a.getName().contains(text)){
//adapter.filter(a.getName());
                List<Airport> air=new ArrayList<Airport>();
                Airport pp=new Airport(a.getName());
                air.add(pp);
                ListViewAdapter myadapter = new ListViewAdapter(this,air) ;
                list.setAdapter(myadapter);
            }
        }
        if(editsearch.getQuery().toString().equals("")){
            ListViewAdapter myadapter = new ListViewAdapter(this,airportList) ;
            list.setAdapter(myadapter);}
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        for(String name: listAdd){
            if(name.equals(editsearch.getQuery().toString())){
                existe=true;
            }}
        if(compt<5){
            if(existe==false){
                TextView tv = new TextView(MainActivity.this);
                tv.setTextColor(Color.WHITE);

                tv.setText(editsearch.getQuery());
                layout.addView(tv);
                compt++;
                listAdd.add(editsearch.getQuery().toString());
            }
            else{
                toast = Toast.makeText(getApplicationContext(), "already added", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else{
            toast = Toast.makeText(getApplicationContext(), "you can't add ", Toast.LENGTH_SHORT);
            toast.show();
        }
        existe=false;
        return false;
    }
    /* @Override
    public boolean onQueryTextChange(String newText) {
    String text = newText;
    adapter.filter(text);
    return false;
    }
    */
    public void openActivityspec(){
        String[] ListeEnvoye=new String[]{"","","","",""};
        int i=0;
        for(String a :listAdd){
            ListeEnvoye[i]=a;
            i++;
        }
        Intent intent=new Intent(this,SpecificAirportsActivity.class);
        intent.putExtra("P1",ListeEnvoye[0]);
        intent.putExtra("P2",ListeEnvoye[1]);
        intent.putExtra("P3",ListeEnvoye[2]);
        intent.putExtra("P4",ListeEnvoye[3]);
        intent.putExtra("P5",ListeEnvoye[4]);
        startActivity(intent);
    }
    public void openActivityall(){

        Intent intent=new Intent(this,AirportsActivity.class);

        startActivity(intent);
    }
    private void jsonrequest() {
        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null ;
                for (int i = 0 ; i < response.length(); i++ ) {
                    try {
                        jsonObject = response.getJSONObject(i) ;
                        Airport airport = new Airport() ;
                        airport.setName(jsonObject.getString("name"));
                        airport.setCountry(jsonObject.getString("country"));
                        airport.setIcao(jsonObject.getString("icao"));
                        airport.setLat(jsonObject.getString("lat"));
                        airport.setLon(jsonObject.getString("lon"));

//System.out.println("JSONNN"+airport.getName());
                        arraylist.add(airport);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                for(Airport ar: arraylist){
                    Airport airp=new Airport(ar.getName());
                    airportList.add(airp);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request) ;
        ListViewAdapter myadapter = new ListViewAdapter(this,airportList) ;
        list.setAdapter(myadapter);
    }
}
