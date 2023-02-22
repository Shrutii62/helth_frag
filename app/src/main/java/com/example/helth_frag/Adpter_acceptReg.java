package com.example.helth_frag;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adpter_acceptReg extends RecyclerView.Adapter<Adpter_acceptReg.Viewholder>{


    Context contextRaccpt;
    ArrayList<Model_hrequestfrm> listRaccpt;
    View view;

    public Adpter_acceptReg(Context context,ArrayList<Model_hrequestfrm> listRacc)
    {
        this.contextRaccpt = context;
        this.listRaccpt = listRacc;
    }


    @NonNull
    @Override
    public Adpter_acceptReg.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contextRaccpt).inflate(R.layout.item_accpt_req,parent,false);
        return  new Adpter_acceptReg.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adpter_acceptReg.Viewholder holder, int position) {

        Model_hrequestfrm model_hrequestfrm= listRaccpt.get(position);

        holder.hname.setText(model_hrequestfrm.h_name);
        holder.pname.setText(model_hrequestfrm.pname);
        holder.description.setText(model_hrequestfrm.description);








        view= holder.rootview;




    }

    @Override
    public int getItemCount() {
        return listRaccpt.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public CardView cardviewRaccpt;
        ImageView call;

        TextView pname, hname, description;
        RadioButton r1, r2;
        RadioGroup radioGroup;
        View rootview;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            pname = itemView.findViewById(R.id.P_name);
            hname = itemView.findViewById(R.id.hnameacc);

//            amnt = itemView.findViewById(R.id.amnt);
            cardviewRaccpt = itemView.findViewById(R.id.cardviewRaccpt);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            description = itemView.findViewById(R.id.descriptionacc);

        }
    }
}
