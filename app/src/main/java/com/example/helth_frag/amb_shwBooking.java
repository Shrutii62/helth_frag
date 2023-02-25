package com.example.helth_frag;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class amb_shwBooking extends Fragment {

    RecyclerView recyclerViewAmb;
    DatabaseReference databaseReferenceBAm;
    AdapterAmbulanceList adapterAmb;
    ArrayList<Model_hAmb_book_frm> listamb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.amb_shw_booking, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String phone = user.getPhoneNumber();
        Toast.makeText(getActivity(), "ph"+phone, Toast.LENGTH_SHORT).show();

        recyclerViewAmb = view.findViewById(R.id.recyclerviewAmbAc);


        recyclerViewAmb.setHasFixedSize(true);
        recyclerViewAmb.setLayoutManager(new LinearLayoutManager(getActivity()));

        listamb = new ArrayList<>();

        databaseReferenceBAm= FirebaseDatabase.getInstance().getReference("bookAmbulance");
        DatabaseReference UdatabaseReference=FirebaseDatabase.getInstance().getReference("Users");

        Query phonCheck = databaseReferenceBAm.orderByChild("reg_phoned").equalTo(phone);



        phonCheck.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                if (snapshot2.exists()){

                    Query statusCheck= databaseReferenceBAm.orderByChild("status").equalTo("on");

                    statusCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot3) {
                            if (snapshot3.exists()){

                                for (DataSnapshot dataSnapshot : snapshot3.getChildren()) {
                                    Model_hAmb_book_frm model_hAmb_book_frm = dataSnapshot.getValue(com.example.helth_frag.Model_hAmb_book_frm.class);

                                    listamb.add(model_hAmb_book_frm);
//                                                Toast.makeText(getActivity(), "3 list here", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(getActivity(), "stats not", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(), "dd"+listamb.size(), Toast.LENGTH_SHORT).show();
                            adapterAmb = new AdapterAmbulanceList(
                                    getActivity(),listamb);
                            recyclerViewAmb.setAdapter(adapterAmb);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }else{ Toast.makeText(getActivity(), "nooooooooooo", Toast.LENGTH_SHORT).show();}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot1) {
//                if (snapshot1.exists()){
//                    Toast.makeText(getActivity(), "ahe", Toast.LENGTH_SHORT).show();
//                    String get_did = snapshot1.child(HencodeUserEmail).child("u_id").getValue(String.class);
//                    Toast.makeText(getActivity(), "did"+ get_did, Toast.LENGTH_SHORT).show();
//
//
//
//
//
//
//
//
//                }else{}
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });




        return view;




    }
}