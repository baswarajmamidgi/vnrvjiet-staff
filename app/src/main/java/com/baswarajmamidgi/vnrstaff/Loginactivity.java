package com.baswarajmamidgi.vnrstaff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loginactivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    TextView signin;
    TextView signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("log", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent i=new Intent(Loginactivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);

                } else {
                    // User is signed out
                    Log.d("log", "onAuthStateChanged:signed_out");
                }

            }
        };
        signin= (TextView) findViewById(R.id.signin);

       signup= (TextView) findViewById(R.id.signup);




        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Loginactivity.this,Login.class);
                startActivity(it);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Loginactivity.this,Register.class);
                startActivity(it);

            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


}
