package com.jeevcode.notepad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {


    private static final String TAG="MainActivity!1";

    private TextView already_reg;
    private Button google_button;
    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN=123;//this is a request code
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mAuthListener;
    private EditText u_mail;
    private EditText u_pass;
    Button reg_button;



    @Override
    protected void onStart() {
        super.onStart();


        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //the below line is very imp.....
        mAuth=FirebaseAuth.getInstance();


        already_reg=(TextView)findViewById(R.id.already_reg);
        google_button=(Button)findViewById(R.id.google_button);
        u_mail=(EditText)findViewById(R.id.u_mail);
        u_pass=(EditText)findViewById(R.id.u_pass);
        reg_button=(Button)findViewById(R.id.reg_button);


        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate())
                {
                    String emailOfUser=u_mail.getText().toString().trim();
                    String passOfUser=u_pass.getText().toString().trim();



                    mAuth.createUserWithEmailAndPassword(emailOfUser,passOfUser).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                            Toast.makeText(MainActivity.this,"Registration Successful!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, NextActivity_1.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {
                                    Log.e(TAG,"onComplete failed=="+task.getException().getMessage());
                                Toast.makeText(MainActivity.this,"Registration Unsuccessful! Please Login if you already have an account!",Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"Validation unsuccessful!",Toast.LENGTH_SHORT).show();
                }

            }
        });




        //Imp part starts here...........................
        //this is where we start the authlistener to see if the user is already signed in or now...


        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth mAuth) {


                if (mAuth.getCurrentUser()!=null)//if the user is logged in,we redirect user from one intent to another one.
                {
                    Toast.makeText(MainActivity.this,"You are already signed in",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, NextActivity_1.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    //startActivity(new Intent(MainActivity.this,NextActivity_1.class));
                }

            }
        };



        google_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });



        already_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }//closing of oncreate().






    private Boolean validate(){
        Boolean result=false;
        String mail_u=u_mail.getText().toString();
        String pass_u=u_pass.getText().toString();

        if(mail_u.isEmpty() || pass_u.isEmpty())
        {
            Toast.makeText(MainActivity.this,"Please enter the details!",Toast.LENGTH_SHORT).show();

        }
        else{
            result= true;
        }

        return result;
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN); //RC stands for request code which you have to declare up.
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                // Google Sign In was successful, authenticate with Firebase

                GoogleSignInAccount account = task.getResult(ApiException.class);//this account willl have all the authentication info required.
                firebaseAuthWithGoogle(account);


            } catch (ApiException e) {

                // Google Sign In failed, update UI appropriately

                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(MainActivity.this, "Authentication went wrong!", Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }








    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

   //the above object is the firebase user object.In this object all the user info is kept,eg name,emailid,etc

                            if (user!=null)//if the user is logged in,we redirect user from one intent to another one.
                            {

                                Intent intent = new Intent(MainActivity.this, NextActivity_1.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

//                                startActivity(new Intent(MainActivity.this,NextActivity_1.class));
                            }

                            Toast.makeText(MainActivity.this,"Successful Sign in!",Toast.LENGTH_SHORT).show();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            Toast.makeText(MainActivity.this,"Authenicattion failed!!",Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }


                    }
                });

    }













}
