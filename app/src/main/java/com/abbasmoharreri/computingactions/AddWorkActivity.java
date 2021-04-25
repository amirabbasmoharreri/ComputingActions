package com.abbasmoharreri.computingactions;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.abbasmoharreri.computingactions.control.Regex;
import com.abbasmoharreri.computingactions.database.DataBaseController;
import com.abbasmoharreri.computingactions.finalVariables.MessageTags;
import com.abbasmoharreri.computingactions.model.Work;
import com.abbasmoharreri.computingactions.ui.WorkPresenter;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.otaliastudios.autocomplete.Autocomplete;
import com.otaliastudios.autocomplete.AutocompleteCallback;
import com.otaliastudios.autocomplete.AutocompletePresenter;
import com.xw.repo.BubbleSeekBar;

public class AddWorkActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, DialogInterface.OnClickListener, BubbleSeekBar.OnProgressChangedListener{

    DatePickerDialog datePickerDialog;
    Button addButton;
    EditText dateText, workName, pointText;
    PersianCalendar persianCalendar;
    DataBaseController dataBaseController;
    BubbleSeekBar seekBarPoint;
    int year, month, day;
    String dayName;
    Autocomplete workNameAutocomplete;
    MaterialTextField workNameMaterial, dateMaterial;
    AlertDialog workNameAlert, dateAlert;
    boolean[] showDialog = {false, false};
    Regex regex;

    //creating AddWorkActivity and initialize components

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
        setContentView( R.layout.activity_add_work );
        setTitle( getString( R.string.add_work_activity_title ) );

        //enabling back Button in ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );


        addButton = findViewById( R.id.add_button );
        addButton.setOnClickListener( this );
        dateText = findViewById( R.id.datePicker_text );
        dateText.setOnClickListener( this );
        workName = findViewById( R.id.workName_text );
        workName.setOnClickListener( this );
        seekBarPoint = findViewById( R.id.update_work_point_seekBar );
        seekBarPoint.setOnProgressChangedListener( this );
        pointText = findViewById( R.id.update_point_text );
        pointText.setInputType( InputType.TYPE_NULL );
        persianCalendar = new PersianCalendar();

        workNameMaterial = findViewById( R.id.workName_material );
        dateMaterial = findViewById( R.id.materialTextDate );

        regex = new Regex();

        setupWorkNameAutoComplete();
    }

    //when clicking on back Button on ActionBar calling this method

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


    @Override
    protected void onResume() {
        super.onResume();

        showDialog[0] = false;
        showDialog[1] = false;

        workName.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                workNameAutocomplete.showPopup( s );

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

    }


    //this method for clicking on components

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.datePicker_text:
                datePickerDialog = DatePickerDialog.newInstance( this
                        , persianCalendar.getPersianYear()
                        , persianCalendar.getPersianMonth()
                        , persianCalendar.getPersianDay() );
                datePickerDialog.show( getFragmentManager(), "" );
                break;
            case R.id.add_button:
                if (inputEditTExtCheck()) {

                    Work work = new Work( workName.getText().toString()
                            , this.day
                            , this.month
                            , this.year
                            , seekBarPoint.getProgress() ).setDayName( this.dayName );
                    Log.i( MessageTags.ACTIVITY_ADD_WORK, "Add Button" );
                    Toast.makeText( this, getString( R.string.toast_add_button_save_work ), Toast.LENGTH_SHORT ).show();
                    try {
                        dataBaseController = new DataBaseController( getApplicationContext() );
                        dataBaseController.addWorkList( work );
                        dataBaseController.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
            case R.id.workName_text:
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService( INPUT_METHOD_SERVICE );
                inputMethodManager.toggleSoftInput( InputMethodManager.SHOW_FORCED, 0 );
                workName.requestFocus();
                break;

        }
    }


    private boolean inputEditTExtCheck() {

        if (!workName.getText().toString().equals( "" )) {
            showDialog[0] = true;
        }
        if (!dateText.getText().toString().equals( "--/--/--" )) {
            showDialog[1] = true;
        }

        if (workName.getText().toString().equals( "" )) {
            workNameAlert = new AlertDialog.Builder( this )
                    .setTitle( getString( R.string.alert_dialog_error ) )
                    .setMessage( R.string.add_work_activity_work_name )
                    .setCancelable( false )
                    .setPositiveButton( getString( R.string.alert_dialog_button_ok ), this ).show();
        } else if (dateText.getText().toString().equals( "--/--/--" )) {
            dateAlert = new AlertDialog.Builder( this )
                    .setTitle( getString( R.string.alert_dialog_error ) )
                    .setMessage( R.string.add_work_activity_date )
                    .setCancelable( false )
                    .setPositiveButton( getString( R.string.alert_dialog_button_ok ), this ).show();
        }
        return showDialog[0] && showDialog[1];
    }


    //setting Data of Date by PersianCalender

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        dateText.setText( date );
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
        this.dayName = persianCalendar.getPersianWeekDayName();

    }


    //setting ability of Auto Complete for WorkName EditText

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

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (dialog.equals( workNameAlert )) {
            workNameMaterial.callOnClick();
            workName.callOnClick();
            showDialog[0] = true;
        } else if (dialog.equals( dateAlert )) {
            dateMaterial.callOnClick();
            dateText.callOnClick();
            showDialog[1] = true;
        }
    }


    //executing when user changing progress of seekBar

    @Override
    public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
        pointText.setText( String.valueOf( progress ) );
    }

    @Override
    public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

    }

    @Override
    public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

    }

}
