package com.abbasmoharreri.computingactions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abbasmoharreri.computingactions.ui.CustomDialogPassword;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final String MY_PASS = "MyPass";
    EditText etPass;
    TextView nextButton;
    TextView saveButton;
    private boolean showTapTarget = false;
    private boolean showPasswordDialog = false;
    private String SHOW_PASSWORD_DIALOG = "showPasswordDialog";
    private String SHOW_TAP_TARGET_LOGIN_ACTIVITY = "showTapTargetLoginActivity";


    //creating LoginActivity and initialize components

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
        setContentView( R.layout.activity_login );
        setTitle( getString( R.string.login_activity_title ) );


        etPass = findViewById( R.id.et_Pass );
        nextButton = findViewById( R.id.tv_login_next );
        saveButton = findViewById( R.id.tv_login_SavePass );

        //this SharePreferences for saving boolean of showing Password Dialog or no

        SharedPreferences sharedPreferences = getSharedPreferences( SHOW_PASSWORD_DIALOG, MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();

        showPasswordDialog = sharedPreferences.getBoolean( SHOW_PASSWORD_DIALOG, false );

        setTabTargetViewShow();

    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
    }

    @Override
    protected void onStart() {
        super.onStart();

        nextButton.setOnClickListener( this );
        saveButton.setOnClickListener( this );
    }


    //writing password in file

    public void writePass(String pass) {

        FileOutputStream outputStream = null;

        try {

            outputStream = openFileOutput( MY_PASS, Context.MODE_PRIVATE );
            outputStream.write( pass.getBytes() );
            outputStream.close();

            Toast.makeText( getApplicationContext(), getString( R.string.toast_saved_password ), Toast.LENGTH_LONG ).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //reading password from file

    private boolean readPass(String pass) {
        BufferedReader input = null;
        File file = null;

        try {
            file = new File( getFilesDir(), MY_PASS );
            input = new BufferedReader( new InputStreamReader( new FileInputStream( file ) ) );

            String line;
            StringBuffer buffer = new StringBuffer();

            while ((line = input.readLine()) != null) {
                buffer.append( line );
            }

            if (buffer.toString().equals( pass ))
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    //create hash from strings

    public String md5(String string) {

        try {
            MessageDigest pass = MessageDigest.getInstance( "MD5" );

            pass.update( string.getBytes() );
            byte messageDigest[] = pass.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte aMessageDigest : messageDigest) {
                hexString.append( Integer.toHexString( 0xFF & aMessageDigest ) );
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //this method for showing TapTargetView

    private void setTabTargetViewShow() {

        SharedPreferences sharedPreferences;
        final SharedPreferences.Editor editor;
        sharedPreferences = getSharedPreferences( SHOW_TAP_TARGET_LOGIN_ACTIVITY, MODE_PRIVATE );
        editor = sharedPreferences.edit();
        showTapTarget = sharedPreferences.getBoolean( SHOW_TAP_TARGET_LOGIN_ACTIVITY, false );

        if (!showTapTarget) {
            TapTargetSequence tapTargetSequence = new TapTargetSequence( this );
            tapTargetSequence.targets( TapTarget.forView( saveButton, getString( R.string.tap_target_title_reset_password_button ), getString( R.string.tap_target_description_reset_password_button ) )
                    .cancelable( false )
                    .tintTarget( false )
                    .targetRadius( 70 ) ).listener( new TapTargetSequence.Listener() {
                @Override
                public void onSequenceFinish() {

                    if (!showPasswordDialog) {
                        CustomDialogPassword customDialogPassword = new CustomDialogPassword( LoginActivity.this, showPasswordDialog );
                        customDialogPassword.show();
                        editor.putBoolean( SHOW_PASSWORD_DIALOG, true );
                        editor.apply();
                    }
                }

                @Override
                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                }

                @Override
                public void onSequenceCanceled(TapTarget lastTarget) {


                }
            } );
            tapTargetSequence.start();
            editor.putBoolean( SHOW_TAP_TARGET_LOGIN_ACTIVITY, true );
            editor.apply();


        }
    }


    //this method for clicking on components

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_login_next:
                if (readPass( md5( etPass.getText().toString() ) )) {
                    Toast.makeText( getApplicationContext(), getString( R.string.toast_true_password ), Toast.LENGTH_SHORT ).show();
                    etPass.setText( "" );
                    Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                    startActivity( intent );
                    overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
                } else {
                    Toast.makeText( getApplicationContext(), getString( R.string.toast_false_password ), Toast.LENGTH_SHORT ).show();
                }
                break;
            case R.id.tv_login_SavePass:
                CustomDialogPassword customDialogPassword = new CustomDialogPassword( this, showPasswordDialog );
                customDialogPassword.show();
                break;
        }


    }
}
