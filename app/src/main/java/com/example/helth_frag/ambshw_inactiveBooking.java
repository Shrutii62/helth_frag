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


public class ambshw_inactiveBooking extends Fragment {

    RecyclerView recyclerViewAmpInac;
    DatabaseReference databaseReference;
    AdapterAmbulanceList adapterAmbInc;
    ArrayList<Model_ambDriverdetail> listAmbInc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.ambshw_inactive_booking, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String phone = user.getPhoneNumber();
        Toast.makeText(getActivity(), "ph"+phone, Toast.LENGTH_SHORT).show();

        recyclerViewAmpInac = view.findViewById(R.id.recyclerviewAmbInAc);


        recyclerViewAmpInac.setHasFixedSize(true);
        recyclerViewAmpInac.setLayoutManager(new LinearLayoutManager(getActivity()));

        listAmbInc = new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference("bookAmbulance");
        DatabaseReference UdatabaseReference=FirebaseDatabase.getInstance().getReference("Users");

        Query phonCheck = databaseReference.orderByChild("reg_phoned").equalTo(phone);



        phonCheck.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                if (snapshot2.exists()){

                    Query statusCheck= databaseReference.orderByChild("status").equalTo("off");

                    statusCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot3) {
                            if (snapshot3.exists()){

                                for (DataSnapshot dataSnapshot : snapshot3.getChildren()) {
                                    Model_ambDriverdetail model_ambDriverdetail = dataSnapshot.getValue(com.example.helth_frag.Model_ambDriverdetail.class);

                                    listAmbInc.add(model_ambDriverdetail);
//                                                Toast.makeText(getActivity(), "3 list here", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(getActivity(), "stats not", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(), "dd"+listAmbInc.size(), Toast.LENGTH_SHORT).show();
                            adapterAmbInc = new AdapterAmbulanceList(
                                    getActivity(),listAmbInc);
                            recyclerViewAmpInac.setAdapter(adapterAmbInc);
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