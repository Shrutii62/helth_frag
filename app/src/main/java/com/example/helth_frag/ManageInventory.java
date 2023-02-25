package com.example.helth_frag;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helth_frag.AdapterInventoryItem;
import com.example.helth_frag.ModelInventoryItem;
import com.example.helth_frag.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ManageInventory extends Fragment implements AdapterInventoryItem.RecyclerViewClickInterface {


    RecyclerView inventoryItemListView;
    ArrayList<ModelInventoryItem> itemsList;
    AdapterInventoryItem adapter;

    DatabaseReference databaseReference;


    MutableLiveData<String> filterBy = new MutableLiveData<>("");
    Spinner filterSpinner;


    FloatingActionButton addItem;
    AdapterInventoryItem.RecyclerViewClickInterface recyclerViewClickInterface = this;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.manage_inventory, container, false);



        databaseReference = FirebaseDatabase.getInstance().getReference("inventory");
        filterSpinner = view.findViewById(R.id.filter_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.added_by, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        filterSpinner.setAdapter(adapter);
        inventoryItemListView = view.findViewById(R.id.inventory_item_list_view);

        itemsList = new ArrayList<>();

        Bundle b = getArguments();
        assert b != null;
        String user = b.getString("navigate");
        switch (user){
            case "Doctor" :
                filterSpinner.setSelection(2);
                break;
            case "Admin" :
                filterSpinner.setSelection(0);
                break;
            case "Lab":
                filterSpinner.setSelection(1);

        }

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s= filterSpinner.getSelectedItem().toString();

                switch (s){
                    case "Admin" : {
                        retrieveFromDatabase(s);
                    }
                    case "Lab" : {
                        retrieveFromDatabase(s);
                    }
                    case "Doctor" : {
                        retrieveFromDatabase(s);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        filterBy.observe(requireActivity(), new Observer<String>() {

            @Override
            public void onChanged(String s) {


            }
        });
        inventoryItemListView.setHasFixedSize(true);
        inventoryItemListView.setLayoutManager(new LinearLayoutManager(getActivity()));


        addItem = view.findViewById(R.id.add_inventory_item);

        retrieveFromDatabase(filterBy.getValue());
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    public void showDialog(){

        EditText etitemName;
        EditText etitemDescription;
        EditText etitemCount;
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_inventory_item);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        etitemName = dialog.findViewById(R.id.add_inventory_item_name);
        etitemDescription = dialog.findViewById(R.id.add_inventory_item_description);
        Button addItem = dialog.findViewById(R.id.add);
        etitemCount = dialog.findViewById(R.id.add_inventory_item_quantity);


        String key = databaseReference.push().getKey();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String quantity = etitemCount.getText().toString();

                String filterBy = filterSpinner.getSelectedItem().toString();

                String itemName = etitemName.getText().toString();
                String itemDescription = etitemDescription.getText().toString();
                ModelInventoryItem item = new ModelInventoryItem(key, itemName, itemDescription, quantity, false, filterBy);
                addToDatabase(key, item);
                retrieveFromDatabase(filterBy);
                dialog.cancel();
            }
        });
        dialog.show();



    }

    @Override
    public void onDoneClick(int position,String quantity) {
        ModelInventoryItem item = itemsList.get(position);
        databaseReference.child(item.getKey()).child("quantity").setValue(quantity).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(requireContext(), "edited", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onEditItem(int position) {

        ModelInventoryItem item = itemsList.get(position);
        showEditDialog(item);


    }

    public void showEditDialog(ModelInventoryItem item){
        TextView textView;
        EditText etitemName;
        EditText etitemDescription;
        EditText etitemCount;
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_inventory_item);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        etitemName = dialog.findViewById(R.id.add_inventory_item_name);
        textView = dialog.findViewById(R.id.text);
        etitemDescription = dialog.findViewById(R.id.add_inventory_item_description);
        Button addItem = dialog.findViewById(R.id.add);
        etitemCount = dialog.findViewById(R.id.add_inventory_item_quantity);

        textView.setText("Edit item");
        addItem.setText("UPDATE");
        etitemName.setText(item.getItemName());
        etitemDescription.setText(item.getItemDescription());
        etitemCount.setText(item.getQuantity());

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etitemCount.getText().toString().equals(item.getQuantity())){
                    updateCount(item,etitemCount.getText().toString());
                }
                if(!etitemDescription.getText().toString().equals(item.getItemDescription())){
                    updateDescription(item,etitemDescription.getText().toString());
                }
                if(!etitemName.getText().toString().equals(item.getItemName())){
                    updateName(item,etitemName.getText().toString());
                }
                dialog.cancel();
                retrieveFromDatabase(filterSpinner.getSelectedItem().toString());
            }

        });

        dialog.show();

    }

    public void updateCount(ModelInventoryItem item,String newQuantity){
        databaseReference.child(item.getKey()).child("quantity").setValue(newQuantity);
    }
    public void updateDescription(ModelInventoryItem item,String newDescription){
        databaseReference.child(item.getKey()).child("itemDescription").setValue(newDescription);
    }

    public void updateName(ModelInventoryItem item,String newName){
        databaseReference.child(item.getKey()).child("itemName").setValue(newName);

    }

    public void addToDatabase(String key, ModelInventoryItem item){

//        Toast .makeText(requireContext(), item.getKey(), Toast.LENGTH_SHORT).show();

        databaseReference.child(key).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        });


    }

    public void retrieveFromDatabase(String filter){

        itemsList.removeAll(itemsList);
        adapter = new AdapterInventoryItem(itemsList,requireContext(),recyclerViewClickInterface);
        inventoryItemListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Query checkAddedBy= databaseReference.orderByChild("addedBy").equalTo(filter);

        checkAddedBy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    itemsList.removeAll(itemsList);
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ModelInventoryItem item = dataSnapshot.getValue(ModelInventoryItem.class);
                        itemsList.add(item);
                    }
                    adapter = new AdapterInventoryItem(itemsList,requireContext(),recyclerViewClickInterface);
                    adapter.notifyDataSetChanged();
                    inventoryItemListView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}