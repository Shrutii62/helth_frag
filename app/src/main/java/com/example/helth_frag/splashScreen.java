package com.example.helth_frag;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

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


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public splashScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment splashScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static splashScreen newInstance(String param1, String param2) {
        splashScreen fragment = new splashScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.splash_screen, container, false);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        imageView = view.findViewById(R.id.splashimage);
//        textView = view.findViewById(R.id.splashtext);

//        upperanimation= AnimationUtils.loadAnimation(getActivity(),R.anim.upper_animation);
//        bottomanimation= AnimationUtils.loadAnimation(getActivity(),R.anim.bottom_animation);

        imageView.setAnimation(upperanimation);
//        textView.setAnimation(bottomanimation);

        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user !=null){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    String email = user.getEmail();

                    DatabaseReference databaseReference = firebaseDatabase.getReference("Hospital");

                    Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();

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


                                        if (dsnapshot.exists()) {
                                            Navigation.findNavController(view).navigate(R.id.user1stpg);
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