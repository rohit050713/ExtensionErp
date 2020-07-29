package com.www.extensionerp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddSales extends Fragment {

    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

Spinner category,product_name;
String [] value={"--SELECT--","Four Wheeler","Two Wheeler"};
String [] product={"--SELECT--","Fortuner","Creta","BMW","Jaguar","Lexus"};
EditText Id,squantity,samount;
Button submit;

  Context context;
    public AddSales(Context context) {
        this.context=context;
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_add_sales, container, false);
       category= view.findViewById(R.id.s_category);
       product_name=view.findViewById(R.id.s_product_name);
       Id=view.findViewById(R.id.s_product_id);
      squantity=view.findViewById(R.id.s_quantity);
       samount=view.findViewById(R.id.s_amount);
       submit=view.findViewById(R.id.s_submit);
        category.setAdapter(new ArrayAdapter<CharSequence>(context,android.R.layout.simple_spinner_dropdown_item,value));
        product_name.setAdapter(new ArrayAdapter<CharSequence>(context,android.R.layout.simple_spinner_dropdown_item,product));

        databaseReference = db.getReference("Sales");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate_id() | !validate_name() | !validate_category() | !validate_quantity() | !validate_amount()){
                    Toast.makeText(context, "Enter all the data", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    String id= Id.getText().toString().trim();
                    String s_name= product_name.getSelectedItem().toString().trim();
                    String s_category= category.getSelectedItem().toString().trim();
                    String s_quantity= squantity.getText().toString().trim();
                    String s_amount= samount.getText().toString().trim();

                    // firebase add new item with the unique name
                    databaseReference.child(s_name).push();

                    //items is the child of the name
                    databaseReference.child(s_name).child("Product id").setValue(id);
                    databaseReference.child(s_name).child("Category").setValue(s_category);
                    databaseReference.child(s_name).child("Quantity").setValue(s_quantity);
                    databaseReference.child(s_name).child("Amount").setValue(s_amount);

                    Toast.makeText(context, "Sales is added.", Toast.LENGTH_SHORT).show();
                }
            }
        });
       return view;
    }

    public boolean validate_id(){
        String id= Id.getText().toString().trim();
        if(id.isEmpty()){
         return false;
        }
        else{
            return true;
        }
    }

    public boolean validate_quantity(){
        String quantity= squantity.getText().toString().trim();
        if(quantity.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean validate_amount(){
        String amount= samount.getText().toString().trim();
        if(amount.isEmpty()){
            return false;
        }
        else{
            return true;
        }
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