package com.jpr.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BindView;
import com.jpr.inject.InjectView;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.text)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectView.bind(this);
        Toast.makeText(this , "----->" + textView,Toast.LENGTH_SHORT).show();
    }
}
