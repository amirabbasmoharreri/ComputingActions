package com.abbasmoharreri.computingactions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.abbasmoharreri.computingactions.finalVariables.MessageTags;
import com.abbasmoharreri.computingactions.finalVariables.ReportType;
import com.abbasmoharreri.computingactions.model.Work;
import com.abbasmoharreri.computingactions.ui.TypePresenter;
import com.abbasmoharreri.computingactions.ui.WorkPresenter;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.otaliastudios.autocomplete.Autocomplete;
import com.otaliastudios.autocomplete.AutocompleteCallback;
import com.otaliastudios.autocomplete.AutocompletePresenter;


public class ShowActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TextWatcher, DialogInterface.OnClickListener {

    Button showButton;
    MaterialTextField materialSpecialWork, materialDateStart, materialDateEnd, materialType;
    Autocomplete typeAutoComplete;
    Autocomplete workNameAutocomplete;
    EditText type, workName, dateStart, dateEnd;
    int startDay, startMonth, startYear, endDay, endMonth, endYear;
    String startName, endName;
    PersianCalendar persianCalendar;
    DatePickerDialog datePickerDialog;
    String[] name;
    boolean[] showDialog = {false, false, false, false};

    AlertDialog dateStartDialog, dateEndDialog, typeDialog, workNameDialog;

    //creating ShowActivity and initialize components

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
        setContentView( R.layout.activity_show );
        setTitle( getString( R.string.show_activity_title ) );

        //enabling backButton in ActionBar

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        name = getResources().getStringArray( R.array.ShowReports );

        showButton = new Button( getApplicationContext() );
        showButton = findViewById( R.id.showActivity_showButton );
        showButton.setOnClickListener( this );

        materialDateEnd = new MaterialTextField( getApplicationContext() );
        materialDateEnd = findViewById( R.id.end_calender_text );

        materialDateStart = new MaterialTextField( getApplicationContext() );
        materialDateStart = findViewById( R.id.start_calender_text );

        materialType = new MaterialTextField( getApplicationContext() );
        materialType = findViewById( R.id.show_type );

        materialSpecialWork = new MaterialTextField( getApplicationContext() );
        materialSpecialWork = findViewById( R.id.specific_work );
        materialSpecialWork.setVisibility( View.INVISIBLE );

        type = findViewById( R.id.show_type_editText );
        type.setOnClickListener( this );
        type.setInputType( InputType.TYPE_NULL );
        type.addTextChangedListener( this );

        workName = findViewById( R.id.specific_work_editText );
        workName.addTextChangedListener( this );
        workName.setOnClickListener( this );

        dateStart = findViewById( R.id.start_calender_editText );
        dateStart.setOnClickListener( this );
        dateStart.setInputType( InputType.TYPE_NULL );

        dateEnd = findViewById( R.id.end_calender_editText );
        dateEnd.setOnClickListener( this );
        dateEnd.setInputType( InputType.TYPE_NULL );

        persianCalendar = new PersianCalendar();
        setupTypeAutoComplete();
        setupWorkNameAutoComplete();
    }

    //when clicking on backButton in ActionBar calling this method

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
    protected void onResume() {
        super.onResume();
        showDialog[0] = false;
        showDialog[1] = false;
        showDialog[2] = false;
        showDialog[3] = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
    }


    //this method for clicking on components

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showActivity_showButton:
                try {
                    if (inputEditTextCheck()) {
                        chooseTypeShow();
                    }
                    Log.i( MessageTags.ACTIVITY_SHOW, "Show Button" );

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.show_type_editText:
                typeAutoComplete.showPopup( "" );

                break;
            case R.id.start_calender_editText:
                datePickerDialog = DatePickerDialog.newInstance( this
                        , persianCalendar.getPersianYear()
                        , persianCalendar.getPersianMonth()
                        , persianCalendar.getPersianDay() );
                datePickerDialog.show( getFragmentManager(), "" );
                break;
            case R.id.end_calender_editText:
                datePickerDialog = DatePickerDialog.newInstance( this
                        , persianCalendar.getPersianYear()
                        , persianCalendar.getPersianMonth()
                        , persianCalendar.getPersianDay() );
                datePickerDialog.show( getFragmentManager(), "" );
                break;
            case R.id.specific_work_editText:
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService( INPUT_METHOD_SERVICE );
                inputMethodManager.toggleSoftInput( InputMethodManager.SHOW_FORCED, 0 );
                break;
        }
    }

    //choosing type of show for showing chart and sending data to ShowChartActivity for show

    private void chooseTypeShow() {


        if (type.getText().toString().equals( name[0] )) {
            Intent intent = new Intent( ShowActivity.this, ShowChartsActivity.class );
            intent.putExtra( MessageTags.START_DAY, startDay );
            intent.putExtra( MessageTags.START_MONTH, startMonth );
            intent.putExtra( MessageTags.START_YEAR, startYear );
            intent.putExtra( MessageTags.START_NAME, startName );
            intent.putExtra( MessageTags.END_DAY, endDay );
            intent.putExtra( MessageTags.END_MONTH, endMonth );
            intent.putExtra( MessageTags.END_YEAR, endYear );
            intent.putExtra( MessageTags.END_NAME, endName );
            intent.putExtra( MessageTags.TYPE, ReportType.FREQUENCY_OF_WORK_REPORT );
            intent.putExtra( MessageTags.NAME_X, getString( R.string.axe_x_work ) );
            intent.putExtra( MessageTags.NAME_Y, getString( R.string.axe_y_number ) );
            startActivity( intent );

        } else if (type.getText().toString().equals( name[1] )) {

            Intent intent = new Intent( ShowActivity.this, ShowChartsActivity.class );
            intent.putExtra( MessageTags.START_DAY, startDay );
            intent.putExtra( MessageTags.START_MONTH, startMonth );
            intent.putExtra( MessageTags.START_YEAR, startYear );
            intent.putExtra( MessageTags.START_NAME, startName );
            intent.putExtra( MessageTags.END_DAY, endDay );
            intent.putExtra( MessageTags.END_MONTH, endMonth );
            intent.putExtra( MessageTags.END_YEAR, endYear );
            intent.putExtra( MessageTags.END_NAME, endName );
            intent.putExtra( MessageTags.TYPE, ReportType.POINT_REPORT );
            intent.putExtra( MessageTags.NAME_X, getString( R.string.axe_x_work ) );
            intent.putExtra( MessageTags.NAME_Y, getString( R.string.axe_y_point ) );
            startActivity( intent );

        } else if (type.getText().toString().equals( name[2] )) {

            Intent intent = new Intent( ShowActivity.this, ShowChartsActivity.class );
            intent.putExtra( MessageTags.START_DAY, startDay );
            intent.putExtra( MessageTags.START_MONTH, startMonth );
            intent.putExtra( MessageTags.START_YEAR, startYear );
            intent.putExtra( MessageTags.START_NAME, startName );
            intent.putExtra( MessageTags.END_DAY, endDay );
            intent.putExtra( MessageTags.END_MONTH, endMonth );
            intent.putExtra( MessageTags.END_YEAR, endYear );
            intent.putExtra( MessageTags.END_NAME, endName );
            intent.putExtra( MessageTags.TYPE, ReportType.CONDITION_REPORT );
            intent.putExtra( MessageTags.NAME_X, getString( R.string.axe_x_condition ) );
            intent.putExtra( MessageTags.NAME_Y, getString( R.string.axe_y_percent ) );
            startActivity( intent );

        } else if (type.getText().toString().equals( name[3] )) {

            Intent intent = new Intent( ShowActivity.this, ShowChartsActivity.class );
            intent.putExtra( MessageTags.START_DAY, startDay );
            intent.putExtra( MessageTags.START_MONTH, startMonth );
            intent.putExtra( MessageTags.START_YEAR, startYear );
            intent.putExtra( MessageTags.START_NAME, startName );
            intent.putExtra( MessageTags.END_DAY, endDay );
            intent.putExtra( MessageTags.END_MONTH, endMonth );
            intent.putExtra( MessageTags.END_YEAR, endYear );
            intent.putExtra( MessageTags.END_NAME, endName );
            intent.putExtra( MessageTags.WORK_NAME, workName.getText().toString() );
            intent.putExtra( MessageTags.TYPE, ReportType.SPECIAL_WORK_REPORT );
            intent.putExtra( MessageTags.NAME_X, getString( R.string.axe_x_date ) );
            intent.putExtra( MessageTags.NAME_Y, getString( R.string.axe_y_point ) );
            startActivity( intent );

        } else if (type.getText().toString().equals( name[4] )) {

            Intent intent = new Intent( ShowActivity.this, ShowChartsActivity.class );
            intent.putExtra( MessageTags.START_DAY, startDay );
            intent.putExtra( MessageTags.START_MONTH, startMonth );
            intent.putExtra( MessageTags.START_YEAR, startYear );
            intent.putExtra( MessageTags.START_NAME, startName );
            intent.putExtra( MessageTags.END_DAY, endDay );
            intent.putExtra( MessageTags.END_MONTH, endMonth );
            intent.putExtra( MessageTags.END_YEAR, endYear );
            intent.putExtra( MessageTags.END_NAME, endName );
            intent.putExtra( MessageTags.TYPE, ReportType.ASC_AND_DESC_REPORT );
            intent.putExtra( MessageTags.NAME_X, getString( R.string.axe_x_work ) );
            intent.putExtra( MessageTags.NAME_Y, getString( R.string.axe_y_point ) );
            startActivity( intent );
        }


    }

    //setting ability of Auto complete for Type of Report EditText

    private void setupTypeAutoComplete() {

        float elevation = 6f;
        Drawable backgroundDrawable = new ColorDrawable( Color.WHITE );
        AutocompletePresenter<String> presenter = new TypePresenter( this );
        AutocompleteCallback<String> callback = new AutocompleteCallback<String>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, String item) {
                editable.clear();
                editable.append( item );
                return true;
            }

            @Override
            public void onPopupVisibilityChanged(boolean shown) {

            }
        };

        typeAutoComplete = Autocomplete.<String>on( type )
                .with( elevation )
                .with( backgroundDrawable )
                .with( presenter )
                .with( callback )
                .build();

    }

    //setting ability of Auto complete for WorkName EditText

    private void setupWorkNameAutoComplete() {

        float elevation = 6f;
        Drawable backgroundDrawable = new ColorDrawable( Color.WHITE );
        AutocompletePresenter<Work> presenter = new WorkPresenter( this );
        AutocompleteCallback<Work> callback = new AutocompleteCallback<Work>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, Work item) {
                editable.clear();
                editable.append( item.getName() );
                return true;
            }

            public void onPopupVisibilityChanged(boolean shown) {
            }
        };

        workNameAutocomplete = Autocomplete.<Work>on( workName )
                .with( elevation )
                .with( backgroundDrawable )
                .with( presenter )
                .with( callback )
                .build();

    }

    //when string of EditText changing calling this methods

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (type.hasFocus()) {
            String[] name = getResources().getStringArray( R.array.ShowReports );
            if (s.toString().equals( name[3] )) {
                materialSpecialWork.setVisibility( View.VISIBLE );
            } else {
                materialSpecialWork.setVisibility( View.INVISIBLE );
            }

        } else if (workName.hasFocus()) {
            workNameAutocomplete.showPopup( s );
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    private boolean inputEditTextCheck() {

        if (!dateStart.getText().toString().equals( "--/--/--" )) {
            showDialog[0] = true;
        }
        if (!dateEnd.getText().toString().equals( "--/--/--" )) {
            showDialog[1] = true;
        }
        if (!type.getText().toString().equals( "" )) {
            showDialog[2] = true;
        }
        if (!workName.getText().toString().equals( "" )) {
            showDialog[3] = true;
        }


        if (dateStart.getText().toString().equals( "--/--/--" )) {
            dateStartDialog = new AlertDialog.Builder( this )
                    .setTitle( getString( R.string.alert_dialog_error) )
                    .setMessage( getString( R.string.show_activity_alert_dialog_start_date) )
                    .setCancelable( false )
                    .setPositiveButton( getString( R.string.alert_dialog_button_ok), this ).show();

        } else if (dateEnd.getText().toString().equals( "--/--/--" )) {

            dateEndDialog = new AlertDialog.Builder( this )
                    .setTitle( getString( R.string.alert_dialog_error) )
                    .setMessage( getString( R.string.show_activity_alert_dialog_end_date) )
                    .setCancelable( false )
                    .setPositiveButton( getString( R.string.alert_dialog_button_ok), this ).show();

        } else if (type.getText().toString().equals( "" )) {
            typeDialog = new AlertDialog.Builder( this )
                    .setTitle( getString( R.string.alert_dialog_error) )
                    .setMessage( getString( R.string.show_activity_alert_dialog_type) )
                    .setCancelable( false )
                    .setPositiveButton( getString( R.string.alert_dialog_button_ok), this ).show();

        } else if (type.getText().toString().equals( name[3] )) {

            if (workName.getText().toString().equals( "" )) {

                workNameDialog = new AlertDialog.Builder( this )
                        .setTitle( getString( R.string.alert_dialog_error) )
                        .setMessage( getString( R.string.show_activity_alert_dialog_work_name) )
                        .setCancelable( false )
                        .setPositiveButton( getString( R.string.alert_dialog_button_ok), this ).show();
            }
        }

        if (showDialog[0] && showDialog[1] && showDialog[2]) {
            if (!type.getText().toString().equals( name[3] )) {
                return true;
            } else {
                if (showDialog[3]) {
                    return true;
                }
            }
        }

        return false;
    }


    //setting data of Date by PersianCalender

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        if (dateStart.hasFocus()) {
            dateStart.setText( date );
            this.startYear = year;
            this.startMonth = monthOfYear + 1;
            this.startDay = dayOfMonth;
            this.startName = persianCalendar.getPersianWeekDayName();
        } else if (dateEnd.hasFocus()) {
            dateEnd.setText( date );
            this.endYear = year;
            this.endMonth = monthOfYear + 1;
            this.endDay = dayOfMonth;
            this.endName = persianCalendar.getPersianWeekDayName();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (dialog.equals( dateStartDialog )) {
            materialDateStart.callOnClick();
            dateStart.callOnClick();
            showDialog[0] = true;
        } else if (dialog.equals( dateEndDialog )) {
            materialDateEnd.callOnClick();
            dateEnd.callOnClick();
            showDialog[1] = true;
        } else if (dialog.equals( typeDialog )) {
            materialType.callOnClick();
            type.callOnClick();
            showDialog[2] = true;
        } else if (dialog.equals( workNameDialog )) {
            materialSpecialWork.callOnClick();
            workName.callOnClick();
            showDialog[3] = true;
        }
    }
}
