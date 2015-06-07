package com.modesteam.pardal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import android.util.Log;


public class SplashScreenActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        ThreadData threadData = new ThreadData();
        Thread thread = new Thread(threadData);
        thread.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView textViewVersion = (TextView) getWindow().findViewById(R.id.textViewVersion);
        textViewVersion.setText("Pardal\n v 1.0");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        textViewVersion.setTypeface(typeface);

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                finish();

                Intent intent = new Intent();
                intent.setClass(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}