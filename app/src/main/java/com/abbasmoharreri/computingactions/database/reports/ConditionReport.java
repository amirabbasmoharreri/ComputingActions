package com.abbasmoharreri.computingactions.database.reports;

import android.content.Context;
import android.util.Log;

import com.abbasmoharreri.computingactions.R;
import com.abbasmoharreri.computingactions.database.DataBaseController;
import com.abbasmoharreri.computingactions.model.Container;
import com.abbasmoharreri.computingactions.model.Work;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ConditionReport extends DataBaseController {


    private int idStartDay;
    private int idStartMonth;
    private int idStartYear;
    private int idEndDay;
    private int idEndMonth;
    private int idEndYear;
    private List<String> countedCondition;
    private List<Container> result;
    private Context context;
    private List<Work> workList;


    public ConditionReport(Context context) {
        super( context );
    }

    public ConditionReport(Context context, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
        super( context );
        this.context = context;
        this.idStartYear = year_getId( startYear );
        this.idStartMonth = month_getId( startMonth, this.idStartYear );
        this.idStartDay = day_getId( startDay, this.idStartMonth, this.idStartYear );
        this.idEndYear = year_getId( endYear );
        this.idEndMonth = month_getId( endMonth, this.idEndYear );
        this.idEndDay = day_getId( endDay, this.idEndMonth, this.idEndYear );
        result = new ArrayList<>();
        countedCondition = new ArrayList<>();
        generateData();
    }


    // generate data for report


    private void generateData() {

        // select list of works in date period from db

        List<Work> selectedWorks = selectPeriodWork( idStartDay, idStartMonth, idStartYear, idEndDay, idEndMonth, idEndYear );

        // this variable for computing sum of points


        int point = 0;

        // searching in list of works by condition


        for (int i = 0; i < 3; i++) {
            try {
                if (countedCondition != null) {
                    if (!countedCondition.contains( selectedWorks.get( i ).getCondition() )) {
                        workList = new ArrayList<>();
                        point = computePoint( selectedWorks, selectedWorks.get( i ) );
                        result.add( new Container().setName( selectedWorks.get( i ).getCondition() ).setPoint( point ).setWorkList( workList ) );
                        countedCondition.add( selectedWorks.get( i ).getCondition() );
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        computePercent();
        setConditionName();
    }

    // computing percent of conditions in list


    private void computePercent() {
        float percent;
        float sumPoint = 0;

        for (int i = 0; i < result.size(); i++) {
            sumPoint += Math.abs( result.get( i ).getPoint() );
        }

        for (int i = 0; i < result.size(); i++) {
            percent = (Math.abs( result.get( i ).getPoint() ) * 100 / sumPoint);
            result.get( i ).setPercent( percent );
        }
    }


    // setting condition name for list of result


    private void setConditionName() {

        for (int i = 0; i < result.size(); i++) {
            switch (result.get( i ).getName()) {
                case "Good":
                    result.get( i ).setName( context.getResources().getString( R.string.Good ) );
                    break;
                case "Medium":
                    result.get( i ).setName( context.getResources().getString( R.string.Medium ) );
                    break;
                case "Bad":
                    result.get( i ).setName( context.getResources().getString( R.string.Bad ) );
                    break;
            }
        }
    }


    // computing sum of points of condition


    private int computePoint(List<Work> works, Work w) {
        int sumPoint = 0;
        for (int i = 0; i < works.size(); i++) {
            if (works.get( i ).getCondition().equals( w.getCondition() )) {
                sumPoint += works.get( i ).getPoint();
                workList.add( works.get( i ) );
            }
        }
        return sumPoint;
    }


    // getting list of result for other classes


    public List<Container> getResult() {
        return result;
    }
}
