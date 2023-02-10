package com.example.helth_frag;

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

public class P_Payment extends Fragment {

    RecyclerView recyclerViewPymt;
    DatabaseReference databaseReference;
    Adapter_pymt adapterPymtr;
    ArrayList<model_d_addPrescriptn> lispymtt;

    private Toolbar topAppBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.p__payment, container, false);


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

//        topAppBar.setOnMenuItemClickListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.men1:
//                    FirebaseAuth.getInstance().signOut();
//                    Navigation.findNavController(view).navigate(R.id.patient1pg_to_select_type_option);
//                    Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.men2:
//                    // Save profile changes
//                    return true;
//                default:
//                    return false;
//            }
//        });


        recyclerViewPymt = view.findViewById(R.id.recyclerview);
        databaseReference= FirebaseDatabase.getInstance().getReference("Dp_description");
        DatabaseReference databaseReferenceP= FirebaseDatabase.getInstance().getReference("Patient");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        Toast.makeText(getActivity(), "mail"+email, Toast.LENGTH_SHORT).show();
        String HencodeUserEmail = email.replace(".", ",");

        Query emailP = databaseReferenceP.orderByChild("email").equalTo(email);

        emailP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotP) {
                if (snapshotP.exists()){
                    String getpidd = snapshotP.child(HencodeUserEmail).child("p_id").getValue(String.class);
                    Toast.makeText(getActivity(), "pid"+getpidd, Toast.LENGTH_SHORT).show();


                    Query checkemail = databaseReference.orderByChild("pid").equalTo(getpidd);

                    checkemail.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()){
                                Toast.makeText(requireContext(), "yes", Toast.LENGTH_SHORT).show();

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    model_d_addPrescriptn model_d_addPrescriptn = dataSnapshot.getValue(com.example.helth_frag.model_d_addPrescriptn.class);

                                    lispymtt.add(model_d_addPrescriptn);
                                    Toast.makeText(getActivity(), "3 list here", Toast.LENGTH_SHORT).show();
                                }
                            }else{}

                            Toast.makeText(getActivity(), "dd"+lispymtt.size(), Toast.LENGTH_SHORT).show();
                            adapterPymtr = new Adapter_pymt(
                                    getActivity(),lispymtt);
                            recyclerViewPymt.setAdapter(adapterPymtr);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }else {
                    Toast.makeText(getActivity(), "not not", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerViewPymt.setHasFixedSize(true);
        recyclerViewPymt.setLayoutManager(new LinearLayoutManager(getActivity()));

        lispymtt = new ArrayList<>();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String email = user.getEmail();
//        Toast.makeText(getActivity(), "mail"+email, Toast.LENGTH_SHORT).show();







        return view;
    }
}