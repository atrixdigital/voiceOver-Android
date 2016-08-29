package com.maximus.voicemessenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Admin_Pannel extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__pannel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin__pannel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.rec) {
            Intent i=new Intent(getApplicationContext(),recording.class);
            startActivity(i);
            overridePendingTransition(R.animator.push_left_in,R.animator.push_up_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addemp) {


            Intent i=new Intent(getApplicationContext(),addEmp.class);
            startActivity(i);
            overridePendingTransition(R.animator.push_left_in,R.animator.push_up_out);

            // Handle the camera action
        } else if (id == R.id.delemp) {

            Intent i=new Intent(getApplicationContext(),removerUsers.class);
            startActivity(i);
            overridePendingTransition(R.animator.push_left_in,R.animator.push_up_out);

        } else if (id == R.id.reqemp) {

            Intent i=new Intent(getApplicationContext(),Requests.class);
            startActivity(i);
            overridePendingTransition(R.animator.push_left_in,R.animator.push_up_out);

        } else if (id == R.id.nav_manage) {

        }
        else if (id == R.id.inbox) {

            Intent i=new Intent(getApplicationContext(),Online_Users.class);
            startActivity(i);
            overridePendingTransition(R.animator.push_left_in,R.animator.push_up_out);

        }
        else if (id == R.id.sent) {

            Intent i=new Intent(getApplicationContext(),Sent.class);
            startActivity(i);
            overridePendingTransition(R.animator.push_left_in,R.animator.push_up_out);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
