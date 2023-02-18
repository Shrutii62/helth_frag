package com.example.helth_frag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class select_type_option extends Fragment {

     Button hosRs,userRs,Patient, ambulance;

    private static SharedPreferences mPrefs;
    private static SharedPreferences.Editor mPrefsEditor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.select_type_option, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();


        hosRs= view.findViewById(R.id.hosR);
        userRs= view.findViewById(R.id.usrR);
        Patient= view.findViewById(R.id.Patient);
        ambulance= view.findViewById(R.id.Ambulance);




        hosRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("user_id", "h");
                h_login newFragment = new h_login ();
                newFragment.setArguments(args);
                Navigation.findNavController(view).navigate(R.id.action_select_type_option_to_usr_login,args);
            }
        });

        userRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle args = new Bundle();
                args.putString("user_id", "u");
                h_login newFragment = new h_login ();
                newFragment.setArguments(args);

//                Bundle bundle = new Bundle();
//                bundle.putInt("VAL", 1);
//
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("strUserName", true);
//                editor.commit();


                Navigation.findNavController(view).navigate(R.id.action_select_type_option_to_usr_login,args);

            }
        });

        Patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();
                args.putString("user_id", "p");
                h_login newFragment = new h_login ();
                newFragment.setArguments(args);
                Navigation.findNavController(view).navigate(R.id.action_select_type_option_to_usr_login,args);
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Bundle args = new Bundle();
//                args.putString("user_id", "p");
//                h_login newFragment = new h_login ();
//                newFragment.setArguments(args);
                Navigation.findNavController(view).navigate(R.id.action_select_type_option_to_amb_Reg);
            }
        });




        return view;
    }


}