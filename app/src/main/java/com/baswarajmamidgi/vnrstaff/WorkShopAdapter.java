package com.baswarajmamidgi.vnrstaff;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by baswarajmamidgi on 10/03/17.
 */

public class WorkShopAdapter  extends RecyclerView.Adapter<WorkShopAdapter.MyViewHolder> {
    public Context context;
    public List<Workshopclass> workshopclassList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public  MyViewHolder(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.workshopthumnail);
            textView = (TextView) view.findViewById(R.id.details);


        }
    }
    public WorkShopAdapter(Context context,List<Workshopclass> workshopclassList){
        this.context=context;
        this.workshopclassList=workshopclassList;

    }

    @Override
    public WorkShopAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_workshop,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final WorkShopAdapter.MyViewHolder holder, int position) {
        final Workshopclass workshopclass=workshopclassList.get(position);
        Glide.with(context).load(workshopclass.getImageUrl())
                .placeholder(R.drawable.navicon)
                .into(holder.imageView);
        holder.textView.setText(workshopclass.getName()+"\n");
        if(workshopclass.getDuration()!=null) {
            holder.textView.append(workshopclass.getDuration()+"n");
        }
        if(workshopclass.getRegistrationfee()!=null) {
            holder.textView.append(workshopclass.getRegistrationfee()+"\n");
        }
        if(workshopclass.getContactno()!=null) {
            holder.textView.append(workshopclass.getContactno());
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           String url=workshopclass.getImageUrl();
                final Dialog dialog = new Dialog(context,R.style.Theme_Dialog);
                dialog.setContentView(R.layout.imageview);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
                ImageView cancel = (ImageView) dialog.findViewById(R.id.cancel);

                Glide.with(context).load(url)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.noimage)
                        .into(imageView);
                dialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return workshopclassList.size();
    }

}

