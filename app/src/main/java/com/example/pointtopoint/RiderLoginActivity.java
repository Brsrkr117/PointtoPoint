package com.example.pointtopoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RiderLoginActivity extends AppCompatActivity {
    private EditText email,password;
    private Button Rlogin;
    private TextView forgotPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_login);

        email = (EditText) findViewById(R.id.EmailRLoginEditText);
        password = (EditText) findViewById(R.id.PasswordRLoginEditText);

        Rlogin = (Button) findViewById(R.id.RLoginButton);
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(RiderLoginActivity.this, RiderSecondActivity.class));
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RiderLoginActivity.this, ForgotpasswordActivity.class));
            }
        });

        Rlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(RiderLoginActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                String Remail = email.getText().toString().trim();
                String Rpassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(Remail)) {
                    Toast.makeText(RiderLoginActivity.this,  "Please Enter Email",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(Rpassword)) {
                    Toast.makeText(RiderLoginActivity.this,  "Please Enter Password",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(Remail, Rpassword).addOnCompleteListener(RiderLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(RiderLoginActivity.this, RiderSecondActivity.class));

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RiderLoginActivity.this,  "Login Failed or User not Available",Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });





    }
}
