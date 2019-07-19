package com.example.washmycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class DashBoard extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ListView lv;
    SharedPreferences prf;
    ArrayList<CompanyList> list = new ArrayList<>();
    CompanyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        this.lv = findViewById(R.id.ListView);
        this.adapter = new CompanyAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        try{
            URL url = new URL("http://192.168.43.19/washmycar/index.php/androidcontroller/get_carwash_station");
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
                String carwash_name = item.getString("station_name");
                String carwashId = item.getString("station_id");
                String CompanyImage = item.getString("path_image");
                list.add(new CompanyList(CompanyImage,carwashId,carwash_name));
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

        CompanyList selectedItem = list.get(i);
        String ID = selectedItem.getId();
//        Intent intent = new Intent(this, CateringProfile.class);
//        intent.putExtra("catering_id", ID);
//        startActivityForResult(intent, 1);

    }
}
