package com.example.helth_frag;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class splashScreen extends Fragment {

    protected static  int timer = 2000;
    ImageView imageView;
//    TextView textView;
    Animation upperanimation, bottomanimation;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.splash_screen, container, false);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();



        imageView = view.findViewById(R.id.splashimage);

        imageView.setAnimation(upperanimation);

        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();


//        Bundle extras = getActivity().getIntent().getExtras();
//        if (extras != null) {
//            String value = extras.getString("key");
//            Navigation.findNavController(view).navigate(R.id.toselection_type_option);
//
//        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user !=null){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    String email = user.getEmail();
                    String phone = user.getPhoneNumber();

                    DatabaseReference databaseReference = firebaseDatabase.getReference("Hospital");

//                    Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();

                    Query checkemail = databaseReference.orderByChild("email").equalTo(email);
                    String HencodeUserEmail = email.replace(".", ",");



                    checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String abc = snapshot.child(HencodeUserEmail).child("email").getValue(String.class);


                            if (snapshot.exists()) {
                                Navigation.findNavController(view).navigate(R.id.h_Admn_selection);
                            }else{
                                DatabaseReference UdatabaseReference = firebaseDatabase.getReference("Users");
                                Query Ucheckemail = UdatabaseReference.orderByChild("email").equalTo(email);

                                Ucheckemail.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dsnapshot) {

                                        String cde = dsnapshot.child(HencodeUserEmail).child("email").getValue(String.class);
                                        String userType = dsnapshot.child(HencodeUserEmail).child("userTypedd").getValue(String.class);


                                        if (dsnapshot.exists()) {
                                            if (userType.equals("1")){
                                                Navigation.findNavController(view).navigate(R.id.user1stpg);
                                            }else if (userType.equals("2")){
                                                Navigation.findNavController(view).navigate(R.id.lab12);
                                            }

                                        }else{
                                            DatabaseReference UdatabaseReference = firebaseDatabase.getReference("Patient");
                                            Query Ucheckemail = UdatabaseReference.orderByChild("email").equalTo(email);

                                            Ucheckemail.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dsnapshot) {

                                                    String cde = dsnapshot.child(HencodeUserEmail).child("email").getValue(String.class);

                                                    if (dsnapshot.exists()) {

                                                        Navigation.findNavController(view).navigate(R.id.stop_selectionPage);
                                                    }else {


                                                        DatabaseReference databaseReferenceAmb = firebaseDatabase.getReference("amb_Drvdetails");

//                                                        Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();

                                                        Query checkPhone = databaseReferenceAmb.orderByChild("reg_phoned").equalTo(phone);


                                                        checkPhone.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                    if (phone!=null){
                                                                        Navigation.findNavController(view).navigate(R.id.tablayoutAmb);
                                                                    }

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
                                        }



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







                }

            }, timer);


        }
        else {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Navigation.findNavController(view).navigate(R.id.toselection_type_option);

                }
            }, timer);        }

//        getActivity().getFragmentManager().popBackStack();












        return view;
    }


}