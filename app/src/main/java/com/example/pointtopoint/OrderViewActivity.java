package com.example.pointtopoint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
        //final String auserfullname,ausername,auserid,ausernumber,auseremail;

        title.setText(aTitle);
        description.setText("Rs" + aDescription + "\n+Additional Delivery Charges");

        placeorder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderViewActivity.this, "Select Pick-up location", Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(getApplicationContext(), OrderLocationActivity.class);
                //intent.putExtra("ordertype",aTitle );
                //intent.putExtra("price", aDescription);


                Bundle bundle =new Bundle();
                bundle.putString("ordertype",aTitle);
                bundle.putString("price",aDescription);

                intent.putExtras(bundle);

                startActivity(intent);

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
