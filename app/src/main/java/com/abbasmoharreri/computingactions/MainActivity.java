package com.abbasmoharreri.computingactions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.abbasmoharreri.computingactions.database.reports.ResultantReport;
import com.abbasmoharreri.computingactions.model.Container;
import com.abbasmoharreri.computingactions.ui.CustomFloatingActionButton;
import com.abbasmoharreri.computingactions.ui.PieChart;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.view.PieChartView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String SHOW_TAP_TARGET_MAIN_ACTIVITY = "showTapTargetMainActivity";
    PieChart pieChartDay;
    PieChart pieChartMonth;
    PieChart pieChartYear;
    PieChartView year_chart;
    PieChartView month_chart;
    PieChartView day_chart;
    ResultantReport resultantReport;
    CustomFloatingActionButton customFloatingActionButton;
    Intent intent;
    Handler handler = new Handler();

    //creating MainActivity and initialize components

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
        setContentView( R.layout.activity_main );
        setTitle( getString( R.string.main_activity_title ) );
        year_chart = findViewById( R.id.year_chart );
        month_chart = findViewById( R.id.month_chart );
        day_chart = findViewById( R.id.day_chart );

        //enabling backButton on ActionBar

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );


        //initialize FloatingActionButton and subButtons

        customFloatingActionButton = new CustomFloatingActionButton( MainActivity.this );

        customFloatingActionButton.createButton();
        customFloatingActionButton.subActionButton1.setOnClickListener( this );
        customFloatingActionButton.subActionButton2.setOnClickListener( this );
        customFloatingActionButton.subActionButton3.setOnClickListener( this );
        customFloatingActionButton.subActionButton4.setOnClickListener( this );

        setTabTargetViewShow();

    }

    //when clicking on backButton in ActionBar

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
    }

    //in this lifeCycle initialize ResultantReport and chart

    @Override
    protected void onResume() {
        super.onResume();
        resultantReport = new ResultantReport( getApplicationContext() );


        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                setChartView();
            }
        }, 450 );


    }


    //setting values for showing chart

    private void setChartView() {

        try {

            pieChartYear = new PieChart( getApplicationContext(), year_chart, resultantReport.getYearResult().size(), resultantReport.getYearResult() );
            pieChartYear.setTextSize( (int) getResources().getDimension( R.dimen.pie_chart_text1_size ), (int) getResources().getDimension( R.dimen.pie_chart_text2_size ) );

            pieChartMonth = new PieChart( getApplicationContext(), month_chart, resultantReport.getMonthResult().size(), resultantReport.getMonthResult() );
            pieChartMonth.setTextSize( (int) getResources().getDimension( R.dimen.pie_chart_mini_text1_size ), (int) getResources().getDimension( R.dimen.pie_chart_mini_text2_size ) );

            pieChartDay = new PieChart( getApplicationContext(), day_chart, resultantReport.getDayResult().size(), resultantReport.getDayResult() );
            pieChartDay.setTextSize( (int) getResources().getDimension( R.dimen.pie_chart_mini_text1_size ), (int) getResources().getDimension( R.dimen.pie_chart_mini_text2_size ) );

        } catch (Exception e) {
            e.printStackTrace();

            List<Container> value = new ArrayList<>();
            value.add( new Container().setName( getResources().getString( R.string.Good ) ).setPercent( 33 ) );
            value.add( new Container().setName( getResources().getString( R.string.Medium ) ).setPercent( 33 ) );
            value.add( new Container().setName( getResources().getString( R.string.Bad ) ).setPercent( 33 ) );

            pieChartYear = new PieChart( getApplicationContext(), year_chart, value.size(), value );
            pieChartYear.setTextSize( (int) getResources().getDimension( R.dimen.pie_chart_text1_size ), (int) getResources().getDimension( R.dimen.pie_chart_text2_size ) );

            pieChartMonth = new PieChart( getApplicationContext(), month_chart, value.size(), value );
            pieChartMonth.setTextSize( (int) getResources().getDimension( R.dimen.pie_chart_mini_text1_size ), (int) getResources().getDimension( R.dimen.pie_chart_mini_text2_size ) );

            pieChartDay = new PieChart( getApplicationContext(), day_chart, value.size(), value );
            pieChartDay.setTextSize( (int) getResources().getDimension( R.dimen.pie_chart_mini_text1_size ), (int) getResources().getDimension( R.dimen.pie_chart_mini_text2_size ) );
        }

        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                prepareAnimation();
            }
        }, 15000 );

    }


    private void prepareAnimation() {
        pieChartDay.prepareDataAnimation();
        pieChartMonth.prepareDataAnimation();
        pieChartYear.prepareDataAnimation();
    }


    //showing TapTargetView

    private void setTabTargetViewShow() {

        final boolean[] click = {true};
        boolean showTapTarget;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = getSharedPreferences( SHOW_TAP_TARGET_MAIN_ACTIVITY, MODE_PRIVATE );
        editor = sharedPreferences.edit();
        showTapTarget = sharedPreferences.getBoolean( SHOW_TAP_TARGET_MAIN_ACTIVITY, false );

        if (!showTapTarget) {
            TapTargetSequence tapTargetSequence = new TapTargetSequence( this );
            tapTargetSequence.targets( TapTarget.forView( customFloatingActionButton.actionButton, getString( R.string.tap_target_title_action_button ), getString( R.string.tap_target_description_action_button ) )
                            .cancelable( false )
                            .tintTarget( false )
                            .targetRadius( 70 ),
                    TapTarget.forView( customFloatingActionButton.subActionButton4, getString( R.string.tap_target_title_action_button_4 ), getString( R.string.tap_target_description_action_button_4 ) )
                            .cancelable( false )
                            .tintTarget( false )
                            .targetRadius( 70 ),
                    TapTarget.forView( customFloatingActionButton.subActionButton3, getString( R.string.tap_target_title_action_button_3 ), getString( R.string.tap_target_description_action_button_3 ) )
                            .cancelable( false )
                            .tintTarget( false )
                            .targetRadius( 70 ),
                    TapTarget.forView( customFloatingActionButton.subActionButton2, getString( R.string.tap_target_title_action_button_2 ), getString( R.string.tap_target_description_acton_button_2 ) )
                            .cancelable( false )
                            .tintTarget( false )
                            .targetRadius( 70 ),
                    TapTarget.forView( customFloatingActionButton.subActionButton1, getString( R.string.tap_target_title_action_button_1 ), getString( R.string.tap_target_description_action_button_1 ) )
                            .cancelable( false )
                            .tintTarget( false )
                            .targetRadius( 70 ) ).listener( new TapTargetSequence.Listener() {
                @Override
                public void onSequenceFinish() {

                    customFloatingActionButton.actionButton.callOnClick();
                }

                @Override
                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {


                    if (click[0]) {
                        customFloatingActionButton.actionButton.callOnClick();
                        click[0] = false;
                    }
                }

                @Override
                public void onSequenceCanceled(TapTarget lastTarget) {


                }
            } );
            tapTargetSequence.start();
            editor.putBoolean( SHOW_TAP_TARGET_MAIN_ACTIVITY, true );
            editor.apply();
        }
    }


    //this method for clicking on components

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.subActionButton1:
                Toast.makeText( getApplicationContext(), getString( R.string.toast_action_button_penalty_activity ), Toast.LENGTH_LONG ).show();
                customFloatingActionButton.animationClose();
                /*intent = new Intent( MainActivity.this, PenaltyActivity.class );
                startActivity( intent );*/
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
                break;
            case R.id.subActionButton2:
                Toast.makeText( getApplicationContext(), getString( R.string.toast_action_button_reward_activity ), Toast.LENGTH_LONG ).show();
                customFloatingActionButton.animationClose();
                /*intent = new Intent( MainActivity.this, RewardActivity.class );
                startActivity( intent );*/
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
                break;
            case R.id.subActionButton3:
                Log.i( "Show Activity", "ShowActivity" );
                customFloatingActionButton.animationClose();
                intent = new Intent( MainActivity.this, ShowActivity.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
                break;
            case R.id.subActionButton4:
                Log.i( "Show Activity", "AddWorkActivity" );
                customFloatingActionButton.animationClose();
                intent = new Intent( MainActivity.this, AddWorkActivity.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
                break;
        }

    }

}
