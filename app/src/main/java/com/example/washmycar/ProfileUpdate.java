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
import android.widget.EditText;
import android.widget.ImageView;
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

public class ProfileUpdate extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    EditText name,email,address,phone;
    ImageView img;
    Button btnUpdate;
    Uri uriImage;
    SharedPreferences prf;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeker_profile_update);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);

        name = findViewById(R.id.editText1);
        email = findViewById(R.id.editText2);
        address = findViewById(R.id.editText3);
        phone = findViewById(R.id.editText4);
        img = findViewById(R.id.imageView1);
        btnUpdate = findViewById(R.id.button);
        btnUpdate.setOnClickListener(this);


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
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onClick(View view) {

        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        String customer_id = prf.getString("seeker_id", "");

        String s_name = name.getText().toString();
        String s_email = email.getText().toString();
        String s_address = address.getText().toString();
        String s_phone = phone.getText().toString();

        if(s_name.equals("") || s_email.equals("") || s_address.equals("") || s_phone.equals(""))
        {
            Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
        }

        else
            {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("seeker_name", s_name));
                nameValuePairs.add(new BasicNameValuePair("seeker_email", s_email));
                nameValuePairs.add(new BasicNameValuePair("seeker_address", s_address));
                nameValuePairs.add(new BasicNameValuePair("seeker_telephone", s_phone));

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://192.168.43.19/washmycar/index.php/androidcontroller/seeker_update/"+customer_id);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    InputStream is;
                    is=entity.getContent();
                    Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Profile.class);
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
}
