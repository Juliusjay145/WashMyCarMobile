package com.example.washmycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
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

public class VehicleData extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    TextView platenumber,brand,model,color,type;
    ImageView img;
    SharedPreferences prf;
    Button btnNext;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vihicle_profile);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);

        platenumber = findViewById(R.id.txt_plateno);
        brand = findViewById(R.id.txt_brand);
        model = findViewById(R.id.txt_model);
        color = findViewById(R.id.txt_color);
        type = findViewById(R.id.txt_type);
        img = findViewById(R.id.image30);
        btnNext = findViewById(R.id.update);
        btnNext.setOnClickListener(this);

        String customer_id = prf.getString("seeker_id", "");
        String stations_id = getIntent().getStringExtra("v_id");

        try{
            URL url = new URL("http://192.168.43.118/washmycar/index.php/androidcontroller/get_vehicle_owner/"+customer_id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("cwseekervehicle");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                String cn = item.getString("seeker_id");
                String p_number = item.getString("cwsv_plateno");
                String v_name = item.getString("cwsv_brand");
                String v_model = item.getString("cwsv_model");
                String v_color = item.getString("cwsv_color");
                String v_type= item.getString("cwsv_type");
                String picture = item.getString("image_vehicle");
                //String cc = item.getString("client_contact");
                //String ct = item.getString("client_contact");
//	        	Toast.makeText(getApplicationContext(), cn, Toast.LENGTH_LONG).show();
                platenumber.setText(p_number);
                brand.setText(v_name);
                color.setText(v_color);
                model.setText(v_model);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //CarWashOwnerList selectedItem = list.get(i);
        //String ID = selectedItem.getId();
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

        String vehicle_id = getIntent().getStringExtra("v_id");
        Intent intent = new Intent(this, VehicleUpdate.class);
        intent.putExtra("car_id", vehicle_id);
        startActivityForResult(intent, 1);

    }
//    end of menu
}
