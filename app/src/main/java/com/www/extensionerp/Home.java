package com.www.extensionerp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.CircularPropagation;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TextView logout;
    Button yes,no;
    CircleImageView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.viewPager);
        logout=findViewById(R.id.logout);

        //for setting tabs
        TabAdapter adapter=new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddProduct(this),"Add Product");
        adapter.addFragment(new AddSales(this),"Add Sales");
        adapter.addFragment(new SalesPrediction(this),"Prediction");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(Home.this);
                dialog.setContentView(R.layout.dialog_logout);
                dialog.setCancelable(false);

                close=dialog.findViewById(R.id.close);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                yes= dialog.findViewById(R.id.btn_yes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(Home.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                });

                no=dialog.findViewById(R.id.btn_no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                if(dialog.getWindow() !=null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                dialog.show();
            }
        });


    }
}