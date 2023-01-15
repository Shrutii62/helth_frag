package com.example.helth_frag;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;


public class P_registration extends Fragment {
    private FirebaseAuth Auth;
    FirebaseDatabase DatabaseP;
    DatabaseReference referenceP;
    ProgressDialog progressDialog;
    String gethid;


    Button btnext1Detailh, already_loginh;
    ImageView backbt;
    TextInputLayout unamem, U_addm, emailmUm, phoneno_Um, passwdh_Um;
    TextInputEditText unamePE, U_addPE, emailmUPE, phoneno_UPE, passwdh_UPE;
    long maxid;

    Spinner spn;
    TextView txt, display;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.p_registration, container, false);

        btnext1Detailh = view.findViewById(R.id.btnext1Detail);
        already_loginh = view.findViewById(R.id.already_login);



        unamem = view.findViewById(R.id.uname);
        U_addm = view.findViewById(R.id.U_add);
        emailmUm = view.findViewById(R.id.emailmU);
        phoneno_Um = view.findViewById(R.id.phoneno_U);
        passwdh_Um = view.findViewById(R.id.passwdh_U);

        unamePE = view.findViewById(R.id.unamePE);
        U_addPE = view.findViewById(R.id.U_addPE);
        emailmUPE = view.findViewById(R.id.emailmUPE);
        phoneno_UPE = view.findViewById(R.id.phoneno_UPE);
        passwdh_UPE = view.findViewById(R.id.passwdh_UPE);


        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        Auth = FirebaseAuth.getInstance();

        progressDialog= new ProgressDialog(getContext());
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your Account");


        unamePE.addTextChangedListener(new TextWatcher() {
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
        U_addPE.addTextChangedListener(new TextWatcher() {
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
        emailmUPE.addTextChangedListener(new TextWatcher() {
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
        phoneno_UPE.addTextChangedListener(new TextWatcher() {
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
        passwdh_UPE.addTextChangedListener(new TextWatcher() {
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

        spn = view.findViewById(R.id.spn);
        txt = view.findViewById(R.id.txt);
        display = view.findViewById(R.id.display);


        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Hospital");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> spinnerdataList = new ArrayList<>();
                for(DataSnapshot childsnapshot:snapshot.getChildren()) {
                    String spinname = childsnapshot.child("hname").getValue(String.class);

                    spinnerdataList.add(spinname);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,spinnerdataList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn.setAdapter(arrayAdapter);

                    spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String selectItem= (String) spn.getSelectedItem();
                            Toast.makeText(getActivity(), ""+selectItem, Toast.LENGTH_SHORT).show();

                            Query checkHname = databaseReference.orderByChild("hname").equalTo(selectItem);

                            checkHname.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dsnapshot) {
                                    for (DataSnapshot childsnapshot2 : dsnapshot.getChildren()){

                                        if (dsnapshot.exists()) {
                                            String getadd = childsnapshot2.child("address").getValue(String.class);
                                            display.setText(getadd);

                                             gethid = childsnapshot2.child("h_id").getValue(String.class);




                                            DatabaseP = DatabaseP.getInstance();
                                referenceP = DatabaseP.getReference("Patient");



                                referenceP.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                                maxid=(snapshot.getChildrenCount());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });





                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }






            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










        btnext1Detailh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName() | !validateAddress() | !validateEmail() | !validatePhoneNo() | !validatePassword()) {
                    return;
                } else {

//                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("logindata",MODE_PRIVATE);
//                    Boolean counter = sharedPreferences.getBoolean("logincounter",Boolean.valueOf(String.valueOf(MODE_PRIVATE)));
//                    String emailget = sharedPreferences.getString("email",String.valueOf(MODE_PRIVATE));
//
//                    String HencodeUserEmail=  emailget.replace(".", ",");
//                    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
//                    DatabaseReference databaseReference = firebaseDatabase.getReference("Hospital");


//                    Query checkemail= databaseReference.orderByChild("email").equalTo(emailget);
//                    checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                            String bcd = snapshot.child(HencodeUserEmail).child("email").getValue(String.class);
//                            Toast.makeText(getActivity(), "bcd"+bcd , Toast.LENGTH_SHORT).show();
//
//                            if(snapshot.exists()){
////                    maxid=(snapshot.getChildrenCount());
////                    String hid = String.valueOf(maxid+1);
//
//                                String gethid = snapshot.child(HencodeUserEmail).child("h_id").getValue(String.class);
//                                Toast.makeText(getActivity(), "gfhgyhg"+gethid , Toast.LENGTH_SHORT).show();
//
//
//
//                                DatabaseP = DatabaseP.getInstance();
//                                referenceP = DatabaseP.getReference("Patient");
//
//
//
//                                referenceP.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.exists())
//                                maxid=(snapshot.getChildrenCount());
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//
//                    String uname_s = unamem.getEditText().getText().toString();
//                    String Uaddr = U_addm.getEditText().getText().toString();
//                    String Uemailm_s = emailmUm.getEditText().getText().toString();
//                    String Uphone_s = phoneno_Um.getEditText().getText().toString();
//                    String Upasswrd_s = passwdh_Um.getEditText().getText().toString();
//                    String P_id = String.valueOf(maxid+1);
//
//
//                    Toast.makeText(getActivity(), "" + uname_s, Toast.LENGTH_SHORT).show();
//
//                    Auth.createUserWithEmailAndPassword
//                                    (emailmUm.getEditText().getText().toString(), passwdh_Um.getEditText().getText().toString())
//                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//
//
//                                    progressDialog.dismiss();
//
//                                    if (task.isSuccessful()) {
//
////                                        modelHD modelHD = new modelHD(uname_s, Uaddr, Uemailm_s, Uphone_s, Upasswrd_s, String.valueOf(maxid+1));
//                                            modelH_p model_p = new modelH_p(uname_s,Uaddr,Uemailm_s,Uphone_s,Upasswrd_s,gethid,String.valueOf(maxid+1));
//
////                            String id = task.getResult().getUser().getUid();
////                            firebaseDatabase.getReference().child("h").child(id).setValue(user);
//
//                                        String hid = String.valueOf(maxid+1);
//
//                                        String encodeUserEmail=  Uemailm_s.replace(".", ",");
//                                        referenceP.child(encodeUserEmail).setValue(model_p);
//
//                                        sendVerificationEmail();
//
//
//
//
//                                        Toast.makeText(getContext(), "user created sucessfully", Toast.LENGTH_SHORT).show();
////                                        Intent intent = new Intent(getContext(), h_login.class);//ithe pn
////                                        startActivity(intent);
//
//                                        Bundle args = new Bundle();
//                                        args.putString("user_id", "PtoL");
//                                        P_registration newFragment = new P_registration ();
//                                        newFragment.setArguments(args);
//
//                                        Navigation.findNavController(view).navigate(R.id.PregToLogin,args);
//
//
//
//
//                                    } else {
//                                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                            });
//                            }else{
//                                Toast.makeText(getActivity(), "user does not exist", Toast.LENGTH_SHORT).show();}
//
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });





                    String uname_s = unamem.getEditText().getText().toString();
                    String Uaddr = U_addm.getEditText().getText().toString();
                    String Uemailm_s = emailmUm.getEditText().getText().toString();
                    String Uphone_s = phoneno_Um.getEditText().getText().toString();
                    String Upasswrd_s = passwdh_Um.getEditText().getText().toString();
                    String P_id = String.valueOf(maxid+1);


                    Auth.createUserWithEmailAndPassword
                                    (emailmUm.getEditText().getText().toString(), passwdh_Um.getEditText().getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    progressDialog.dismiss();

                                    if (task.isSuccessful()) {

//                                        modelHD modelHD = new modelHD(uname_s, Uaddr, Uemailm_s, Uphone_s, Upasswrd_s, String.valueOf(maxid+1));
                                        modelH_p model_p = new modelH_p(uname_s,Uaddr,Uemailm_s,Uphone_s,Upasswrd_s,gethid,String.valueOf(maxid+1));

//                            String id = task.getResult().getUser().getUid();
//                            firebaseDatabase.getReference().child("h").child(id).setValue(user);

                                        String hid = String.valueOf(maxid+1);

                                        String encodeUserEmail=  Uemailm_s.replace(".", ",");
                                        referenceP.child(encodeUserEmail).setValue(model_p);

                                        sendVerificationEmail();




                                        Toast.makeText(getContext(), "user created sucessfully", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getContext(), h_login.class);//ithe pn
//                                        startActivity(intent);

//                                        Bundle args = new Bundle();
//                                        args.putString("user_id", "PtoL");
//                                        P_registration newFragment = new P_registration ();
//                                        newFragment.setArguments(args);

                                        Bundle args = new Bundle();
                                        args.putString("user_id", hid);
                                        h_registration newFragment = new h_registration ();
                                        newFragment.setArguments(args);

                                        Navigation.findNavController(view).navigate(R.id.PregToLogin,args);




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


                Navigation.findNavController(view).navigate(R.id.PregToLogin,args);
            }
        });


        return view;
    }

    private Boolean validateName() {
        String val = unamem.getEditText().getText().toString();

        if (val.isEmpty()) {
            unamem.setError("Field cannot be empty");
            return false;
        }
        else {
            unamem.setError(null);
            unamem.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validateAddress() {
        String val = U_addm.getEditText().getText().toString();

        if (val.isEmpty()) {
            U_addm.setError("Field cannot be empty");
            return false;
        }
        else {
            U_addm.setError(null);
            U_addm.setErrorEnabled(false);
            return true;
        }
    }

//    private Boolean validateManagerName() {
//        String val = mngr_nameh.getEditText().getText().toString();
//        String noWhiteSpace = "\\A\\w{4,20}\\z";
//
//        if (val.isEmpty()) {
//            mngr_nameh.setError("Field cannot be empty");
//            return false;
//        } else if (val.length() >= 15) {
//            mngr_nameh.setError("Username too long");
//            return false;
//        } else if (!val.matches(noWhiteSpace)) {
//            mngr_nameh.setError("White Spaces are not allowed");
//            return false;
//        } else {
//            mngr_nameh.setError(null);
//            mngr_nameh.setErrorEnabled(false);
//            return true;
//        }
//    }

    private Boolean validateEmail() {
        String val = emailmUm.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String encodeUserEmail=  val.replace(".", ",");
        String decodeUserEmail=  val.replace(",", ".");




        if (val.isEmpty()) {
            emailmUm.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            emailmUm.setError("Invalid email address");
            return false;
        } else {
            emailmUm.setError(null);
            emailmUm.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = phoneno_Um.getEditText().getText().toString();
        String phonePattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";

        if (val.isEmpty()) {
            phoneno_Um.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(phonePattern)) {
            phoneno_Um.setError("Invalid phone number");
            return false;
        } else {
            phoneno_Um.setError(null);
            phoneno_Um.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = passwdh_Um.getEditText().getText().toString();
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
            passwdh_Um.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            passwdh_Um.setError("Password is too weak");
            return false;
        } else {
            passwdh_Um.setError(null);
            passwdh_Um.setErrorEnabled(false);
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