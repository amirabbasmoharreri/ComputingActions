package com.abbasmoharreri.computingactions;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.abbasmoharreri.computingactions.database.reports.AscAndDescReport;
import com.abbasmoharreri.computingactions.database.reports.ConditionReport;
import com.abbasmoharreri.computingactions.database.reports.FrequencyOfWorkReport;
import com.abbasmoharreri.computingactions.database.reports.PointsReport;
import com.abbasmoharreri.computingactions.database.reports.SpecialWorkReport;
import com.abbasmoharreri.computingactions.finalVariables.MessageTags;
import com.abbasmoharreri.computingactions.finalVariables.ReportType;
import com.abbasmoharreri.computingactions.ui.ComboChart;

import lecho.lib.hellocharts.view.ComboLineColumnChartView;

public class ShowChartsActivity extends AppCompatActivity {

    private int type;
    int startDay, startMonth, startYear, endDay, endMonth, endYear;
    String startName, endName;

    ComboChart comboChart;
    ComboLineColumnChartView comboLineColumnChartView;
    String nameX, nameY, workName;
    Handler handler = new Handler();

    //creating ShowCartActivity and initialize components

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
        setContentView( R.layout.activity_show_charts );
        setTitle( getString( R.string.show_chart_activity_title ) );

        //enabling backButton in ActionBar

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        comboLineColumnChartView = findViewById( R.id.show_chart );
        startDay = getIntent().getIntExtra( MessageTags.START_DAY, 0 );
        startMonth = getIntent().getIntExtra( MessageTags.START_MONTH, 0 );
        startYear = getIntent().getIntExtra( MessageTags.START_YEAR, 0 );
        startName = getIntent().getStringExtra( MessageTags.START_NAME );
        endDay = getIntent().getIntExtra( MessageTags.END_DAY, 0 );
        endMonth = getIntent().getIntExtra( MessageTags.END_MONTH, 0 );
        endYear = getIntent().getIntExtra( MessageTags.END_YEAR, 0 );
        endName = getIntent().getStringExtra( MessageTags.END_NAME );
        type = getIntent().getIntExtra( MessageTags.TYPE, 0 );
        nameX = getIntent().getStringExtra( MessageTags.NAME_X );
        nameY = getIntent().getStringExtra( MessageTags.NAME_Y );


    }

    //when clicking on BackButton in ActionBar calling this method

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

    //in this lifeCycle setting Data for showing in chart
    // this method is updating when using backButton from other activity or starting this activity

    @Override
    protected void onResume() {
        super.onResume();

        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                setComboChart();
            }
        }, 450 );

    }

    //setting data for showing chart

    public void setComboChart() {
        switch (type) {
            case ReportType.POINT_REPORT:
                PointsReport pointsReport = new PointsReport( getApplicationContext(), startDay, startMonth, startYear, endDay, endMonth, endYear );
                comboChart = new ComboChart( getApplicationContext(), comboLineColumnChartView, 2, pointsReport.getResult().size(), pointsReport.getResult(), nameX, nameY, ReportType.POINT_REPORT );
                break;
            case ReportType.ASC_AND_DESC_REPORT:
                AscAndDescReport ascAndDescReport = new AscAndDescReport( getApplicationContext(), startDay, startMonth, startYear, endDay, endMonth, endYear );
                comboChart = new ComboChart( getApplicationContext(), comboLineColumnChartView, 2, ascAndDescReport.getResult().size(), ascAndDescReport.getResult(), nameX, nameY, ReportType.ASC_AND_DESC_REPORT );
                break;
            case ReportType.CONDITION_REPORT:
                ConditionReport conditionReport = new ConditionReport( getApplicationContext(), startDay, startMonth, startYear, endDay, endMonth, endYear );
                comboChart = new ComboChart( getApplicationContext(), comboLineColumnChartView, 0, conditionReport.getResult().size(), conditionReport.getResult(), nameX, nameY, ReportType.CONDITION_REPORT );
                comboChart.toggleLines();
                break;
            case ReportType.FREQUENCY_OF_WORK_REPORT:
                FrequencyOfWorkReport frequencyOfWorkReport = new FrequencyOfWorkReport( getApplicationContext(), startDay, startMonth, startYear, endDay, endMonth, endYear );
                comboChart = new ComboChart( getApplicationContext(), comboLineColumnChartView, 0, frequencyOfWorkReport.getResult().size(), frequencyOfWorkReport.getResult(), nameX, nameY, ReportType.FREQUENCY_OF_WORK_REPORT );
                comboChart.toggleLines();
                break;
            case ReportType.SPECIAL_WORK_REPORT:
                workName = getIntent().getStringExtra( MessageTags.WORK_NAME );
                SpecialWorkReport specialWorkReport = new SpecialWorkReport( getApplicationContext(), workName, startDay, startMonth, startYear, endDay, endMonth, endYear );
                comboChart = new ComboChart( getApplicationContext(), comboLineColumnChartView, 2, specialWorkReport.getResult().size(), specialWorkReport.getResult(), nameX, nameY, ReportType.SPECIAL_WORK_REPORT );
                break;
        }

        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                prepareAnimation();
            }
        }, 200 );
    }


    private void prepareAnimation() {
        comboChart.prepareDataAnimation();
    }
}
