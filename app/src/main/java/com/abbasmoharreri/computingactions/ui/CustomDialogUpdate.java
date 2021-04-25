package com.abbasmoharreri.computingactions.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abbasmoharreri.computingactions.R;
import com.abbasmoharreri.computingactions.SpecialWorkActivity;
import com.abbasmoharreri.computingactions.database.DataBaseController;
import com.abbasmoharreri.computingactions.model.Day;
import com.abbasmoharreri.computingactions.model.Work;
import com.xw.repo.BubbleSeekBar;

import java.util.List;

import info.hoang8f.widget.FButton;

public class CustomDialogUpdate extends Dialog implements View.OnClickListener, BubbleSeekBar.OnProgressChangedListener {

    private FButton update, cancel;
    private Button yearIncrease, yearDecrease, monthIncrease, monthDecrease, dayIncrease, dayDecrease;
    private EditText name, yearEdit, monthEdit, dayEdit, pointEdit;
    private BubbleSeekBar point;
    private TextView id;
    private Context context;
    private List<Work> work;
    private int position;
    private int year;
    private int month;
    private int day;


    public CustomDialogUpdate(@NonNull Context context, List<Work> work, int position) {
        super( context );
        this.context = context;
        this.work = work;
        this.position = position;
        this.day = work.get( position ).getDateDay();
        this.month = work.get( position ).getDateMonth();
        this.year = work.get( position ).getDateYear();
    }

    //creating Custom dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.update_dialog );

        yearIncrease = findViewById( R.id.year_increase_button );
        yearIncrease.setOnClickListener( this );

        yearDecrease = findViewById( R.id.year_decrease_button );
        yearDecrease.setOnClickListener( this );

        monthIncrease = findViewById( R.id.month_increase_button );
        monthIncrease.setOnClickListener( this );

        monthDecrease = findViewById( R.id.month_decrease_button );
        monthDecrease.setOnClickListener( this );

        dayIncrease = findViewById( R.id.day_increase_button );
        dayIncrease.setOnClickListener( this );

        dayDecrease = findViewById( R.id.day_decrease_button );
        dayDecrease.setOnClickListener( this );

        yearEdit = findViewById( R.id.date_increase_decrease_year_text );
        monthEdit = findViewById( R.id.date_increase_decrease_month_text );
        dayEdit = findViewById( R.id.date_increase_decrease_day_text );


        yearEdit.setText( String.valueOf( year ) );
        monthEdit.setText( String.valueOf( month ) );
        dayEdit.setText( String.valueOf( day ) );

        update = findViewById( R.id.update_work_button_update );
        update.setOnClickListener( this );

        cancel = findViewById( R.id.update_work_button_cancel );
        cancel.setOnClickListener( this );

        name = findViewById( R.id.update_work_name_edit );
        id = findViewById( R.id.update_work_id_text );
        point = findViewById( R.id.update_work_point_seekBar );
        pointEdit = findViewById( R.id.update_point_text );
        pointEdit.setInputType( InputType.TYPE_NULL );

        id.setText( String.valueOf( work.get( position ).getId() ) );

        name.setText( work.get( position ).getName() );


        point.setProgress( work.get( position ).getPoint() );
        point.setOnProgressChangedListener( this );
        pointEdit.setText( String.valueOf( point.getProgress() ) );

    }

    //this method for clicking on components

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.update_work_button_update:
                DataBaseController dataBaseController = new DataBaseController( context );
                Day day1 = new Day( day, month, year );
                work.get( position ).setName( name.getText().toString() );
                work.get( position ).setDateDay( day );
                work.get( position ).setDateMonth( month );
                work.get( position ).setDateYear( year );
                work.get( position ).setPoint( point.getProgress() ).setDayName( day1.getName() );
                dataBaseController.updateWork( work.get( position ) );
                SpecialWorkActivity specialWorkActivity = (SpecialWorkActivity) context;
                specialWorkActivity.worksAdapter.notifyDataSetChanged();
                dismiss();
                break;
            case R.id.update_work_button_cancel:
                dismiss();
                break;
            case R.id.year_increase_button:
                year++;
                yearEdit.setText( String.valueOf( year ) );
                break;
            case R.id.year_decrease_button:
                year--;
                yearEdit.setText( String.valueOf( year ) );
                break;
            case R.id.month_increase_button:
                month++;
                monthEdit.setText( String.valueOf( month ) );
                break;
            case R.id.month_decrease_button:
                month--;
                monthEdit.setText( String.valueOf( month ) );
                break;
            case R.id.day_increase_button:
                day++;
                dayEdit.setText( String.valueOf( day ) );
                break;
            case R.id.day_decrease_button:
                day--;
                dayEdit.setText( String.valueOf( day ) );
                break;
        }
    }


    //when value of ProgressBar is changing , calling this methods

    @Override
    public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
        pointEdit.setText( String.valueOf( progress ) );
    }

    @Override
    public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

    }

    @Override
    public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

    }
}
