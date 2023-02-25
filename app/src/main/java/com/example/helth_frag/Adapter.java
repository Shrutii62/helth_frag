package com.example.helth_frag;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helth_frag.activities.Act_P;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {


    Context context;
    ArrayList<modelH_usr> list;



    public Adapter(Context context, ArrayList<modelH_usr> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.p_item_row,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        modelH_usr modelH_usr= list.get(position);
         holder.name.setText(modelH_usr.getUname());
        holder.email.setText(modelH_usr.getPhoneno());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "recycler", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View dailogview = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.p_dailog_recyl,null );

                TextView getAptmt;
                getAptmt=dailogview.findViewById(R.id.getApptmt);

                getAptmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = list.get(holder.getAdapterPosition()).u_id;
                        String name = list.get(holder.getAdapterPosition()).getEmail();
//                        Toast.makeText(view.getContext(), "pp"+name, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), Act_P.class);
                        intent.putExtra("id",id);
                        intent.putExtra("name",name);
//                            intent.putExtra("STRING_I_NEED",);
                        view.getContext().startActivity(intent);

                    }
                });

                builder.setView(dailogview);
                builder.setCancelable(true);
                builder.show();


            }
        });







//        holder.dot3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "hghjgvhg", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CardView cardView;
        TextView name, email;
        ImageView dot3;
        View rootview;



        public Viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.dname);
            email = itemView.findViewById(R.id.demail);
            cardView = itemView.findViewById(R.id.cardRecyl);
            dot3 = (ImageView) itemView.findViewById(R.id.dot3);
            dot3.setClickable(true);
            dot3.setOnClickListener((View.OnClickListener) this);


        }

        public void onClick(View v){
            showPopUpmenu(v);
        }

        private void showPopUpmenu(View view){

            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.dotmenu_doc);
//            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.men1:



                            Intent intent = new Intent(view.getContext(), Act_P.class);
//                            intent.putExtra("STRING_I_NEED",);
                    view.getContext().startActivity(intent);

                    return true;
                        case R.id.men2:

                            //handle menu2 click
                            return true;

                        default:
                            return false;
                    }
                }
            });


        }


//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            final Intent intent;
//            switch (menuItem.getItemId()){
//                case R.id.men1:
//                     intent = new Intent(contextt.getApplicationContext(), p.class);
//                    contextt.getApplicationContext().startActivity(intent);
//
//                     break;
//                case R.id.men2:
//                    return true;
//
//
//            }
//            return false;
//        }
    }



}
