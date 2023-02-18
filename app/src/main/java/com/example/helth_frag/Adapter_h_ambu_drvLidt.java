package com.example.helth_frag;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_h_ambu_drvLidt extends RecyclerView.Adapter<Adapter_h_ambu_drvLidt.Viewholder> {


    Context contextAmD;
    ArrayList<Model_ambDriverdetail> listAmb;
    View view;


    public Adapter_h_ambu_drvLidt(Context context,ArrayList<Model_ambDriverdetail> listAm)
    {
        this.contextAmD = context;
        this.listAmb = listAm;
    }

    @NonNull
    @Override
    public Adapter_h_ambu_drvLidt.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contextAmD).inflate(R.layout.h_amb_drv_itemrow,parent,false);
        return  new Adapter_h_ambu_drvLidt.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_h_ambu_drvLidt.Viewholder holder, int position) {

        Model_ambDriverdetail model_ambDriverdetail = listAmb.get(position);
        holder.Drivname.setText(model_ambDriverdetail.d_name);
        holder.phoneNoDrv.setText(model_ambDriverdetail.amb_number);

        holder.cardRecylAmbD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(view.getContext(), Actity_UpiPayment.class);
//                intent.putExtra("a",amount);
                view.getContext().startActivity(intent);
            }
        });



        view= holder.rootvieww;

    }

    @Override
    public int getItemCount() {
        return listAmb.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public CardView cardRecylAmbD;

        TextView Drivname, phoneNoDrv;
        View rootvieww;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            Drivname = itemView.findViewById(R.id.driver_name);
            phoneNoDrv = itemView.findViewById(R.id.phoneNoDrv);
            cardRecylAmbD = itemView.findViewById(R.id.cardRecylAmbD);

        }
    }
}
