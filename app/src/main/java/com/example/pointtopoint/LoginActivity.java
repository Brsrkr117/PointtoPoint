package com.example.pointtopoint;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText txtemail, txtpassword;
    Button btn_login;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    private TextView forgotPassword;
    private TextView Riderlogin;


    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS,Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        txtemail = (EditText) findViewById(R.id.EmailRLoginEditText);
        txtpassword = (EditText) findViewById(R.id.PasswordRLoginEditText);
        btn_login = (Button) findViewById(R.id.RLoginButton);
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
        Riderlogin = (TextView)findViewById(R.id.tvRider_login);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        if(user != null){
            finish();
            startActivity(new Intent(LoginActivity.this, SecondActivity.class));
        }



        /*mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFireBaseUser = firebaseAuth.getCurrentUser();
                if (mFireBaseUser != null){
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(LoginActivity.this,  "You are logged in",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginActivity.this,  "Please Log in",Toast.LENGTH_LONG).show();
                }
            }
        };*/

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotpasswordActivity.class));
            }
        });

        Riderlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RiderLoginActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                String email = txtemail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this,  "Please Enter Email",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this,  "Please Enter Password",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    progressDialog.dismiss();
                                    finish();
                                    startActivity(new Intent(LoginActivity.this, SecondActivity.class));

                                } else {

                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this,  "Login Failed or User not Available",Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

            }
        });

    }

    /*@Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
        FirebaseUser user = firebaseAuth.getCurrentUser();

    }*/
    public void btn_register(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
}