package com.example.helth_frag;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
    FirebaseAuth auth;
    String verificationId= "";
    String phone = "";

    PinView pinView;

    Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();

        View view = inflater.inflate(R.layout.amb__o_t_p_verify, container, false);


        submitButton = view.findViewById(R.id.btsubmit);


        pinView = view.findViewById(R.id.pinView);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            phone = bundle.getString("phone_no");
        }




        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91" + phone)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(getActivity())                 // Activity (for callback binding)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential p0) {
                        String code = p0.getSmsCode();
                        pinView.setText(code);


                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException p0) {
                        Toast.makeText(getActivity(), p0.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String p0, @NonNull PhoneAuthProvider.ForceResendingToken p1) {
                        super.onCodeSent(p0, p1);
                        verificationId = p0;
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);







        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (pinView.getText() == null || pinView.getText().length() != 6) {
                    Toast.makeText(getActivity(), "Please Enter 6 digit ", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, pinView.getText().toString());
                    auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = task.getResult().getUser();
                                Toast.makeText(getActivity(), "haa", Toast.LENGTH_SHORT).show();


                                Bundle args = new Bundle();
                                args.putString("pnumber", phone);
                                amb_Reg newFragment = new amb_Reg();
                                newFragment.setArguments(args);
                                Navigation.findNavController(view).navigate(R.id.amb_OTPVerifyTo_ambu1_form,args);


                            } else {
                                // Sign in failed, display a message and update the UI
                                Log.w(ContentValues.TAG, "signInWithCredential:failure", task.getException());
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(getActivity(), "Otp is wrong", Toast.LENGTH_SHORT).show();
                                }
                                // Update UI
                            }
                        }
                    });
                }

            }
        });

        return view;
    }
}