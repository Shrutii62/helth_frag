package com.example.helth_frag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class lab2_PDF extends AppCompatActivity {

    private CardView addPDFM;
    //    private TextInputLayout PDFTitleM;
    TextInputEditText titletIpdfM;
    private Button btUPloadPDF;
    private TextView pdfTV;
    private String pdfName, title;

    private final  int REQ = 1;
    private Uri pdfdata;
    private TextInputLayout noticeTitleM;

    //for filepath storage
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    String downloadURL  = "";

    private ProgressDialog progressDialog;
    String pid, did, hid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab2_pdf);

        addPDFM = findViewById(R.id.addPDF);
        titletIpdfM = findViewById(R.id.titletIpdf);
//        PDFTitleM = findViewById(R.id.PDFTitle);
        btUPloadPDF = findViewById(R.id.btUPloadPDF);
        pdfTV = findViewById(R.id.pdfTV);

        progressDialog = new ProgressDialog(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("lab");
        storageReference = FirebaseStorage.getInstance().getReference();

        pid = getIntent().getExtras().getString("piid");
        hid = getIntent().getExtras().getString("hiid");
        did = getIntent().getExtras().getString("diid");

        addPDFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDFGallery();
            }
        });


        btUPloadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(lab2_PDF.this, "p"+pid, Toast.LENGTH_SHORT).show();
                title = titletIpdfM.getText().toString();
                if (title.isEmpty()){
                    titletIpdfM.setError("Fields cannot be empty");
                    titletIpdfM.requestFocus();
                }else if(pdfdata == null){
                    Toast.makeText(lab2_PDF.this, "Please upload PDF", Toast.LENGTH_SHORT).show();
                }else{
                    uploadPDF();
                }


            }
        });
    }


    private void uploadPDF() {

        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Uploading PDF");
        progressDialog.show();
        //for umique pdf name =System.currentTimeMillis()
        StorageReference reference = storageReference.child("lab/" + pdfName + "-" + System.currentTimeMillis()+ ".pdf");
        reference.putFile(pdfdata)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
                        uploaData(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(lab2_PDF.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploaData(String downloadURL) {
//        databaseReference  = databaseReference.child("pdf");
//        final  String uniqueKey = databaseReference.push().getKey();
        final String uniqueKey = databaseReference.child("lab").push().getKey();


//        HashMap<String, Object> data = new HashMap<>();
//        data.put("pdfTitle", title);
//        data.put("pdfURL", downloadURL);
//        data.put("pid", pid);
//        data.put("did", did);
//        data.put("hid", hid);

//        HashMap data = new HashMap();
//        data.put("pdfTitle",title);
//        data.put("pdfURL",downloadURL);

//        PDFModal pdfModal = new PDFModal(title,downloadURL, uniqueKey);

        ModelP_pdfuplod modelP_pdfuplod = new ModelP_pdfuplod(title,downloadURL,pid, did, hid);


        databaseReference.child("lab").child(uniqueKey).setValue(modelP_pdfuplod).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(lab2_PDF.this, "PDF Uploaded Successfully", Toast.LENGTH_SHORT).show();
                titletIpdfM.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(lab2_PDF.this, "Failed to upload PDF", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void openPDFGallery() {
        Intent intent = new Intent();
//        intent.setType("pdf/docs/ppt");
        intent.setType("application/pdf");
//        intent.setDataAndType(Uri.parse("application/pdf/mnt/sdcard/xxx/xxx/Pictures/xxx.jpg"), "image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF File"),REQ);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //pick image
        if (requestCode == REQ && resultCode == RESULT_OK){
            pdfdata = data.getData();

            if (pdfdata.toString().startsWith("content://")){
                Cursor cursor = null;
                try {
                    cursor = lab2_PDF.this.getContentResolver().query(pdfdata, null,null,null,null);

                    if (cursor != null && cursor.moveToFirst()){
                        pdfName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

            }else  if(pdfdata.toString().startsWith("file://")){
                pdfName = new File(pdfdata.toString()).getName();
            }

            pdfTV.setText(pdfName);



        }
    }
}