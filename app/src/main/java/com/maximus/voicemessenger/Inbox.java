package com.maximus.voicemessenger;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Muhammad on 27/08/2016.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Inbox extends AppCompatActivity {

    private static final int REQUEST_RUNTIME_PERMISSION = 1;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;

    private String mFileName=null;
    private final String LOG_TAG= "RECORD_LOG";

    private StorageReference mStorage;
    private ProgressDialog mprogress;

    EditText editText;
    ImageButton sendbutton;

    ArrayList<MsgKey> chatmsgsList = new ArrayList<MsgKey>();
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<MsgKey, ChatMessageViewHolder> mFirebaseAdapter1 = null;
    private FirebaseRecyclerAdapter<MsgKey, ChatMessageViewHolder> mFirebaseAdapter2 = null;
    Firebase firebase_chatnode ;
    Firebase ref_chatchildnode1 = null;
    Firebase ref_chatchildnode2 = null;
    String from_user, to_user, newmsg, LoggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        Firebase.setAndroidContext(this);

        mStorage= FirebaseStorage.getInstance().getReference();
        firebase_chatnode = new Firebase("https://voice-messenger-892c8.firebaseio.com/Voice_Users/Chats/");




        sendbutton = (ImageButton) findViewById(R.id.imageButton);
        Intent startingintent = getIntent();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutmgr = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutmgr);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mprogress = new ProgressDialog(this);

       // from_user = startingintent.getStringExtra("FROM_USER");
        to_user = startingintent.getStringExtra("TO_USER");
       // LoggedInUser = startingintent.getStringExtra("LOG_IN_USER");
from_user= "sohail";
        LoggedInUser="sohailllll";
        setTitle(to_user);
        mFileName= Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName+="/recording.mp3";

        ref_chatchildnode1 = firebase_chatnode.child(from_user + " " + to_user);

        ref_chatchildnode2 = firebase_chatnode.child(to_user + " " + from_user);
       sendbutton.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    startRecording();

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    MsgKey k = new MsgKey();
                    k.setSender(LoggedInUser);
                    k.setMessage("1");
                    ref_chatchildnode1.push().setValue(k);
                    ref_chatchildnode2.push().setValue(k);

                    stopRecording();

                }
                return false;
            }
        });



        ref_chatchildnode1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               // MsgKey chatmsg = dataSnapshot.getValue(MsgKey.class);
              //  chatmsgsList.add(chatmsg);
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
        ref_chatchildnode2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               // MsgKey chatmsg = dataSnapshot.getValue(MsgKey.class);
               // chatmsgsList.add(chatmsg);
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


    }
    private void startRecording()
    {

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try
        {
            mRecorder.prepare();
        } catch (IOException e)
        {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();

    }

    private void stopRecording()
    {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        uploadaudio();
    }
    private void uploadaudio()
    {
        mprogress.setMessage("Uploading Audio....");
        mprogress.show();


        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("audio/mp3")
                .build();

// Upload the file and metadata
        //uploadTask = storageRef.child("images/mountains.jpg").putFile(file, metadata);



        final StorageReference filepath= mStorage.child("Audio").child("My_Audio");

        Uri uri= Uri.fromFile(new File(mFileName));

        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {

                mprogress.dismiss();


                //  startPlaying();


            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter1 = new FirebaseRecyclerAdapter<MsgKey, ChatMessageViewHolder>(MsgKey.class,
                R.layout.textview,
                ChatMessageViewHolder.class,
                ref_chatchildnode1) {
            @Override
            protected void populateViewHolder(ChatMessageViewHolder chatMessageViewHolder, MsgKey m, int i) {



                chatMessageViewHolder.sender.setText(m.getSender());
                chatMessageViewHolder.key.setText(m.getMessage());
            }
        };
        mFirebaseAdapter2 = new FirebaseRecyclerAdapter<MsgKey, ChatMessageViewHolder>(MsgKey.class,
                R.layout.textview,
                ChatMessageViewHolder.class,
                ref_chatchildnode2) {
            @Override
            protected void populateViewHolder(ChatMessageViewHolder chatMessageViewHolder, MsgKey m, int i) {


                chatMessageViewHolder.sender.setText(m.getSender());
                chatMessageViewHolder.key.setText(m.getMessage());
            }
        };

        mRecyclerView.setAdapter(mFirebaseAdapter1);

        mRecyclerView.setAdapter(mFirebaseAdapter2);
    }


}