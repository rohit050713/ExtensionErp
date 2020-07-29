package com.www.extensionerp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProduct extends AppCompatActivity {

    EditText e_id,e_category,e_quantity,e_amount;
    TextView e_name;
    Button e_submit;
    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        databaseReference = db.getReference("Products");


        e_id=findViewById(R.id.e_product_id);
        e_category=findViewById(R.id.e_category);
        e_quantity=findViewById(R.id.e_quantity);
        e_amount=findViewById(R.id.e_amount);

        e_name=findViewById(R.id.tv_e_product_name);
        e_submit=findViewById(R.id.e_submit);

        //getting data from the tutorial activity
        Intent intent=getIntent();
       String name= intent.getStringExtra("product_name");
       String id= intent.getStringExtra("product_id");
        String category=intent.getStringExtra("category");
       String quantity= intent.getStringExtra("quantity");
        String amount=intent.getStringExtra("amount");

        e_name.setText(name);
        e_id.setText(id);
        e_category.setText(category);
        e_quantity.setText(quantity);
        e_amount.setText(amount);

        e_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate_id() | !validate_name() | !validate_category() | !validate_quantity() | !validate_amount()){
                    Toast.makeText(EditProduct.this, "Please enter all the details.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //for updating data to the firebase
                else{
                    String id= e_id.getText().toString().trim();
                    String p_name= e_name.getText().toString().trim();
                    String p_category= e_category.getText().toString().trim();
                    String p_quantity= e_quantity.getText().toString().trim();
                    String p_amount= e_amount.getText().toString().trim();

                    // firebase add new item with the unique name
                    databaseReference.child(p_name).push().getKey();

                    //items is the child of the name
                    databaseReference.child(p_name).child("Product_name").setValue(p_name);
                    databaseReference.child(p_name).child("Product_id").setValue(id);
                    databaseReference.child(p_name).child("Category").setValue(p_category);
                    databaseReference.child(p_name).child("Quantity").setValue(p_quantity);
                    databaseReference.child(p_name).child("Amount").setValue(p_amount);

                    Toast.makeText(EditProduct.this, "Product is updated.", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(EditProduct.this,Tutorial.class);

                    startActivity(intent);



                }
            }
        });


    }


    public boolean validate_id(){
        String id= e_id.getText().toString().trim();
        if(id.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean validate_name(){
        String p_name= e_name.getText().toString().trim();
        if(p_name.isEmpty()){
            return false;

        }
        else{
            return true;
        }
    }

    public boolean validate_category(){
        String p_category= e_category.getText().toString().trim();
        if(p_category.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean validate_quantity(){
        String p_quantity= e_quantity.getText().toString().trim();
        if(p_quantity.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean validate_amount(){
        String p_amount= e_amount.getText().toString().trim();
        if(p_amount.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
}