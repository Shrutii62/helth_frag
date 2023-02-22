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

public class Adapter_h_request extends RecyclerView.Adapter<Adapter_h_request.Viewholder>{

    Context contextHd;
    ArrayList<modelHD> listHD;
    View view;

    public Adapter_h_request(Context context,ArrayList<modelHD> listHD)
    {
        this.contextHd = context;
        this.listHD = listHD;
    }

    @NonNull
    @Override
    public Adapter_h_request.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contextHd).inflate(R.layout.h_itemrow_requestdis_hos,parent,false);
        return  new Adapter_h_request.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_h_request.Viewholder holder, int position) {
        modelHD modelHD= listHD.get(position);

        holder.Hr_name.setText(modelHD.getHname());

        holder.hraddrs.setText(modelHD.getAddress());

        holder.cardviewHDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View dailogview = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.dailog_h_request,null );

                TextView send_request,accept_reject, ViewReq;
                send_request=dailogview.findViewById(R.id.send_request);
                accept_reject=dailogview.findViewById(R.id.accept_reject);
                ViewReq=dailogview.findViewById(R.id.ViewReq);

                send_request.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String address = listHD.get(holder.getAdapterPosition()).address;
                        String email = listHD.get(holder.getAdapterPosition()).email;
                        String Hname = listHD.get(holder.getAdapterPosition()).Hname;
                        String phoneno = listHD.get(holder.getAdapterPosition()).phoneno;
                        String hid = listHD.get(holder.getAdapterPosition()).h_id;


//                        Toast.makeText(builder.getContext(), "pid"+pid, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(view.getContext(), h_requestSentForm.class);
                        intent.putExtra("address",address);
                        intent.putExtra("email",email);
                        intent.putExtra("Hname",Hname);
                        intent.putExtra("phoneno",phoneno);
                        intent.putExtra("hid",hid);
//                            intent.putExtra("STRING_I_NEED",);
                        view.getContext().startActivity(intent);
                    }
                });

                accept_reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String address = listHD.get(holder.getAdapterPosition()).address;
                        String email = listHD.get(holder.getAdapterPosition()).email;
                        String Hname = listHD.get(holder.getAdapterPosition()).Hname;
                        String phoneno = listHD.get(holder.getAdapterPosition()).phoneno;
                        String hid = listHD.get(holder.getAdapterPosition()).h_id;


//                        Toast.makeText(builder.getContext(), "pid"+pid, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(view.getContext(), tablayout_request.class);
                        intent.putExtra("address",address);
                        intent.putExtra("email",email);
                        intent.putExtra("Hname",Hname);
                        intent.putExtra("phoneno",phoneno);
                        intent.putExtra("hid",hid);
//                            intent.putExtra("STRING_I_NEED",);
                        view.getContext().startActivity(intent);

                    }
                });
                ViewReq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String address = listHD.get(holder.getAdapterPosition()).address;
                        String email = listHD.get(holder.getAdapterPosition()).email;
                        String Hname = listHD.get(holder.getAdapterPosition()).Hname;
                        String phoneno = listHD.get(holder.getAdapterPosition()).phoneno;
                        String hid = listHD.get(holder.getAdapterPosition()).h_id;


//                        Toast.makeText(builder.getContext(), "pid"+pid, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(view.getContext(), ViewRequest.class);
                        intent.putExtra("address",address);
                        intent.putExtra("email",email);
                        intent.putExtra("Hname",Hname);
                        intent.putExtra("phoneno",phoneno);
                        intent.putExtra("hid",hid);
//                            intent.putExtra("STRING_I_NEED",);
                        view.getContext().startActivity(intent);

                    }
                });


                builder.setView(dailogview);
                builder.setCancelable(true);
                builder.show();



//                String did = listlt.get(holder.getAdapterPosition()).did;
//                String hid = listlt.get(holder.getAdapterPosition()).hid;
//                String pid = listlt.get(holder.getAdapterPosition()).pid;

//                Intent intent = new Intent(view.getContext(), lab2_PDF.class);
//                intent.putExtra("piid",pid);
//                intent.putExtra("hiid",hid);
//                intent.putExtra("diid",did);
//                view.getContext().startActivity(intent);
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {



                AlertDialog.Builder builder = new AlertDialog.Builder(v1.getRootView().getContext(),R.style.MyAlertDialogStyle);
                builder.setMessage("Do you want to call "+modelHD.getHname()+"?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1)
                    {

                        if (ContextCompat.checkSelfPermission(contextHd   ,
                                Manifest.permission.CALL_PHONE)
                                == PackageManager.PERMISSION_GRANTED) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + modelHD.phoneno));
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contextHd.startActivity(callIntent);
                        }else{
                            Toast.makeText(contextHd, "Calling Permission is disabled :-(", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                AlertDialog alert = builder.create();

                alert.show();



            }
        });





        view= holder.rootview;
    }

    @Override
    public int getItemCount() {
        return listHD.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public CardView cardviewHDR;
        ImageView call;

        TextView Hr_name, hraddrs;

        View rootview;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            Hr_name = itemView.findViewById(R.id.Hr_name);
            hraddrs = itemView.findViewById(R.id.hr_address);
//            amnt = itemView.findViewById(R.id.amnt);
            cardviewHDR = itemView.findViewById(R.id.cardviewHDR);
            call = itemView.findViewById(R.id.call);
        }
    }
}
