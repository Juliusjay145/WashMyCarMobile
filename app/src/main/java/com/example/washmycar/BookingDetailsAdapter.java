package com.example.washmycar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BookingDetailsAdapter extends BaseAdapter {


    Context context;
    ArrayList<BookingDetailsList> list;
    LayoutInflater inflater;


    public BookingDetailsAdapter(Context context, ArrayList<BookingDetailsList> list) {
        super();
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        CompanyHandler handler = null;
        if(arg1==null)
        {
            arg1 = inflater.inflate(R.layout.seeker_details_data, null);
            handler = new CompanyHandler();
            handler.name =  arg1.findViewById(R.id.textview1);
            handler.image =  arg1.findViewById(R.id.imageView);
            handler.date =  arg1.findViewById(R.id.textview2);
            handler.time =  arg1.findViewById(R.id.textview3);
            handler.service_name =  arg1.findViewById(R.id.textview4);
            handler.employee_name =  arg1.findViewById(R.id.textview5);
            handler.station_name =  arg1.findViewById(R.id.textview6);
            handler.price =  arg1.findViewById(R.id.textview7);
            handler.status =  arg1.findViewById(R.id.textview8);
            arg1.setTag(handler);
        }else

            handler=(CompanyHandler) arg1.getTag();
        Bitmap bm  = BitmapFactory.decodeFile(list.get(arg0).getImage());
        handler.name.setText(list.get(arg0).getName());
        handler.image.setImageBitmap(bm);
        handler.date.setText(list.get(arg0).getDate());
        handler.time.setText(list.get(arg0).getTime());
        handler.service_name.setText(list.get(arg0).getService());
        handler.employee_name.setText(list.get(arg0).getEmployee());
        handler.station_name.setText(list.get(arg0).getStation());
        handler.price.setText(list.get(arg0).getPrice());
        handler.status.setText(list.get(arg0).getStatus());





        return arg1;
    }

    static Bitmap getBitmapFromURL(String src)
    {
        try{
            URL url = new URL(src);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.connect();
            InputStream is = con.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(is);
            Log.e("Bitmap", "returned");
            return myBitmap;
        }catch(IOException e){
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

    static class CompanyHandler
    {
        TextView name,date,time,service_name,employee_name,station_name,price,status;
        ImageView image;
    }









}
