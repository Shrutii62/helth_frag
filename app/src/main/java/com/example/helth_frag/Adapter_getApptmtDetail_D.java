package com.example.helth_frag;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helth_frag.activities.Act_D;
import com.example.helth_frag.activities.Act_P;

import java.util.ArrayList;

public class Adapter_getApptmtDetail_D extends RecyclerView.Adapter<Adapter_getApptmtDetail_D.Viewholder>{

    Context contextD;
    ArrayList<model_appointment> listD;
    View view;

    public Adapter_getApptmtDetail_D(Context context, ArrayList<model_appointment> list) {
        this.contextD = context;
        this.listD = list;
    }

    @NonNull
    @Override
    public Adapter_getApptmtDetail_D.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contextD).inflate(R.layout.d_itemrow_shw_appt,parent,false);
        return new Adapter_getApptmtDetail_D.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_getApptmtDetail_D.Viewholder holder, int position) {
        model_appointment model_appointment= listD.get(position);
        holder.issue.setText(model_appointment.getP_email());
        holder.email.setText(model_appointment.getIssue());
        holder.time.setText(model_appointment.getTime());
        holder.date.setText(model_appointment.getDate());
//        holder.status.setText(model_appointment.getStatus());
        String s = model_appointment.getStatus();

        view= holder.rootview;


        holder.cardRecylA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(contextD, "status"+s, Toast.LENGTH_SHORT).show();


                if ( s.equals("on")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    View dailogview = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.pytm_item_row,null );

                    TextView adddetails;
                    adddetails=dailogview.findViewById(R.id.adddetails);
                    adddetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String id = listD.get(holder.getAdapterPosition()).d_id;
                            String name = listD.get(holder.getAdapterPosition()).p_name;
                            String pid = listD.get(holder.getAdapterPosition()).ppid;
                            String aptid = listD.get(holder.getAdapterPosition()).getAptmt_id();



//                        NavController navController= Navigation.findNavController(view);
//                        navController.navigateUp();
//                        navController.navigate(R.id.d_addPrescription);

                            Toast.makeText(builder.getContext(), "bhjdbfj"+pid, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(view.getContext(), Act_D.class);
                            intent.putExtra("id",id);
                            intent.putExtra("pid",pid);
                            intent.putExtra("n",name);
                            intent.putExtra("aptid",aptid);
//                            intent.putExtra("STRING_I_NEED",);
                            view.getContext().startActivity(intent);
                        }
                    });
                    builder.setView(dailogview);
                    builder.setCancelable(true);
                    builder.show();
                }







            }
        });


    }

    @Override
    public int getItemCount() {
        return listD.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public CardView cardRecylA;
        TextView issue, email, date, time, status;
        ImageView dot3;
        View rootview;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            issue = itemView.findViewById(R.id.Aname);
            email = itemView.findViewById(R.id.Aemail);
            time = itemView.findViewById(R.id.Atime);
            date = itemView.findViewById(R.id.Adate);
            cardRecylA = itemView.findViewById(R.id.cardRecylA);
//            dot3 = (ImageView) itemView.findViewById(R.id.Adot);
//            dot3.setClickable(true);
//            dot3.setOnClickListener((View.OnClickListener) this);
        }
    }
}
