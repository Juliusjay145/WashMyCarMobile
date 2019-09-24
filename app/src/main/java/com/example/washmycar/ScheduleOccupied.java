package com.example.washmycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class ScheduleOccupied extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    ListView lv;
    Button btnVacant;
    SharedPreferences prf;
    ArrayList<ScheduleList> list = new ArrayList<>();
    ScheduleAdapter adapter;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_occupied);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        btnVacant = findViewById(R.id.vacant);
        btnVacant.setOnClickListener(this);
        getSupportActionBar().setTitle("Occupied Schedules");
        this.lv = findViewById(R.id.ListView);
        this.adapter = new ScheduleAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        String customer_id = prf.getString("seeker_id", "");
        String stations_id = getIntent().getStringExtra("stations_id");

        try{
//            URL url = new URL("http://192.168.43.118/washmycar/index.php/androidcontroller/get_carwash_station");
            URL url = new URL("http://192.168.43.19/washmycar/index.php/androidcontroller/get_schdule_occupied/"+stations_id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("cwschedule");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                String ScheduleId = item.getString("schedule_id");
                String StationId = item.getString("station_id");
                String carwash_date = item.getString("seeker_date");
                String carwash_time = item.getString("seeker_time");
                String carwash_status = item.getString("status");
                list.add(new ScheduleList(ScheduleId,carwash_date,carwash_time,carwash_status,StationId));
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

        ScheduleList selectedItem = list.get(i);
        String ID = selectedItem.getId();
        String date = selectedItem.getDate();
        String time = selectedItem.getTime();
        String stations_id = getIntent().getStringExtra("stations_id");
        String stations_name = getIntent().getStringExtra("st_name");
        Toast.makeText(getApplicationContext(), stations_id, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CarWashStationService.class);
        intent.putExtra("v_id", ID);
        intent.putExtra("stations_id", stations_id);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("schedule_id", ID);
        intent.putExtra("st_name", stations_name);
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
        if (id==R.id.details){
            Toast.makeText(this, "My Details", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,BookingDetails.class));
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

    @Override
    public void onClick(View view) {

        String stations_id = getIntent().getStringExtra("stations_id");
        Intent vacant = new Intent(this, Schedule.class);
        vacant.putExtra("stations_id", stations_id);
        startActivityForResult(vacant, 1);
    }
//    end of menu
}
