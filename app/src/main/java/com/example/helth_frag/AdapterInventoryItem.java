package com.example.helth_frag;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterInventoryItem extends RecyclerView.Adapter<AdapterInventoryItem.ViewHolder> {

    ArrayList<ModelInventoryItem> itemList;
    Context context;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    MutableLiveData<Boolean> isEditing = new MutableLiveData<>(false);




    public AdapterInventoryItem(ArrayList<ModelInventoryItem> itemList,Context context,RecyclerViewClickInterface recyclerViewClickInterface){
        this.itemList = itemList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterInventoryItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.inventory_list_item,parent,false);
        return new AdapterInventoryItem.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInventoryItem.ViewHolder holder, int position) {


        ModelInventoryItem item = itemList.get(holder.getAdapterPosition());
        holder.inventoryName.setText(item.getItemName());
        holder.inventoryDescription.setText(item.getItemDescription());
        holder.quantity.setText(item.getQuantity());


        isEditing.observe((LifecycleOwner) context, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                ModelInventoryItem item = itemList.get(holder.getAdapterPosition());
                item.setEditing(isEditing.getValue());

            }
        });


        holder.addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.quantity.getText().toString());
                quantity += 1;
                holder.quantity.setText(String.valueOf(quantity));
                isEditing.setValue(true);
                if(item.isEditing){
                    holder.doneLayout.setVisibility(View.VISIBLE);
                }

            }
        });

        holder.subtractQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.quantity.getText().toString());
                quantity -= 1;
                holder.quantity.setText(String.valueOf(quantity));
                isEditing.setValue(true);
                if(item.isEditing){
                    holder.doneLayout.setVisibility(View.VISIBLE);
                }

            }
        });

        holder.doneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditing.setValue(false);
                holder.doneLayout.setVisibility(View.GONE);

                String key = itemList.get(holder.getAdapterPosition()).getKey();
                recyclerViewClickInterface.onDoneClick(holder.getAdapterPosition(),holder.quantity.getText().toString());
            }
        });



        holder.cardInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewClickInterface.onEditItem(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout doneLayout;
        public CardView cardInventory;
        public ImageView addQuantity;
        public ImageView subtractQuantity;
        public TextView inventoryName,inventoryDescription,quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            doneLayout = itemView.findViewById(R.id.inventory_edit_done);
            cardInventory = itemView.findViewById(R.id.card_inventory);
            inventoryDescription = itemView.findViewById(R.id.inventory_item_description);
            inventoryName = itemView.findViewById(R.id.inventory_item_name);
            quantity = itemView.findViewById(R.id.inventory_item_count);
            addQuantity = itemView.findViewById(R.id.inventory_item_add);
            subtractQuantity = itemView.findViewById(R.id.inventory_item_subtrat);


        }
    }

    public interface RecyclerViewClickInterface {

        void onDoneClick(int position,String quantity);

        void onEditItem(int position);

    }


}
