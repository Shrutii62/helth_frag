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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAmbulanceList extends RecyclerView.Adapter<AdapterAmbulanceList.Viewholder> {

    Context contextD;
    View view;
    ArrayList<Model_ambDriverdetail> listD;


    public AdapterAmbulanceList(Context context,ArrayList<Model_ambDriverdetail> list){
        this.contextD = context;
        this.listD = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contextD).inflate(R.layout.ambulance_list_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Model_ambDriverdetail ambulance = listD.get(position);
        holder.ambulanceNumber.setText(ambulance.getReg_phoned());
        holder.driverName.setText(ambulance.getD_name());



        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext(),R.style.MyAlertDialogStyle);
                builder.setMessage("Do you want to call "+ambulance.getD_name()+"?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1)
                    {

                        if (ContextCompat.checkSelfPermission(contextD   ,
                                Manifest.permission.CALL_PHONE)
                                == PackageManager.PERMISSION_GRANTED) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + ambulance.getReg_phoned()));
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contextD.startActivity(callIntent);
                        }else{
                            Toast.makeText(contextD, "Calling Permission is disabled :-(", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                AlertDialog alert = builder.create();

                alert.show();



            }
        });

    }

    @Override
    public int getItemCount() {
        return listD.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        public ImageView callBtn;
        TextView driverName,ambulanceNumber;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            callBtn = itemView.findViewById(R.id.call_btn);
            driverName = itemView.findViewById(R.id.driver_name);
            ambulanceNumber = itemView.findViewById(R.id.Ambulance_number);

        }
    }
}
