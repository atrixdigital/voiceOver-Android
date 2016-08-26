package com.maximus.voicemessenger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by Muhammad on 23/08/2016.
 */
public class Requests extends AppCompatActivity{

    private ListView lv;

    TextView tit;

    Firebase firebase_requsers ;

    Firebase firebase_registeredusers ;

    String name,user,pass;

    ArrayAdapter<String> mAdapter ;
   ArrayList<String> usersList = new ArrayList<String>();
    ArrayList <User> Users=new ArrayList<User>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.requests);
        lv= (ListView) findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersList);
        lv.setAdapter(mAdapter);

        tit=(TextView) findViewById(R.id.textView2);

        Firebase.setAndroidContext(this);
        firebase_requsers = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Requesting_Users/");
        firebase_registeredusers = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Registered_Users/");


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View v, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Requests.this);
                builder.setTitle(parent.getItemAtPosition(position).toString());
                mAdapter.notifyDataSetChanged();






                builder.setItems(new CharSequence[]
                                {"Accept", "Remove"},

                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                switch (which)
                                {
                                    case 0:
                                        firebase_requsers.child((parent.getItemAtPosition(position).toString())).addValueEventListener(new ValueEventListener() {



                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot)
                                            {


                                                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                                {

                                                    name = dataSnapshot.child("name").getValue(String.class);
                                                    pass = dataSnapshot.child("password").getValue(String.class);
                                                    user = dataSnapshot.child("username").getValue(String.class);



                                                    try {

                                                        if(user.equals(parent.getItemAtPosition(position).toString()))
                                                        {

                                                            firebase_registeredusers.child((parent.getItemAtPosition(position).toString())).setValue(new User(name, user, pass));
                                                            firebase_requsers.child((parent.getItemAtPosition(position)).toString()).removeValue();
                                                            Toast.makeText(Requests.this,"User Approved", Toast.LENGTH_SHORT).show();
                                                            mAdapter.notifyDataSetChanged();
                                                        }
                                                    } catch (NullPointerException d) {
                                                        Toast.makeText(Requests.this, d.toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled(FirebaseError firebaseError) {

                                            }
                                            });

                                        break;



                                    case 1:
                                        firebase_requsers.child((parent.getItemAtPosition(position)).toString()).removeValue();
                                        mAdapter.notifyDataSetChanged();

                                        break;


                                    default:
                                        break;
                                }
                            }
                        });
                builder.create().show();

            }
        });


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        firebase_requsers.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                usersList.clear();

                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext())
                {
                    usersList.add(((DataSnapshot) i.next()).getKey());

                }
                if(usersList.isEmpty())
                {
                    tit.setText("No user to approve");
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
