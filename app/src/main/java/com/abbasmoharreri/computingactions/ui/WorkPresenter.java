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
import com.abbasmoharreri.computingactions.database.DataBaseController;
import com.abbasmoharreri.computingactions.model.Work;
import com.otaliastudios.autocomplete.RecyclerViewPresenter;

import java.util.ArrayList;
import java.util.List;

public class WorkPresenter extends RecyclerViewPresenter<Work> {

    protected Adapter adapter;
    private DataBaseController dataBaseController;

    public WorkPresenter(Context context) {
        super( context );
        dataBaseController = new DataBaseController( context );
    }

    //setting size of showing popup

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

    //searching in list of values ,if exist showing
    @Override
    protected void onQuery(@Nullable CharSequence query) {
        List<Work> allName = dataBaseController.getAllWorkName();
        if (TextUtils.isEmpty( query )) {
            adapter.setData( allName );
        } else {
            query = query.toString().toLowerCase();
            List<Work> list = new ArrayList<>();
            for (Work w : allName) {
                if (w.getName().toLowerCase().contains( query )) {
                    list.add( w );
                }
                adapter.setData( list );
                //Log.e( "WorkPresenter", "found " + list.size() + " users for query " + query );
            }
        }
        adapter.notifyDataSetChanged();
    }

    //this class is RecycleView's Adapter

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        private List<Work> data;

        //this class for hold components of popup

        class Holder extends RecyclerView.ViewHolder {

            private View root;
            private TextView textView;

            public Holder(View itemView) {
                super( itemView );
                root = itemView;
                textView = itemView.findViewById( R.id.AutoComplete_layout_textView );

            }

        }

        public void setData(List<Work> data) {
            this.data = data;
        }

        //creating adapter

        @NonNull
        @Override
        public Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder( LayoutInflater.from( getContext() ).inflate( R.layout.autocompelete_layout, parent, false ) );
        }

        //setting values for showing

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            if (isEmpty()) {
                holder.textView.setText(getContext().getString( R.string.No_Suggestion ) );
                holder.root.setOnClickListener( null );
            } else {
                final Work work = data.get( position );
                holder.textView.setText( work.getName() );
                holder.root.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dispatchClick( work );
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
