package com.abbasmoharreri.computingactions.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abbasmoharreri.computingactions.R;
import com.otaliastudios.autocomplete.RecyclerViewPresenter;

import java.util.ArrayList;
import java.util.List;

public class TypePresenter extends RecyclerViewPresenter<String> {

    protected Adapter adapter;

    public TypePresenter(Context context) {
        super( context );
    }

    //setting size of showing popup of Auto Complete

    @Override
    protected PopupDimensions getPopupDimensions() {
        PopupDimensions dims = new PopupDimensions();
        dims.width = 600;
        dims.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        return dims;
    }

    //setting adapter for RecycleView

    @Override
    protected RecyclerView.Adapter instantiateAdapter() {
        adapter = new Adapter();
        return adapter;
    }

    //searching in list of values , if exist showing in popup

    @Override
    protected void onQuery(@Nullable CharSequence query) {
        List<String> allType = new ArrayList<>();
        String[] name = getContext().getResources().getStringArray( R.array.ShowReports );
        for (String s : name) {
            allType.add( s );
        }
        if (TextUtils.isEmpty( query )) {
            adapter.setData( allType );
        } else {
            query = query.toString().toLowerCase();
            List<String> list = new ArrayList<>();
            for (String w : allType) {
                if (w.toLowerCase().contains( query )) {
                    list.add( w );
                }
                adapter.setData( list );
                //Log.e( "WorkPresenter", "found " + list.size() + " users for query " + query );
            }
        }
        adapter.notifyDataSetChanged();
    }

    //this class is RecycleView's adapter

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        private List<String> data;

        //this class for hold components of popup

        class Holder extends RecyclerView.ViewHolder {

            private View root;
            private TextView textView;

            public Holder(View itemView) {
                super( itemView );
                root = itemView;
                textView = ((TextView) itemView.findViewById( R.id.AutoComplete_layout_textView ));

            }

        }


        public void setData(List<String> data) {
            this.data = data;
        }

        //creating Adapter

        @NonNull
        @Override
        public Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder( LayoutInflater.from( getContext() ).inflate( R.layout.autocompelete_layout, parent, false ) );
        }

        //setting values for showing
        @Override
        public void onBindViewHolder(@NonNull Adapter.Holder holder, int position) {
            if (isEmpty()) {
                holder.textView.setText( R.string.No_Suggestion );
                holder.root.setOnClickListener( null );
            } else {
                final String st = data.get( position );
                holder.textView.setText( st );
                holder.root.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dispatchClick( st );
                    }
                } );
            }
        }

        public boolean isEmpty() {
            return data == null || data.isEmpty();
        }

        @Override
        public int getItemCount() {
            return isEmpty() ? 1 : data.size();
        }
    }
}
