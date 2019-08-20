package com.example.washmycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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

public class Profile extends AppCompatActivity implements AdapterView.OnItemClickListener {


    TextView name,email,address,phone;
    ImageView img;
    Button btnUpdate;
    Uri uriImage;
    SharedPreferences prf;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeker_profile);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);

        name = findViewById(R.id.txt_profile_name);
        email = findViewById(R.id.txt_profile_email);
        address = findViewById(R.id.txt_profile_addr);
        phone = findViewById(R.id.txt_pofile_contact);
        img = findViewById(R.id.imageView1);
        String customer_id = prf.getString("seeker_id", "");

        try{
            URL url = new URL("http://192.168.43.118/washmycar/index.php/androidcontroller/get_profile_carwashseeker/"+customer_id);
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
                //String cc = item.getString("client_contact");
                //String ct = item.getString("client_contact");
//	        	Toast.makeText(getApplicationContext(), cn, Toast.LENGTH_LONG).show();
                name.setText(cname);
                email.setText(clname);
                address.setText(cnumber);
                phone.setText(caddress);
                img.setImageBitmap(BitmapFactory.decodeFile(picture));
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
        if (id==R.id.home){
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Profile.class));
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

}
