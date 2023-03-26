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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


public class Lab1 extends Fragment {

    RecyclerView recyclerviewLT;
    DatabaseReference databaseReference_DP;
    Adapter_l_testList adapter_l_testList;
    ArrayList<model_d_addPrescriptn> list_lt;
    private Toolbar topAppBar;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lab1, container, false);

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





//        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.mngInvetory:
//                        Bundle bundle = new Bundle();
//                        bundle.putString("navigate","Lab");
//                        ManageInventory mi = new ManageInventory();
//                        mi.setArguments(bundle);
//                        Navigation.findNavController(view).navigate(R.id.lab1_to_manageInventory2,bundle);
//                        return true;
//
//                    case R.id.logOut:
//                        FirebaseAuth.getInstance().signOut();
//
//                        Toast.makeText(getActivity(), "out", Toast.LENGTH_SHORT).show();
//                        Navigation.findNavController(view).navigate(R.id.lab1_to_select_type_option2);
//                        return true;
//
//                }
//                return false;
//            }
//        });

        topAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.men1:
                    FirebaseAuth.getInstance().signOut();

                    NavHostFragment.findNavController(Lab1.this).navigate(R.id.l_to_selection);
                    Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
                    return true;
//                case R.id.men2:
//                    // Save profile changes
//                    return true;
                default:
                    return false;
            }
        });




        recyclerviewLT = view.findViewById(R.id.recyclerviewLT);


        recyclerviewLT.setHasFixedSize(true);
        recyclerviewLT.setLayoutManager(new LinearLayoutManager(getActivity()));

        list_lt = new ArrayList<>();


        databaseReference_DP= FirebaseDatabase.getInstance().getReference("Dp_description");
        DatabaseReference databaseReferenceUl= FirebaseDatabase.getInstance().getReference("Users");

        Query checkemail = databaseReferenceUl.orderByChild("email").equalTo(email);
        String HencodeUserEmail = email.replace(".", ",");
        checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                if (snapshot1.exists()){
                    String get_hid = snapshot1.child(HencodeUserEmail).child("h_id").getValue(String.class);
                    Query hd = databaseReference_DP.orderByChild("hid").equalTo(get_hid);
                    hd.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            if (snapshot2.exists()){
                                for (DataSnapshot dataSnapshot : snapshot2.getChildren()) {
                                    model_d_addPrescriptn model_d_addPrescriptn = dataSnapshot.getValue(com.example.helth_frag.model_d_addPrescriptn.class);

                                    list_lt.add(model_d_addPrescriptn);

                                }
                            }else{
                                Toast.makeText(getActivity(), "sanp2 not exist", Toast.LENGTH_SHORT).show();
                            }
                            adapter_l_testList = new Adapter_l_testList(
                                    getActivity(),list_lt);
                            recyclerviewLT.setAdapter(adapter_l_testList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }else {
                    Toast.makeText(getActivity(), "sanp1 not exist", Toast.LENGTH_SHORT).show();
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