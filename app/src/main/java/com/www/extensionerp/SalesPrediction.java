package com.www.extensionerp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SalesPrediction extends Fragment {

    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    Button submit;

    Spinner category,product_name;
    String [] values={"--SELECT--","Four Wheeler","Two Wheeler"};
    String [] products={"--SELECT--","Fortuner","Creta","BMW","Jaguar","Lexus"};

    Context context;
    public SalesPrediction(Context context) {
        this.context=context;
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sales_prediction, container, false);
        category= view.findViewById(R.id.sp_category);
        product_name=view.findViewById(R.id.sp_product_name);
        category.setAdapter(new ArrayAdapter<CharSequence>(context,android.R.layout.simple_spinner_dropdown_item,values));
        product_name.setAdapter(new ArrayAdapter<CharSequence>(context,android.R.layout.simple_spinner_dropdown_item,products));
        submit=view.findViewById(R.id.sp_submit);

        databaseReference = db.getReference("Sales Prediction");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate_category() | !validate_name()){
                    Toast.makeText(context, "Please select all the item.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    //for getting the spinner data
                    String s_name= product_name.getSelectedItem().toString().trim();
                    String s_category= category.getSelectedItem().toString().trim();

                    databaseReference.child(s_name).push();

                    databaseReference.child(s_name).child("Category").setValue(s_category);

                    Toast.makeText(context, "Data is sent.", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }

    public boolean validate_category(){
        String scategory= category.getSelectedItem().toString().trim();
        if(scategory.equals("--SELECT--")){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean validate_name(){
        String sname= product_name.getSelectedItem().toString().trim();
        if(sname.equals("--SELECT--")){
            return false;
        }
        else{
            return true;
        }
    }
}