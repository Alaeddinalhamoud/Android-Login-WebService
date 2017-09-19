package com.example.alaeddin.loginappapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
String GetEmail;
    TextView TV_Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TV_Email=(TextView)findViewById(R.id.TVEmail);
        GetEmail=getIntent().getStringExtra("GetEmail");

        TV_Email.setText(GetEmail);

    }
}
