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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adpter_ReceivedRequest extends RecyclerView.Adapter<Adpter_ReceivedRequest.Viewholder>{


    Context contextRaccpt;
    ArrayList<Model_hrequestfrm> listRaccpt;
    View view;

    public Adpter_ReceivedRequest(Context context, ArrayList<Model_hrequestfrm> listRacc)
    {
        this.contextRaccpt = context;
        this.listRaccpt = listRacc;
    }


    @NonNull
    @Override
    public Adpter_ReceivedRequest.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contextRaccpt).inflate(R.layout.item_requestget,parent,false);
        return  new Adpter_ReceivedRequest.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adpter_ReceivedRequest.Viewholder holder, int position) {

        Model_hrequestfrm model_hrequestfrm= listRaccpt.get(position);

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

                        databaseReferenceA.child(model_hrequestfrm.getKey()).child("statusact").setValue("on").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(contextRaccpt, "edited", Toast.LENGTH_SHORT).show();
                            }
                        });
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
