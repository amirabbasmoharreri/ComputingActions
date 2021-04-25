package com.abbasmoharreri.computingactions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PenaltyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_penalty );
    }

    @Override
    protected void onPause() {
        super.onPause();
         overridePendingTransition( android.R.anim.fade_in, android.R.anim.slide_out_right );
    }
}
