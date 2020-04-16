package com.example.pointtopoint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RiderOtpActivity extends AppCompatActivity {

    private TextView orderid,ordertype,pickuplocation,droplocation,usernumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_otp);

        orderid=(TextView)findViewById(R.id.orderid);
        //ordertype=(TextView)findViewById(R.id.order_type);

        Intent intent = getIntent();
        orderid.setText(intent.getStringExtra("orderid"));
        //ordertype.setText(intent.getStringExtra("ordertype"));




    }
}
