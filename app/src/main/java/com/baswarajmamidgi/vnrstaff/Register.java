package com.baswarajmamidgi.vnrstaff;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText username;
    EditText email;
    EditText password;
    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        email= (EditText) findViewById(R.id.mail);
        signin= (TextView) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createuser();
            }
        });

    }

    public void createuser(){

        final String name=username.getText().toString();
        String pass=password.getText().toString();
        String mail=email.getText().toString();

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

        if(TextUtils.isEmpty(mail))
        {
            Toast.makeText(this, "enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("log", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "failed",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("log", "Email sent.");
                            }
                        }
                    });
        }
    }
}
