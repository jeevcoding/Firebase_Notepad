package com.jeevcode.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.os.Build.VERSION_CODES.O;

public class loginActivity extends AppCompatActivity {


    TextView enter_havent_reg;
    TextView enter_forgot_pass;
    Button enter_login;
    EditText enter_pass;
    EditText enter_username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enter_havent_reg=(TextView)findViewById(R.id.enter_havent_reg);
        enter_forgot_pass=(TextView)findViewById(R.id.enter_forgot_pass);
        enter_login=(Button)findViewById(R.id.enter_login);
        enter_pass=(EditText)findViewById(R.id.enter_pass);
        enter_username=(EditText)findViewById(R.id.enter_username);




        enter_havent_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
}
