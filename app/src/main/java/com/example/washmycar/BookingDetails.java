package com.example.washmycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class BookingDetails extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ListView lv;
    SharedPreferences prf;
    ArrayList<BookingDetailsList> list = new ArrayList<>();
    BookingDetailsAdapter adapter;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        this.lv = findViewById(R.id.ListView);
        this.adapter = new BookingDetailsAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        String ID = getIntent().getStringExtra("sta_id");
        String serviceName = getIntent().getStringExtra("service_name");
        String stationName = getIntent().getStringExtra("station_name");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String schedule_id = getIntent().getStringExtra("schedule_id");
        //Toast.makeText(getApplicationContext(), stationName +"" + serviceName, Toast.LENGTH_SHORT).show();
        String customer_id = prf.getString("seeker_id", "");
        String picture = prf.getString("seeker_image", "");

        try{
//            URL url = new URL("http://192.168.43.118/washmycar/index.php/androidcontroller/get_carwash_station");
            URL url = new URL("http://192.168.43.19/washmycar/index.php/androidcontroller/get_details/"+customer_id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("cwowner_booking");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                String customer_name = item.getString("seeker_name");
                String Customer_image = item.getString("seeker_image");
                String transaction_id = item.getString("request_id");
                String customer_date = item.getString("seeker_date");
                String customer_time = item.getString("seeker_time");
                String customer_service = item.getString("service_name");
                String employee = item.getString("washboy_name");
                String station = item.getString("station_name");
                String status = item.getString("status");
                String sek_id = item.getString("seeker_id");
                list.add(new BookingDetailsList(Customer_image,customer_name,customer_date,customer_time,customer_service,employee,station,status,transaction_id,sek_id));
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

        BookingDetailsList selectedItem = list.get(i);
        String stationID = getIntent().getStringExtra("sta_id");
        String stationName = getIntent().getStringExtra("station_name");
        String serviceName = getIntent().getStringExtra("service_name");
        String service_id = getIntent().getStringExtra("service_id");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String schedule_id = getIntent().getStringExtra("schedule_id");
        String ID = selectedItem.getId();
        Intent intent = new Intent(this, Washboy.class);
        intent.putExtra("v_id", ID);
        intent.putExtra("s_id", stationID);
        intent.putExtra("s_name", stationName);
        intent.putExtra("service_name", serviceName);
        intent.putExtra("service_id", service_id);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("schedule_id", schedule_id);
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
            startActivity(new Intent(this, Vehicle.class));
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
