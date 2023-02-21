package com.example.helth_frag;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class amb_OTPVerify extends Fragment {

    TextView getmobile, resendotp;
    String backendOTp;
    EditText otp1, otp2, otp3, otp4, otp5,otp6;
    Button btsubmit;
//    ProgressBar prrgressbarV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.amb__o_t_p_verify, container, false);

        getmobile = view.findViewById(R.id.getmobile);
        btsubmit = view.findViewById(R.id.btsubmit);
        resendotp = view.findViewById(R.id.resendotp);
        otp1 = view.findViewById(R.id.otp1);
        otp2 = view.findViewById(R.id.otp2);
        otp3 = view.findViewById(R.id.otp3);
        otp4 = view.findViewById(R.id.otp4);
        otp5 = view.findViewById(R.id.otp5);
        otp6 = view.findViewById(R.id.otp6);






//        getmobile.setText(String.format("+91-%s",
//                getActivity().getIntent().getStringExtra("pnumber")));
//
//        backendOTp = getActivity().getIntent().getStringExtra("s");
//

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String number = bundle.getString("pnumber");
             backendOTp = bundle.getString("s");
        }


       // Toast.makeText(requireActivity(), backendOTp, Toast.LENGTH_SHORT).show();

       btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!otp1.getText().toString().trim().isEmpty() && !otp2.getText().toString().trim().isEmpty() &&
                        !otp3.getText().toString().trim().isEmpty() && !otp4.getText().toString().trim().isEmpty() &&
                        !otp5.getText().toString().trim().isEmpty() && !otp6.getText().toString().trim().isEmpty())
                {
                    String  entercodeOtp =
                            otp1.getText().toString() +
                                   otp2.getText().toString() +
                                    otp3.getText().toString() +
                                    otp4.getText().toString() +
                                    otp5.getText().toString() +
                                    otp6.getText().toString() ;

                    if (backendOTp!=null){
//                        prrgressbarV.setVisibility(View.VISIBLE);
//                        btsubmit.setVisibility(View.INVISIBLE);

                        Toast.makeText(getContext(), entercodeOtp, Toast.LENGTH_SHORT).show();
                        PhoneAuthCredential phoneAuthCredential =PhoneAuthProvider.getCredential(backendOTp ,entercodeOtp);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

//                                        prrgressbarV.setVisibility(
//
//                                       View.INVISIBLE);
                                        btsubmit.setVisibility(View.VISIBLE);

                                        if (task.isSuccessful()){
                                            Navigation.findNavController(view).navigate(R.id.amb_OTPVerifyTo_ambu1_form);

                                        }else {
                                           Toast.makeText(getContext(), "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }else {

                        Toast.makeText(getContext(), "Please check Internet connection", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getContext(), "verifying OTP", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Please Enter All Numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });//onc end

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getActivity().getIntent().getStringExtra("mobile"),
                        60, TimeUnit.SECONDS, getActivity(),
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String news, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                backendOTp = news;
                                Toast.makeText(getContext(), "Otp sent succesfully", Toast.LENGTH_SHORT).show();




                            }
                        }
                );
            }
        });

        numotpmove();








        return view;
    }

    //for auto movment of  otp no box
    private void numotpmove() {
        otp1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                   otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
       otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()){
                   otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
       otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()){
                  otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
       otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()){
                   otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()){
                    otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}