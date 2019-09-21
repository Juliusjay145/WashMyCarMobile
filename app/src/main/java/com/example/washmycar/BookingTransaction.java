package com.example.washmycar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingTransaction extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    private MenuItem item;
    EditText s_name,wash_name,time1,date2,about;
    Button btnBooking;
    InputStream is;
    SharedPreferences prf;

    DateFormat format=DateFormat.getDateInstance();
    @SuppressLint("NewApi")
    Calendar calendar=Calendar.getInstance();
    @SuppressLint("NewApi")
    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    @SuppressLint("NewApi")
    int currentMinute = calendar.get(Calendar.MINUTE);
    TimePickerDialog timePickerDialog;
    String amPm;
    int year;
    int month;
    int dayOfMonth;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy mm:hh a");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        s_name = findViewById(R.id.editText1);
        wash_name = findViewById(R.id.editText2);
        time1 = findViewById(R.id.editText3);
        date2 = findViewById(R.id.editText4);
        about = findViewById(R.id.editText5);



        btnBooking = findViewById(R.id.book1);


        btnBooking.setOnClickListener(this);
        time1.setOnClickListener(this);
        date2.setOnClickListener(this);

        String customer_id = prf.getString("seeker_id", "");
        String picture = prf.getString("seeker_image", "");
        String station_id = getIntent().getStringExtra("station_id");
        String service_name = getIntent().getStringExtra("service_name");
        String vehicle_id = getIntent().getStringExtra("car_id");
        String washboy_name = getIntent().getStringExtra("washboy_name");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String schedule_id = getIntent().getStringExtra("schedule_id");
        String st_name = getIntent().getStringExtra("station_name");
        Toast.makeText(getApplicationContext(), st_name, Toast.LENGTH_SHORT).show();

        s_name.setText(service_name);
        wash_name.setText(washboy_name);
        time1.setText(time);
        date2.setText(date);



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

    //    end of menu




    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @SuppressLint("NewApi")
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, arg1);
            calendar.set(Calendar.MONTH, arg2);
            calendar.set(Calendar.DAY_OF_MONTH, arg3);
            date2.setText(format.format(calendar.getTime()));
        }
    };


    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {

        timePickerDialog = new TimePickerDialog(BookingTransaction.this, new TimePickerDialog.OnTimeSetListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                String amPm;
//	        	if (hourOfDay >= 12) {
//	        	        amPm = "PM";
//	        	    } else {
//	        	        amPm = "AM";
//	        	    }
                if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    amPm = "PM";
                } else if (hourOfDay == 0) {
                    hourOfDay += 12;
                    amPm = "AM";
                } else if (hourOfDay == 12){
                    amPm = "PM";
                }else{
                    amPm = "AM";
                }
                calendar.set(Time.HOUR, hourOfDay);
                calendar.set(Time.MINUTE, minutes);
                time1.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
            }
        }, currentHour, currentMinute, false);





        switch (view.getId()) {


            case R.id.book1:

                prf = getSharedPreferences("user_details",MODE_PRIVATE);


                String customer_id = prf.getString("seeker_id", "");
                String picture = prf.getString("seeker_image", "");
                String customer_name = prf.getString("seeker_name", "");
                String station_id = getIntent().getStringExtra("station_id");
                String serv_name = getIntent().getStringExtra("service_name");
                String vehicle_id = getIntent().getStringExtra("car_id");
                String employee_name = getIntent().getStringExtra("washboy_name");
                String service_id = getIntent().getStringExtra("service_id");
                String st_name = getIntent().getStringExtra("station_name");
                String price = getIntent().getStringExtra("price");
                String se_name = s_name.getText().toString();
                String w_name = wash_name.getText().toString();
                String t_time = time1.getText().toString();
                String t_date = date2.getText().toString();
                String t_about = about.getText().toString();

                if(calendar.getTime().before(new Date()))
                {
                    Toast.makeText(getApplicationContext(), "Invalid Past Date", Toast.LENGTH_SHORT).show();
                }

                else{
                    List<NameValuePair> nameValuePairs = new ArrayList<>(1);
                    nameValuePairs.add(new BasicNameValuePair("station_id", station_id));
                    nameValuePairs.add(new BasicNameValuePair("seeker_id", customer_id));
                    nameValuePairs.add(new BasicNameValuePair("cwsv_id", vehicle_id));
                    nameValuePairs.add(new BasicNameValuePair("service_id", service_id));
                    nameValuePairs.add(new BasicNameValuePair("seeker_about", t_about));
                    nameValuePairs.add(new BasicNameValuePair("seeker_date", t_date));
                    nameValuePairs.add(new BasicNameValuePair("seeker_time", t_time));
                    nameValuePairs.add(new BasicNameValuePair("seeker_name", customer_name));
                    nameValuePairs.add(new BasicNameValuePair("service_name", se_name));
                    nameValuePairs.add(new BasicNameValuePair("washboy_name", w_name));
                    nameValuePairs.add(new BasicNameValuePair("station_name", st_name));
                    nameValuePairs.add(new BasicNameValuePair("seeker_image", picture));
                    nameValuePairs.add(new BasicNameValuePair("seeker_paid", price));



                    try{
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://192.168.43.19/washmycar/index.php/androidcontroller/booking");
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

                    String schedule_id = getIntent().getStringExtra("schedule_id");
                    try{
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://192.168.43.19/washmycar/index.php/androidcontroller/schedule_update/"+schedule_id);
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



                break;

            case R.id.editText3:
                timePickerDialog.show();
                break;

            case R.id.editText4:
                new DatePickerDialog(this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;






        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this,adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}