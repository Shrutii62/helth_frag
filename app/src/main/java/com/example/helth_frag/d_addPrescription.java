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

    TextInputEditText P_eamilE, issueDescriptionE, testnameE;
    TextInputLayout P_eamil ,issueDescription, testname;
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

        P_eamil = v.findViewById(R.id.P_eamil);
        issueDescription = v.findViewById(R.id.issueDescription);
        testname = v.findViewById(R.id.testname);

        update_details = v.findViewById(R.id.update_details);


        update_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( !validatemail() | !validateissueDescrp() | !validateTest()) {
                    return;
                }else {

//                    progressDialogP.show();







//                     firebaseDatabase= FirebaseDatabase.getInstance();
//                    DatabaseReference databaseReference = firebaseDatabase.getReference("Users");


//                    id generate

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



//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    String demail = user.getEmail();



//                    FirebaseDatabase PDatabase= FirebaseDatabase.getInstance();
//                    DatabaseReference PReference = PDatabase.getReference("Patient");

//                    long idapt=(aptmnt_id +1) ;
//                    String aptmtID = String.valueOf(idapt);

//                    Toast.makeText(getActivity(), "aptmt"+aptmtID, Toast.LENGTH_SHORT).show();
//                    String encodeP_Email = Pemail.replace(".", ",");
//
//                    Query check_Pid = PReference.orderByChild("email").equalTo(Pemail);
//                    check_Pid.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
//                            if (snapshot2.exists()){
//
//                                get_Pid = snapshot2.child(encodeP_Email).child("p_id").getValue(String.class);
//                                getnameP = snapshot2.child(encodeP_Email).child("pname").getValue(String.class);
//
//                            }else{
//                                Toast.makeText(getActivity(), "user does't exist", Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });

                    String emal = P_eamil.getEditText().getText().toString();
                    String tstname = testname.getEditText().getText().toString();
                    String issueDescripton = issueDescription.getEditText().getText().toString();
                    String pid = getActivity().getIntent().getExtras().getString("pid");
                    String did = getActivity().getIntent().getExtras().getString("id");
                    String aptid = getActivity().getIntent().getExtras().getString("aptid");
//                    Toast.makeText(getActivity(), "did"+did, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "pd"+pid, Toast.LENGTH_SHORT).show();


                    //update in appointment
                    FirebaseDatabase firebaseA= FirebaseDatabase.getInstance();
                    DatabaseReference databaseReferenceA = firebaseA.getReference("appointment");



//                    databaseReferenceA.child("myDb").child("awais@gmailcom").child("leftSpace").setValue("YourDateHere");
//                    databaseReferenceA.child("").child(aptid).setValue("off");
                    databaseReferenceA.child(aptid).child("status").setValue("off");



                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String Demail = user.getEmail();
                    String encodeD_Email = Demail.replace(".", ",");

                    FirebaseDatabase firebaseU= FirebaseDatabase.getInstance();
                    DatabaseReference databaseReferenceU = firebaseU.getReference("Users");

                    Query hhereId= databaseReferenceU.orderByChild("u_id").equalTo(did);

                    hhereId.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshoth) {

                            if (snapshoth.exists()){
                                get_hid = snapshoth.child(encodeD_Email).child("h_id").getValue(String.class);

                            }else{
                                Toast.makeText(getActivity(), "snapshot does not exist", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });





                     firebaseDatabaseDdscrptn= FirebaseDatabase.getInstance();
                     databaseReferenceDdscrptn = firebaseDatabaseDdscrptn.getReference("Dp_description");

                    String id = String.valueOf(descrip_id+1);
                    String keyDescp = databaseReferenceA.push().getKey();

                        databaseReferenceDdscrptn.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.exists()){

                                    model_d_addPrescriptn model_d_addPrescriptn = new model_d_addPrescriptn(pid,did,issueDescripton,tstname,get_hid,keyDescp);


                                    databaseReferenceDdscrptn.child(keyDescp).setValue(model_d_addPrescriptn);
                                    Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();

//                                    Runnable progressRunnable = new Runnable() {
//
//                                        @Override
//                                        public void run() {
//                                            progressDialogP.dismiss();
//                                        }
//                                    };
//                                    Handler pdCanceller = new Handler();
//                                    pdCanceller.postDelayed(progressRunnable, 3000);

//                                 progressDialogP.dismiss();
                                }else {
                                    Toast.makeText(getActivity(), "datbase not exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }






//                    Query checkdoc = databaseReference.orderByChild("uname").equalTo(Item);
//                        checkdoc.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
//                                if (snapshot1.exists()){
//
//
//
//
//
//                                    Toast.makeText(getActivity(), "exisssssssssst", Toast.LENGTH_SHORT).show();
//                                }else {
//                                    Toast.makeText(getActivity(), "not exist", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });

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