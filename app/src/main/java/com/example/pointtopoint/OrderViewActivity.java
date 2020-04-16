package com.example.pointtopoint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OrderViewActivity extends AppCompatActivity {
    //ImageView imageView;
    private TextView title, description;
    private int position;
    private Button placeorder,goback;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);

        title = findViewById(R.id.titleText);
        description = findViewById(R.id.descriptionText);
        goback = (Button) findViewById(R.id.btngoback);
        placeorder = (Button) findViewById(R.id.btnPlaceOrder);

        db= FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        final String aTitle = intent.getStringExtra("title");
        final String aDescription = intent.getStringExtra("description");

        title.setText(aTitle);
        description.setText("Rs" + aDescription + "\n+Additional Delivery Charges");


        placeorder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                UserID=firebaseAuth.getCurrentUser().getUid();
                Map<String, Object> order = new HashMap<>();
                order.putIfAbsent("ordertype",aTitle);
                order.putIfAbsent("price",aDescription);
                db.collection("orders").add(order).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(OrderViewActivity.this, "Select Pick-up location", Toast.LENGTH_LONG).show();
                        Map<String, Object> orderid = new HashMap<>();
                        orderid.putIfAbsent("orderid",documentReference.getId());
                        db.collection("orders").document(documentReference.getId()).update(orderid);

                        Toast.makeText(OrderViewActivity.this, "Select Pick-up location", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(OrderViewActivity.this, OrderLocationActivity.class ));

                        //Toast.makeText(OrderViewActivity.this, "Select Pick-up location", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(OrderViewActivity.this, OrderLocationActivity.class ));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OrderViewActivity.this, "Order cannot be placed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderViewActivity.this, "Order Cancelled", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(OrderViewActivity.this,ItemListActivity.class));
            }
        });
    }
}
