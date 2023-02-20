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

import java.util.ArrayList;

public class AdapterAmbulanceList extends RecyclerView.Adapter<AdapterAmbulanceList.Viewholder> {

    Context contextAmb;
    View view;
    ArrayList<Model_ambDriverdetail> listAmb;


    public AdapterAmbulanceList(Context context,ArrayList<Model_ambDriverdetail> list){
        this.contextAmb = context;
        this.listAmb = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contextAmb).inflate(R.layout.ambulance_list_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Model_ambDriverdetail ambulance = listAmb.get(position);
        holder.ambulanceNumber.setText(ambulance.getReg_phoned());
        holder.driverName.setText(ambulance.getD_name());

        view= holder.rootview;



        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {



                AlertDialog.Builder builder = new AlertDialog.Builder(v1.getRootView().getContext(),R.style.MyAlertDialogStyle);
                builder.setMessage("Do you want to call "+ambulance.getD_name()+"?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1)
                    {

                        if (ContextCompat.checkSelfPermission(contextAmb   ,
                                Manifest.permission.CALL_PHONE)
                                == PackageManager.PERMISSION_GRANTED) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + ambulance.getReg_phoned()));
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contextAmb.startActivity(callIntent);
                        }else{
                            Toast.makeText(contextAmb, "Calling Permission is disabled :-(", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                AlertDialog alert = builder.create();

                alert.show();



            }
        });

        holder.cardViewAmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(contextAmb, "yasssss", Toast.LENGTH_SHORT).show();

                Intent ii = new Intent(view.getContext(), h_Amb_book_frm_Acti.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//                Intent intent= new Intent(contextAmb, h_Amb_book_frm_Acti.class);
////                intent.putExtra("your_extra","your_class_value");
//               contextAmb.startActivity(intent);


//                String did = listAmb.get(holder.getAdapterPosition()).;
//                String hid = listAmb.get(holder.getAdapterPosition()).hid;
//                String pid = listAmb.get(holder.getAdapterPosition()).pid;


//                Intent intent = new Intent(view.getContext(), h_Amb_book_frm_Acti.class);
////                intent.putExtra("piid",pid);
////                intent.putExtra("hiid",hid);
////                intent.putExtra("diid",did);
//                view.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return listAmb.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        public ImageView callBtn;
        TextView driverName,ambulanceNumber;
        CardView cardViewAmb;
        View rootview;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            callBtn = itemView.findViewById(R.id.call_btn);
            driverName = itemView.findViewById(R.id.driver_name);
            ambulanceNumber = itemView.findViewById(R.id.Ambulance_number);
            cardViewAmb = itemView.findViewById(R.id.cardviewAmb);

        }
    }
}
