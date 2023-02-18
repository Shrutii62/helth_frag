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

public class p_AdapterPdf  extends RecyclerView.Adapter<p_AdapterPdf.Viewholder>{

    Context contextpdf;
    ArrayList<ModelP_pdfuplod> listpdf;
    View view;

    public p_AdapterPdf(Context context, ArrayList<ModelP_pdfuplod> list){

        this.contextpdf =context;
        this.listpdf=list;
    }

    @NonNull
    @Override
    public p_AdapterPdf.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contextpdf).inflate(R.layout.pdfview_item_row,parent,false);
        return new p_AdapterPdf.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull p_AdapterPdf.Viewholder holder, int position) {

        ModelP_pdfuplod modelP_pdfuplod= listpdf.get(position);
        holder.pdfName.setText(modelP_pdfuplod.pdfTitle);

        holder.cardRecylpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.cardRecylpdf.getContext(),p_view_act.class);
                intent.putExtra("titlename",modelP_pdfuplod.getPdfTitle());
                intent.putExtra("pdf",modelP_pdfuplod.getPdfURL());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.cardRecylpdf.getContext().startActivity(intent);
            }
        });

//        holder.viewpdf.setText(modelP_pdfuplod.getPdfURL());

//        holder.status.setText(model_appointment.getStatus());

        view= holder.rootview;



    }

    @Override
    public int getItemCount() {
        return listpdf.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        CardView cardRecylpdf;
        TextView pdfName, viewpdf;
        ImageView dot3;
        View rootview;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            pdfName = itemView.findViewById(R.id.pdfName);
            cardRecylpdf = itemView.findViewById(R.id.cardRecylpdf);
            viewpdf = itemView.findViewById(R.id.viewpdf);


        }
    }
}
