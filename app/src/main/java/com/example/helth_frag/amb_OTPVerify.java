package com.example.helth_frag;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class amb_OTPVerify extends Fragment {
        PinView pinView;
        Button btsubmit;

    ProgressBar mprogressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.amb__o_t_p_verify, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        String number = getActivity().getIntent().getExtras().getString("phone_no");

        pinView= view.findViewById(R.id.pinView);
        btsubmit= view.findViewById(R.id.btsubmit);




                        mprogressBar.setVisibility(View.VISIBLE);
                        btsubmit.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" +number, 60, TimeUnit.SECONDS, requireActivity(),
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        String code = phoneAuthCredential.getSmsCode();
                                       pinView.setText(code);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(s, forceResendingToken);
                                        mprogressBar.setVisibility(View.GONE);
                                        btsubmit.setVisibility(View.VISIBLE);






                                    }
                                }
                        );


                        

        return view;
    }
}