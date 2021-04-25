package com.abbasmoharreri.computingactions.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.abbasmoharreri.computingactions.LoginActivity;
import com.abbasmoharreri.computingactions.R;

import info.hoang8f.widget.FButton;

public class CustomDialogPassword extends Dialog implements View.OnClickListener {

    private Context context;
    private EditText question, answer;
    private FButton save;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private boolean showPasswordDialogFirst;
    private final String FIRST_SHOW = "first_show";
    private final String QUESTION = "Question";
    private final String ANSWER = "Answer";
    private int count = 0;


    public CustomDialogPassword(@NonNull Context context, boolean showPasswordDialogFirst) {
        super( context );
        this.context = context;
        this.showPasswordDialogFirst = showPasswordDialogFirst;
    }

    //creating Custom dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.password_dialog );

        question = findViewById( R.id.question_edit_text );

        answer = findViewById( R.id.answer_edit_text );


        save = findViewById( R.id.password_dialog_save_button );
        save.setOnClickListener( this );
        count = 0;

        sharedPreferences = context.getSharedPreferences( FIRST_SHOW, Context.MODE_PRIVATE );
        editor = sharedPreferences.edit();
        setTexts();

    }

    //setting text of components in dialog for showing

    private void setTexts() {

        if (!showPasswordDialogFirst) {
            if (count < 3) {
                question.setHint( context.getString( R.string.custom_dialog_password_question ) + (count + 1) );
                question.setText( "" );
                answer.setHint( context.getString( R.string.custom_dialog_password_answer ) + (count + 1) );
                answer.setText( "" );
                save.setText( context.getString( R.string.button_next ) );
            } else if (count == 3) {
                question.setHint( context.getString( R.string.custom_dialog_password_password ) );
                question.setText( "" );
                answer.setHint( context.getString( R.string.custom_dialog_password_retry_password ) );
                answer.setText( "" );
                save.setText( context.getString( R.string.custom_dialog_password_save_button ) );
            }
        } else {
            if (count < 3) {
                question.setText( sharedPreferences.getString( QUESTION + (count + 1), "----" ) );
                answer.setHint( context.getString( R.string.custom_dialog_password_answer ) + (count + 1) );
                answer.setText( "" );
                save.setText( context.getString( R.string.button_next ) );
            } else if (count == 3) {
                question.setHint( context.getString( R.string.custom_dialog_password_password ) );
                question.setText( "" );
                answer.setHint( context.getString( R.string.custom_dialog_password_retry_password ) );
                answer.setText( "" );
                save.setText( context.getString( R.string.custom_dialog_password_save_button ) );
            }
        }

    }

    //this method for clicking on components

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.password_dialog_save_button) {

            if (!showPasswordDialogFirst) {
                if (count < 3) {
                    editor.putString( QUESTION + (count + 1), question.getText().toString() );
                    editor.putString( ANSWER + (count + 1), answer.getText().toString() );
                    editor.apply();
                } else {
                    if (question.getText().toString().equals( answer.getText().toString() )) {
                        try {
                            LoginActivity loginActivity = (LoginActivity) context;
                            loginActivity.writePass( loginActivity.md5( question.getText().toString() ) );
                            dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            } else {

                if (sharedPreferences.getString( ANSWER + (count + 1), "----" ).equals( answer.getText().toString() ) && count < 3) {
                    setTexts();
                } else if (!sharedPreferences.getString( ANSWER + (count + 1), "----" ).equals( answer.getText().toString() )) {
                    dismiss();
                    Toast.makeText( context, context.getString( R.string.toast_mistake_answer ), Toast.LENGTH_LONG ).show();
                } else {
                    if (question.getText().toString().equals( answer.getText().toString() )) {
                        try {
                            LoginActivity loginActivity = (LoginActivity) context;
                            loginActivity.writePass( loginActivity.md5( question.getText().toString() ) );
                            dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            count++;
            setTexts();
        }


    }
}
