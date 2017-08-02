package com.baswarajmamidgi.vnrstaff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText username;
    private EditText password;
    private TextView forgotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        forgotpassword= (TextView) findViewById(R.id.forgotpassword);
        TextView signin= (TextView) findViewById(R.id.newsignin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                dialog.setCancelable(false);
                dialog.setTitle("Forgot password?");
                final String email=username.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Enter the registered the email", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.setMessage(" New password  request will be sent to "+ email+".Do you want to continue? ");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mAuth.sendPasswordResetEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Login.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Login.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });                    }
                })
                        .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Action for "Cancel".
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });



    }

    public void loginuser()
    {

        final String name=username.getText().toString();
        String pass=password.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "enter Login id", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<6)
        {
            Toast.makeText(this, "Password length should be atlest 6 chars", Toast.LENGTH_SHORT).show();
            return;
        }





        mAuth.signInWithEmailAndPassword(name,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Login.this, "Login success", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {

                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
