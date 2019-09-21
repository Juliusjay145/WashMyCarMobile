package com.example.washmycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

public class BookingDetailsCancel extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    TextView txtname,txtdate,txttime,txtservice,txtemployee,txtstation,txtprice,txtstatus;
    ImageView img;
    InputStream is;
    Button btnCancel;
    SharedPreferences prf;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeker_details_data_cancel);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);

        txtname = findViewById(R.id.textview11);
        txtdate = findViewById(R.id.textview12);
        txttime = findViewById(R.id.textview13);
        txtservice = findViewById(R.id.textview14);
        txtemployee = findViewById(R.id.textview15);
        txtstation = findViewById(R.id.textview16);
        txtprice = findViewById(R.id.textview17);
        txtstatus = findViewById(R.id.textview18);
        img = findViewById(R.id.imageView);

        btnCancel = findViewById(R.id.book12);

        btnCancel.setOnClickListener(this);

        String ID = getIntent().getStringExtra("booking_id");
        String serviceName = getIntent().getStringExtra("service_name");
        String stationName = getIntent().getStringExtra("station_name");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String schedule_id = getIntent().getStringExtra("schedule_id");
        //Toast.makeText(getApplicationContext(), stationName +"" + serviceName, Toast.LENGTH_SHORT).show();
        String customer_id = prf.getString("seeker_id", "");
        String picture = prf.getString("seeker_image", "");
        getSupportActionBar().setTitle("Booking Details");

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
                String price = item.getString("seeker_paid");
                String status = item.getString("status");
                String sek_id = item.getString("seeker_id");
                txtname.setText(customer_name);
                txtdate.setText(customer_date);
                txttime.setText(customer_time);
                txtservice.setText(customer_service);
                txtemployee.setText(employee);
                txtstation.setText(station);
                txtprice.setText(price);
                txtstatus.setText(status);
                img.setImageBitmap(BitmapFactory.decodeFile(Customer_image));

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

//        BookingDetailsList selectedItem = list.get(i);
//        String stationID = getIntent().getStringExtra("sta_id");
//        String stationName = getIntent().getStringExtra("station_name");
//        String serviceName = getIntent().getStringExtra("service_name");
//        String service_id = getIntent().getStringExtra("service_id");
//        String date = getIntent().getStringExtra("date");
//        String time = getIntent().getStringExtra("time");
//        String schedule_id = getIntent().getStringExtra("schedule_id");
//        String ID = selectedItem.getId();
        Intent intent = new Intent(this, Washboy.class);
//        intent.putExtra("v_id", ID);
//        intent.putExtra("s_id", stationID);
//        intent.putExtra("s_name", stationName);
//        intent.putExtra("service_name", serviceName);
//        intent.putExtra("service_id", service_id);
//        intent.putExtra("date", date);
//        intent.putExtra("time", time);
//        intent.putExtra("schedule_id", schedule_id);
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
        if (id==R.id.details){
            Toast.makeText(this, "My Details", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, BookingDetailsCancel.class));
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


        String ID = getIntent().getStringExtra("booking_id");
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.43.19/washmycar/index.php/androidcontroller/cancel/"+ID);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            is=entity.getContent();
            Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(this, BookingDetails.class);
            startActivity(intent1);
            //			txtname.setText("");
            //			address.setText(caddress);
            //			txtcontact.setText("");
            //			txtusername.setText("");
            //			txtpassword.setText("");


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
//    end of menu
}
