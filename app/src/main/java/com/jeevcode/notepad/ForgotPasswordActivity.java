package com.jeevcode.notepad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email_forgot;
    Button reset_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_forgot=(EditText)findViewById(R.id.email_forgot);
        reset_password=(Button)findViewById(R.id.reset_password);
        mAuth=FirebaseAuth.getInstance();


        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String useremail=email_forgot.getText().toString().trim();

                if(useremail.equals(""))
                {
                    Toast.makeText(ForgotPasswordActivity.this,"Please enter your registered email id",Toast.LENGTH_SHORT).show();

                }else {

                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if (task.isSuccessful())
                            {
                                Toast.makeText(ForgotPasswordActivity.this,"Password reset email sent",Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(ForgotPasswordActivity.this, loginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else{

                                Toast.makeText(ForgotPasswordActivity.this,"Error in sending password reset email.You haven't entered registered email1",Toast.LENGTH_SHORT).show();


                            }

                        }
                    });
                }
            }
        });

    }
}
