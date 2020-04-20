package com.example.pointtopoint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RiderSecondActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button signout;
    private Button Viewprofile;
    private Button Vieworders;
    private EditText radius;
    private String UserID;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_second);

        firebaseAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        UserID=firebaseAuth.getCurrentUser().getUid();
        signout=(Button)findViewById(R.id.buttonRsignout);
        Viewprofile=(Button) findViewById(R.id.btnviewprof);
        Vieworders=(Button) findViewById(R.id.btnViewOrder);
        radius=(EditText) findViewById(R.id.etradius);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        Viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RiderSecondActivity.this, RiderProfileViewActivity.class));
            }
        });

        Vieworders.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String rad=radius.getText().toString();
                if(rad.isEmpty()){
                    Toast.makeText(RiderSecondActivity.this, "Enter radius", Toast.LENGTH_SHORT).show();
                }
                else{
                    db=FirebaseFirestore.getInstance();

                    Map<String, Object> usermap = new HashMap<>();
                    usermap.putIfAbsent("radius",rad);

                    db.collection("riders").document(UserID).update(usermap);

                    Bundle bundle = new Bundle();
                    bundle.putString("radius",rad);

                    Intent intent = new Intent(getApplicationContext(), RiderViewOrderActivity.class);
                    intent.putExtras(bundle);
                    //finish();
                    startActivity(intent);
                }


            }
        });

    }
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(RiderSecondActivity.this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                finish();
                startActivity(new Intent(RiderSecondActivity.this, RiderProfileViewActivity.class));
                break;
            case R.id.nav_logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(RiderSecondActivity.this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);

    }



}
