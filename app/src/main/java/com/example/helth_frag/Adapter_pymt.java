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

public class Adapter_pymt extends RecyclerView.Adapter<Adapter_pymt.Viewholder>{

    Context contextD;
    ArrayList<model_d_addPrescriptn> listpytm;
    View view;

    public Adapter_pymt(Context context,ArrayList<model_d_addPrescriptn> listpytm)
    {
        this.contextD = context;
        this.listpytm = listpytm;
    }

    @NonNull
    @Override
    public Adapter_pymt.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contextD).inflate(R.layout.pytm_item_row,parent,false);
        return  new Adapter_pymt.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_pymt.Viewholder holder, int position) {

        model_d_addPrescriptn model_d_addPrescriptn= listpytm.get(position);
        holder.Date.setText(model_d_addPrescriptn.Date);
        holder.amnt.setText("Amount To Be Paid : ₹"+model_d_addPrescriptn.amount);

        holder.cardRecylpytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amount = listpytm.get(holder.getAdapterPosition()).amount;

                Intent intent = new Intent(view.getContext(), Actity_UpiPayment.class);
                intent.putExtra("a",amount);
                view.getContext().startActivity(intent);
//                view.getContext().startActivity(intent);
            }
        });



        view= holder.rootview;
    }

    @Override
    public int getItemCount() {
        return listpytm.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder  {

        public CardView cardRecylpytm;
        TextView issue, email, date, time, status;
        TextView Date, amnt;
        ImageView dot3;
        View rootview;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            Date = itemView.findViewById(R.id.test_rprtName);
            amnt = itemView.findViewById(R.id.amnt);
            cardRecylpytm = itemView.findViewById(R.id.cardRecylpytm);
        }

    }
}
