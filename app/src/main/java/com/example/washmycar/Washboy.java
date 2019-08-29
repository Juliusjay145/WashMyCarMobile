package com.example.washmycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Washboy extends AppCompatActivity implements AdapterView.OnItemClickListener {


    GridView gv;
    SharedPreferences prf;
    ArrayList<WashboyList> list = new ArrayList<>();
    WashboyAdapter adapter;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_washboy);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        this.gv = findViewById(R.id.GridView12);
        this.adapter = new WashboyAdapter(this, list);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(this);

        String ID = getIntent().getStringExtra("stations_id");
        String sID = getIntent().getStringExtra("s_id");
        String serviceName = getIntent().getStringExtra("service_name");
        String vehicle_id = getIntent().getStringExtra("v_id");
        String stationName = getIntent().getStringExtra("s_name");

        try{
            URL url = new URL("http://192.168.43.19/washmycar/index.php/androidcontroller/get_washboy/"+ sID);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("cwowner_washboy");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                String serv_name = item.getString("employee_name");
                String serv_id = item.getString("employee_id");
                String ServiceImage = item.getString("employee_picture");
                list.add(new WashboyList(ServiceImage,serv_id,serv_name));
                adapter.notifyDataSetChanged();

            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }





    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        WashboyList selectedItem = list.get(i);
        String ID = selectedItem.getId();
        String washboy_name = selectedItem.getName();
        String sID = getIntent().getStringExtra("s_id"); //station id
        String serviceName = getIntent().getStringExtra("service_name"); //service name
        String stationName = getIntent().getStringExtra("s_name"); // station name
        String vehicle_id = getIntent().getStringExtra("v_id"); //vehicle id
        String service_id = getIntent().getStringExtra("service_id"); // service_id
        Intent intent = new Intent(this, BookingTransaction.class);
        intent.putExtra("washboy_id", ID);
        intent.putExtra("station_id", sID);
        intent.putExtra("service_name", serviceName);
        intent.putExtra("station_name", stationName);
        intent.putExtra("washboy_name", washboy_name);
        intent.putExtra("car_id", vehicle_id);
        intent.putExtra("service_id", service_id);
        startActivityForResult(intent, 1);
    }


    //menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commonmenus,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.item = item;
        int id = item.getItemId();
        if (id==R.id.maps){
            Toast.makeText(this, "Maps", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MapsActivity.class));
        }
        else
        if (id==R.id.home){
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,DashBoard.class));
        }
        else
        if (id==R.id.vehicle){
            Toast.makeText(this, "My Vehicle", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Vehicle.class));
        }
        else
        if (id==R.id.settings){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Profile.class));
        }
        else
        if (id==R.id.logout){
            Toast.makeText(this, "Thank you for Using Washmycar", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
//    end of menu
}
