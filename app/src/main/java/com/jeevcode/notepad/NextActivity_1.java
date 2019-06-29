package com.jeevcode.notepad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class NextActivity_1 extends AppCompatActivity {

    Button logout_button;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_1);

        logout_button=(Button)findViewById(R.id.Logout);
        mAuth=FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(NextActivity_1.this,MainActivity.class));
                    Toast.makeText(NextActivity_1.this,"Signing out!!",Toast.LENGTH_SHORT).show();
                }

            }
        };






        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NextActivity_1.this,"Signing out () called!!",Toast.LENGTH_SHORT).show();
                mAuth.signOut();



            }
        });



    }
}
