package com.jeevcode.notepad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.os.Build.VERSION_CODES.O;

public class loginActivity extends AppCompatActivity {


    TextView enter_havent_reg;
    TextView enter_forgot_pass;
    Button enter_login;
    EditText enter_pass;
    EditText enter_username;
    private FirebaseAuth firebaseAuth;

private static final String TAG="loginactivity";


    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enter_havent_reg=(TextView)findViewById(R.id.enter_havent_reg);
        enter_forgot_pass=(TextView)findViewById(R.id.enter_forgot_pass);
        enter_login=(Button)findViewById(R.id.enter_login);
        enter_pass=(EditText)findViewById(R.id.enter_pass);
        enter_username=(EditText)findViewById(R.id.enter_username);
        Log.d(TAG,"entered the loginactivity:**********************************************************");

        firebaseAuth=FirebaseAuth.getInstance();




        enter_havent_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();//finish i.e destroy  the ongoing activity after calling the next activity by intent
            }
        });

        enter_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate(enter_username.getText().toString(),enter_pass.getText().toString());
            }
        });



    }


    private void validate(String uname,String pass)

    {


        firebaseAuth.signInWithEmailAndPassword(uname,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(loginActivity.this,"Login Successful!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this, NextActivity_1.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    Toast.makeText(loginActivity.this,"Login Failed!",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }




}
