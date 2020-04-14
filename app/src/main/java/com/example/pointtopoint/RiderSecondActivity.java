package com.example.pointtopoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RiderSecondActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_second);


        firebaseAuth = FirebaseAuth.getInstance();
        signout=(Button)(Button) findViewById(R.id.buttonRsignout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(RiderSecondActivity.this, LoginActivity.class));
    }
}
