package com.example.helth_frag;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class h_request_servces extends Fragment {

    RecyclerView recyclerviewReq;
    DatabaseReference databaseReference_HD;
    Adapter_h_request adapter_h_request;
    ArrayList<modelHD> list_HD;
    Toolbar topAppBar;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.h_request_pg, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        setHasOptionsMenu(true);


        topAppBar = view.findViewById(R.id.topAppBar);
        topAppBar.inflateMenu(R.menu.main_dotmenu);

//        ((AppCompatActivity) getActivity()).setSupportActionBar(topAppBar);

        float radius = getResources().getDimension(R.dimen.default_corner_radius);
        MaterialShapeDrawable materialShapeDrawable = (MaterialShapeDrawable)topAppBar.getBackground();
        materialShapeDrawable.setShapeAppearanceModel(materialShapeDrawable.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,radius)
                .build());

        recyclerviewReq = view.findViewById(R.id.recyclerviewReq);


        recyclerviewReq.setHasFixedSize(true);
        recyclerviewReq.setLayoutManager(new LinearLayoutManager(getActivity()));

        list_HD = new ArrayList<>();
//        Toast.makeText(getActivity(), "e"+email, Toast.LENGTH_SHORT).show();

        databaseReference_HD= FirebaseDatabase.getInstance().getReference("Hospital");



        String encodeD_Email = email.replace(".", ",");

        databaseReference_HD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                if (snapshot1.exists()){
                    for (DataSnapshot dataSnapshot : snapshot1.getChildren()) {
                        modelHD modelHD = dataSnapshot.getValue(com.example.helth_frag.modelHD.class);

                        String hnam=snapshot1.child(encodeD_Email).child("hname").getValue(String.class);
//                        Toast.makeText(getActivity(), "hey"+hnam, Toast.LENGTH_SHORT).show();

                        list_HD.add(modelHD);

                    }

                }else {
                    Toast.makeText(getActivity(), "sanp1 not exist", Toast.LENGTH_SHORT).show();
                }
                adapter_h_request = new Adapter_h_request(
                        getActivity(),list_HD);
                recyclerviewReq.setAdapter(adapter_h_request);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }
}