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

public class VehicleUpdate extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    EditText plate_number,brand,model,color,type;
    ImageView img;
    Button btnUpdate;
    Uri uriImage;
    SharedPreferences prf;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vihicle_profile_update);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);

        plate_number = findViewById(R.id.editText31);
        brand = findViewById(R.id.editText32);
        model = findViewById(R.id.editText33);
        color = findViewById(R.id.editText34);
        type = findViewById(R.id.editText35);
        img = findViewById(R.id.image30);
        btnUpdate = findViewById(R.id.update40);
        btnUpdate.setOnClickListener(this);

        String vehicle_id = getIntent().getStringExtra("car_id");
        String customer_id = prf.getString("seeker_id", "");

        try{
            URL url = new URL("http://192.168.43.19/washmycar/index.php/androidcontroller/get_vehicle_owner/"+customer_id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("cwseeker_vehicle");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                String cn = item.getString("seeker_id");
                String p_number = item.getString("plate_number");
                String v_name = item.getString("brand_name");
                String v_model = item.getString("model");
                String v_color = item.getString("color");
                String v_type= item.getString("vehicle_type");
                String picture = item.getString("image_vehicle");

                plate_number.setText(p_number);
                brand.setText(v_name);
                model.setText(v_model);
                color.setText(v_color);
                type.setText(v_type);
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
            startActivity(new Intent(this, VehicleUpdate.class));
        }
        else
        if (id==R.id.settings){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, VehicleUpdate.class));
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
        String vehicle_id = getIntent().getStringExtra("car_id");

        String p_number = plate_number.getText().toString();
        String p_brand = brand.getText().toString();
        String p_model = model.getText().toString();
        String p_color = color.getText().toString();
        String p_type = type.getText().toString();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("plate_number", p_number));
        nameValuePairs.add(new BasicNameValuePair("brand_name", p_brand));
        nameValuePairs.add(new BasicNameValuePair("model", p_model));
        nameValuePairs.add(new BasicNameValuePair("color", p_color));
        nameValuePairs.add(new BasicNameValuePair("vehicle_type", p_type));

        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.43.19/washmycar/index.php/androidcontroller/seeker_vehicle_update/"+vehicle_id);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            InputStream is;
            is=entity.getContent();
            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, VehicleData.class);
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
