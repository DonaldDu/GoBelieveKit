package com.example.gobelievedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.IMKIT;

public class MainActivity extends AppCompatActivity {
    long sender = 1000005;
    long receiver = 1000004;
    String token = "x0oqDSwAfJMa4IH7dCBfhCKRAnM5zT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showChat(View view) {
        IMKIT.showChat(this, token, sender, "test", receiver);
        finish();
    }
}
