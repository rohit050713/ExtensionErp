package com.www.extensionerp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddProduct extends Fragment {

    public static final String ID_KEY= "com.www.extensionerp.ID_KEY";
    public static final String NAME_KEY= "com.www.extensionerp.NAME_KEY";
    public static final String CATEGORY_KEY= "com.www.extensionerp.CATEGORY_KEY";
    public static final String QUANTITY_KEY= "com.www.extensionerp.QUANTITY_KEY";
    public static final String AMOUNT_KEY= "com.www.extensionerp.AMOUNT_KEY";

    AppCompatActivity appCompatActivity=new AppCompatActivity();


    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    EditText Id,name,category,quantity,amount;
    Button submit;

    Context context;
    public AddProduct(Context context) {
        this.context=context;
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_product, container, false);
        Id=view.findViewById(R.id.p_product_id);
        name=view.findViewById(R.id.p_product_name);
        category=view.findViewById(R.id.p_category);
        quantity=view.findViewById(R.id.p_quantity);
        amount=view.findViewById(R.id.p_amount);
        submit=view.findViewById(R.id.p_submit);

        databaseReference = db.getReference("Products");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate_id() | !validate_name() | !validate_category() | !validate_quantity() | !validate_amount()){
                    Toast.makeText(context, "Please enter all the details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    String id= Id.getText().toString().trim();
                    String p_name= name.getText().toString().trim();
                    String p_category= category.getText().toString().trim();
                    String p_quantity= quantity.getText().toString().trim();
                    String p_amount= amount.getText().toString().trim();

                    // firebase add new item with the unique name
                    databaseReference.child(p_name).push().getKey();

                    //items is the child of the name
                    databaseReference.child(p_name).child("Product_name").setValue(p_name);
                    databaseReference.child(p_name).child("Product_id").setValue(id);
                    databaseReference.child(p_name).child("Category").setValue(p_category);
                    databaseReference.child(p_name).child("Quantity").setValue(p_quantity);
                    databaseReference.child(p_name).child("Amount").setValue(p_amount);

                    Toast.makeText(context, "Product is added.", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(getActivity().getBaseContext(),Tutorial.class);
//
//                    intent.putExtra(ID_KEY,id);
//                    intent.putExtra(NAME_KEY,p_name);
//                    intent.putExtra(CATEGORY_KEY,p_category);
//                    intent.putExtra(QUANTITY_KEY,p_quantity);
//                    intent.putExtra(AMOUNT_KEY,p_amount);

//        appCompatActivity.setResult(Activity.RESULT_OK,intent);
                    getActivity().startActivity(intent);



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

    public boolean validate_name(){
        String p_name= name.getText().toString().trim();
        if(p_name.isEmpty()){
            return false;

        }
        else{
            return true;
        }
    }

    public boolean validate_category(){
        String p_category= category.getText().toString().trim();
        if(p_category.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean validate_quantity(){
        String p_quantity= quantity.getText().toString().trim();
        if(p_quantity.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean validate_amount(){
        String p_amount= amount.getText().toString().trim();
        if(p_amount.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }




}