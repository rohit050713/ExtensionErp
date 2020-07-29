package com.www.extensionerp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Tutorial extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    Button yes,no;
    Button yes_delete,no_delete;
    CircleImageView close_delete,close1;
    ProgressBar progressBar;
    ImageView search,power,close;
    SearchView searchView;
    TextView title;
    RecyclerView recyclerView;
    ImageView add;
    DatabaseReference reference;
    String Name;
    ArrayList<ModelTutorial> arrayList=new ArrayList<>();

    FirebaseRecyclerAdapter<ModelTutorial, ViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        progressBar=findViewById(R.id.progress_bar_tutorial);
        search=findViewById(R.id.search);
        power=findViewById(R.id.power);
        title=findViewById(R.id.title1);

        searchView=findViewById(R.id.search_view);
        close= findViewById(R.id.circle_close);

        //for search icon
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                power.setVisibility(View.GONE);

                searchView.setVisibility(View.VISIBLE);
                close.setVisibility(View.VISIBLE);
            }
        });

        //for close icon
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.GONE);
                close.setVisibility(View.GONE);

                title.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                power.setVisibility(View.VISIBLE);
            }
        });

        //for logout icon
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(Tutorial.this);
                dialog.setContentView(R.layout.dialog_logout);
                dialog.setCancelable(false);

                close1=dialog.findViewById(R.id.close);

                close1.setOnClickListener(new View.OnClickListener() {
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
                        Intent intent=new Intent(Tutorial.this,Login.class);
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

        // for the searching
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {

            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if(!newText.toString().isEmpty()){
                Search(newText);
            }
            else{
                Search("");
            }
            return true;
        }
    });

        add=findViewById(R.id.iv_add);
        recyclerView=findViewById(R.id.recycler_view);
        reference= FirebaseDatabase.getInstance().getReference("Products");





        recyclerView.setLayoutManager(new LinearLayoutManager(this));



      //for adding note
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Tutorial.this,Home.class);
                   startActivity(intent);
            }
        });

    }



    //for loading data to the recyclerview from firebase
        @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);

        //for setting the recycler view data from firebase
        FirebaseRecyclerOptions options= new FirebaseRecyclerOptions.Builder<ModelTutorial>()
                .setQuery(reference,ModelTutorial.class)
                .build();

       adapter= new FirebaseRecyclerAdapter<ModelTutorial, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i, @NonNull ModelTutorial modelTutorial) {

                Name= getRef(i).getKey();
                reference.child(Name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {

                            final String name = snapshot.child("Product_name").getValue(String.class);
                            final String id = snapshot.child("Product_id").getValue(String.class);
                            final String category = snapshot.child("Category").getValue(String.class);
                            final String quantity = snapshot.child("Quantity").getValue(String.class);
                            final String amount = snapshot.child("Amount").getValue(String.class);

                            viewHolder.id.setText(id);
                            viewHolder.name.setText(name);
                            viewHolder.category.setText(category);
                            viewHolder.quantity.setText(quantity);
                            viewHolder.amount.setText(amount);

                            progressBar.setVisibility(View.GONE);


//for editing the value by clicking on recycler view
                            viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Tutorial.this, EditProduct.class);
                                    intent.putExtra("product_name", name);
                                    intent.putExtra("product_id", id);
                                    intent.putExtra("category", category);
                                    intent.putExtra("quantity", quantity);
                                    intent.putExtra("amount", amount);

                                    startActivity(intent);


                                }

                            });

                        }

                        //for deleting item by swiping right or left
                     new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                         @Override
                         public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                             return false;
                         }

                         @Override
                         public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                             deleteitem(viewHolder.getAdapterPosition());
                             Toast.makeText(Tutorial.this, "Product is deleted.", Toast.LENGTH_SHORT).show();

                         }
                     }).attachToRecyclerView(recyclerView);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_tutorial,parent,false);


                return new ViewHolder(view);
            }

            //for delete item from recycler view and firebase
           public void deleteitem(int position){
                 getSnapshots().getSnapshot(position).getRef().removeValue();
           }

        };




        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }










    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id,name,category,quantity,amount;
        LinearLayout ll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.product_id);
            name=itemView.findViewById(R.id.product_name);
            category=itemView.findViewById(R.id.category);
            quantity=itemView.findViewById(R.id.quantity);
            amount=itemView.findViewById(R.id.amount);
            ll=itemView.findViewById(R.id.linear_layout);
        }
    }

    //for searching
    private void Search(String newText) {
        if(!newText.isEmpty()) {
            //what to search here
            Query query = reference.orderByChild("Product_name")
                    .startAt(newText)
                    .endAt(newText + "\uf8ff");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChildren()) {
                        arrayList.clear();
                        for (DataSnapshot dss : snapshot.getChildren()) {
                            final ModelTutorial item = dss.getValue(ModelTutorial.class);
                            arrayList.add(item);
                        }

                        TutorialAdapter myadapter = new TutorialAdapter(getApplicationContext(), arrayList);
                        recyclerView.setAdapter(myadapter);
                        myadapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else{
            onStart();
        }



//        ArrayList<ModelTutorial> mylist=new ArrayList<>();
//        for(ModelTutorial object: arrayList){
//            if(object.getProduct_name().toLowerCase().contains(newText.toLowerCase())){
//                mylist.add(object);
//            }
//            TutorialAdapter aadapter=new TutorialAdapter(getApplicationContext(),mylist);
//            recyclerView.setAdapter(aadapter);
//        }
    }


//    public void alertDelete(final String qwerty){
//
//        final Dialog dialog=new Dialog(Tutorial.this);
//        dialog.setContentView(R.layout.dialog_delete);
//        dialog.setCancelable(false);
//
//        close_delete=dialog.findViewById(R.id.delete_close);
//
//        close_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        yes_delete= dialog.findViewById(R.id.btn_delete_yes);
//        yes_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              deleteRef(qwerty);
//              dialog.dismiss();
//                Toast.makeText(Tutorial.this, "Delete Succesfully.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        no_delete=dialog.findViewById(R.id.btn_delete_no);
//        no_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        if(dialog.getWindow() !=null){
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        }
//        dialog.show();
//
//
//    }

//    private void deleteRef(String qwerty){
//        reference.child(qwerty).removeValue();
//    }

}