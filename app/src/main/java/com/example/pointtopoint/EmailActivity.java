package com.example.pointtopoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EmailActivity extends AppCompatActivity {

    private String OrderID;
    private Button deliveryconfirm;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        deliveryconfirm=(Button)findViewById(R.id.confirmorder);


        Intent intent=getIntent();
        OrderID=intent.getStringExtra("orderid");


        deliveryconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> usermap = new HashMap<>();
                usermap.put("orderstatus","delivered");

                firebaseAuth = FirebaseAuth.getInstance();
                db=FirebaseFirestore.getInstance();

                DocumentReference docref=db.collection("orders").document(OrderID);
                docref.update(usermap);
                Toast.makeText(EmailActivity.this, "You have confirmed Order delivery", Toast.LENGTH_LONG).show();
                finish();
                //firebaseAuth.signOut();
                startActivity(new Intent(EmailActivity.this,SecondActivity.class));
            }
        });



    }
}
