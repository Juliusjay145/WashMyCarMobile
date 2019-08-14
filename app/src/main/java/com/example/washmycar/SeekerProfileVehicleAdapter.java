package com.example.washmycar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SeekerProfileVehicleAdapter extends BaseAdapter {

    Context context;
    ArrayList<VehicleList> list;
    LayoutInflater inflater;

    public SeekerProfileVehicleAdapter(Context context, ArrayList<VehicleList> list){
        super();
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VehicleHandler handler = null;
        if(view == null){
            view = inflater.inflate(R.layout.profile_vehicle_list,null);
            handler = new VehicleHandler();
            handler.model = view.findViewById(R.id.txt_model);
            handler.brand = view.findViewById(R.id.txt_brand);
            handler.plateno = view.findViewById(R.id.txt_plateno);
            handler.id = view.findViewById(R.id.txt_id);
            handler.color = view.findViewById(R.id.txt_color);
            handler.image = view.findViewById(R.id.imageView);
        }else handler=(VehicleHandler) view.getTag();

        handler.model.setText(list.get(i).getModel());
        handler.brand.setText(list.get(i).getBrand());
        handler.plateno.setText(list.get(i).getPlateno());
        handler.id.setText(list.get(i).getId());
        handler.color.setText(list.get(i).getColor());
        //handler.image = view.findViewById.

        return view;
    }


    static class VehicleHandler{
        TextView model,id,plateno,brand,color;
        ImageView image;

    }
}
