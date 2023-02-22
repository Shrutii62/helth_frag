package com.example.helth_frag;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adapter_viewReq extends RecyclerView.Adapter<Adapter_viewReq.Viewholder>{


    Context contextV;
    ArrayList<Model_hrequestfrm> listV;
    View view;

    public Adapter_viewReq(Context context,ArrayList<Model_hrequestfrm> listRacc)
    {
        this.contextV = context;
        this.listV = listRacc;
    }

    @NonNull
    @Override
    public Adapter_viewReq.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contextV).inflate(R.layout.item_accpt_req,parent,false);
        return  new Adapter_viewReq.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_viewReq.Viewholder holder, int position) {


        Model_hrequestfrm model_hrequestfrm= listV.get(position);

        holder.hname.setText(model_hrequestfrm.h_name);
        holder.pname.setText(model_hrequestfrm.pname);
        holder.description.setText(model_hrequestfrm.description);

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {


                    case R.id.r1:
                        FirebaseDatabase firebaseA= FirebaseDatabase.getInstance();
                        DatabaseReference databaseReferenceA = firebaseA.getReference("request");

                        databaseReferenceA.child(model_hrequestfrm.getKey()).child("statusact").setValue("on");
                        break;
                    case R.id.r2:
                        FirebaseDatabase firebaseB= FirebaseDatabase.getInstance();
                        DatabaseReference databaseReferenceB = firebaseB.getReference("request");

                        databaseReferenceB.child(model_hrequestfrm.getKey()).child("statusact").setValue("off");
                        break;
                    default:
                        Log.v(TAG, "Huh?");
                        break;
                }


            }
        });

        view= holder.rootview;

    }

    @Override
    public int getItemCount() {
        return listV.size();
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
            r1 = itemView.findViewById(R.id.r1);
            r2 = itemView.findViewById(R.id.r2);
//            amnt = itemView.findViewById(R.id.amnt);
            cardviewRaccpt = itemView.findViewById(R.id.cardviewRaccpt);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            description = itemView.findViewById(R.id.descriptionacc);

        }
    }
}
