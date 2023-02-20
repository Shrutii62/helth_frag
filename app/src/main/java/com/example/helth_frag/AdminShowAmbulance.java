package com.example.helth_frag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminShowAmbulance extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Model_ambDriverdetail> listAMB;
    AdapterAmbulanceList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_show_ambulance);

        //Call permission

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        }


        listAMB = new ArrayList<>();
        recyclerView = findViewById(R.id.ambulance_list_display);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference= FirebaseDatabase.getInstance().getReference("amb_DrvDetails");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data : snapshot.getChildren()) {
                        Model_ambDriverdetail ambulance = data.getValue(com.example.helth_frag.Model_ambDriverdetail.class);
                        listAMB.add(ambulance);
                    }
                    Toast.makeText(AdminShowAmbulance.this, listAMB.size()+"heu", Toast.LENGTH_SHORT).show();
                    adapter = new AdapterAmbulanceList(getApplicationContext(),listAMB);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}