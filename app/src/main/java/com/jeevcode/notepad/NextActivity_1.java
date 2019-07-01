package com.jeevcode.notepad;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class NextActivity_1 extends AppCompatActivity {

    Button logout_button;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    TextView uname;
    TextView upass;
    private String name;
    private String email;
    private static final String TAG="NextACtivity";

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_1);

        uname=(TextView)findViewById(R.id.user_name);
        upass=(TextView)findViewById(R.id.user_pass);
       // logout_button=(Button)findViewById(R.id.Logout);


        mAuth=FirebaseAuth.getInstance();
        Log.d(TAG,"entered the NEXTACTIVITY:**********************************************************");

        mAuthListener=new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth mAuth)
            {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null)
                {


                    Intent intent = new Intent(NextActivity_1.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();//finish this NextActivity_1

                    // startActivity(new Intent(NextActivity_1.this,MainActivity.class));
                    Toast.makeText(NextActivity_1.this,"Signing out!!",Toast.LENGTH_SHORT).show();
                }
                else{

                    for (UserInfo profile : user.getProviderData())
                    {
                        // Id of the provider (ex: google.com)


                        // Name, email address, and profile photo Url
                         name = profile.getDisplayName();
                         email = profile.getEmail();
                        uname.setText(name);
                        upass.setText(email);
                        //Uri photoUrl = profile.getPhotoUrl();
                    }

                }

            }
        };




//        logout_button.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick(View v)
//
//            {
//                //Toast.makeText(NextActivity_1.this,"Signing out () called!!",Toast.LENGTH_SHORT).show();
//                mAuth.signOut();
//
//
//
//            }
//        });



    }//closing of oncreate()


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logoutMenu:{
                mAuth.signOut();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
