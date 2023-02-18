package com.example.helth_frag;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class amb_Reg extends Fragment {

    Button getotpm;

    EditText menternum;
    ProgressBar mprogressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.amb__reg, container, false);

        getotpm= view.findViewById(R.id.getotp);
        menternum = view.findViewById(R.id.enternumber);
        mprogressBar= view.findViewById(R.id.prgressbar);

        getotpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menternum.getText().toString().trim().isEmpty()){
                    if ((menternum.getText().toString().trim()).length()==10){

                        mprogressBar.setVisibility(View.VISIBLE);
                        getotpm.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + menternum.getText().toString(), 60, TimeUnit.SECONDS, requireActivity(),
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(s, forceResendingToken);
                                        mprogressBar.setVisibility(View.GONE);
                                        getotpm.setVisibility(View.VISIBLE);

                                        Bundle args = new Bundle();
                                        args.putString("phone_no", menternum.getText().toString());
                                        amb_Reg newFragment = new amb_Reg();
                                        newFragment.setArguments(args);
                                        Navigation.findNavController(view).navigate(R.id.amb_Reg2To_amb_OTPVerify,args);





                                    }
                                }
                        );



                    }else {
                        Toast.makeText(getActivity(), "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Enter mobile number", Toast.LENGTH_SHORT).show();
                }

            }
        });



        return view;
    }
}