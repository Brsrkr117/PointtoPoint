package com.example.pointtopoint;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText txtFullName, txtusername, txtemail, txtpassword, txtMobileNumber;
    private RadioButton customerButton, riderButton, regularButton, irregularButton;
    private Button SignUp;
    private RadioGroup CustomerTypeGroup;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    /*@Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
        FirebaseUser user = firebaseAuth.getCurrentUser();

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtemail = (EditText) findViewById(R.id.EmailEditText);
        txtFullName = (EditText) findViewById(R.id.NameEditText);
        txtusername = (EditText) findViewById(R.id.UsernameEditText);
        txtMobileNumber = (EditText) findViewById(R.id.MobileNumberEditText);
        txtpassword = (EditText) findViewById(R.id.PasswordEditText);
        SignUp = (Button) findViewById(R.id.SignUpButton);
        customerButton = (RadioButton) findViewById(R.id.CustomerButton);
        riderButton = (RadioButton) findViewById(R.id.RiderButton);
        regularButton = (RadioButton) findViewById(R.id.RegularButton);
        irregularButton = (RadioButton) findViewById(R.id.IrregularButton);
        CustomerTypeGroup = (RadioGroup) findViewById(R.id.CustomerTypeGroup);

        //databaseReference = FirebaseDatabase.getInstance().getReference("user");

        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseAuth.signOut();

        /*mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFireBaseUser = firebaseAuth.getCurrentUser();
                if (mFireBaseUser != null) {
                    FirebaseAuth.getInstance().signOut();
                }
            }
        };*/

        CustomerTypeGroup.setVisibility(View.INVISIBLE);

        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customerButton.isChecked()) {
                    CustomerTypeGroup.setVisibility(View.VISIBLE);
                }
            }
        });

        riderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (riderButton.isChecked()) {
                    CustomerTypeGroup.setVisibility(View.INVISIBLE);
                }
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = txtemail.getText().toString().trim();
                final String password = txtpassword.getText().toString().trim();
                final String fullName = txtFullName.getText().toString();
                final String Username = txtusername.getText().toString();
                final String MobileNumber = txtMobileNumber.getText().toString();
                String Role = "";
                String CustomerType = "";

                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(SignUpActivity.this,  "Please Enter Full Name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Username)) {
                    Toast.makeText(SignUpActivity.this,  "Please Enter Username",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(MobileNumber)) {
                    Toast.makeText(SignUpActivity.this,  "Please Enter Username",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUpActivity.this,  "Please Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this,  "Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (customerButton.isChecked() == false && riderButton.isChecked() == false) {
                    Toast.makeText(SignUpActivity.this,  "Please select either Customer or Rider",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (customerButton.isChecked()) {
                    if (regularButton.isChecked() == false && irregularButton.isChecked() == false) {
                        Toast.makeText(SignUpActivity.this, "Please select either Regular or Irregular", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Role = "Customer";
                    if (regularButton.isChecked()){
                        CustomerType = "Regular";
                    } else if (irregularButton.isChecked()){
                        CustomerType = "Irregular";
                    }
                } else if (riderButton.isChecked()) {
                    Role = "Rider";
                }

                final String finalRole = Role;
                final String finalCustomerType = CustomerType;
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this,new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user details = new user(fullName, Username, MobileNumber, email, finalRole, finalCustomerType);
                            Map<String, Object> usermap = new HashMap<>();

                            usermap.putIfAbsent("name",fullName);
                            usermap.putIfAbsent("Username",Username);
                            usermap.putIfAbsent("Mobilenumber",MobileNumber);
                            usermap.putIfAbsent("Email",email);
                            usermap.putIfAbsent("Role",finalRole);
                            usermap.putIfAbsent("CustomerType",finalCustomerType);

                            final String collpath;
                            if(finalRole.equals("Rider")){
                                collpath="riders";
                            }
                            else{
                                collpath="users";
                            }

                            db.collection(collpath).add(details).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUpActivity.this, "Registration failed.Try at another time", Toast.LENGTH_SHORT).show();
                                }
                            });
                            firebaseAuth.signOut();
                            Toast.makeText(SignUpActivity.this, "Now Log in", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        }else{
                            Toast.makeText(SignUpActivity.this, "Registration failed.Use Another Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }
}