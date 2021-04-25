package com.abbasmoharreri.computingactions.database.reports;

import android.content.Context;

import com.abbasmoharreri.computingactions.database.DataBaseController;
import com.abbasmoharreri.computingactions.model.Container;
import com.abbasmoharreri.computingactions.model.Work;

import java.util.ArrayList;
import java.util.List;

public class FrequencyOfWorkReport extends DataBaseController {


    private int idStartDay;
    private int idStartMonth;
    private int idStartYear;
    private int idEndDay;
    private int idEndMonth;
    private int idEndYear;
    private List<String> countedWorks;
    private List<Container> result;
    private List<Work> workList;

    public FrequencyOfWorkReport(Context context, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
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


    // generate data for report


    private void generateData() {

        //select list of works in date period from db

        List<Work> selectedWorks = selectPeriodWork( idStartDay, idStartMonth, idStartYear, idEndDay, idEndMonth, idEndYear );

        //this variable counting works

        int count = 0;

        // searching in list of works by name

        for (int i = 0; i < selectedWorks.size(); i++) {
            if (countedWorks != null) {
                if (!countedWorks.contains( selectedWorks.get( i ).getName() )) {
                    workList = new ArrayList<>();
                    count = computePoint( selectedWorks, selectedWorks.get( i ) );
                    result.add( new Container().setName( selectedWorks.get( i ).getName() ).setCount( count ).setWorkList( workList ) );
                    countedWorks.add( selectedWorks.get( i ).getName() );
                }
            }
        }
    }


    // counting of works


    private int computePoint(List<Work> works, Work w) {
        int sumPoint = 0;
        for (int i = 0; i < works.size(); i++) {
            if (works.get( i ).getName().equals( w.getName() )) {
                workList.add( works.get( i ) );
                sumPoint++;
            }
        }
        return sumPoint;
    }


    // getting list of result for other classes


    public List<Container> getResult() {
        return result;
    }
}
