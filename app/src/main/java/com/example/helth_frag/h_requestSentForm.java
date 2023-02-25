package com.example.helth_frag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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

import java.text.DateFormat;

public class h_requestSentForm extends AppCompatActivity {

    private FirebaseAuth Auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    String hid_current;

    DatabaseReference databaseReference_HD;

    Button sendRq;
    ImageView backbt;
    TextInputLayout pnameReq, descriptionReq;
    long maxid;


    TextInputEditText pnameReqE,descriptionReqE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_request_sent);

        sendRq = findViewById(R.id.sendRq);
        pnameReq = findViewById(R.id.pnameReq);
        pnameReqE = findViewById(R.id.pnameReqE);
        descriptionReq = findViewById(R.id.descriptionReq);
        descriptionReqE = findViewById(R.id.descriptionReqE);

        Auth = FirebaseAuth.getInstance();
        //databsase



        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your Account");



        String hname = getIntent().getExtras().getString("Hname");
        String address = getIntent().getExtras().getString("address");
        String phone = getIntent().getExtras().getString("phoneno");
        String hid_recieved_R = getIntent().getExtras().getString("hid");

        pnameReqE.addTextChangedListener(new TextWatcher() {
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
        descriptionReqE.addTextChangedListener(new TextWatcher() {
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


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        databaseReference_HD= FirebaseDatabase.getInstance().getReference("Hospital");

        String encodeD_Email = email.replace(".", ",");

        databaseReference_HD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        modelHD modelHD = dataSnapshot.getValue(com.example.helth_frag.modelHD.class);

                         hid_current=snapshot.child(encodeD_Email).child("h_id").getValue(String.class);
//                        Toast.makeText(getApplicationContext(), "hey"+hid_current, Toast.LENGTH_SHORT).show();



                    }
                }else {}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        sendRq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName() | !validateAddress() ) {
                    return;
                } else {

                    firebaseDatabase = firebaseDatabase.getInstance();
                    reference = firebaseDatabase.getReference("request");


                    String keyDescp = reference.push().getKey();

                    String pname = pnameReq.getEditText().getText().toString();
                    String description = descriptionReq.getEditText().getText().toString();

                    String statusact = "pending";

                    Model_hrequestfrm model_hrequestfrm = new Model_hrequestfrm(pname, description, address, hname,statusact,keyDescp,hid_recieved_R,hid_current );
                    reference.child(keyDescp).setValue(model_hrequestfrm);

                    Toast.makeText(h_requestSentForm.this, "Request Sent....Done!", Toast.LENGTH_SHORT).show();



                }
            }
        });




    }

    private Boolean validateName() {
        String val = pnameReq.getEditText().getText().toString();

        if (val.isEmpty()) {
            pnameReq.setError("Field cannot be empty");
            pnameReq.requestFocus();
            return false;
        }
        else {
            pnameReq.setError(null);
            pnameReq.setErrorEnabled(false);

            return true;
        }
    }


    private Boolean validateAddress() {
        String val = descriptionReq.getEditText().getText().toString();

        if (val.isEmpty()) {
            descriptionReq.setError("Field cannot be empty");
            return false;
        }
        else {
            descriptionReq.setError(null);
            descriptionReq.setErrorEnabled(false);
            return true;
        }
    }


}