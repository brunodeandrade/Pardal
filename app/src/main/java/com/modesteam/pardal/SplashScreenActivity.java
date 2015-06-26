package com.modesteam.pardal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
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

import com.modesteam.pardal.brand.BrandContent;
import com.modesteam.pardal.city.CityContent;
import com.modesteam.pardal.highwayStretch.HighwayStretchContent;
import com.modesteam.pardal.model.ModelContent;
import com.modesteam.pardal.state.StateContent;
import com.modesteam.pardal.type.TypeContent;


public class SplashScreenActivity extends Activity {

    Thread mThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView textViewVersion = (TextView) getWindow().findViewById(R.id.textViewVersion);
        textViewVersion.setText("Pardal\n\t v 1.0");

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Quango.otf");
        textViewVersion.setTypeface(typeface);

        new LoadListsTask().execute();
    }

    private class LoadListsTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            new TypeContent();
            new BrandContent();
            new StateContent();
            new CityContent();
            new HighwayStretchContent();
            new ModelContent();
            Log.d("TAG: ",ModelContent.ITEMS.get(10).getMaximumMeasuredVelocity().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();

            Intent intent = new Intent();
            intent.setClass(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}