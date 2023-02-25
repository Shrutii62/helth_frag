package com.example.helth_frag;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helth_frag.activities.Act_D;

import java.util.ArrayList;

public class AdapterAmbulanceList extends RecyclerView.Adapter<AdapterAmbulanceList.Viewholder> {

    Context contextAmb;
    View view;
    ArrayList<Model_hAmb_book_frm> listAmb;


    public AdapterAmbulanceList(Context context,ArrayList<Model_hAmb_book_frm> list){
        this.contextAmb = context;
        this.listAmb = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contextAmb).inflate(R.layout.driverviewbking_list_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Model_hAmb_book_frm model_hAmb_book_frm = listAmb.get(position);
        holder.hos_name.setText(model_hAmb_book_frm.Address);
        holder.haddressget.setText(model_hAmb_book_frm.Address);
        holder.date.setText(model_hAmb_book_frm.dateget);
        holder.time.setText(model_hAmb_book_frm.timeget);

        view= holder.rootview;







    }

    @Override
    public int getItemCount() {
        return listAmb.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {


        TextView hos_name,haddressget,date, time;
        CardView cardviewD;
        View rootview;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            hos_name = itemView.findViewById(R.id.hos_name);
            haddressget = itemView.findViewById(R.id.haddressget);
            cardviewD = itemView.findViewById(R.id.cardviewD);

        }
    }
}
