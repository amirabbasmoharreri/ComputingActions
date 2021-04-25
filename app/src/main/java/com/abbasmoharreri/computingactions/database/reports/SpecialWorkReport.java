package com.abbasmoharreri.computingactions.database.reports;

import android.content.Context;

import com.abbasmoharreri.computingactions.database.DataBase;
import com.abbasmoharreri.computingactions.database.DataBaseController;
import com.abbasmoharreri.computingactions.model.Container;
import com.abbasmoharreri.computingactions.model.Work;

import java.util.ArrayList;
import java.util.List;

public class SpecialWorkReport extends DataBaseController {

    private List<Container> result;

    private int idStartDay;
    private int idStartMonth;
    private int idStartYear;
    private int idEndDay;
    private int idEndMonth;
    private int idEndYear;
    private int idName;
    private List<String> countedDate;
    private List<Work> workList;
    private int[] point = new int[2];


    public SpecialWorkReport(Context context) {
        super( context );

    }


    public SpecialWorkReport(Context context, String name, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
        super( context );
        this.idName = work_getId( name );
        this.idStartYear = year_getId( startYear );
        this.idStartMonth = month_getId( startMonth, this.idStartYear );
        this.idStartDay = day_getId( startDay, this.idStartMonth, this.idStartYear );
        this.idEndYear = year_getId( endYear );
        this.idEndMonth = month_getId( endMonth, this.idEndYear );
        this.idEndDay = day_getId( endDay, this.idEndMonth, this.idEndYear );
        result = new ArrayList<>();
        countedDate = new ArrayList<>();
        generateData();
    }

    // select list of works in date period with id from db

    private List<Work> selectPeriodSpecialWork(int idName, int idStartDay, int idStartMonth, int idStartYear, int idEndDay, int idEndMonth, int idEndYear) {

        cursor = db.query( DataBase.NAME_TABLE_WORK_LIST
                , new String[]{DataBase.KEY_ID
                        , DataBase.KEY_NAME
                        , DataBase.KEY_DATE_DAY
                        , DataBase.KEY_DATE_MONTH
                        , DataBase.KEY_DATE_YEAR
                        , DataBase.KEY_POINT}
                , DataBase.KEY_DATE_DAY + " >=? AND "
                        + DataBase.KEY_DATE_MONTH + " >=? AND "
                        + DataBase.KEY_DATE_YEAR + " >=? AND "
                        + DataBase.KEY_DATE_DAY + " <=? AND "
                        + DataBase.KEY_DATE_MONTH + " <=? AND "
                        + DataBase.KEY_DATE_YEAR + " <=? AND "
                        + DataBase.KEY_NAME + " =? "
                , new String[]{String.valueOf( idStartDay )
                        , String.valueOf( idStartMonth )
                        , String.valueOf( idStartYear )
                        , String.valueOf( idEndDay )
                        , String.valueOf( idEndMonth )
                        , String.valueOf( idEndYear )
                        , String.valueOf( idName )}
                , null
                , null
                , null
                , null );
        List<Work> selectedWorks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Work work = new Work( Integer.parseInt( cursor.getString( 0 ) )
                        , cursor.getString( 1 )
                        , Integer.parseInt( cursor.getString( 2 ) )
                        , Integer.parseInt( cursor.getString( 3 ) )
                        , Integer.parseInt( cursor.getString( 4 ) )
                        , Integer.parseInt( cursor.getString( 5 ) ) );
                selectedWorks.add( work );
            } while (cursor.moveToNext());
        }

        for (Work w : selectedWorks) {
            String name = work_getName( Integer.parseInt( w.getName() ) );
            int day = day_value( w.getDateDay() );
            int month = month_value( w.getDateMonth() );
            int year = year_value( w.getDateYear() );

            w.setName( name );
            w.setDayName( selectDay( w.getDateDay() ).getName() );
            w.setDateDay( day );
            w.setDateMonth( month );
            w.setDateYear( year );
        }

        return selectedWorks;
    }


    //generate data for report

    private void generateData() {

        // select list of works in date period with id from db

        List<Work> selectedWorks = selectPeriodSpecialWork( idName, idStartDay, idStartMonth, idStartYear, idEndDay, idEndMonth, idEndYear );

        //this array for sum of points and count of works

        String stDate;
        point[0] = 0;
        point[1] = 0;

        //searching in list of works by date

        for (int i = 0; i < selectedWorks.size(); i++) {
            stDate = selectedWorks.get( i ).getDateDay() + "/"
                    + selectedWorks.get( i ).getDateMonth() + "/"
                    + selectedWorks.get( i ).getDateYear();

            if (countedDate != null) {
                if (!countedDate.contains( stDate )) {
                    workList = new ArrayList<>();
                    computePoint( selectedWorks, selectedWorks.get( i ) );
                    result.add( new Container().setName( stDate ).setPoint( point[0] ).setCount( point[1] ).setWorkList( workList ) );
                    countedDate.add( stDate );
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
            if (works.get( i ).getDateDay() == w.getDateDay() && works.get( i ).getDateMonth() == w.getDateMonth() && works.get( i ).getDateYear() == w.getDateYear()) {
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
