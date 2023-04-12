package com.example.helth_frag;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class P_LabReportView extends Fragment {

    RecyclerView recyclerviewPdf;
    p_AdapterPdf adapterPdf_p;
    DatabaseReference PdatabaseReference;
    ArrayList<ModelP_pdfuplod> listPdf;
    DatabaseReference databaseReferenceLl;
    String get_Pid;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String Pemail = user.getEmail();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.p__lab_report_view, container, false);

        recyclerviewPdf = view.findViewById(R.id.recyclerviewPdf);

        recyclerviewPdf.setHasFixedSize(true);
        recyclerviewPdf.setLayoutManager(new LinearLayoutManager(getActivity()));

        listPdf = new ArrayList<>();

        databaseReferenceLl= FirebaseDatabase.getInstance().getReference("lab").child("lab");
        PdatabaseReference=FirebaseDatabase.getInstance().getReference("Patient");

        Query email_match= PdatabaseReference.orderByChild("email").equalTo(Pemail);
        String HencodeUserEmail = Pemail.replace(".", ",");


        email_match.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                if (snapshot1.exists()){
                     get_Pid = snapshot1.child(HencodeUserEmail).child("p_id").getValue(String.class);



                    Query pd = databaseReferenceLl.orderByChild("pid").equalTo(get_Pid);
                    pd.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    ModelP_pdfuplod modelP_pdfuplod = dataSnapshot.getValue(com.example.helth_frag.ModelP_pdfuplod.class);

                                    listPdf.add(modelP_pdfuplod);
                                }

                            }else {
                                Toast.makeText(getActivity(), "s not exist", Toast.LENGTH_SHORT).show();
                            }
                            adapterPdf_p = new p_AdapterPdf(
                                    getActivity(),listPdf);
                            recyclerviewPdf.setAdapter(adapterPdf_p);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }else {
                    Toast.makeText(getActivity(), "1 not exist", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });













        return view;
    }
}