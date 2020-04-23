package com.example.pointtopoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserOtpActivity extends AppCompatActivity {
    private EditText otp;
    private TextView tv1,tv2;
    private Button confirm,cancel;
    private FirebaseFirestore db,db1,db2,db3;
    private FirebaseAuth firebaseAuth;

    private String OrderID;
    private String compareotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_otp);

        Intent intent=getIntent();
        OrderID=intent.getStringExtra("orderid");

        tv1=(TextView) findViewById(R.id.tv1);
        tv2=(TextView) findViewById(R.id.tv2);
        otp=(EditText) findViewById(R.id.etotp);
        confirm=(Button)findViewById(R.id.btnconfirm);
        cancel=(Button)findViewById(R.id.btncancel);

        //Toast.makeText(UserOtpActivity.this, OrderID, Toast.LENGTH_LONG).show();
        db=FirebaseFirestore.getInstance();
        final DocumentReference docref=db.collection("orders").document(OrderID);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=FirebaseFirestore.getInstance();

                db.collection("orders").document(OrderID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc=task.getResult();
                            compareotp=doc.getString("orderotp");
                            String receivedotp=otp.getText().toString();
                            if (receivedotp.isEmpty()){
                                Toast.makeText(UserOtpActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                            }
                            else if(compareotp.equals(receivedotp)){
                                db3=FirebaseFirestore.getInstance();
                                DocumentReference docref3=db3.collection("orders").document(OrderID);

                                Map<String, Object> usermap = new HashMap<>();
                                usermap.put("orderstatus","confirmed");

                                docref3.update(usermap);
                                Toast.makeText(UserOtpActivity.this, "Correct OTP", Toast.LENGTH_LONG).show();

                                finish();
                                Intent intent = new Intent(getApplicationContext(), EmailActivity.class);
                                intent.putExtra("orderid",OrderID);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(UserOtpActivity.this, "Incorrect OTP", Toast.LENGTH_LONG).show();
                            }


                        }
                    }
                });






            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db2=FirebaseFirestore.getInstance();
                DocumentReference docref2=db2.collection("orders").document(OrderID);


                Map<String, Object> usermap = new HashMap<>();
                usermap.put("orderstatus","cancelled");

                docref2.update(usermap);

                Toast.makeText(UserOtpActivity.this, "Order cancelled", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UserOtpActivity.this, SecondActivity.class));

            }
        });
    }
}
