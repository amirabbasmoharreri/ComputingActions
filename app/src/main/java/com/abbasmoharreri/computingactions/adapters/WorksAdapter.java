package com.abbasmoharreri.computingactions.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.abbasmoharreri.computingactions.R;
import com.abbasmoharreri.computingactions.SpecialWorkActivity;
import com.abbasmoharreri.computingactions.database.DataBaseController;
import com.abbasmoharreri.computingactions.finalVariables.Conditions;
import com.abbasmoharreri.computingactions.model.Work;
import com.abbasmoharreri.computingactions.ui.CustomDialogUpdate;

import java.util.List;


public class WorksAdapter extends RecyclerView.Adapter<WorksAdapter.ViewHolder>  {


    private ViewHolder viewHolder;
    private List<Work> work;
    private Context context;

    public WorksAdapter(Context context, List<Work> work) {
        this.context = context;
        this.work = work;
    }


    // this method implementation view of works for showing in SpecialWorkActivity


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from( context );
        View contentView = inflater.inflate( R.layout.show_work, parent, false );
        viewHolder = new ViewHolder( contentView );


        return viewHolder;
    }


    // when showing works in SpecialWorkActivity set value of components


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        TextView tvName = holder.tvName;
        TextView tvCondition = holder.tvCondition;
        TextView tvPoint = holder.tvPoint;
        TextView tvDate = holder.tvDate;
        CardView workCardView = holder.workCardView;

        Work work1 = work.get( position );
        tvName.setText( work1.getName() );
        tvCondition.setText( work1.getCondition() );
        tvPoint.setText( String.valueOf( work1.getPoint() ) );
        tvDate.setText( work1.getDateYear() + "/" + work1.getDateMonth() + "/" + work1.getDateDay() );

        switch (work1.getCondition()) {
            case Conditions.Good:
                workCardView.setCardBackgroundColor( context.getColor( R.color.good_condition ) );
                break;
            case Conditions.Medium:
                workCardView.setCardBackgroundColor( context.getColor( R.color.medium_condition ) );
                break;
            case Conditions.Bad:
                workCardView.setCardBackgroundColor( context.getColor( R.color.bad_condition ) );
                break;
        }

        workCardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogUpdate update = new CustomDialogUpdate( context, work, position );
                update.show();
            }
        } );


        workCardView.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopup( v, position );
                return false;
            }
        } );


    }

    @Override
    public int getItemCount() {
        return work.size();
    }


    public void showPopup(View v, final int position) {
        PopupMenu popupMenu = new PopupMenu( context, v );
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate( R.menu.delete_menu, popupMenu.getMenu() );
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_delete_item) {
                    try {
                        DataBaseController dataBaseController = new DataBaseController( context );
                        dataBaseController.deleteWork( work.get( position ) );
                        work.remove( position );
                        notifyDataSetChanged();
                        Toast.makeText( context, R.string.toast_delete_item ,Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        } );
    }


    // implement  ViewHolder Class for hold instance of components


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvCondition;
        TextView tvPoint;
        TextView tvDate;
        CardView workCardView;

        ViewHolder(View itemView) {
            super( itemView );

            tvName = (TextView) itemView.findViewById( R.id.tvWorkName );
            tvCondition = (TextView) itemView.findViewById( R.id.tvCondition );
            tvPoint = (TextView) itemView.findViewById( R.id.tvPoint );
            tvDate = itemView.findViewById( R.id.tvDate );
            workCardView = (CardView) itemView.findViewById( R.id.work_CardView );
        }
    }
}
