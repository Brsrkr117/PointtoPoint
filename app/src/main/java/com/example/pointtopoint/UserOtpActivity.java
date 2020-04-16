package com.example.pointtopoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class UserOtpActivity extends AppCompatActivity {
    private EditText otp;
    private Button confirm,cancel;
    private FirebaseFirestore db;
    private String OrderID;
    private String compareotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_otp);

        Intent intent=getIntent();
        OrderID=intent.getStringExtra("orderid");

        otp=(EditText) findViewById(R.id.etotp);
        confirm=(Button)findViewById(R.id.btnconfirm);
        cancel=(Button)findViewById(R.id.btncancel);

        final DocumentReference docref=db.collection("orders").document(OrderID);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docref.addSnapshotListener(UserOtpActivity.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        compareotp=documentSnapshot.getString("orderotp");
                        String receivedotp=otp.getText().toString();
                        if(compareotp.equals(receivedotp)){

                            Map<String, Object> usermap = new HashMap<>();
                            usermap.put("orderstatus","confirmed");

                            docref.update(usermap);
                            Toast.makeText(UserOtpActivity.this, "Correct otp", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(UserOtpActivity.this, "Incorrect otp", Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> usermap = new HashMap<>();
                usermap.put("orderstatus","cancelled");

                docref.update(usermap);

                Toast.makeText(UserOtpActivity.this, "Order cancelled", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UserOtpActivity.this, ItemListActivity.class));


            }
        });








    }
}
