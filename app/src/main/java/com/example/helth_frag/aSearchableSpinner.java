package com.example.helth_frag;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class aSearchableSpinner extends Fragment {


   Spinner spn;
   TextView txt, display;


    ArrayList<String> spinnerlist;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    ProgressDialog progressDialog;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;

//   List<String> namesH;
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.searchable_spinner, container, false);
       spn = view.findViewById(R.id.spn);
       txt = view.findViewById(R.id.txt);
       display = view.findViewById(R.id.display);


       firebaseDatabase= FirebaseDatabase.getInstance();
       databaseReference = firebaseDatabase.getReference("Hospital");





       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               ArrayList<String> spinnerdataList = new ArrayList<>();
               for(DataSnapshot childsnapshot:snapshot.getChildren()) {
                   String spinname = childsnapshot.child("hname").getValue(String.class);

                   spinnerdataList.add(spinname);
                   ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,spinnerdataList);
                   arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                   spn.setAdapter(arrayAdapter);


                   spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                       @Override
                       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                           String selectItem= (String) spn.getSelectedItem();
                           Toast.makeText(getActivity(), ""+selectItem, Toast.LENGTH_SHORT).show();

                           Query checkHname = databaseReference.orderByChild("hname").equalTo(selectItem);



                           checkHname.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dsnapshot) {
                                   for (DataSnapshot childsnapshot2 : dsnapshot.getChildren()){
//                                   String cde = childsnapshot.child("h_id").getValue(String.class);
//                                   Toast.makeText(getActivity(), "" + cde, Toast.LENGTH_SHORT).show();
//                                   display.setText(cde);

                                       if (dsnapshot.exists()) {
                                           String gethid = childsnapshot2.child("address").getValue(String.class);
                                           Toast.makeText(getActivity(), "" + gethid, Toast.LENGTH_SHORT).show();
                                           display.setText(gethid);
                                       }
                               }
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {
                               }
                           });


//                           databaseReference.child(selectItem).addValueEventListener(new ValueEventListener() {
//                               @Override
//                               public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                                   String currentItemStock = snapshot.child("address").getValue(String.class);
//                                   Toast.makeText(getActivity(), "" +currentItemStock, Toast.LENGTH_SHORT).show();
//                               }
//
//                               @Override
//                               public void onCancelled(@NonNull DatabaseError error) {
//
//                               }
//                           });

                       }

                       @Override
                       public void onNothingSelected(AdapterView<?> adapterView) {

                       }
                   });


               }
//               String text = spn.getSelectedItem().toString();
//               Toast.makeText(getActivity(), ""+text, Toast.LENGTH_SHORT).show();





               }



           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });












        return view;
    }






//    public void retrieveData(){
//        listener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ArrayList<modelHD> data = new ArrayList<>();
//                for (DataSnapshot item : snapshot.getChildren() ){
//
//                    modelHD user = item.getValue(modelHD.class);
//                    List<String> id = Arrays.asList(user.h_id);
//                    List<String> nameh = Arrays.asList(user.Hname);
//                    Map<String,String> map = new LinkedHashMap<String,String>();  // ordered
//                    data.add(user);
//
////                    for (int i=0; i<nameh.size(); i++) {
////                        map.put(nameh.get(i), id.get(i));    // is there a clearer way?
////
////                    }
////
////                    spinnerdataList.add(user.Hname);
////                    Toast.makeText(dropdown.this, ""+nameh, Toast.LENGTH_SHORT).show();
//
////                    modelHD user = item.getValue(modelHD.class);
////                    spinnerdataList.add(user.h_id);
//
//                }
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }



}