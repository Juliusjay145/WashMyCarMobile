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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
import java.util.List;

public class ProfileData extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    ListView lv;
    SharedPreferences prf;
    ArrayList<CarWashOwnerList> list = new ArrayList<>();
    CarWashOwnerAdapter adapter;
    Button btnNext,btnFavor;
    TextView rateus;
    InputStream is;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().setTitle("Station Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        this.lv = findViewById(R.id.ListView);
        this.adapter = new CarWashOwnerAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        btnNext = findViewById(R.id.book1);
        btnNext.setOnClickListener(this);

        btnFavor = findViewById(R.id.book2);
        btnFavor.setOnClickListener(this);

        rateus = findViewById(R.id.star);
        rateus.setOnClickListener(this);





        String customer_id = prf.getString("seeker_id", "");
        String stations_id = getIntent().getStringExtra("station_id");
        String stations_name = getIntent().getStringExtra("stat_name");
        String wallet = getIntent().getStringExtra("wallet");



        try{
//            URL url = new URL("http://192.168.43.118/washmycar/index.php/androidcontroller/get_carwash_station");
            URL url = new URL("http://192.168.43.19/washmycar/index.php/androidcontroller/get_profile_carwashowner/"+stations_id);
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
                String carwash_wallet = item.getString("station_wallet");
                list.add(new CarWashOwnerList(CompanyImage,carwashId,carwash_name,carwash_address,carwash_telephone,carwash_desc,carwash_wallet));
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

        Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();

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

      if(view == btnNext)
      {
          String stations_id = getIntent().getStringExtra("station_id");
          String stations_name = getIntent().getStringExtra("stat_name");
          String wallet = getIntent().getStringExtra("wallet");
          Intent intent = new Intent(this, Schedule.class);
          intent.putExtra("stations_id", stations_id);
          intent.putExtra("st_name", stations_name);
          intent.putExtra("wallet", wallet);
          startActivityForResult(intent, 1);
      }

      if(view == rateus)
      {
          String stations_id = getIntent().getStringExtra("station_id");
          Intent intent = new Intent(this, AddRating.class);
          intent.putExtra("stations_id", stations_id);
          startActivityForResult(intent, 1);
      }

      if(view == btnFavor)
      {
          String stations_id = getIntent().getStringExtra("station_id");
          String customer_id = prf.getString("seeker_id", "");

          List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
          nameValuePairs.add(new BasicNameValuePair("seeker_id", customer_id));
          try{
              HttpClient httpClient = new DefaultHttpClient();
              HttpPost httpPost = new HttpPost("http://192.168.43.19/washmycar/index.php/AndroidController/add_favorites/"+ stations_id);
              httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
              HttpResponse response = httpClient.execute(httpPost);
              HttpEntity entity = response.getEntity();
              is=entity.getContent();
              Toast.makeText(getApplicationContext(), "Rated Successfully", Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(this, DashBoard.class);
              startActivity(intent);

          }
          catch(ClientProtocolException e)
          {
              Log.e("ClientProtocol","Log_tag");
              e.printStackTrace();
          }
          catch(IOException e)
          {
              Log.e("Log_tag", "IOException");
              e.printStackTrace();
          }
      }




    }


//    end of menu
}
