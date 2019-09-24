package com.example.washmycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Profile extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    TextView name,email,address,phone,wallet;
    ImageView img;
    Button btnUpdate,btnAdd;
    Uri uriImage;
    SharedPreferences prf;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carwash_seeker_profile);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);

        name = findViewById(R.id.txt_seeker_name);
        email = findViewById(R.id.txt_seeker_email);
        address = findViewById(R.id.txt_seeker_addr);
        phone = findViewById(R.id.txt_seeker_contact);
        img = findViewById(R.id.imageView12);
        wallet = findViewById(R.id.txt_seeker_wallet);
        btnUpdate = findViewById(R.id.button);
        btnAdd = findViewById(R.id.button2);
        btnUpdate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        getSupportActionBar().setTitle("Profile");
        String customer_id = prf.getString("seeker_id", "");

        try{
            URL url = new URL("http://192.168.43.19/washmycar/index.php/androidcontroller/get_profile_carwashseeker/"+customer_id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("cwseekeracc");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                String cn = item.getString("seeker_id");
                String cname = item.getString("seeker_name");
                String clname = item.getString("seeker_email");
                String cnumber = item.getString("seeker_address");
                String caddress = item.getString("seeker_telephone");
                String picture = item.getString("seeker_image");
                String seeker_wallet = item.getString("seeker_wallet");
                //String cc = item.getString("client_contact");
                //String ct = item.getString("client_contact");
//	        	Toast.makeText(getApplicationContext(), cn, Toast.LENGTH_LONG).show();
                name.setText(cname);
                email.setText(clname);
                address.setText(cnumber);
                phone.setText(caddress);
                img.setImageBitmap(BitmapFactory.decodeFile(picture));
                wallet.setText(seeker_wallet);
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }

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
    //    end of menu
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onClick(View view) {

        if(view == btnUpdate)
        {
            Intent intent1 = new Intent(this, AddVihicle.class);
            startActivity(intent1);
        }

        if(view == btnAdd)
        {
            Intent intent1 = new Intent(this, ProfileUpdate.class);
            startActivity(intent1);
        }



    }
}
