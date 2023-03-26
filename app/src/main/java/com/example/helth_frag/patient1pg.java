package com.example.helth_frag;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import java.util.HashMap;


public class patient1pg extends Fragment {
RecyclerView recyclerView;
DatabaseReference databaseReference;
Adapter adapter;
ArrayList<modelH_usr> list;

    private Toolbar topAppBar;


    Button abc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
View view = inflater.inflate(R.layout.patient1pg, container, false);;
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



        topAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.men1:
                    FirebaseAuth.getInstance().signOut();

                    NavHostFragment.findNavController(patient1pg.this).navigate(R.id.patient1pg_to_select_type_option);
                    Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
                    return true;
//                case R.id.men2:
//                    // Save profile changes
//                    return true;
                default:
                    return false;
            }
        });




//abc = view.findViewById(R.id.abc);
//abc.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//
//
//        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference("Patient");
//        databaseReference.child("pname").child("phoneno").setValue("abcfgf");
//        Toast.makeText(getActivity(), "next", Toast.LENGTH_SHORT).show();
//
//
//
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String email = user.getEmail();
//        Toast.makeText(getActivity(), ""+email, Toast.LENGTH_SHORT).show();
//
//
//    }
//});

        recyclerView = view.findViewById(R.id.recyclerview);
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference adatabaseReference=FirebaseDatabase.getInstance().getReference("Patient");



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        Toast.makeText(getActivity(), "mail"+email, Toast.LENGTH_SHORT).show();
        Query checkemail = adatabaseReference.orderByChild("email").equalTo(email);
        String HencodeUserEmail = email.replace(".", ",");

        checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                if (snapshot1.exists()) {
                    Toast.makeText(getActivity(), "ahe", Toast.LENGTH_SHORT).show();
                    String gethid = snapshot1.child(HencodeUserEmail).child("h_id").getValue(String.class);
                    Toast.makeText(getActivity(), "mila"+ gethid, Toast.LENGTH_SHORT).show();

                    Query hidcheck = databaseReference.orderByChild("h_id").equalTo(gethid);

                    hidcheck.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            if (snapshot2.exists()){
                                Toast.makeText(getActivity(), "hidcheck_mila"+ gethid, Toast.LENGTH_SHORT).show();
                                Query checkDoc = databaseReference.orderByChild("userTypedd").equalTo("4");

                                checkDoc.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if (snapshot.exists()){
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                modelH_usr modelH_usr = dataSnapshot.getValue(com.example.helth_frag.modelH_usr.class);

//                                                list.add(modelH_usr);



                                                if(modelH_usr.h_id.equals(gethid)){
                                                list.add(modelH_usr);
                                                }else {

                                                    Toast.makeText(getActivity(), "hjghjgjh", Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                        }else {
                                            Toast.makeText(getActivity(), "nooooooooooo", Toast.LENGTH_SHORT).show();
                                        }
                                        Toast.makeText(getActivity(), "dd"+list.size(), Toast.LENGTH_SHORT).show();
                                        adapter = new Adapter(getActivity(),list);
                                        recyclerView.setAdapter(adapter);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_dotmenu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}