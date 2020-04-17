package com.example.pointtopoint;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
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

public class EmailActivity extends AppCompatActivity {
    private TextView tv1,tv2;
    private String OrderID;
    private Button deliveryconfirm;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String UserID;
    //private String ridername,riderusername,ridernumber,amountpayable,rideremail,usernumber;

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        //final String ridername,riderusername,ridernumber,amountpayable,rideremail,usernumber;

        deliveryconfirm=(Button)findViewById(R.id.confirmorder);

        firebaseAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        UserID=firebaseAuth.getCurrentUser().getUid();

        Intent intent=getIntent();
        OrderID=intent.getStringExtra("orderid");

        DocumentReference docref=db.collection("orders").document(OrderID);

        /*docref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                usernumber=documentSnapshot.getString("usernumber");
                ridername=documentSnapshot.getString("riderfullname");
                riderusername=documentSnapshot.getString("ridername");
                ridernumber=documentSnapshot.getString("ridernumber");
                rideremail=documentSnapshot.getString("rideremail");
                amountpayable=documentSnapshot.getString("price");
            }
        });*/
        db=FirebaseFirestore.getInstance();
        db.collection("orders").document(OrderID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc=task.getResult();

                    String ridername,riderusername,ridernumber,amountpayable,rideremail,usernumber,orderid;
                    orderid=(doc.getString("orderid"));
                    usernumber=(doc.getString("usernumber")).trim();
                    ridername=doc.getString("riderfullname");
                    riderusername=doc.getString("ridername");
                    ridernumber=doc.getString("ridernumber").trim();
                    rideremail=doc.getString("rideremail");
                    amountpayable=doc.getString("price");

                    //Toast.makeText(EmailActivity.this,usernumber,Toast.LENGTH_LONG).show();
                    //Toast.makeText(EmailActivity.this,doc.getString("username"),Toast.LENGTH_LONG).show();

                    String smsline0 ="Order ID: "+ orderid;
                    String smsline1 = "Rider name: "+ ridername;
                    String smsline2 = "Rider number: "+ ridernumber;
                    String smsline3 = "Rider mail: "+ rideremail;
                    String smsline4 = "Amount Payable:Rs "+ amountpayable;


                    String smsMessage =smsline0+ "\n" +smsline1 + "\n" + smsline2+ "\n" + smsline3+ "\n" + smsline4;


                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(usernumber, null,smsMessage, null, null);
                }

            }
        });

        //Toast.makeText(EmailActivity.this,usernumber,Toast.LENGTH_LONG).show();

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


        //sendotp.setEnabled(false);

        //Toast.makeText(EmailActivity.this,usernumber,Toast.LENGTH_LONG).show();

        /*String phoneNumber = ridernumber;
        //Random rand = new Random();
        //String otp=Integer.toString((rand.nextInt((99999 - 10000) + 1) + 10000));
        String smsline1 = "Rider name: "+ ridername;
        String smsline2 = "Rider number: "+ ridernumber;
        String smsline3 = "Rider mail: "+ rideremail;
        String smsline4 = "Amount Payable:Rs "+ amountpayable;*/

        //String smsMessage = "12345";
        //String smsMessage =smsline1 + "\n" + smsline2+ "\n" + smsline3+ "\n" + smsline4;

        /*if(checkPermission(Manifest.permission.SEND_SMS)){
            //sendotp.setEnabled(true);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(usernumber, null,smsline1 , null, null);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);

        }*/


        /*SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(usernumber, null,smsMessage , null, null);*/

    }


}
