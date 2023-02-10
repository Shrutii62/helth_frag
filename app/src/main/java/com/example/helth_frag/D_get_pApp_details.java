package com.example.helth_frag;

import android.os.Bundle;

import androidx.annotation.NonNull;
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


public class D_get_pApp_details extends Fragment {

//Button bb;
    RecyclerView recyclerViewD;
    DatabaseReference databaseReference;
    Adapter_getApptmtDetail_D adapterD;
    ArrayList<model_appointment> listD;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.d_get_papp_details, container, false);

//        bb=view.findViewById(R.id.bb);
//        bb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.d_to_tryy);
//            }
//        });

        recyclerViewD = view.findViewById(R.id.recyclerviewD);
        databaseReference= FirebaseDatabase.getInstance().getReference("appointment");
        DatabaseReference UdatabaseReference=FirebaseDatabase.getInstance().getReference("Users");

        recyclerViewD.setHasFixedSize(true);
        recyclerViewD.setLayoutManager(new LinearLayoutManager(getActivity()));

        listD = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        Toast.makeText(getActivity(), "mail"+email, Toast.LENGTH_SHORT).show();
        Query checkemail = UdatabaseReference.orderByChild("email").equalTo(email);
        String HencodeUserEmail = email.replace(".", ",");

//        checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot1) {
//                if (snapshot1.exists()){
//                    Toast.makeText(getActivity(), "ahe", Toast.LENGTH_SHORT).show();
//                    String get_did = snapshot1.child(HencodeUserEmail).child("u_id").getValue(String.class);
//                    Toast.makeText(getActivity(), "mila"+ get_did, Toast.LENGTH_SHORT).show();
//
//
//                    Query didcheck = databaseReference.orderByChild("d_id").equalTo(get_did);
//
//
//
//                    didcheck.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
//                            if (snapshot2.exists()){
////                                Toast.makeText(getActivity(), "hidcheck_mila"+ get_did, Toast.LENGTH_SHORT).show();
////                                for (DataSnapshot dataSnapshot : snapshot2.getChildren()) {
////                                    model_appointment model_appointment = dataSnapshot.getValue(com.example.helth_frag.model_appointment.class);
////
////                                    listD.add(model_appointment);}
//
//                                Query statusCheck= databaseReference.orderByChild("status").equalTo("on");
//                                    statusCheck.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot snapshot3) {
//                                            if (snapshot3.exists()){
//
//                                                Toast.makeText(getActivity(), "3 exist", Toast.LENGTH_SHORT).show();
//                                                for (DataSnapshot dataSnapshot : snapshot3.getChildren()) {
//                                                    model_appointment model_appointment = dataSnapshot.getValue(com.example.helth_frag.model_appointment.class);
//
//                                                    listD.add(model_appointment);
//                                                    Toast.makeText(getActivity(), "3 list here", Toast.LENGTH_SHORT).show();
//                                                }
//
//                                            }else{
//                                                Toast.makeText(getActivity(), "stats not", Toast.LENGTH_SHORT).show();
//                                            }
//
//                                            adapterD = new Adapter_getApptmtDetail_D(
//                                                    getActivity(),listD
//                                            );
//                                            recyclerViewD.setAdapter(adapterD);
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError error) {
//
//                                        }
//                                    });
//
//
//
//
//                                }else{ Toast.makeText(getActivity(), "nooooooooooo", Toast.LENGTH_SHORT).show();}
//
//                            Toast.makeText(getActivity(), "dd"+listD.size(), Toast.LENGTH_SHORT).show();
//                            adapterD = new Adapter_getApptmtDetail_D(
//                                    getActivity(),listD);
//                            recyclerViewD.setAdapter(adapterD);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//
//                }else{}
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                if (snapshot1.exists()){
                    Toast.makeText(getActivity(), "ahe", Toast.LENGTH_SHORT).show();
                    String get_did = snapshot1.child(HencodeUserEmail).child("u_id").getValue(String.class);
                    Toast.makeText(getActivity(), "mila"+ get_did, Toast.LENGTH_SHORT).show();

                    Query didcheck = databaseReference.orderByChild("d_id").equalTo(get_did);



                    didcheck.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            if (snapshot2.exists()){
//                                Toast.makeText(getActivity(), "hidcheck_mila"+ get_did, Toast.LENGTH_SHORT).show();
//                                for (DataSnapshot dataSnapshot : snapshot2.getChildren()) {
//                                    model_appointment model_appointment = dataSnapshot.getValue(com.example.helth_frag.model_appointment.class);
//
//                                    listD.add(model_appointment);
//                                }
                                Query statusCheck= databaseReference.orderByChild("status").equalTo("on");

                                statusCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot3) {
                                        if (snapshot3.exists()){

                                            for (DataSnapshot dataSnapshot : snapshot3.getChildren()) {
                                                model_appointment model_appointment = dataSnapshot.getValue(com.example.helth_frag.model_appointment.class);

                                                listD.add(model_appointment);
//                                                Toast.makeText(getActivity(), "3 list here", Toast.LENGTH_SHORT).show();
                                            }

                                        }else{
                                            Toast.makeText(getActivity(), "stats not", Toast.LENGTH_SHORT).show();
                                        }
                                        Toast.makeText(getActivity(), "dd"+listD.size(), Toast.LENGTH_SHORT).show();
                                        adapterD = new Adapter_getApptmtDetail_D(
                                                getActivity(),listD);
                                        recyclerViewD.setAdapter(adapterD);
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


                }else{}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;


    }
}