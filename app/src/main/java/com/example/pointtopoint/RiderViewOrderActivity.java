package com.example.pointtopoint;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RiderViewOrderActivity extends AppCompatActivity {

    private RecyclerView mOrderlist;
    private FirebaseFirestore db;

    private List<orders> orderlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_view_order);
        mOrderlist=(RecyclerView)findViewById(R.id.Order_list);

        orderlist=new ArrayList<>();

        db=FirebaseFirestore.getInstance();

        db.collection("orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType()==DocumentChange.Type.ADDED){
                        orders ord =doc.getDocument().toObject(orders.class);
                        orderlist.add(ord);

                    }
                }
            }
        });



    }
}
