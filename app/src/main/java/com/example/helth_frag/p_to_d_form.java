package com.example.helth_frag;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class p_to_d_form extends Fragment {
    TextInputEditText dateE, timeE, issueE;
    TextInputLayout date ,time, issue;

    MaterialDatePicker datePicker;

    ProgressDialog  progressD, progressDialogP;

    Button getApptmt;
    String get_Pid, getnameP;
    String dateget, timeget;

    FirebaseDatabase DatabaseU;
    DatabaseReference referenceU;


    FirebaseDatabase firebaseDatabaseA;
    DatabaseReference databaseReferenceA;

    long aptmnt_id;

    boolean isDateSelected = false;
    boolean isTimeSelected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.p_to__d_form, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

//        button = v.findViewById(R.id.button);


        dateE = v.findViewById(R.id.dateE);
        timeE = v.findViewById(R.id.timeE);
        issueE = v.findViewById(R.id.issueE);

        date = v.findViewById(R.id.date);
        time = v.findViewById(R.id.time);
        issue = v.findViewById(R.id.issue);

        getApptmt = v.findViewById(R.id.getApptmt);

        progressDialogP = new ProgressDialog(getActivity());
        progressDialogP.setTitle("Book Appointment");
        progressDialogP.setMessage("Appointment successfully scheduled");

        progressD = new ProgressDialog(getActivity());
        progressD.setTitle("Error occur");
        progressD.setMessage("Fields cannot be empty");



        dateE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                validateDate();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateDate();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        timeE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               validateTime();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateTime();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        issueE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                validatIssue();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatIssue();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(p_to_d_form.this).navigate(R.id.p_to_D_form_to_user1stpg2);
//
//            }
//        });


         datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("select date").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        dateE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getParentFragmentManager(),"Material_date_picker");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        date.getEditText().setText(datePicker.getHeaderText());
                         dateget = datePicker.getHeaderText();
                        isDateSelected= true;

                    }
                });
            }
        });



        Calendar calendar= Calendar.getInstance();
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                        .setMinute(calendar.get(Calendar.MINUTE))
                                .build();

        timeE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            materialTimePicker.show(getParentFragmentManager(), "Material_time_picker");
            materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Hour = materialTimePicker.getHour();
                    int min = materialTimePicker.getMinute();
                    time.getEditText().setText(Hour+":"+min);
                     timeget = Hour+":"+min;
                    isTimeSelected= true;

                }
            });

            }
        });




        getApptmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( !validatIssue() | !validateTime() | !validateDate()) {
                    return;
                }else {

                    progressDialogP.show();


                    String did = getActivity().getIntent().getExtras().getString("id");
                    String demail = getActivity().getIntent().getExtras().getString("name");
                    Toast.makeText(getActivity(), "did"+did, Toast.LENGTH_SHORT).show();




//                     firebaseDatabase= FirebaseDatabase.getInstance();
//                    DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

                    DatabaseU = DatabaseU.getInstance();
                    referenceU = DatabaseU.getReference("appointment");


                    referenceU.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                                aptmnt_id=(snapshot.getChildrenCount());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String Pemail = user.getEmail();



                    FirebaseDatabase PDatabase= FirebaseDatabase.getInstance();
                    DatabaseReference PReference = PDatabase.getReference("Patient");

//                    long idapt=(aptmnt_id +1) ;
//                    String aptmtID = String.valueOf(idapt);

//                    Toast.makeText(getActivity(), "aptmt"+aptmtID, Toast.LENGTH_SHORT).show();
                    String encodeP_Email = Pemail.replace(".", ",");

                    Query check_Pid = PReference.orderByChild("email").equalTo(Pemail);
                    check_Pid.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            if (snapshot2.exists()){

                                 get_Pid = snapshot2.child(encodeP_Email).child("p_id").getValue(String.class);
                                 getnameP = snapshot2.child(encodeP_Email).child("pname").getValue(String.class);

                            }else{
                                Toast.makeText(getActivity(), "user does't exist", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    String issueM = issue.getEditText().getText().toString();
                     firebaseDatabaseA= FirebaseDatabase.getInstance();
                     databaseReferenceA = firebaseDatabaseA.getReference("appointment");

                     String id = String.valueOf(aptmnt_id+1);
                    if(isDateSelected && isTimeSelected){
                        databaseReferenceA.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.exists()){

                                    model_appointment mdl_apt = new model_appointment(dateget,timeget,issueM,get_Pid,dateget, id, demail,getnameP);
                                    String emailEncode = demail.replace(".", ",");
                                    databaseReferenceA.child(id).setValue(mdl_apt);
                                    Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();

                                    Runnable progressRunnable = new Runnable() {

                                        @Override
                                        public void run() {
                                            progressDialogP.dismiss();
                                        }
                                    };
                                    Handler pdCanceller = new Handler();
                                    pdCanceller.postDelayed(progressRunnable, 3000);

//                                 progressDialogP.dismiss();
                                }else {
                                    Toast.makeText(getActivity(), "datbase not exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        progressDialogP.dismiss();
                        progressD.show();
                        Toast.makeText(getActivity(), "Select date", Toast.LENGTH_SHORT).show();
                        validateDate();
                        validateTime();
                        Runnable progressRunnable = new Runnable() {

                            @Override
                            public void run() {
                                progressD.dismiss();
                            }
                        };
                        Handler pdCanceller = new Handler();
                        pdCanceller.postDelayed(progressRunnable, 2000);
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


            }
        });




        return v;
    }


    private Boolean validatIssue() {
        String val = issue.getEditText().getText().toString();

        if (val.isEmpty()) {
            issue.setError("Field cannot be empty");
            issue.requestFocus();
            return false;
        }
        else {
            issue.setError(null);
            issue.setErrorEnabled(false);

            return true;
        }
    }


    private Boolean validateTime() {
        String val = time.getEditText().getText().toString();
        if (val.isEmpty()) {
            time.setError("Field cannot be empty");
            Toast.makeText(getContext(), "Select Time\n fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            time.setError(null);
            time.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateDate() {
        String val = dateE.getText().toString();


        if (val.isEmpty()) {
            date.setError("Field cannot be empty");
            Toast.makeText(getContext(), "Select Date\n fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            dateE.setError(null);
            date.setErrorEnabled(false);
            return true;
        }
    }



}