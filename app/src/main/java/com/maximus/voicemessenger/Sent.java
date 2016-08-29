package com.maximus.voicemessenger;

import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Muhammad on 25/08/2016.
 */
public class Sent extends AppCompatActivity {

    //  File file;
    ListView musiclist;
    Cursor musiccursor;
    int music_column_index;
    int count;
    MediaPlayer mMediaPlayer;
    // final String MEDIA_PATH = Environment.getExternalStorageDirectory()
    // .getPath() ;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private String mp3Pattern = ".mp3";
    private ListView mListView;
    private File file;
    private MediaPlayer mp = new MediaPlayer();
    private Handler mHandler = new Handler() {
        @Override
        public void close() {

        }

        @Override
        public void flush() {

        }

        @Override
        public void publish(LogRecord record) {

        }
    };
    private List<String> myList = new ArrayList<String>();

    private static final String MEDIA_PATH = new String(
            Environment.getExternalStorageDirectory() + "/voice/sent/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sent);
        //lv = (ListView) findViewById(R.id.ListView);

        mListView = (ListView) findViewById(R.id.listView2);
        // mPlayPause = (ImageView) findViewById(R.id.play_pause);

        file = new File(MEDIA_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        File list[] = file.listFiles();
        for (int i = 0; i < list.length; i++) {
            myList.add(list[i].getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myList);
        adapter.notifyDataSetChanged();
        mListView.setAdapter(adapter);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setCacheColorHint(Color.TRANSPARENT);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                playSong(MEDIA_PATH + myList.get(position));
            }
        });

       /*  mPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mp.isPlaying()) {
                    if (mp != null) {
                        mp.pause();
                        mPlayPause.setImageResource(R.drawable.play);
                    }
                } else {
                    if (mp != null) {
                        mp.start();
                        mPlayPause.setImageResource(R.drawable.pause);
                    }
                }
            }
        }); */
    }

    private void playSong(String songPath) {
        try {
            mp.reset();
            mp.setDataSource(songPath);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Log.v(getString(R.string.app_name), e.getMessage());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.release();
    }

    // init_phone_music_grid();
    //getPlayList();

}
