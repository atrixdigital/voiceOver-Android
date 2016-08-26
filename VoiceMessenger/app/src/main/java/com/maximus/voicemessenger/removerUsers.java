package com.maximus.voicemessenger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class removerUsers extends AppCompatActivity {

    private ListView lv;

    TextView tit;

    Firebase firebase_registeredusers ;

    String name,user,pass;

    ArrayAdapter<String> mAdapter ;
    ArrayList<String> usersList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remover_users);

        lv= (ListView) findViewById(R.id.list);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersList);
        lv.setAdapter(mAdapter);

        tit=(TextView) findViewById(R.id.tit);

        Firebase.setAndroidContext(this);
        firebase_registeredusers = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Registered_Users/");


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View v, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(removerUsers.this);
                builder.setTitle(parent.getItemAtPosition(position).toString());
                mAdapter.notifyDataSetChanged();






                builder.setItems(new CharSequence[]
                                { "Remove","Cancel"},

                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                switch (which)
                                {
                                    case 0:

                                        firebase_registeredusers.child((parent.getItemAtPosition(position)).toString()).removeValue();
                                        mAdapter.notifyDataSetChanged();

                                        break;



                                    case 1:


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
        firebase_registeredusers.addValueEventListener(new ValueEventListener()
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
