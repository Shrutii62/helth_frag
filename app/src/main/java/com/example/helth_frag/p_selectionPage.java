package com.example.helth_frag;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.firebase.auth.FirebaseAuth;


public class p_selectionPage extends Fragment {
    CardView appointment, payment, t_reports;
    private Toolbar topAppBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.p_selection_page, container, false);

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

                    Navigation.findNavController(view).navigate(R.id.p_selectionPageTo_select_type_option);
                    Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
                    return true;
//                case R.id.men2:
//                    // Save profile changes
//                    return true;
                default:
                    return false;
            }
        });


        appointment = view.findViewById(R.id.takappointmnt);
        payment = view.findViewById(R.id.payment);
        t_reports = view.findViewById(R.id.t_reports);


        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.p_selectionPageTo_patient1pg);

            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.p_selectionPageTo_p_Payment);

            }
        });


        t_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.p_selectionPageTo_p_LabReportView);

            }
        });




        return view;
    }
}