package com.example.helth_frag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Actity_UpiPayment extends AppCompatActivity {

    TextInputLayout  noteEt, nameEt, upiIdE;
    TextView amtET;

    TextInputEditText  noteEtE, nameEtE, upiIdEtE;
    Button send;
    String amountt;



    final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actity_upi_payment);
        getSupportActionBar().hide();

//        getActivity().getActionBar().hide();

        initializeViews();

        nameEtE.addTextChangedListener(new TextWatcher() {
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

        noteEtE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                validateNote();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateNote();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        upiIdEtE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                validateUPI();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateUPI();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

         amountt = getIntent().getExtras().getString("a");

        amtET.setText("You have to pay : "+amountt+"rs");


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Actity_UpiPayment.this, "a"+amountt, Toast.LENGTH_SHORT).show();

                if (!validateName() | !validateNote() | !validateUPI()) {
                    return;
                } else {

                //Getting the values from the EditTexts


                String note = noteEt.getEditText().getText().toString();
                String name = nameEt.getEditText().getText().toString();
                String upiId = upiIdE.getEditText().getText().toString();
                payUsingUpi(amountt, upiId, name, note);
            }

        }
        });



    }

    void initializeViews() {
        send = findViewById(R.id.send);
//        amountEtE = findViewById(R.id.amount_etE);
        noteEtE = findViewById(R.id.noteE);
        nameEtE = findViewById(R.id.nameE);
        upiIdEtE = findViewById(R.id.upi_idE);
        amtET = findViewById(R.id.amount_et);

//        amountEt = findViewById(R.id.amount);
        noteEt = findViewById(R.id.note);
        nameEt = findViewById(R.id.name);
        upiIdE = findViewById(R.id.upi_id);
    }

    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(Actity_UpiPayment.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(Actity_UpiPayment.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(Actity_UpiPayment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(Actity_UpiPayment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Actity_UpiPayment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Actity_UpiPayment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }


    private Boolean validateName() {
        String val = nameEt.getEditText().getText().toString();

        if (val.isEmpty()) {
            nameEt.setError("Field cannot be empty");
            return false;
        }
        else {
            nameEt.setError(null);
            nameEt.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateNote() {
        String val = noteEt.getEditText().getText().toString();

        if (val.isEmpty()) {
            noteEt.setError("Field cannot be empty");
            return false;
        }
        else {
            noteEt.setError(null);
            noteEt.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUPI() {
        String val = upiIdE.getEditText().getText().toString();
//        String regex = "[a-zA-Z0-9\\\\.\\\\-]{2,256}\\\\@[a-zA-Z][a-zA-Z]{2,64}";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(val);


        if (val.isEmpty()) {
            upiIdE.setError("Field cannot be empty");
            return false;
        }
        else {
            nameEt.setError(null);
            nameEt.setErrorEnabled(false);
            return true;
        }
    }




}