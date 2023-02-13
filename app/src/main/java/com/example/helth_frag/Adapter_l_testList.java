package com.example.helth_frag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_l_testList extends RecyclerView.Adapter<Adapter_l_testList.Viewholder>{

    Context contextlt;
    ArrayList<model_d_addPrescriptn> listlt;
    View view;

    public Adapter_l_testList(Context context,ArrayList<model_d_addPrescriptn> listlt)
    {
        this.contextlt = context;
        this.listlt = listlt;
    }

    @NonNull
    @Override
    public Adapter_l_testList.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contextlt).inflate(R.layout.lab1_item_row,parent,false);
        return  new Adapter_l_testList.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_l_testList.Viewholder holder, int position) {

        model_d_addPrescriptn model_d_addPrescriptn= listlt.get(position);
        holder.textnamelt.setText(model_d_addPrescriptn.test_recomd);
//        holder.amnt.setText("Amount To Be Paid : ₹"+model_d_addPrescriptn.amount);
//



        view= holder.rootview;
    }

    @Override
    public int getItemCount() {
        return listlt.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public CardView cardRecylpytm;

        TextView textnamelt, amnt;

        View rootview;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            textnamelt = itemView.findViewById(R.id.Testamelt);
//            amnt = itemView.findViewById(R.id.amnt);
            cardRecylpytm = itemView.findViewById(R.id.cardRecylpytm);
        }
    }
}
