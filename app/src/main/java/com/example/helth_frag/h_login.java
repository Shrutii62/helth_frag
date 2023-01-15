package com.example.helth_frag;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class h_login extends Fragment {

    ProgressDialog progressDialog ;
    FirebaseAuth auth;
    //google

    //for database
    FirebaseDatabase database;

    ImageView Imback;
    static Button createAcc, btlogon;
    TextInputLayout email, passwdll;
    CheckBox chRemmbrme1M;

    TextInputEditText emailE,passwdllE;












    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public h_login() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static h_login newInstance(String param1, String param2) {
        h_login fragment = new h_login();
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
        View view=inflater.inflate(R.layout.h_login, container, false);



        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();


        createAcc = view.findViewById(R.id.btcreateAcc1);
        btlogon = view.findViewById(R.id.btlogon1);
        email = view.findViewById(R.id.email);
        chRemmbrme1M = view.findViewById(R.id.chRemmbrme1);
        passwdll = view.findViewById(R.id.passwdl);
        Imback = view.findViewById(R.id.Imback);

        emailE = view.findViewById(R.id.emailE);
        passwdllE = view.findViewById(R.id.passwdlE);

        auth = FirebaseAuth.getInstance();
        //for database
        database= FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Login");
        progressDialog.setMessage("login to your account your Account");

        emailE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                validateEmail();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateEmail();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwdllE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                validatePassword();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        SharedPreferences preferences =getActivity().getSharedPreferences("checkbox",MODE_PRIVATE);
//        String checkbox = preferences.getString("remember"," ");
//        if (checkbox.equals("true")){
//            Navigation.findNavController(view).navigate(R.id.action_h_login_to_h_Admin_selection);
//
//        }else if(checkbox.equals("false")){
//            Toast.makeText(getActivity(), "Please sign in", Toast.LENGTH_SHORT).show();
//
//        }
//        chRemmbrme1M.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (compoundButton.isChecked()){
//                    SharedPreferences preferences = getActivity().getSharedPreferences("checkbox",MODE_PRIVATE);
//                    SharedPreferences.Editor editor=preferences.edit();
//                    editor.putString("remember","true");
//                    editor.apply();
//                    Toast.makeText(getActivity(), "checked", Toast.LENGTH_SHORT).show();
//
//                }else if (!compoundButton.isChecked()){
//                    SharedPreferences preferences =getActivity().getSharedPreferences("checkbox",MODE_PRIVATE);
//                    SharedPreferences.Editor editor=preferences.edit();
//                    editor.putString("remember","false");
//                    editor.apply();
//                    Toast.makeText(getActivity(), "Unchecked", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
//       if (sharedPreferences.getBoolean("strUserName",true)){
//           Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
//           createAcc.setVisibility(View.GONE);
//       }else if (sharedPreferences.getBoolean("strUserName",false)){
//           Toast.makeText(getActivity(), "not", Toast.LENGTH_SHORT).show();
//           createAcc.setVisibility(View.VISIBLE);
//       }



        String  b = getArguments().getString("user_id");
//        Bundle b = this.getArguments();


//        int s = b.getInt("user_id");
        if (b.equals("u")){
            Toast.makeText(getActivity(), "yayyaaa", Toast.LENGTH_SHORT).show();
            createAcc.setVisibility(View.GONE);
        }
        else if (b.equals("h")){
            Toast.makeText(getActivity(), "naaaaaa", Toast.LENGTH_SHORT).show();
            createAcc.setVisibility(View.VISIBLE);
        }else if (b.equals("ral")){
            Toast.makeText(getActivity(), "ral", Toast.LENGTH_SHORT).show();
            createAcc.setVisibility(View.VISIBLE);
        }else if (b.equals("ural")){
            Toast.makeText(getActivity(), "ural", Toast.LENGTH_SHORT).show();
            createAcc.setVisibility(View.VISIBLE);
        }else if (b.equals("p")){
            Toast.makeText(getActivity(), "ural", Toast.LENGTH_SHORT).show();
            createAcc.setVisibility(View.VISIBLE);
        }






        btlogon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( !validatePassword() | !validateEmail()) {
                    return;
                }else {




                    progressDialog.show();
                    final String huser = email.getEditText().getText().toString();
                    final String passData = passwdll.getEditText().getText().toString();



                    auth.signInWithEmailAndPassword(huser, passData)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {



//                                FirebaseUser user = auth.getCurrentUser();
//                                if (user.isEmailVerfied()){}

                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        boolean isEmailVerified = auth.getCurrentUser().isEmailVerified();
                                        if(!isEmailVerified){
                                            Toast.makeText(getActivity(), "Verify the e-mail", Toast.LENGTH_SHORT).show();

                                        }else{



                                            savedata(huser,passData);

                                            //navigate by retriving
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                                            String email = user.getEmail();

                                            DatabaseReference databaseReference = firebaseDatabase.getReference("Hospital");

                                            Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();

                                            Query checkemail = databaseReference.orderByChild("email").equalTo(email);
                                            String HencodeUserEmail = email.replace(".", ",");

                                            checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                    String abc = snapshot.child(HencodeUserEmail).child("email").getValue(String.class);
                                                    Toast.makeText(getActivity(), "abc" + abc, Toast.LENGTH_SHORT).show();

                                                    if (snapshot.exists()) {
                                                        Navigation.findNavController(view).navigate(R.id.action_h_login_to_h_Admin_selection);
                                                    }else{
                                                        DatabaseReference UdatabaseReference = firebaseDatabase.getReference("Users");
                                                        Query Ucheckemail = UdatabaseReference.orderByChild("email").equalTo(email);

                                                        Ucheckemail.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dsnapshot) {

                                                                String cde = dsnapshot.child(HencodeUserEmail).child("email").getValue(String.class);
                                                                Toast.makeText(getActivity(), "cde" + cde, Toast.LENGTH_SHORT).show();

                                                                if (dsnapshot.exists()) {
                                                                    Navigation.findNavController(view).navigate(R.id.ltouser1stpg);
                                                                }else{
                                                                    DatabaseReference UdatabaseReference = firebaseDatabase.getReference("Patient");
                                                                    Query Ucheckemail = UdatabaseReference.orderByChild("email").equalTo(email);

                                                                    Ucheckemail.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dsnapshot) {

                                                                            String fgh = dsnapshot.child(HencodeUserEmail).child("email").getValue(String.class);
                                                                            Toast.makeText(getActivity(), "cde" + fgh, Toast.LENGTH_SHORT).show();

                                                                            if (dsnapshot.exists()) {
                                                                                Navigation.findNavController(view).navigate(R.id.ltopatient1pg);
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



//                                            String  b = getArguments().getString("user_id");
//                                            if (b.equals("u")){
//                                                Navigation.findNavController(view).navigate(R.id.ltouser1stpg);
//
//                                            }else if (b.equals("ural")){
//                                                Navigation.findNavController(view).navigate(R.id.ltouser1stpg);
//                                            }
//                                            else if (b.equals("h")){
//                                                Navigation.findNavController(view).navigate(R.id.action_h_login_to_h_Admin_selection);
//                                            }else if (b.equals("ral")){
//                                                Toast.makeText(getActivity(), "user registration done", Toast.LENGTH_SHORT).show();
//                                                Navigation.findNavController(view).navigate(R.id.action_h_login_to_h_Admin_selection);
//
//                                            }

//                                            if (b.equals("h")){
//                                                Navigation.findNavController(view).navigate(R.id.action_h_login_to_h_Admin_selection);
//
//
//                                            }









                                        }
                                    } else {
                                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                }
            }
        });


        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (b.equals("h")){
                    Toast.makeText(getActivity(), "yayyaaa", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_h_login_to_h_registration);

                }else if (b.equals("ral")){
                    Toast.makeText(getActivity(), "ral", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_h_login_to_h_registration);

                }else if (b.equals("p")){
                    Toast.makeText(getActivity(), "pral", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_h_login_to_p_registration);

                }






            }
        });
        Imback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.select_type_option);


            }
        });



        return  view;
    }






    public void savedata(String email, String passwd ){
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("logindata", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("logincounter",true);
        editor.putString("email",email);
        editor.putString("passwd", passwd);
        editor.apply();








    }





    private Boolean validateEmail() {
        String val = email.getEditText().getText().toString();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String encodeUserEmail=  val.replace(".", ",");
        String decodeUserEmail=  val.replace(",", ".");






        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validatePassword() {
        String val = passwdll.getEditText().getText().toString();


        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            passwdll.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            passwdll.setError("Password is too weak");
            return false;
        } else {
            passwdll.setError(null);
            passwdll.setErrorEnabled(false);
            return true;
        }
    }


}