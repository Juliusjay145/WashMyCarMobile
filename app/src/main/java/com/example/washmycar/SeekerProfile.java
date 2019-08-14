package com.example.washmycar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SeekerProfile extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private MenuItem item;
    String username = "ler", password = "ler", id = "1";

    String seekerName,seekerPass,seekerAddress,seekerNumber,seekerEmail;
    TextView txtname, txtaddr, txtnumber, txtemail;

    ImageButton myImageButton;



    ListView vehicleList;
    ArrayList<VehicleList> list = new ArrayList<VehicleList>();
    SeekerProfileVehicleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeker_profile);





        txtname = findViewById(R.id.txt_profile_name);
        txtaddr = findViewById(R.id.txt_profile_addr);
        txtnumber = findViewById(R.id.txt_pofile_contact);
        txtemail = findViewById(R.id.txt_profile_email);


        vehicleList = findViewById(R.id.imgbtn);
        adapter = new SeekerProfileVehicleAdapter(this,list);
        vehicleList.setAdapter(adapter);
//        myImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentLoadNewActivity = new Intent(SeekerProfile.this, VihicleProfile.class);
//                startActivity(intentLoadNewActivity);
//            }
//        });


        fetchProfileData();
        txtname.setText(seekerName);
        txtaddr.setText(seekerAddress);
        txtnumber.setText(seekerNumber);
        txtemail.setText(seekerEmail);
        fetchProfileVehicleData();






    }


    void fetchProfileVehicleData(){
        try{
//            URL url = new URL("http://192.168.43.118/washmycar/index.php/androidcontroller/get_carwash_station");
            URL url = new URL("http://192.168.254.106/WashMyCar/Project/Model/mobile_seeker_vehicle.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("myvehicle");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                String v_id = item.getString("cwsv_id");
                String user_id = item.getString("seeker_id");
                String v_plateno = item.getString("cwsv_plateno");
                String v_brand = item.getString("cwsv_brand");
                String v_model = item.getString("cwsv_model");
                String v_color = item.getString("cwsv_color");

                if(id.equals(user_id)){
                   list.add(new VehicleList("image",user_id,v_model,v_plateno,v_brand,v_color));
                }


            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }


        adapter.notifyDataSetChanged();

    }

    void fetchProfileData(){


        try{
//            URL url = new URL("http://192.168.43.118/washmycar/index.php/androidcontroller/get_carwash_station");
            URL url = new URL("http://192.168.254.106/WashMyCar/Project/Model/mobile_seeker_profile.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("myprofile");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
               String seekerNames = item.getString("seeker_name");
                String seekerEmails = item.getString("seeker_email");
                String seekerAddresss = item.getString("seeker_address");
                String seekerNumbers = item.getString("seeker_telephone");
                String seekerPasss = item.getString("seeker_password");

                if(seekerEmails.equals(username) && seekerPasss.equals(password)){
                    seekerName = seekerNames;
                    seekerEmail = seekerEmails;
                    seekerAddress = seekerAddresss;
                    seekerNumber = seekerNumbers;
                }


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
        if (id==R.id.settings){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,SeekerProfile.class));
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
