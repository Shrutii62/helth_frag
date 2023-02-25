package com.example.helth_frag;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class h_registration extends Fragment {




    private FirebaseAuth Auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    Button btnext1Detailh, already_loginh;
    ImageView backbt;
    TextInputLayout hnameh, addrh, mngr_nameh, emailmh, phoneno, passwdhm;
    long maxid;

//    EditText hnameE;
    TextInputEditText hnameE,phonenoE,addE,mngr_nameE, emailmE,passwdhE;





    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public h_registration() {
        // Required empty public constructor
    }
    public static h_registration newInstance(String param1, String param2) {
        h_registration fragment = new h_registration();
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
        View view =inflater.inflate(R.layout.h_registration, container, false);


        btnext1Detailh = view.findViewById(R.id.btnext1Detail);
        already_loginh = view.findViewById(R.id.already_login);



        hnameh = view.findViewById(R.id.hname);
        addrh = view.findViewById(R.id.hadd);
        mngr_nameh = view.findViewById(R.id.mngr_name);
        emailmh = view.findViewById(R.id.emailm);
        phoneno = view.findViewById(R.id.phoneno);
        passwdhm = view.findViewById(R.id.passwdh);

        hnameE = view.findViewById(R.id.hnameE);
        phonenoE = view.findViewById(R.id.phonenoE);
        addE = view.findViewById(R.id.addE);
        mngr_nameE = view.findViewById(R.id.mngr_nameE);
        emailmE = view.findViewById(R.id.emailmE);
        passwdhE = view.findViewById(R.id.passwdhE);



//        getSupportActionBar().hide();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        Auth = FirebaseAuth.getInstance();
        //databsase


        progressDialog= new ProgressDialog(getContext());
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your Account");



//        hnameE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//
//                    validateName();
//
//            }
//        });
        hnameE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               validateName();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateName();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        addE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               validateAddress();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateAddress();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        emailmE.addTextChangedListener(new TextWatcher() {
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
        mngr_nameE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                validateManagerName();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateManagerName();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        phonenoE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              validatePhoneNo();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePhoneNo();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwdhE.addTextChangedListener(new TextWatcher() {
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


        btnext1Detailh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName() | !validateAddress() | !validateEmail() | !validateManagerName() | !validatePhoneNo() | !validatePassword()) {
                    return;
                } else {

                    firebaseDatabase = firebaseDatabase.getInstance();
                    reference = firebaseDatabase.getReference("Hospital");


                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                                maxid=(snapshot.getChildrenCount());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    String hname_s = hnameh.getEditText().getText().toString();
                    String addr_s = addrh.getEditText().getText().toString();
                    String mngr_name_s = mngr_nameh.getEditText().getText().toString();
                    String emailm_s = emailmh.getEditText().getText().toString();
                    String phone_s = phoneno.getEditText().getText().toString();
                    String passwrd_s = passwdhm.getEditText().getText().toString();
                    String h_id = String.valueOf(maxid+1);


//                    Toast.makeText(getActivity(), "" + hname_s, Toast.LENGTH_SHORT).show();

                    Auth.createUserWithEmailAndPassword
                                    (emailmh.getEditText().getText().toString(), passwdhm.getEditText().getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    progressDialog.dismiss();

                                    if (task.isSuccessful()) {

                                        modelHD modelHD = new modelHD(hname_s, addr_s, mngr_name_s, emailm_s, phone_s, passwrd_s, String.valueOf(maxid+1));


//                            String id = task.getResult().getUser().getUid();
//                            firebaseDatabase.getReference().child("h").child(id).setValue(user);

                                        String hid = String.valueOf(maxid+1);

                                        String encodeUserEmail=  emailm_s.replace(".", ",");
                                        reference.child(encodeUserEmail).setValue(modelHD);

                                        sendVerificationEmail();




                                        Toast.makeText(getContext(), "user created sucessfully", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getContext(), h_login.class);//ithe pn
//                                        startActivity(intent);

                                        Bundle args = new Bundle();
                                        args.putString("user_id", "ral");
                                        h_registration newFragment = new h_registration ();
                                        newFragment.setArguments(args);

                                        Navigation.findNavController(view).navigate(R.id.regToLogin,args);




                                    } else {
                                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
            }
        });



        already_loginh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("user_id", "ral");
                h_registration newFragment = new h_registration ();
                newFragment.setArguments(args);


                Navigation.findNavController(view).navigate(R.id.regToLogin,args);
            }
        });
        return view;

    }





    private Boolean validateName() {
        String val = hnameh.getEditText().getText().toString();

        if (val.isEmpty()) {
            hnameh.setError("Field cannot be empty");
            hnameh.requestFocus();
            return false;
        }
        else {
            hnameh.setError(null);
            hnameh.setErrorEnabled(false);

            return true;
        }
    }


    private Boolean validateAddress() {
        String val = addrh.getEditText().getText().toString();

        if (val.isEmpty()) {
            addrh.setError("Field cannot be empty");
            return false;
        }
        else {
            addrh.setError(null);
            addrh.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateManagerName() {
        String val = mngr_nameh.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            mngr_nameh.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            mngr_nameh.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            mngr_nameh.setError("White Spaces are not allowed");
            return false;
        } else {
            mngr_nameh.setError(null);
            mngr_nameh.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = emailmh.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String encodeUserEmail=  val.replace(".", ",");
        String decodeUserEmail=  val.replace(",", ".");




        if (val.isEmpty()) {
            emailmh.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            emailmh.setError("Invalid email address");
            return false;
        } else {
            emailmh.setError(null);
            emailmh.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = phoneno.getEditText().getText().toString();
        String phonePattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";

        if (val.isEmpty()) {
            phoneno.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(phonePattern)) {
            phoneno.setError("Invalid phone number");
            return false;
        } else {
            phoneno.setError(null);
            phoneno.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = passwdhm.getEditText().getText().toString();
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
            passwdhm.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            passwdhm.setError("Password is too weak");
            return false;
        } else {
            passwdhm.setError(null);
            passwdhm.setErrorEnabled(false);
            return true;
        }
    }



    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent


                            // after email is sent just logout the user and finish this activity
//                            FirebaseAuth.getInstance().signOut();
//                            startActivity(new Intent(getActivity(), h_login.class));
//                            Navigation.findNavController(requireView()).navigate(R.id.action_h_registration_to_h_login);




                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
//                            getActivity().overridePendingTransition(0, 0);
//                            getActivity().finish();
//                            getActivity().overridePendingTransition(0, 0);
//                            startActivity(getActivity().getIntent());

                        }
                    }
                });
    }






}