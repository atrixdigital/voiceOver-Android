package com.maximus.voicemessenger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Muhammad on 23/08/2016.
 */
public class Requests extends AppCompatActivity{

    private ListView lv;

    Firebase firebase_requsers = null;
    Firebase RemoveRef=null;
    Firebase firebase_registeredusers = null;



    ArrayAdapter<String> mAdapter = null;
   ArrayList<String> usersList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.requests);
        lv= (ListView) findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersList);
        lv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    // RemoveRef = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Requesting_Users/");
       // RemoveRef.removeValue();
        Firebase.setAndroidContext(this);
        firebase_requsers = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Requesting_Users/");
        firebase_registeredusers = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Registered_Users/");





    }

    @Override
    protected void onStart() {
        super.onStart();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View v, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Requests.this);
                builder.setTitle(""+parent.getItemAtPosition(position));
                mAdapter.notifyDataSetChanged();

                //builder.setMessage(""
                //      +parent.getItemAtPosition(position));
                //  RemoveRef = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Requesting_Users/");

                builder.setItems(new CharSequence[]
                                {"Accept", "Remove"},

                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Toast.makeText(Requests.this, "Request Accepted",Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        firebase_requsers.child((parent.getItemAtPosition(position)).toString()).removeValue();
                                        mAdapter.notifyDataSetChanged();
                                        Toast.makeText(Requests.this, "Request Removed",Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });
                builder.create().show();

            }
        });
        firebase_requsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator i = dataSnapshot.getChildren().iterator();

                usersList.clear();


                while (i.hasNext()) {
                    usersList.add(((DataSnapshot) i.next()).getKey());

                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
