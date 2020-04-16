package com.example.pointtopoint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class OrderConfirmationActivity extends AppCompatActivity {
    private TextView ordertype,fullprice,pickuplocation,droplocation;
    private Button Placeorder;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String UserID;
    private String auserfullname,ausername,auserid,ausernumber,auseremail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        ordertype = findViewById(R.id.ordertype);
        fullprice= findViewById(R.id.fullprice);
        pickuplocation= findViewById(R.id.pickuplocation);
        droplocation= findViewById(R.id.droplocation);
        Placeorder=findViewById(R.id.confirmorder);

        firebaseAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        UserID=firebaseAuth.getCurrentUser().getUid();


        Intent intent = getIntent();
        final String aordertype = intent.getStringExtra("ordertype");
        final String afullprice = intent.getStringExtra("price");

        ordertype.setText(aordertype);
        fullprice.setText(afullprice);


        DocumentReference docref=db.collection("users").document(UserID);

        docref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                auserfullname=documentSnapshot.getString("name");
                ausername= documentSnapshot.getString("Username");
                ausernumber=documentSnapshot.getString("Mobilenumber");
                auseremail=documentSnapshot.getString("Email");
            }
        });

        Placeorder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Map<String, Object> order = new HashMap<>();
                order.putIfAbsent("orderstatus","pending");
                order.putIfAbsent("userid",UserID);
                order.putIfAbsent("ordertype",aordertype);
                order.putIfAbsent("price",afullprice);
                order.putIfAbsent("userfullname",auserfullname);
                order.putIfAbsent("username",ausername);
                order.putIfAbsent("useremail",auseremail);
                order.putIfAbsent("usernumber",ausernumber);

                db.collection("orders").add(order).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Map<String, Object> orderid = new HashMap<>();
                        orderid.putIfAbsent("orderid",documentReference.getId());

                        db.collection("orders").document(documentReference.getId()).update(orderid);

                        Toast.makeText(OrderConfirmationActivity.this, "Order placed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), UserOtpActivity.class);
                        intent.putExtra("orderid",documentReference.getId());
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OrderConfirmationActivity.this, "Order cannot be placed", Toast.LENGTH_SHORT).show();
                    }
                });

                //Toast.makeText(OrderConfirmationActivity.this, "Order placed", Toast.LENGTH_SHORT).show();

                /*finish();
                startActivity(new Intent(OrderViewActivity.this,ItemListActivity.class));*/
            }
        });






    }
}
