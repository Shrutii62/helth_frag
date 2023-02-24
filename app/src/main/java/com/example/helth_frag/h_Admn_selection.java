package com.example.helth_frag;

import static android.content.Context.MODE_PRIVATE;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class h_Admn_selection extends Fragment {
    CardView add_userm, invntory;
    CardView ambulance_book, RequestServc;
    SharedPreferences sharedPreferences;
    LinearLayout dateLayout;
    String hospitalAddress;
    MaterialDatePicker datePicker;
    DatabaseReference hospitalDatabase;
    String hospitalEmail;
    FirebaseUser user;

    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseAuth.AuthStateListener mAuthListener;

    Button logoutm;
    private Toolbar topAppBar;

    TextView Hlist, viewReq;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.h__admn_selection, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        setHasOptionsMenu(true);

        add_userm = view.findViewById(R.id.add_user);
        ambulance_book = view.findViewById(R.id.ambulance_book);
        topAppBar = view.findViewById(R.id.topAppBar);
        RequestServc = view.findViewById(R.id.RequestServc);
        invntory = view.findViewById(R.id.invntory);




//        ((AppCompatActivity) getActivity()).setSupportActionBar(topAppBar);


        float radius = getResources().getDimension(R.dimen.default_corner_radius);
        MaterialShapeDrawable materialShapeDrawable = (MaterialShapeDrawable)topAppBar.getBackground();
        materialShapeDrawable.setShapeAppearanceModel(materialShapeDrawable.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,radius)
                .build());



       topAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.men1:
                    FirebaseAuth.getInstance().signOut();

                NavHostFragment.findNavController(h_Admn_selection.this).navigate(R.id.action_h_Admn_selection_to_select_type_option);
                Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.men2:
                    // Save profile changes
                    return true;
                default:
                    return false;
            }
        });

//        logoutm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//
//                NavHostFragment.findNavController(h_Admn_selection.this).navigate(R.id.action_h_Admn_selection_to_select_type_option);
//                Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
//            }
//        });

        add_userm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Navigation.findNavController(view).navigate(R.id.action_h_Admn_selection_to_usr_registration);
            }
        });


        ambulance_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog();
                Navigation.findNavController(view).navigate(R.id.action_h_Admn_selection_to_adminShowAmbulance);

            }
        });
        RequestServc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(requireContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dailog_on_ptransfer);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


                dialog.findViewById(R.id.Hlist).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(view).navigate(R.id.action_h_Admn_selection_to_h_request_servces2);
                        dialog.cancel();
                    }
                });

                dialog.findViewById(R.id.viewReq).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(view).navigate(R.id.action_h_Admn_selection_to_tablayoutAmb);
                        dialog.cancel();

                    }
                });

//                showDialog();
//                Navigation.findNavController(view).navigate(R.id.action_h_Admn_selection_to_h_request_servces2);

            }
        });

        invntory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog();
                Bundle bundle = new Bundle();
                bundle.putString("navigate", "Admin");
                ManageInventory mi = new ManageInventory();
                mi.setArguments(bundle);

                Navigation.findNavController(view).navigate(R.id.action_h_Admn_selection_to_manageInventory, bundle);

            }
        });








        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.main_dotmenu,menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//
//    public void showDialog(){
//        Dialog dialog = new Dialog(requireContext());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_book_ambulance);
//        dateLayout = dialog.findViewById(R.id.selectDate);
//        TextView dateText;
//        dateText = dialog.findViewById(R.id.date);
//        Button btn = dialog.findViewById(R.id.booking_done);
//        EditText address = dialog.findViewById(R.id.hospital_address);
//        address.setText(hospitalAddress);
//        RadioButton now = dialog.findViewById(R.id.booking_now);
//
//
//        RadioGroup ambulanceType = dialog.findViewById(R.id.rg);
//
////        ambulanceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(RadioGroup radioGroup, int i) {
////                switch(i){
////                    case R.id.type_als: {
////                        selectedType = "ALS";
////                        break;
////                    }
////                    case R.id.type_bls:{
////                        selectedType = "BLS";
////                        break;
////                    }
////
////                    case R.id.type_dk: {
////                        selectedType = "DK";
////                        break;
////                    }
////                    case R.id.type_motuary: {
////                        selectedType = "MOTUARY";
////                        break;
////                    }
////                    case R.id.type_pTransport:{
////                        selectedType = "PT";
////                        break;
////                    }
////                }
////            }
////        });
//
//        dateText.setText(getDate(System.currentTimeMillis(),"MMM dd yyyy"));
//        dateText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                datePicker.show(getParentFragmentManager(), "Material_date_picker");
//                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
//                    @Override
//                    public void onPositiveButtonClick(Object selection) {
//                        dateText.setText(datePicker.getHeaderText());
//                    }
//                });
//            }
//        });
//
//
//        now.setOnClickListener(new View.OnClickListener() {
//                                   @Override
//                                   public void onClick(View view) {
//
//                                       dateLayout.setVisibility(View.GONE);
//                                   }
//                               }
//        );
//
//        RadioButton later = dialog.findViewById(R.id.booking_later);
//        later.setOnClickListener(new View.OnClickListener() {
//                                     @Override
//                                     public void onClick(View view) {
//                                         dateLayout.setVisibility(View.VISIBLE);
//                                     }
//                                 }
//        );
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(requireActivity(),AdminShowAmbulance.class);
//                startActivity(intent);
//            }
//        });
//
//
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//    }
//
//    public static String getDate(long milliSeconds, String dateFormat)
//    {
//        // Create a DateFormatter object for displaying date in specified format.
//        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
//
//        // Create a calendar object that will convert the date and time value in milliseconds to date.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(milliSeconds);
//        return formatter.format(calendar.getTime());
//    }



//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.men1:
//
//                FirebaseAuth.getInstance().signOut();
//
//                NavHostFragment.findNavController(h_Admn_selection.this).navigate(R.id.action_h_Admn_selection_to_select_type_option);
//                Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
//                return true;
//
//
//
//
//
//
////                FirebaseAuth.getInstance().signOut();
////                Intent i = new Intent(getActivity(),
////                        MainActivity.class);
////                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
////                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                startActivity(i);
//
//
//            case R.id.men2:
//                Toast.makeText(getActivity(), "men2", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.men3:
//                Toast.makeText(getActivity(), "men3", Toast.LENGTH_SHORT).show();
//                break;
//        }
//        return true;
//    }


}
