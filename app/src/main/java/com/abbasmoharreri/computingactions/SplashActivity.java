package com.abbasmoharreri.computingactions;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {


    TextView englishButton;
    TextView farsiButton;
    String language = "";
    private SharedPreferences setting;
    private SharedPreferences.Editor editor;
    private static String PREFERENCES = "myPreference";
    private final static String LANGUAGE = "Language";
    private Resources resources;
    private Configuration configuration;
    private DisplayMetrics displayMetrics;

    //creating SplashActivity and initialize components

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        setTitle( getString( R.string.splash_activity_title ) );

        resources = getResources();
        configuration = resources.getConfiguration();
        displayMetrics = resources.getDisplayMetrics();

        englishButton = (TextView) findViewById( R.id.EnglishButton );
        farsiButton = (TextView) findViewById( R.id.FarsiButton );
        setting = getSharedPreferences( PREFERENCES, MODE_PRIVATE );
        editor = setting.edit();

        //if language saving in SharePreferences setting this language for using it in Application

        if (settingLanguage()) {

            language = setting.getString( LANGUAGE, "en" );
            configuration.locale = new Locale( language );
            resources.updateConfiguration( configuration, displayMetrics );
            editor.putString( LANGUAGE, language );
            editor.apply();
            Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
            startActivity( intent );
            finish();

        } else {

            englishButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    configuration.locale = new Locale( "en" );
                    resources.updateConfiguration( configuration, displayMetrics );
                    language = "en";
                    editor.putString( LANGUAGE, language );
                    editor.apply();
                    Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
                    startActivity( intent );
                    finish();
                }
            } );

            farsiButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    configuration.locale = new Locale( "fa" );
                    resources.updateConfiguration( configuration, displayMetrics );
                    language = "fa";
                    editor.putString( LANGUAGE, language );
                    editor.apply();
                    Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
                    startActivity( intent );
                    finish();
                }
            } );
        }


    }

    //checking , is language saving on SharePreferences

    private boolean settingLanguage() {
        boolean flag = false;
        String st = setting.getString( LANGUAGE, "" );
        if (!st.equals( "" )) {
            flag = true;
        }
        return flag;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {

    }
}
