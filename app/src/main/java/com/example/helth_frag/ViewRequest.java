package com.example.helth_frag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

public class ViewRequest extends AppCompatActivity {


    RecyclerView recyclerViewV;
    DatabaseReference databaseReferenceV;
    Adapter_viewReq adapterV;
    ArrayList<Model_hrequestfrm> listV;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hr_view_request);


        recyclerViewV = findViewById(R.id.recyclerviewV);


        recyclerViewV.setHasFixedSize(true);
        recyclerViewV.setLayoutManager(new LinearLayoutManager(this));

        listV = new ArrayList<>();


        databaseReferenceV= FirebaseDatabase.getInstance().getReference("request");
        DatabaseReference HdatabaseReference=FirebaseDatabase.getInstance().getReference("Hospital");
        Query checkemail = HdatabaseReference.orderByChild("email").equalTo(email);
        String HencodeUserEmail = email.replace(".", ",");
        checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                if (snapshot1.exists()){
//                    Toast.makeText(ViewRequest.this, "ahe", Toast.LENGTH_SHORT).show();
                    String get_hid = snapshot1.child(HencodeUserEmail).child("h_id").getValue(String.class);
//                    Toast.makeText(ViewRequest.this, "get_hid "+ get_hid, Toast.LENGTH_SHORT).show();
                    Query hidcheck = databaseReferenceV.orderByChild("hid").equalTo(get_hid);





                    hidcheck.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            if (snapshot2.exists()){

                                for (DataSnapshot dataSnapshot : snapshot2.getChildren()) {
                                    Model_hrequestfrm model_hrequestfrm = dataSnapshot.getValue(com.example.helth_frag.Model_hrequestfrm.class);

                                    listV.add(model_hrequestfrm);
//                                                Toast.makeText(getActivity(), "3 list here", Toast.LENGTH_SHORT).show();
                                }
                                adapterV = new Adapter_viewReq(
                                        ViewRequest.this,listV);
                                recyclerViewV.setAdapter(adapterV);

//                                Query statusCheck= databaseReferenceV.orderByChild("statusact")
//                                statusCheck.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot3) {
//                                        if (snapshot3.exists()){
//
//                                            for (DataSnapshot dataSnapshot : snapshot3.getChildren()) {
//                                                Model_hrequestfrm model_hrequestfrm = dataSnapshot.getValue(com.example.helth_frag.Model_hrequestfrm.class);
//
//                                                listV.add(model_hrequestfrm);
////                                                Toast.makeText(getActivity(), "3 list here", Toast.LENGTH_SHORT).show();
//                                            }
//
//                                        }else{
//                                            Toast.makeText(ViewRequest.this, "stats not", Toast.LENGTH_SHORT).show();
//                                        }
//                                        Toast.makeText(ViewRequest.this, "dd"+listV.size(), Toast.LENGTH_SHORT).show();
//                                        adapterV = new Adapter_viewReq(
//                                                ViewRequest.this,listV);
//                                        recyclerViewV.setAdapter(adapterV);
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                })
                            }else{ Toast.makeText(ViewRequest.this, "nooooooooooo", Toast.LENGTH_SHORT).show();}

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



    }
}