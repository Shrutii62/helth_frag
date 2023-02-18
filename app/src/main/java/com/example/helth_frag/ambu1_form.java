package com.example.helth_frag;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;


public class ambu1_form extends Fragment {

    TextInputLayout dname, amb_number, alt_moblnum;
    TextInputEditText dnameE, amb_numberE, alt_moblnumE;
    TextView displaynum;
    Button submit;
    String numphone;

    FirebaseDatabase firebaseDatabaseAmb;
    DatabaseReference databaseReferenceAmb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ambu1_form, container, false);

        dname= view.findViewById(R.id.dname);
        dnameE= view.findViewById(R.id.dnameE);
        amb_number= view.findViewById(R.id.amb_number);
        amb_numberE= view.findViewById(R.id.amb_numberE);
        alt_moblnum= view.findViewById(R.id.alt_moblnum);
        alt_moblnumE= view.findViewById(R.id.alt_moblnumE);
        displaynum= view.findViewById(R.id.displaynum);
        submit= view.findViewById(R.id.submit);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

         firebaseDatabaseAmb= FirebaseDatabase.getInstance();
         databaseReferenceAmb = firebaseDatabaseAmb.getReference("amb_DrvDetails");



//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            numphone = bundle.getString("pnumber");
//        }

        String numphone = getActivity().getIntent().getExtras().getString("pnumber");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validatePhoneNo() | !validateambnumber() | !validateDname()) {
                    return;
                } else {

                    String drvename = dname.getEditText().getText().toString();
                    String ambunumber = amb_number.getEditText().getText().toString();
                    String alterntphone = alt_moblnum.getEditText().getText().toString();
                    String keyAmb = databaseReferenceAmb.push().getKey();

                    Model_ambDriverdetail model_ambDriverdetail = new Model_ambDriverdetail(drvename, ambunumber, alterntphone, numphone);

                    databaseReferenceAmb.child(keyAmb).setValue(model_ambDriverdetail);
                    Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();


                }


            }
        });



        return view;
    }


    private Boolean validatePhoneNo() {
        String val = alt_moblnum.getEditText().getText().toString();
        String phonePattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";

        if (val.isEmpty()) {
            alt_moblnum.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(phonePattern)) {
            alt_moblnum.setError("Invalid phone number");
            return false;
        } else {
            alt_moblnum.setError(null);
            alt_moblnum.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateambnumber() {
        String val = amb_number.getEditText().getText().toString();

        if (val.isEmpty()) {
            amb_number.setError("Field cannot be empty");
            return false;
        }
        else {
            amb_number.setError(null);
            amb_number.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateDname() {
        String val = dname.getEditText().getText().toString();

        if (val.isEmpty()) {
            dname.setError("Field cannot be empty");
            return false;
        }
        else {
            dname.setError(null);
            dname.setErrorEnabled(false);
            return true;
        }
    }
}