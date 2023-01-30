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

public class Usr_registration extends Fragment {

    Spinner userCategoryM;
    int spinnerPosition;

    private FirebaseAuth Auth;
    FirebaseDatabase DatabaseU;
    DatabaseReference referenceU;
    ProgressDialog progressDialog;

    Button btnext2udetailsu, already_loginhu;
    ImageView backbt;
    TextInputLayout unameu, emailuu, phonenouu, passwduu;
    TextInputEditText  unameuE, emailuuE, phonenouuE, passwduuE;
    long u_id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Usr_registration() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Usr_registration newInstance(String param1, String param2) {
        Usr_registration fragment = new Usr_registration();
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
        View view =inflater.inflate(R.layout.usr_registration, container, false);

        userCategoryM = view.findViewById(R.id.userCategory);
        btnext2udetailsu = view.findViewById(R.id.btnext2udetails);
        already_loginhu = view.findViewById(R.id.already_login);

        unameu = view.findViewById(R.id.uname);
        emailuu = view.findViewById(R.id.emailu);
        phonenouu = view.findViewById(R.id.phonenou);
        passwduu = view.findViewById(R.id.passwdu);

        unameuE = view.findViewById(R.id.unameE);
        emailuuE = view.findViewById(R.id.emailuE);
        phonenouuE = view.findViewById(R.id.phonenouE);
        passwduuE = view.findViewById(R.id.passwduE);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        Auth = FirebaseAuth.getInstance();
        //databsase




        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your Account");


        unameuE.addTextChangedListener(new TextWatcher() {
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
        emailuuE.addTextChangedListener(new TextWatcher() {
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
        phonenouuE.addTextChangedListener(new TextWatcher() {
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
        passwduuE.addTextChangedListener(new TextWatcher() {
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



        //for spinner items
        String[] items = new String[]{"select user type", " Doctor", "Nurse", " Lab Assistant", "blood bank manager", "Inventory manager"};
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
//        userCategoryM.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));
        userCategoryM.setAdapter(adapter);

        userCategoryM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  Category = userCategoryM.getSelectedItem().toString();
                spinnerPosition = adapter.getPosition(Category);

                Toast.makeText(getActivity(), ""+spinnerPosition, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//spinner end

        btnext2udetailsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName()  | !validateEmail()  | !validatePhoneNo() | !validatePassword()) {
                    return;
                } else {




                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("logindata",MODE_PRIVATE);
                    Boolean counter = sharedPreferences.getBoolean("logincounter",Boolean.valueOf(String.valueOf(MODE_PRIVATE)));
                    String emailget = sharedPreferences.getString("email",String.valueOf(MODE_PRIVATE));

                    String HencodeUserEmail=  emailget.replace(".", ",");
                    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("Hospital");


                    Query checkemail= databaseReference.orderByChild("email").equalTo(emailget);
                    checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String abc = snapshot.child(HencodeUserEmail).child("email").getValue(String.class);
                            Toast.makeText(getActivity(), "abc"+abc , Toast.LENGTH_SHORT).show();

                            if(snapshot.exists()){
//                    maxid=(snapshot.getChildrenCount());
//                    String hid = String.valueOf(maxid+1);

                                String gethid = snapshot.child(HencodeUserEmail).child("h_id").getValue(String.class);
                                Toast.makeText(getActivity(), "gfhgyhg"+gethid , Toast.LENGTH_SHORT).show();



                                DatabaseU = DatabaseU.getInstance();
                                referenceU = DatabaseU.getReference("Users");


                                referenceU.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists())
                                            u_id=(snapshot.getChildrenCount());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                                String hname_u = unameu.getEditText().getText().toString();
//                    String addr_s = addrh.getEditText().getText().toString();
//                    String mngr_name_s = mngr_nameh.getEditText().getText().toString();
                                String emailm_u = emailuu.getEditText().getText().toString();
                                String phone_u = phonenouu.getEditText().getText().toString();
                                String passwrd_u = passwduu.getEditText().getText().toString();
//                    String h_id = String.valueOf(maxid + 1);
                                String usr_id = String.valueOf(u_id+1);


                                Toast.makeText(getActivity(), "" + hname_u, Toast.LENGTH_SHORT).show();

                                Auth.createUserWithEmailAndPassword
                                                (emailuu.getEditText().getText().toString(), passwduu.getEditText().getText().toString())
                                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {


                                                progressDialog.dismiss();

                                                if (task.isSuccessful()) {

                                                    modelH_usr modelur = new modelH_usr(hname_u,emailm_u,phone_u,passwrd_u,gethid,spinnerPosition,  String.valueOf(u_id+1));

//                            String id = task.getResult().getUser().getUid();
//                            firebaseDatabase.getReference().child("h").child(id).setValue(user);

//                                                    String hid = String.valueOf(maxid + 1);

                                                    String encodeUserEmailU = emailm_u.replace(".", ",");
                                                    referenceU.child(encodeUserEmailU).setValue(modelur);

                                                    sendVerificationEmail();


                                                    Toast.makeText(getActivity(), "user created sucessfully", Toast.LENGTH_SHORT).show();
//                                                    Intent intent = new Intent(getApplicationContext(), signin.class);//ithe pn
//                                                    startActivity(intent);

                                                    Bundle args = new Bundle();
                                                    args.putString("user_id", "ural");
                                                    h_registration newFragment = new h_registration ();
                                                    newFragment.setArguments(args);

                                                    Toast.makeText(getActivity(), "User Registered successfully", Toast.LENGTH_SHORT).show();
                                                    Navigation.findNavController(view).navigate(R.id.action_usr_registration_to_h_Admn_selection,args);

                                                } else {
                                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });








                            }else{
                                Toast.makeText(getActivity(), "user does not exist", Toast.LENGTH_SHORT).show();;}

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });





                }
            }
        });



        already_loginhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("user_id", "ral");
                h_registration newFragment = new h_registration ();
                newFragment.setArguments(args);
                Navigation.findNavController(view).navigate(R.id.action_usr_registration_to_h_login,args);
            }
        });




        return view;
    }


    private Boolean validateName() {
        String val = unameu.getEditText().getText().toString();

        if (val.isEmpty()) {
            unameu.setError("Field cannot be empty");
            return false;
        }
        else {
            unameu.setError(null);
            unameu.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validateEmail() {
        String val = emailuu.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String encodeUserEmail=  val.replace(".", ",");
        String decodeUserEmail=  val.replace(",", ".");




        if (val.isEmpty()) {
            emailuu.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            emailuu.setError("Invalid email address");
            return false;
        } else {
            emailuu.setError(null);
            emailuu.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = phonenouu.getEditText().getText().toString();
        String phonePattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";

        if (val.isEmpty()) {
            phonenouu.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(phonePattern)) {
            phonenouu.setError("Invalid phone number");
            return false;
        } else {
            phonenouu.setError(null);
            phonenouu.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = passwduu.getEditText().getText().toString();
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
            passwduu.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            passwduu.setError("Password is too weak");
            return false;
        } else {
            passwduu.setError(null);
            passwduu.setErrorEnabled(false);
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