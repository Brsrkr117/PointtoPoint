package com.example.pointtopoint;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RiderViewOrderActivity extends AppCompatActivity {

    private static final String TAG = "here displaying";
    private RecyclerView mOrderlist;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private OrderListAdapter ordersListAdapter;
    private List<orders> ordersList;
    private String UserID;
    private String rad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_view_order);
        mOrderlist=(RecyclerView)findViewById(R.id.Order_list);

        firebaseAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        UserID=firebaseAuth.getCurrentUser().getUid();

        Bundle b = getIntent().getExtras();
        rad = b.getString("radius");

        Toast.makeText(RiderViewOrderActivity.this,"Your radius is " + rad +"km", Toast.LENGTH_LONG).show();

        db=FirebaseFirestore.getInstance();
        ordersList=new ArrayList<>();
        ordersListAdapter= new OrderListAdapter(ordersList,this);

        mOrderlist=(RecyclerView)findViewById(R.id.Order_list);
        mOrderlist.setHasFixedSize(true);
        mOrderlist.setLayoutManager(new LinearLayoutManager(this));
        mOrderlist.setAdapter(ordersListAdapter);


        db=FirebaseFirestore.getInstance();
        db.collection("orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.d(TAG,"Error"+e.getMessage());
                }

                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType()==DocumentChange.Type.ADDED){
                       /* String name1=doc.getDocument().getString("Order id");
                        Log.d(TAG,"Order ID "+ name1);*/
                        orders orders =doc.getDocument().toObject(orders.class);
                        String tempordertype=orders.getOrdertype();
                        String temporderstatus=orders.getOrderstatus();

                        /*String pickuplat=orders.getPickuplat();
                        String pickuplong=orders.getPickuplong();
                        String droplat=orders.getDroplat();
                        String droplong=orders.getDroplong();

                        String pickupaddr=orders.getPickaddr();
                        String dropaddr=orders.getDropaddr();*/

                        //conditional recycler view

                        if(temporderstatus.equals("pending")){
                            ordersList.add(orders);
                            ordersListAdapter.notifyDataSetChanged();
                        }


                    }
                }
            }
        });



    }
}
