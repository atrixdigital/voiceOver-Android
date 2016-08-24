package com.maximus.voicemessenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Muhammad on 22/08/2016.
 */
public class Register extends AppCompatActivity {

    private EditText inputusername, inputPassword,inputname, repeatpass;
    private Button btnSignIn, btnSignUp;

    public String id;
    Firebase firebase_requsers = null;
    DatabaseReference ref= FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);
        firebase_requsers = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Requesting_Users/");


        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputname = (EditText) findViewById(R.id.name);
        inputusername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        repeatpass = (EditText) findViewById(R.id.checkpassword);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputname.getText().toString().equals("") || inputusername.getText().toString().equals("") || inputPassword.getText().toString().equals("") || repeatpass.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please Check All fields", Toast.LENGTH_SHORT).show();
                }


                else if (!inputPassword.getText().toString().equals(repeatpass.getText().toString())) {
                    Toast.makeText(Register.this, "Your Password doesnt match", Toast.LENGTH_SHORT).show();
                }


                else {

               Toast.makeText(Register.this, "Successfully registered. Please Log In to continue", Toast.LENGTH_SHORT).show();
                ref.child("Voice_Users").child("Requesting_Users").child(inputusername.getText().toString()).setValue(new User( inputusername.getText().toString(),inputname.getText().toString(), inputPassword.getText().toString()));


                inputname.setText("");
                //user.setText("");
                inputPassword.setText("");

                inputusername.setText("");
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

            }
    });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);


            }
        });
    }
}


