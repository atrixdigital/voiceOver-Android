package com.maximus.voicemessenger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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

public class senderlist extends AppCompatActivity {


    private ListView lv;

    TextView tit;

    Firebase firebase_registeredusers ;



    ArrayAdapter mAdapter ;
    ArrayList<String> usersList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remover_users);

        lv= (ListView) findViewById(R.id.list);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, usersList);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(mAdapter);

        tit=(TextView) findViewById(R.id.tit);

        Firebase.setAndroidContext(this);
        firebase_registeredusers = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Registered_Users/");


        lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked)
            {
                mode.setTitle(lv.getCheckedItemCount() + " Selected");


            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

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
                    tit.setText("No user to select");
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


}
