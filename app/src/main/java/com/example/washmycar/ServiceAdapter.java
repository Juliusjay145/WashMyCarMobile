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

import static com.example.washmycar.CompanyAdapter.getBitmapFromURL;

public class ServiceAdapter extends BaseAdapter {

    Context context;
    ArrayList<ServiceList> list;
    LayoutInflater inflater;

    public ServiceAdapter(Context context, ArrayList<ServiceList> list) {
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

        ServiceHandler handler = null;
        if(arg1==null)
        {
            arg1 = inflater.inflate(R.layout.station_service_data, null);
            handler = new ServiceHandler();
            handler.name =  arg1.findViewById(R.id.textView);
            handler.price = arg1.findViewById(R.id.textView2);
            handler.image = arg1.findViewById(R.id.imageView);
            arg1.setTag(handler);
        }else

            handler=(ServiceHandler) arg1.getTag();
        Bitmap bm  = getBitmapFromURL(list.get(arg0).getImage());
        handler.name.setText(list.get(arg0).getName());
        handler.price.setText(list.get(arg0).getPrice());
        handler.image.setImageBitmap(bm);



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

    static class ServiceHandler
    {
        TextView name,price;
        ImageView image;
    }



















}
