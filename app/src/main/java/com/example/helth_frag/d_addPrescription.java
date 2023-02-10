package com.example.helth_frag;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class d_addPrescription extends Fragment {

    TextInputEditText P_eamilE, issueDescriptionE, testnameE,amountE;
    TextInputLayout P_eamil ,issueDescription, testname, amount;
    Button update_details;

    ProgressDialog progressdp;

    FirebaseDatabase firebaseDatabaseDdscrptn;
    DatabaseReference databaseReferenceDdscrptn;

    long descrip_id;

    String get_hid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.d_add_prescription, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        P_eamilE = v.findViewById(R.id.P_eamilE);
        issueDescriptionE = v.findViewById(R.id.issueDescriptionE);
        testnameE = v.findViewById(R.id.testnameE);
        amountE = v.findViewById(R.id.amountE);

        P_eamil = v.findViewById(R.id.P_eamil);
        issueDescription = v.findViewById(R.id.issueDescription);
        testname = v.findViewById(R.id.testname);
        amount = v.findViewById(R.id.amount);

        update_details = v.findViewById(R.id.update_details);

        String pid = getActivity().getIntent().getExtras().getString("pid");
        String did = getActivity().getIntent().getExtras().getString("id");
        String aptid = getActivity().getIntent().getExtras().getString("aptid");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Demail = user.getEmail();
        String encodeD_Email = Demail.replace(".", ",");

        FirebaseDatabase firebaseU= FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceU = firebaseU.getReference("Users");

        Query hhereId= databaseReferenceU.orderByChild("u_id").equalTo(did);

        hhereId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshoth) {

                if (snapshoth.exists()){
                    get_hid = snapshoth.child(encodeD_Email).child("h_id").getValue(String.class);

                    Toast.makeText(getActivity(), "pid"+pid, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "did"+did, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "hid"+get_hid, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "snapshot does not exist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( !validatemail() | !validateissueDescrp() | !validateTest()) {
                    return;
                }else {


                    firebaseDatabaseDdscrptn= FirebaseDatabase.getInstance();
                    databaseReferenceDdscrptn = firebaseDatabaseDdscrptn.getReference("Dp_description");

                    databaseReferenceDdscrptn.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                                descrip_id=(snapshot.getChildrenCount());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    String emal = P_eamil.getEditText().getText().toString();
                    String tstname = testname.getEditText().getText().toString();
                    String Aamount = amount.getEditText().getText().toString();
                    String issueDescripton = issueDescription.getEditText().getText().toString();



                    //update in appointment
                    FirebaseDatabase firebaseA= FirebaseDatabase.getInstance();
                    DatabaseReference databaseReferenceA = firebaseA.getReference("appointment");

                    databaseReferenceA.child(aptid).child("status").setValue("off");

                     firebaseDatabaseDdscrptn= FirebaseDatabase.getInstance();
                     databaseReferenceDdscrptn = firebaseDatabaseDdscrptn.getReference("Dp_description");

                    String keyDescp = databaseReferenceA.push().getKey();




                    Toast.makeText(getActivity(), "did"+did, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "pid"+pid, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "hid"+get_hid, Toast.LENGTH_SHORT).show();

                    model_d_addPrescriptn model_d_addPrescriptn = new model_d_addPrescriptn(pid,did,issueDescripton,tstname,get_hid,keyDescp,Aamount);

                    databaseReferenceDdscrptn.child(keyDescp).setValue(model_d_addPrescriptn);

                    }


                }



        });





        return v;
    }



    private Boolean validatemail() {
        String val = P_eamil.getEditText().getText().toString();

        if (val.isEmpty()) {
            P_eamil.setError("Field cannot be empty");
            P_eamil.requestFocus();
            return false;
        }
        else {
            P_eamil.setError(null);
            P_eamil.setErrorEnabled(false);

            return true;
        }
    }


    private Boolean validateissueDescrp() {
        String val = issueDescription.getEditText().getText().toString();
        if (val.isEmpty()) {
            issueDescription.setError("Field cannot be empty");
            Toast.makeText(getContext(), "Select Time\n fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            issueDescription.setError(null);
            issueDescription.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateTest() {
        String val = testname.getEditText().getText().toString();


        if (val.isEmpty()) {
            testname.setError("Field cannot be empty");
            Toast.makeText(getContext(), "Select Date\n fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            testname.setError(null);
            testname.setErrorEnabled(false);
            return true;
        }
    }


}