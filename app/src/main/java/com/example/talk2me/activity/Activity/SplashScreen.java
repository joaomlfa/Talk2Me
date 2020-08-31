package com.example.talk2me.activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.talk2me.activity.MainActivity;
import com.example.talk2me.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                abrirApp();
            }
        },4000);

    }
    private void abrirApp(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
