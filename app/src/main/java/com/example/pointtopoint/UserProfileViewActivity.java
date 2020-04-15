package com.example.pointtopoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserProfileViewActivity extends AppCompatActivity {
    private TextView profileName, profileUsername, profileEmail,profileMobileNumber;
    private Button profileUpdate, changePassword,goback;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_view);

        profileName = findViewById(R.id.tvProfileName);
        profileUsername = findViewById(R.id.tvProfileUsername);
        profileEmail = findViewById(R.id.tvProfileEmail);
        profileMobileNumber = findViewById(R.id.tvMobileNumber);
        profileUpdate = findViewById(R.id.btnProfileUpdate);
        changePassword = findViewById(R.id.btnChangePassword);
        goback = findViewById(R.id.btnback);

        firebaseAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        UserID=firebaseAuth.getCurrentUser().getUid();

        DocumentReference docref=db.collection("users").document(UserID);

        docref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                profileName.setText("Name: " + documentSnapshot.getString("name"));
                profileUsername.setText("Username: " + documentSnapshot.getString("Username"));
                profileMobileNumber.setText("Mobile Number: " + documentSnapshot.getString("Mobilenumber"));
                profileEmail.setText("Email: " + documentSnapshot.getString("Email"));
            }
        });


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(UserProfileViewActivity.this, SecondActivity.class));
            }
        });


        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(UserProfileViewActivity.this, ProfileActivity.class));
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileViewActivity.this, ChangePasswordActivity.class));
            }
        });



    }
}
