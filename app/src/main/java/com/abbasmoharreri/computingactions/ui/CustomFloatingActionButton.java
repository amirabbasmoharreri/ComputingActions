package com.abbasmoharreri.computingactions.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import com.abbasmoharreri.computingactions.*;
import com.oguzdev.circularfloatingactionmenu.library.animation.MenuAnimationHandler;

import static com.abbasmoharreri.computingactions.R.anim.rotation_forward;

public class CustomFloatingActionButton {

    private Context context;
    public FloatingActionButton actionButton;
    private FloatingActionMenu actionMenu;
    private SubActionButton.Builder builder;
    public SubActionButton subActionButton1;
    public SubActionButton subActionButton2;
    public SubActionButton subActionButton3;
    public SubActionButton subActionButton4;


    public CustomFloatingActionButton(Context context) {
        this.context = context;
    }

    //creating Custom Floating Action Button

    public void createButton() {

        //initialize ImageViews

        ImageView icon1 = new ImageView( context );
        ImageView icon2 = new ImageView( context );
        ImageView icon3 = new ImageView( context );
        ImageView icon4 = new ImageView( context );
        ImageView icon5 = new ImageView( context );

        //setting icons of buttons

        icon1.setImageDrawable( context.getResources().getDrawable( R.drawable.ic_add ) );
        icon2.setImageDrawable( context.getResources().getDrawable( R.drawable.penalty ) );
        icon3.setImageDrawable( context.getResources().getDrawable( R.drawable.reward ) );
        icon4.setImageDrawable( context.getResources().getDrawable( R.drawable.pie_chart ) );
        icon5.setImageDrawable( context.getResources().getDrawable( R.drawable.work ) );


        //Building buttons

        builder = new SubActionButton.Builder( (Activity) context );
        subActionButton1 = builder.setContentView( icon2 ).build();
        subActionButton1.setId( R.id.subActionButton1 );
        subActionButton2 = builder.setContentView( icon3 ).build();
        subActionButton2.setId( R.id.subActionButton2 );
        subActionButton3 = builder.setContentView( icon4 ).build();
        subActionButton3.setId( R.id.subActionButton3 );
        subActionButton4 = builder.setContentView( icon5 ).build();
        subActionButton4.setId( R.id.subActionButton4 );
        actionButton = new FloatingActionButton.Builder( (Activity) context ).setContentView( icon1 ).build();
        actionButton.setId( R.id.ActionButton );
        actionMenu = new FloatingActionMenu.Builder( (Activity) context )
                .addSubActionView( subActionButton1 )
                .addSubActionView( subActionButton2 )
                .addSubActionView( subActionButton3 )
                .addSubActionView( subActionButton4 )
                .attachTo( actionButton )
                .build();



    }

    //setting animation for closing button

    public void animationClose() {
        actionMenu.close( true );
    }
}
