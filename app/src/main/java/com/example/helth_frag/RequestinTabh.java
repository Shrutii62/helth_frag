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


public class RequestinTabh extends Fragment {

    RecyclerView recyclerViewR;
    DatabaseReference databaseReference;
    Adpter_ReceivedRequest adapteracc;
    ArrayList<Model_hrequestfrm> listR;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.requestin_intab, container, false);


        recyclerViewR = view.findViewById(R.id.recyclerviewrecivedR);

        Toast.makeText(getActivity(), "hellow", Toast.LENGTH_SHORT).show();


        recyclerViewR.setHasFixedSize(true);
        recyclerViewR.setLayoutManager(new LinearLayoutManager(getActivity()));

        listR = new ArrayList<>();


        databaseReference= FirebaseDatabase.getInstance().getReference("request");
        DatabaseReference HdatabaseReference=FirebaseDatabase.getInstance().getReference("Hospital");
        Query checkemail = HdatabaseReference.orderByChild("email").equalTo(email);
        String HencodeUserEmail = email.replace(".", ",");
        checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                if (snapshot1.exists()){
                    Toast.makeText(getActivity(), "ahe", Toast.LENGTH_SHORT).show();
                    String get_hid = snapshot1.child(HencodeUserEmail).child("h_id").getValue(String.class);
                    Toast.makeText(getActivity(), "get_hid "+ get_hid, Toast.LENGTH_SHORT).show();
                    Query hidcheck = databaseReference.orderByChild("hid_recivedRHos").equalTo(get_hid);



                    hidcheck.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            if (snapshot2.exists()){


                                    for (DataSnapshot dataSnapshot : snapshot2.getChildren()) {
                                        Model_hrequestfrm model_hrequestfrm = dataSnapshot.getValue(com.example.helth_frag.Model_hrequestfrm.class);

                                        listR.add(model_hrequestfrm);
                                        Toast.makeText(getActivity(), "3 list here", Toast.LENGTH_SHORT).show();
                                    }
                                    adapteracc = new Adpter_ReceivedRequest(
                                            getActivity(),listR);
                                    recyclerViewR.setAdapter(adapteracc);






//                                Query statusCheck= databaseReference.orderByChild("statusact").equalTo("on");

//                                statusCheck.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot3) {
//                                        if (snapshot3.exists()){
//
//                                            for (DataSnapshot dataSnapshot : snapshot3.getChildren()) {
//                                                Model_hrequestfrm model_hrequestfrm = dataSnapshot.getValue(com.example.helth_frag.Model_hrequestfrm.class);
//
//                                                listR.add(model_hrequestfrm);
//                                                Toast.makeText(getActivity(), "3 list here", Toast.LENGTH_SHORT).show();
//                                            }
//                                            adapteracc = new Adpter_acceptReg(
//                                                    getActivity(),listR);
//                                            recyclerViewR.setAdapter(adapteracc);
//
//                                        }else{
//                                            Toast.makeText(getActivity(), "stats not", Toast.LENGTH_SHORT).show();
//                                        }
//                                        Toast.makeText(getActivity(), "dd"+listR.size(), Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });




                            }else{ Toast.makeText(getActivity(), "nooooooooooo", Toast.LENGTH_SHORT).show();}

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });





                }else{
                    Toast.makeText(getActivity(), "not", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return view;
    }
}