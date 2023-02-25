package com.example.helth_frag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_dshow_ambBokking extends RecyclerView.Adapter<Adapter_dshow_ambBokking.Viewholder> {

    Context contextAmb;
    View view;
    ArrayList<Model_hAmb_book_frm> listAmb;
    String hname;


    public Adapter_dshow_ambBokking(Context context, ArrayList<Model_hAmb_book_frm> list){
        this.contextAmb = context;
        this.listAmb = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contextAmb).inflate(R.layout.driverviewbking_list_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Model_hAmb_book_frm model_hAmb_book_frm = listAmb.get(position);
        holder.haddressget.setText(model_hAmb_book_frm.Address);
        holder.date.setText(model_hAmb_book_frm.dateget);
        holder.time.setText(model_hAmb_book_frm.timeget);

        view= holder.rootview;




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bookingReference = database.getReference("bookAmbulance");
        Query getHname = bookingReference.orderByChild("get_Hid").equalTo(model_hAmb_book_frm.get_Hid);
        getHname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    hname = snapshot.child(model_hAmb_book_frm.getKey()).child("get_Hid").getValue(String.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.hos_name.setText(hname);

        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();

                if (checked){
                    FirebaseDatabase firebaseA= FirebaseDatabase.getInstance();
                    DatabaseReference databaseReferenceA = firebaseA.getReference("bookAmbulance");

                    databaseReferenceA.child(model_hAmb_book_frm.key).child("status").setValue("off");
                }
                else{
                    // Do your coding
                }
            }
        });






    }

    @Override
    public int getItemCount() {
        return listAmb.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {


        TextView hos_name,haddressget,date, time;
        CardView cardviewD;
        CheckBox done;
        View rootview;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            String hname;
            date = itemView.findViewById(R.id.date);
            done = itemView.findViewById(R.id.done);
            time = itemView.findViewById(R.id.time);
            hos_name = itemView.findViewById(R.id.hos_name);
            haddressget = itemView.findViewById(R.id.haddressget);
            cardviewD = itemView.findViewById(R.id.cardviewD);


        }
    }
}
