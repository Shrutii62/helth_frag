package com.example.helth_frag;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.firebase.auth.FirebaseAuth;

import pl.droidsonroids.gif.GifDecoder;


public class user1stpg extends Fragment {



//    CardView  invntoryD;
    private Toolbar topAppBar;

    LinearLayout appt_details,invntoryD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user1stpg, container, false);
//        setHasOptionsMenu(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
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

                    NavHostFragment.findNavController(user1stpg.this).navigate(R.id.action_user1stpg_to_select_type_option);
                    Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
                    return true;
//                case R.id.men2:
//                    // Save profile changes
//                    return true;
                default:
                    return false;
            }
        });





        appt_details = view.findViewById(R.id.appt_details);
        invntoryD = view.findViewById(R.id.invntoryD);


        appt_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(),tablayoutP.class);
                startActivity(intent);

//                NavHostFragment.findNavController(user1stpg.this).navigate(R.id.u_to_d_get_pApp_details2);




            }
        });

        invntoryD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog();
                Bundle bundle = new Bundle();
                bundle.putString("navigate", "Doctor");
                ManageInventory mi = new ManageInventory();
                mi.setArguments(bundle);

                Navigation.findNavController(view).navigate(R.id.action_user1stpg_to_manageInventory, bundle);

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
//                NavHostFragment.findNavController(user1stpg.this).navigate(R.id.action_user1stpg_to_select_type_option);
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