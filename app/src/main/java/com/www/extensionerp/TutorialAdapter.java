package com.www.extensionerp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.Viewholder> {
Context context;
ArrayList<ModelTutorial> arrayList;


    public TutorialAdapter(Context context, ArrayList<ModelTutorial> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }




    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_tutorial,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
     final ModelTutorial model= arrayList.get(position);
     holder.id.setText(model.getProduct_id());
     holder.name.setText(model.getProduct_name());
     holder.category.setText(model.getCategory());
     holder.quantity.setText(model.getQuantity());
     holder.amount.setText(model.getAmount());


//for on click edit of the recycler view item
     holder.ll.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(context.getApplicationContext(),EditProduct.class);
             intent.putExtra("product_name",model.getProduct_name());
             intent.putExtra("product_id",model.getProduct_id());
             intent.putExtra("category",model.getCategory());
             intent.putExtra("quantity",model.getQuantity());
             intent.putExtra("amount",model.getAmount());
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
             context.getApplicationContext().startActivity(intent);




         }
     });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }



    public class Viewholder extends RecyclerView.ViewHolder {
        TextView id,name,category,quantity,amount;
        LinearLayout ll;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.product_id);
            name=itemView.findViewById(R.id.product_name);
            category=itemView.findViewById(R.id.category);
            quantity=itemView.findViewById(R.id.quantity);
            amount=itemView.findViewById(R.id.amount);
            ll=itemView.findViewById(R.id.linear_layout);
        }
    }
}
