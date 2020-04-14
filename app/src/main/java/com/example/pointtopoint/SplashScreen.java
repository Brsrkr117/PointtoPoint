package com.example.pointtopoint;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    /*TextView dubstep, dabba;
    ImageView logo;*/

    private static int SPLASH_SCREEN = 4000; //4 Ssecs

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*dubstep = findViewById(R.id.dubstepText);
        dabba = findViewById(R.id.dabbaText);
        logo = findViewById(R.id.logoImage);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        }, SPLASH_SCREEN);
    }
}
