package com.example.pointtopoint;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RiderOtpActivity extends AppCompatActivity {

    private TextView orderid,ordertype,pickuplocation,droplocation,usernumber,username,userfullname,price,useremail;
    private Button sendotp;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String OrderID;
    private String UserID;
    private String auserfullname,ausername,auserid,ausernumber,auseremail;

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_otp);

        orderid=(TextView)findViewById(R.id.orderid);

        userfullname=(TextView)findViewById(R.id.userfullname);
        username=(TextView)findViewById(R.id.username);
        usernumber=(TextView)findViewById(R.id.usernumber);
        useremail=(TextView)findViewById(R.id.useremail);
        ordertype=(TextView)findViewById(R.id.ordertype);
        price=(TextView)findViewById(R.id.price);

        sendotp=(Button)findViewById(R.id.send);


        Intent intent = getIntent();
        orderid.setText(intent.getStringExtra("orderid"));


        firebaseAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        UserID=firebaseAuth.getCurrentUser().getUid();
        OrderID=intent.getStringExtra("orderid");

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


        docref=db.collection("orders").document(OrderID);

        docref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                userfullname.setText(documentSnapshot.getString("userfullname"));
                username.setText(documentSnapshot.getString("username"));
                usernumber.setText(documentSnapshot.getString("usernumber"));
                useremail.setText(documentSnapshot.getString("useremail"));
                ordertype.setText(documentSnapshot.getString("ordertype"));
                price.setText("To be paid: Rs " + documentSnapshot.getString("price"));
            }
        });


        sendotp.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)){
            sendotp.setEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }


    }

    public void onSend(View v){
        String phoneNumber = usernumber.getText().toString().trim();
        Random rand = new Random();
        String otp=Integer.toString((rand.nextInt((99999 - 10000) + 1) + 10000));
        String smsMessage = "Your OTP:" + otp;


        if(phoneNumber == null || phoneNumber.length() == 0 ||
                smsMessage == null || smsMessage.length() == 0){
            return;
        }

        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, smsMessage, null, null);

            Map<String, Object> usermap = new HashMap<>();
            usermap.put("orderotp",otp);
            usermap.put("riderfullname",auserfullname);
            usermap.put("ridername",ausername);
            usermap.put("ridernumber",ausernumber);
            usermap.put("riderid",auserid);
            usermap.put("rideremail",auseremail);

            DocumentReference docref=db.collection("orders").document(OrderID);
            docref.update(usermap);

            firebaseAuth = FirebaseAuth.getInstance();
            db=FirebaseFirestore.getInstance();


            Toast.makeText(this, "message sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


}
