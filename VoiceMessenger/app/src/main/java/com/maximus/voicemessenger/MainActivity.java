package com.maximus.voicemessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.firebase.client.Firebase;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    Button login,reg;
    RadioButton user,admin;
    public static String username_editext;
    public ArrayList<String> temp = new ArrayList<String>();


    String radio="user";
    Firebase firebase_registeredusers ;
    Firebase firebase_requsers ;
    String user_name;
    EditText username,password;

    DatabaseReference ref= FirebaseDatabase.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login=(Button)findViewById(R.id.btn_login);
        reg=(Button)findViewById(R.id.button);
        username=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
       user=(RadioButton)findViewById(R.id.radioButton2);
        admin=(RadioButton)findViewById(R.id.radioButton);

        Firebase.setAndroidContext(this);

        firebase_registeredusers = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Registered_Users/");
        firebase_requsers = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Requesting_Users/");





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radio.equals("user"))
                {
                    firebase_registeredusers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Iterator i = dataSnapshot.getChildren().iterator();

                            Set<String> set = new HashSet<String>();

                            while (i.hasNext())
                            {
                                set.add(((DataSnapshot) i.next()).getKey());
                            }


                            temp.addAll(set);
                            username_editext = username.getText().toString().trim();

                            for (int f = 0; f < temp.size(); f++)
                            {

                                user_name=dataSnapshot.child(temp.get(f)).child("name").getValue(String.class);


                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {

                                if (username_editext.equals(user_name)) {

                                    startActivity(new Intent(MainActivity.this, AdminPannel.class).putExtra("LOG_IN_USER", username_editext));
                                    Toast.makeText(MainActivity.this, "You are Online", Toast.LENGTH_SHORT).show();

                                    username.setText("");
                                    password.setText("");

                                    break;

                                }
                            }
                        }
                        }


                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }


                if (radio.equals("admin"))
                {
                   if((username.getText().toString().equals(getResources().getString(R.string.adminusername)))&&(password.getText().toString().equals(getResources().getString(R.string.adminpass))))
                   {
                       Intent i=new Intent(getApplicationContext(),Admin_Pannel.class);
                       startActivity(i);
                       overridePendingTransition(R.animator.push_left_in,R.animator.push_up_out);
                   }
                    else
                   {
                       Toast t = Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_SHORT);
                       t.show();
                   }


                }

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Register.class);
                startActivity(i);
            }
        });




    }


    public void radiocheck(View view) {

        switch(view.getId()) {
            case R.id.radioButton2:
                radio="user";
                    break;
            case R.id.radioButton:
                radio="admin";
                    break;
        }
    }
}
