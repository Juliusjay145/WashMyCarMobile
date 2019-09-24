package com.example.washmycar;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
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

public class CarWashStationService extends AppCompatActivity implements AdapterView.OnItemClickListener {


    GridView gv;
    SharedPreferences prf;
    ArrayList<ServiceList> list = new ArrayList<>();
    ServiceAdapter adapter;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_service);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Station Services");
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        this.gv = findViewById(R.id.GridView1);
        this.adapter = new ServiceAdapter(this, list);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(this);

        String ID = getIntent().getStringExtra("stations_id");
        String sname = getIntent().getStringExtra("st_name");
        String schedule_id = getIntent().getStringExtra("schedule_id");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String wallet = getIntent().getStringExtra("wallet");
        Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();

        try{
            URL url = new URL("http://192.168.43.19/washmycar/index.php/androidcontroller/get_service/"+ ID);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("cwowner_services");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                String serv_name = item.getString("service_name");
                String serv_price = item.getString("service_price");
                String serv_id = item.getString("service_id");
                String ServiceImage = item.getString("service_picture");
                String pest = item.getString("station_id");
                list.add(new ServiceList(ServiceImage,serv_id,serv_name,serv_price,pest));
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

        ServiceList selectedItem = list.get(i);
        String ID = selectedItem.getId();
        String StationName = selectedItem.getName();
        String sID = getIntent().getStringExtra("stations_id");
        String schedule_id = getIntent().getStringExtra("schedule_id");
        String price = selectedItem.getPrice();
        String sname = getIntent().getStringExtra("st_name");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String wallet = getIntent().getStringExtra("wallet");
        Intent intent = new Intent(this, MyVehicle.class);
        intent.putExtra("service_id", ID);
        intent.putExtra("sta_id", sID);
        intent.putExtra("service_name", StationName);
        intent.putExtra("station_name", sname);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("schedule_id", schedule_id);
        intent.putExtra("price", price);
        intent.putExtra("wallet", wallet);
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
        if (id==R.id.favorites){
            Toast.makeText(this, "My Favorites", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MyFavorites.class));
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
