package com.abbasmoharreri.computingactions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RewardPenaltyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reward_penalty );
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left );
    }
}
