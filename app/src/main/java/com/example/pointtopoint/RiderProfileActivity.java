package com.example.pointtopoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class RiderProfileActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mFullName;
    private EditText mMobileNumber;
    private EditText mEmail;
    private Button UpdateButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_profile);

        mUsername = findViewById(R.id.mUsername);
        mFullName = findViewById(R.id.mFullName);
        mMobileNumber = findViewById(R.id.mMobileNumber);
        //mEmail = findViewById(R.id.mEmail);
        UpdateButton = findViewById(R.id.UpdateButton);

        firebaseAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        UserID=firebaseAuth.getCurrentUser().getUid();

        DocumentReference docref=db.collection("riders").document(UserID);

        docref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mFullName.setText(documentSnapshot.getString("name"));
                mUsername.setText(documentSnapshot.getString("Username"));
                mMobileNumber.setText(documentSnapshot.getString("Mobilenumber"));
                //profileEmail.setText("Email: " + documentSnapshot.getString("Email"));
            }
        });

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newfullname=mFullName.getText().toString();
                String newusername=mUsername.getText().toString();
                String newmobilenumber=mMobileNumber.getText().toString();

                Map<String, Object> usermap = new HashMap<>();

                usermap.put("name",newfullname);
                usermap.put("Username",newusername);
                usermap.put("Mobilenumber",newmobilenumber);

                firebaseAuth = FirebaseAuth.getInstance();
                db=FirebaseFirestore.getInstance();
                UserID=firebaseAuth.getCurrentUser().getUid();
                DocumentReference docref=db.collection("riders").document(UserID);

                docref.update(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RiderProfileActivity.this, "Updated successfully", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(RiderProfileActivity.this, RiderProfileViewActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RiderProfileActivity.this, "Update unsuccessful", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }
}
