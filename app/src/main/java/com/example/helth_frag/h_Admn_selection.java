package com.example.helth_frag;

import static android.content.Context.MODE_PRIVATE;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class h_Admn_selection extends Fragment {
    CardView add_userm;
    SharedPreferences sharedPreferences;

    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseAuth.AuthStateListener mAuthListener;

    Button logoutm;
    private Toolbar topAppBar;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.h__admn_selection, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        setHasOptionsMenu(true);

        add_userm = view.findViewById(R.id.add_user);
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

                NavHostFragment.findNavController(h_Admn_selection.this).navigate(R.id.action_h_Admn_selection_to_select_type_option);
                Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.men2:
                    // Save profile changes
                    return true;
                default:
                    return false;
            }
        });

//        logoutm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//
//                NavHostFragment.findNavController(h_Admn_selection.this).navigate(R.id.action_h_Admn_selection_to_select_type_option);
//                Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
//            }
//        });

        add_userm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Navigation.findNavController(view).navigate(R.id.action_h_Admn_selection_to_usr_registration);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_dotmenu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.men1:
//
//                FirebaseAuth.getInstance().signOut();
//
//                NavHostFragment.findNavController(h_Admn_selection.this).navigate(R.id.action_h_Admn_selection_to_select_type_option);
//                Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
//                return true;
//
//
//
//
//
//
////                FirebaseAuth.getInstance().signOut();
////                Intent i = new Intent(getActivity(),
////                        MainActivity.class);
////                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
////                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                startActivity(i);
//
//
//            case R.id.men2:
//                Toast.makeText(getActivity(), "men2", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.men3:
//                Toast.makeText(getActivity(), "men3", Toast.LENGTH_SHORT).show();
//                break;
//        }
//        return true;
//    }


}
