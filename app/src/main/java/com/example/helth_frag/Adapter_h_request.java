package com.example.helth_frag;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.hname.setText(modelHD.Hname);
        holder.hraddrs.setText(modelHD.address);

        holder.cardviewHDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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



        view= holder.rootview;
    }

    @Override
    public int getItemCount() {
        return listHD.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public CardView cardviewHDR;

        TextView hname, hraddrs;

        View rootview;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            hname = itemView.findViewById(R.id.Hr_name);
            hraddrs = itemView.findViewById(R.id.hr_address);
//            amnt = itemView.findViewById(R.id.amnt);
            cardviewHDR = itemView.findViewById(R.id.cardviewHDR);
        }
    }
}
