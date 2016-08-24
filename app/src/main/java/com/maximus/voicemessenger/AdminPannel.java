package com.maximus.voicemessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AdminPannel extends AppCompatActivity {


    ImageButton add, employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pannel);



        add=(ImageButton) findViewById(R.id.addemp);
        employees=(ImageButton) findViewById(R.id.employees);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),addEmp.class);
                startActivity(i);
                overridePendingTransition(R.animator.push_left_in,R.animator.push_up_out);
            }
        });
        employees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Requests.class);
                startActivity(i);
                overridePendingTransition(R.animator.push_left_in,R.animator.push_up_out);
            }
        });
    }
}