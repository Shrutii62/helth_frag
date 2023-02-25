package com.example.helth_frag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
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

public class h_Amb_book_frm_Acti extends AppCompatActivity {

    TextInputEditText dateAmbE, timeAmbE, AddressAmbE;
    TextInputLayout dateAmb ,timeAmb, AddressAmb;

    MaterialDatePicker datePicker;
    String regnum;

    ProgressDialog progressD, progressDialogP;

    Button bookAmb;
    String get_Hid, getnameH;
    String dateget, timeget;

    FirebaseDatabase DatabaseU;
    DatabaseReference referenceU;


    FirebaseDatabase firebaseDatabaseA;
    DatabaseReference databaseReferenceA;

    long aptmnt_id;
    String status;

    boolean isDateSelected = false;
    boolean isTimeSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_amb_book_frm);

        String amb_number = getIntent().getExtras().getString("amb_number");
        String reg_phoned = getIntent().getExtras().getString("reg_phoned");

         regnum = "+91"+ reg_phoned;

        dateAmbE = findViewById(R.id.dateAmbE);
        timeAmbE = findViewById(R.id.timeAmbE);
        AddressAmbE = findViewById(R.id.AddressAmbE);

        dateAmb = findViewById(R.id.dateAmb);
        timeAmb = findViewById(R.id.timeAmb);
        AddressAmb = findViewById(R.id.AddressAmb);



        bookAmb = findViewById(R.id.bookAmb);

        progressDialogP = new ProgressDialog(this);
        progressDialogP.setTitle("Book Appointment");
        progressDialogP.setMessage("Appointment successfully scheduled");

        progressD = new ProgressDialog(this);
        progressD.setTitle("Error occur");
        progressD.setMessage("Fields cannot be empty");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Hemail = user.getEmail();

        FirebaseDatabase PDatabase = FirebaseDatabase.getInstance();
        DatabaseReference PReference = PDatabase.getReference("Hospital");

        String encodeP_Email = Hemail.replace(".", ",");

        Query check_Hid = PReference.orderByChild("email").equalTo(Hemail);

        check_Hid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                get_Hid = snapshot.child(encodeP_Email).child("h_id").getValue(String.class);
                getnameH = snapshot.child(encodeP_Email).child("hname").getValue(String.class);


                Toast.makeText(h_Amb_book_frm_Acti.this, "pid" + get_Hid, Toast.LENGTH_SHORT).show();
                Toast.makeText(h_Amb_book_frm_Acti.this, "did" + getnameH, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dateAmbE.addTextChangedListener(new TextWatcher() {
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
        timeAmbE.addTextChangedListener(new TextWatcher() {
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
        AddressAmbE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                validatAddressAmb();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatAddressAmb();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        CalendarConstraints.Builder cc = new CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now());


        datePicker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(cc.build())
                .setTitleText("select date").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();


        dateAmbE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getSupportFragmentManager(), "Material_date_picker");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        dateAmb.getEditText().setText(datePicker.getHeaderText());
                        dateget = datePicker.getHeaderText();
                        isDateSelected = true;

                    }
                });
            }
        });


        Calendar calendar = Calendar.getInstance();
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .build();

        timeAmbE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                materialTimePicker.show(getSupportFragmentManager(), "Material_time_picker");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int Hour = materialTimePicker.getHour();
                        int min = materialTimePicker.getMinute();
                        timeAmb.getEditText().setText(Hour + ":" + min);
                        timeget = Hour + ":" + min;
                        isTimeSelected = true;

                    }
                });

            }
        });



        bookAmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validatAddressAmb() | !validateTime() | !validateDate()) {
                    return;
                } else {

                    progressDialogP.show();



                    status = "on";


                    DatabaseU = DatabaseU.getInstance();
                    referenceU = DatabaseU.getReference("bookAmbulance");


                    String Address = AddressAmb.getEditText().getText().toString();
                    firebaseDatabaseA = FirebaseDatabase.getInstance();
                    databaseReferenceA = firebaseDatabaseA.getReference("bookAmbulance");

                    String id = String.valueOf(aptmnt_id + 1);


                    String key = databaseReferenceA.push().getKey();
                    Model_hAmb_book_frm model_hAmb_book_frm = new Model_hAmb_book_frm(dateget, timeget, Address, amb_number, regnum, key, get_Hid, status);
                    String emailEncode = Hemail.replace(".", ",");

                    databaseReferenceA.child(key).setValue(model_hAmb_book_frm);

                    Runnable progressRunnable = new Runnable() {

                        @Override
                        public void run() {
                            progressDialogP.dismiss();

                        }
                    };
                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 3000);


                }


            }


        });






    }



    private Boolean validatAddressAmb() {
        String val = AddressAmb.getEditText().getText().toString();

        if (val.isEmpty()) {
            AddressAmb.setError("Field cannot be empty");
            AddressAmb.requestFocus();
            return false;
        }
        else {
            AddressAmb.setError(null);
            AddressAmb.setErrorEnabled(false);

            return true;
        }
    }


    private Boolean validateTime() {
        String val = timeAmb.getEditText().getText().toString();
        if (val.isEmpty()) {
            timeAmb.setError("Field cannot be empty");
            Toast.makeText(this, "Select Time\n fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            timeAmb.setError(null);
            timeAmb.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateDate() {
        String val = dateAmbE.getText().toString();


        if (val.isEmpty()) {
            dateAmb.setError("Field cannot be empty");
            Toast.makeText(this, "Select Date\n fields cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            dateAmbE.setError(null);
            dateAmb.setErrorEnabled(false);
            return true;
        }
    }


}