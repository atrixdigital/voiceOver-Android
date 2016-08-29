package com.maximus.voicemessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addEmp extends AppCompatActivity {

    EditText name,user,pass,conpass;
    Button add,reset;
    Firebase firebase_registeredusers = null;

    DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);

        name=(EditText)findViewById(R.id.name);
        user=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.password);
        conpass=(EditText)findViewById(R.id.checkpassword);

        add=(Button) findViewById(R.id.add);
        firebase_registeredusers = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Registered_Users/");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
                {

                if (name.getText().toString().equals("") || user.getText().toString().equals("") || pass.getText().toString().equals("") || conpass.getText().toString().equals("")) {
                    Toast.makeText(addEmp.this, "Please Check All fields", Toast.LENGTH_SHORT).show();
                }


                else if (!pass.getText().toString().equals(conpass.getText().toString())) {
                    Toast.makeText(addEmp.this, "Your Password doesnt match", Toast.LENGTH_SHORT).show();
                }


                else {

                    Toast.makeText(addEmp.this, "User Successfully Added", Toast.LENGTH_SHORT).show();
                    ref.child("Voice_Users").child("Registered_Users").child(user.getText().toString()).setValue(new User( name.getText().toString(),user.getText().toString(), pass.getText().toString()));


                    name.setText("");
                    //user.setText("");
                    pass.setText("");

                    user.setText("");
                    Intent i = new Intent(getApplicationContext(), Admin_Pannel.class);
                    startActivity(i);
                }

            }
        });


    }
}
