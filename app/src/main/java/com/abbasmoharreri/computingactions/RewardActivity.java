package com.abbasmoharreri.computingactions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RewardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reward );
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right );
    }
}
