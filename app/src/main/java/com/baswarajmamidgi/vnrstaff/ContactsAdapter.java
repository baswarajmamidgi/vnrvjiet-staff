package com.baswarajmamidgi.vnrstaff;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by baswarajmamidgi on 16/03/17.
 */

public class ContactsAdapter  extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    public Context context;
    public List<Carddetails> carddetailsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView contact,mail,share;
        TextView title,name,address;


        public  MyViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.contacttitle);
            name = (TextView) view.findViewById(R.id.person_name);
            address = (TextView) view.findViewById(R.id.address);
            contact = (ImageView) view.findViewById(R.id.contact);
            mail = (ImageView) view.findViewById(R.id.mail);
            share = (ImageView) view.findViewById(R.id.share);




        }
    }
    public ContactsAdapter(Context context,List<Carddetails> carddetailsList){
        this.context=context;
        this.carddetailsList=carddetailsList;

    }

    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.misccontacts_card,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Carddetails carddetails=carddetailsList.get(position);
        holder.title.setText(carddetails.getTitle());
        holder.name.setText(carddetails.getName());
        holder.address.setText(carddetails.getAddress());
        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",carddetails.getContact(),null));
                context.startActivity(i);

            }
        });

        holder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",carddetails.getMail(),null));
                context.startActivity(i);

            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, carddetails.getName() +"\n"+carddetails.getContact());
                context.startActivity(Intent.createChooser(i,"Share via"));

            }
        });
    }


    @Override
    public int getItemCount() {
        return carddetailsList.size();
    }

}


