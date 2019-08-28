package com.example.washmycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
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

public class ProfileData extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    ListView lv;
    SharedPreferences prf;
    ArrayList<CarWashOwnerList> list = new ArrayList<>();
    CarWashOwnerAdapter adapter;
    Button btnNext;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        this.lv = findViewById(R.id.ListView);
        btnNext = findViewById(R.id.book1);
        btnNext.setOnClickListener(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        this.adapter = new CarWashOwnerAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        String customer_id = prf.getString("seeker_id", "");
        String stations_id = getIntent().getStringExtra("station_id");



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);


        try{
//            URL url = new URL("http://192.168.43.118/washmycar/index.php/androidcontroller/get_carwash_station");
            URL url = new URL("http://192.168.43.118/washmycar/index.php/androidcontroller/get_profile_carwashowner/"+stations_id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("cwowner_station");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                String CompanyImage = item.getString("path_image");
                String carwashId = item.getString("station_id");
                String carwash_name = item.getString("station_name");
                String carwash_address = item.getString("station_address");
                String carwash_telephone = item.getString("station_tele");
                String carwash_desc = item.getString("station_description");
                list.add(new CarWashOwnerList(CompanyImage,carwashId,carwash_name,carwash_address,carwash_telephone,carwash_desc));
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

        CarWashOwnerList selectedItem = list.get(i);
        String ID = selectedItem.getId();
//        Intent intent = new Intent(this, CateringProfile.class);
//        intent.putExtra("catering_id", ID);
//        startActivityForResult(intent, 1);

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
        if (id==R.id.home){
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DashBoard.class));
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

        String stations_id = getIntent().getStringExtra("station_id");
        Intent intent = new Intent(this, CarWashStationService.class);
        intent.putExtra("stations_id", stations_id);
        startActivityForResult(intent, 1);

    }
//    end of menu
}
