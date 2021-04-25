package com.abbasmoharreri.computingactions;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.abbasmoharreri.computingactions.adapters.WorksAdapter;
import com.abbasmoharreri.computingactions.finalVariables.MessageTags;
import com.abbasmoharreri.computingactions.model.Container;

public class SpecialWorkActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Container result;
    private RecyclerView recyclerView;
    public WorksAdapter worksAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    //creating SpecialWorkActivity and initialize components

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
        setContentView( R.layout.activity_special_work );
        setTitle( getString( R.string.special_work_activity_title ) );

        //enabling backButton in ActionBar

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        swipeRefreshLayout = findViewById( R.id.special_work_refresh );
        swipeRefreshLayout.setOnRefreshListener( this );
        result = getIntent().getParcelableExtra( MessageTags.WORKS );
        recyclerView = findViewById( R.id.rv_works );
        worksAdapter = new WorksAdapter( this, result.getWorkList() );
        recyclerView.setAdapter( worksAdapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( getApplicationContext() ) );
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
    protected void onPause() {
        super.onPause();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
    }

    //this method for refreshing data in RecycleView

    @Override
    public void onRefresh() {
        worksAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing( false );
    }

}
