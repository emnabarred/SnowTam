package com.emna.snowtamer.Adapters;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.emna.snowtamer.Activities.SnowTamActivity;
import com.emna.snowtamer.Activities.MapsActivity;
import com.emna.snowtamer.Models.Airport;
import com.emna.snowtamer.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Airport> mData ;
    RequestOptions option;


    public RecyclerViewAdapter(Context mContext, List<Airport> mData) {
        this.mContext = mContext;
        this.mData = mData;
        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loadingimg).error(R.drawable.loadingimg);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.card_item,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;


       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.aironame.setText(mData.get(position).getName());
        holder.citycountry.setText(mData.get(position). getCountry());
        holder.detsbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, SnowTamActivity.class);
                i.putExtra("icao",mData.get(position).getIcao());
                i.putExtra("name",mData.get(position).getName());

                mContext.startActivity(i);

            }
        });

        holder.geolocbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, MapsActivity.class);
                i.putExtra("X",mData.get(position).getLon());
                i.putExtra("Y",mData.get(position).getLat());
                mContext.startActivity(i);

            }
        });


        // Load Image from the internet and set it into Imageview using Glide

        Glide.with(mContext).load(mData.get(position).getUrlimage()).apply(option).into(holder.airoimg);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView citycountry ;
        TextView aironame ;
        Button detsbut;
        ImageButton geolocbut;
        ImageView airoimg;
        ConstraintLayout airo;
        ConstraintLayout back;





        public MyViewHolder(View itemView) {
            super(itemView);

            back = itemView.findViewById(R.id.back);
            citycountry = itemView.findViewById(R.id.citycountry);
            aironame = itemView.findViewById(R.id.aironame);
            detsbut  = itemView.findViewById(R.id.detsbut);;
            geolocbut  = itemView.findViewById(R.id.geolocbut);;
            airoimg  = itemView.findViewById(R.id.airoimg);;
            airo  = itemView.findViewById(R.id.airo);;
        }
    }

}
