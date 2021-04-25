package com.abbasmoharreri.computingactions.database.reports;


import android.content.Context;

import com.abbasmoharreri.computingactions.database.DataBaseController;
import com.abbasmoharreri.computingactions.model.Container;
import com.abbasmoharreri.computingactions.model.Work;

import java.util.ArrayList;
import java.util.List;

public class PointsReport extends DataBaseController {

    private List<Container> result;

    private int idStartDay;
    private int idStartMonth;
    private int idStartYear;
    private int idEndDay;
    private int idEndMonth;
    private int idEndYear;
    private List<String> countedWorks;
    private List<Work> workList;
    private int[] point = new int[2];

    public PointsReport(Context context) {
        super( context );

    }


    public PointsReport(Context context, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
        super( context );
        this.idStartYear = year_getId( startYear );
        this.idStartMonth = month_getId( startMonth, this.idStartYear );
        this.idStartDay = day_getId( startDay, this.idStartMonth, this.idStartYear );
        this.idEndYear = year_getId( endYear );
        this.idEndMonth = month_getId( endMonth, this.idEndYear );
        this.idEndDay = day_getId( endDay, this.idEndMonth, this.idEndYear );
        result = new ArrayList<>();
        countedWorks = new ArrayList<>();
        generateData();
    }


    //generate data for report

    private void generateData() {

        // select list of works in date period from db

        List<Work> selectedWorks = selectPeriodWork( idStartDay, idStartMonth, idStartYear, idEndDay, idEndMonth, idEndYear );

        // this variable for sum of point and count of works

        point[0] = 0;
        point[1] = 0;

        // searching in list of works by name

        for (int i = 0; i < selectedWorks.size(); i++) {
            if (countedWorks != null) {
                if (!countedWorks.contains( selectedWorks.get( i ).getName() )) {
                    workList = new ArrayList<>();
                    computePoint( selectedWorks, selectedWorks.get( i ) );
                    result.add( new Container().setName( selectedWorks.get( i ).getName() ).setPoint( point[0] ).setCount( point[1] ).setWorkList( workList ) );
                    countedWorks.add( selectedWorks.get( i ).getName() );
                }
            }
        }
    }


    //computing sum of points


    private void computePoint(List<Work> works, Work w) {
        int[] sumPoint = new int[2];
        sumPoint[0] = 0;
        sumPoint[1] = 0;
        for (int i = 0; i < works.size(); i++) {
            if (works.get( i ).getName().equals( w.getName() )) {
                sumPoint[0] += works.get( i ).getPoint();
                sumPoint[1]++;
                workList.add( works.get( i ) );
            }
        }
        point = sumPoint;
    }


    //getting list of result for other classes


    public List<Container> getResult() {
        return result;
    }
}
