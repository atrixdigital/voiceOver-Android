package com.maximus.voicemessenger;

/**
 * Created by Muhammad on 28/08/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class Online_Users extends AppCompatActivity {


    ListView listView;
    ArrayAdapter<String> mAdapter = null;
    ArrayList<String> usersList = new ArrayList<String>();

    Firebase firebase_onlineusers ;
    String LoggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_users);
        listView = (ListView) findViewById(R.id.listview);
        firebase_onlineusers = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Registered_Users/");


        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersList);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
       firebase_onlineusers.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

               //usersList.clear();

                Iterator i = dataSnapshot.getChildren().iterator();


                usersList.clear();

                while (i.hasNext())
                {
                    usersList.add(((DataSnapshot) i.next()).getKey());

                }
                if(usersList.isEmpty())
                {
                   // tit.setText("No user to approve");
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        onListItemClick();
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        firebase_onlineusers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                s = dataSnapshot.getValue(String.class);
                Log.v("VALUE", s);
                usersList.add(s);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        onListItemClick();
    }*/

    private void onListItemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String to_user = (String) listView.getItemAtPosition(i);
                Intent lastintent = getIntent();
             //   String from_user = lastintent.getStringExtra("FROM_USER");
               // LoggedInUser = lastintent.getStringExtra("LOG_IN_USER");
//                Log.v("from_user", from_user);
                Intent intent = new Intent(Online_Users.this, Inbox.class);
                //intent.putExtra("FROM_USER", from_user);
                intent.putExtra("TO_USER", to_user);
              //  intent.putExtra("LOG_IN_USER", LoggedInUser);
                startActivity(intent);


            }
        });
    }
}
