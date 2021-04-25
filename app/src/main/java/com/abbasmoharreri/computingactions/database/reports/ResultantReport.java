package com.abbasmoharreri.computingactions.database.reports;

import android.content.Context;

import com.abbasmoharreri.computingactions.R;
import com.abbasmoharreri.computingactions.database.DataBase;
import com.abbasmoharreri.computingactions.database.DataBaseController;
import com.abbasmoharreri.computingactions.finalVariables.Conditions;
import com.abbasmoharreri.computingactions.model.Container;
import com.abbasmoharreri.computingactions.model.Day;
import com.abbasmoharreri.computingactions.model.Month;
import com.abbasmoharreri.computingactions.model.Work;
import com.abbasmoharreri.computingactions.model.Year;

import java.util.ArrayList;
import java.util.List;

public class ResultantReport extends DataBaseController {

    private Context context;
    private String condition;
    private List<Container> dayResult;
    private List<Container> monthResult;
    private List<Container> yearResult;
    private Year maxResultYear;
    private Month maxResultMonth;
    private Day maxResultDay;


    public ResultantReport(Context context) {
        super( context );
        this.context = context;
        getAllMax();

        dayResult = new ArrayList<>();
        monthResult = new ArrayList<>();
        yearResult = new ArrayList<>();
        maxResultYear = getMaxYear();
        maxResultMonth = getMaxMonth();
        maxResultDay = getMaxDay();

        day_condition();
        month_condition();
        year_condition();
    }


    // select works in latest date for day

    private List<Work> selectWork_day() {
        List<Work> selectedWorks = new ArrayList<>();

        cursor = db.query( DataBase.NAME_TABLE_WORK_LIST
                , new String[]{DataBase.KEY_ID
                        , DataBase.KEY_NAME
                        , DataBase.KEY_DATE_DAY
                        , DataBase.KEY_DATE_MONTH
                        , DataBase.KEY_DATE_YEAR
                        , DataBase.KEY_CONDITION
                        , DataBase.KEY_POINT}
                , DataBase.KEY_DATE_DAY + " =? AND "
                        + DataBase.KEY_DATE_MONTH + " =? AND "
                        + DataBase.KEY_DATE_YEAR + " =? "
                , new String[]{String.valueOf( maxDay.getId() )
                        , String.valueOf( maxMonth.getId() )
                        , String.valueOf( maxYear.getId() )}
                , null
                , null
                , null
                , null );

        if (cursor.moveToFirst()) {
            do {
                Work work = new Work()
                        .setId( cursor.getInt( 0 ) )
                        .setName( cursor.getString( 1 ) )
                        .setDateDay( cursor.getInt( 2 ) )
                        .setDateMonth( cursor.getInt( 3 ) )
                        .setDateYear( cursor.getInt( 4 ) )
                        .setCondition( cursor.getString( 5 ) )
                        .setPoint( cursor.getInt( 6 ) );
                selectedWorks.add( work );
            } while (cursor.moveToNext());
        }

        return selectedWorks;

    }


    // select works in latest date for month

    private List<Work> selectWork_month() {
        List<Work> selectedWorks = new ArrayList<>();

        cursor = db.query( DataBase.NAME_TABLE_WORK_LIST
                , new String[]{DataBase.KEY_ID
                        , DataBase.KEY_NAME
                        , DataBase.KEY_DATE_DAY
                        , DataBase.KEY_DATE_MONTH
                        , DataBase.KEY_DATE_YEAR
                        , DataBase.KEY_CONDITION
                        , DataBase.KEY_POINT}
                , DataBase.KEY_DATE_MONTH + " =? AND "
                        + DataBase.KEY_DATE_YEAR + " =? "
                , new String[]{String.valueOf( maxMonth.getId() )
                        , String.valueOf( maxYear.getId() )}
                , null
                , null
                , null
                , null );

        if (cursor.moveToFirst()) {
            do {
                Work work = new Work()
                        .setId( cursor.getInt( 0 ) )
                        .setName( cursor.getString( 1 ) )
                        .setDateDay( cursor.getInt( 2 ) )
                        .setDateMonth( cursor.getInt( 3 ) )
                        .setDateYear( cursor.getInt( 4 ) )
                        .setCondition( cursor.getString( 5 ) )
                        .setPoint( cursor.getInt( 6 ) );
                selectedWorks.add( work );
            } while (cursor.moveToNext());
        }

        return selectedWorks;

    }


    // select works in latest date for year

    private List<Work> selectWork_year() {
        List<Work> selectedWorks = new ArrayList<>();

        cursor = db.query( DataBase.NAME_TABLE_WORK_LIST
                , new String[]{DataBase.KEY_ID
                        , DataBase.KEY_NAME
                        , DataBase.KEY_DATE_DAY
                        , DataBase.KEY_DATE_MONTH
                        , DataBase.KEY_DATE_YEAR
                        , DataBase.KEY_CONDITION
                        , DataBase.KEY_POINT}
                , DataBase.KEY_DATE_YEAR + " =? "
                , new String[]{String.valueOf( maxYear.getId() )}
                , null
                , null
                , null
                , null );

        if (cursor.moveToFirst()) {
            do {
                Work work = new Work()
                        .setId( cursor.getInt( 0 ) )
                        .setName( cursor.getString( 1 ) )
                        .setDateDay( cursor.getInt( 2 ) )
                        .setDateMonth( cursor.getInt( 3 ) )
                        .setDateYear( cursor.getInt( 4 ) )
                        .setCondition( cursor.getString( 5 ) )
                        .setPoint( cursor.getInt( 6 ) );
                selectedWorks.add( work );
            } while (cursor.moveToNext());
        }

        return selectedWorks;

    }


    //generate data for day

    public void day_condition() {
        List<Day> dayList = new ArrayList<>();

        //select list of works in max day

        List<Work> selectedDay = selectWork_day();

        // this variable for computing sum of points for one works
        int sum;

        //this variable for computing sum of points for every works

        int totalSum = 0;

        //searching in list of works by condition

        for (int j = 1; j <= 3; j++) {
            getType( j );
            sum = 0;
            for (int i = 0; i < selectedDay.size(); i++) {

                if (selectedDay.get( i ).getCondition().equals( this.condition )) {
                    sum += selectedDay.get( i ).getPoint();
                    totalSum += selectedDay.get( i ).getPoint();
                }
            }
            dayList.add( new Day( maxResultDay.getDateDay() ).setCondition( this.condition ).setPercent( percentCompare( sum, totalSum ) ) );
        }

        for (Day d : dayList) {
            dayResult.add( new Container().setName( d.getCondition() ).setPercent( d.getPercent() ).setDescription( maxResultYear.getDateYear() + "/" + maxResultMonth.getDateMonth() + "/" + maxResultDay.getDateDay() ).setDescription2( "برآیند روز" ) );
        }

        setConditionName( dayResult );
    }

    //generate data for month

    public void month_condition() {
        List<Month> monthList = new ArrayList<>();

        //select list of works in max month

        List<Work> selectedMonth = selectWork_month();

        //this variable for sum of points for one work

        int sum = 0;

        //this variable for sum of points for every works

        int totalSum = 0;

        //searching in list of works by condition

        for (int j = 1; j <= 3; j++) {
            getType( j );
            sum = 0;

            for (int i = 0; i < selectedMonth.size(); i++) {
                if (selectedMonth.get( i ).getCondition().equals( this.condition )) {
                    sum += selectedMonth.get( i ).getPoint();
                    totalSum += selectedMonth.get( i ).getPoint();
                }
            }
            monthList.add( new Month( maxResultMonth.getDateMonth() ).setCondition( this.condition ).setPercent( percentCompare( sum, totalSum ) ) );
        }

        for (Month m : monthList) {
            monthResult.add( new Container().setName( m.getCondition() ).setPercent( m.getPercent() ).setDescription( maxResultYear.getDateYear() + "/" + maxResultMonth.getDateMonth() ).setDescription2( "برآیند ماه" ) );
        }

        setConditionName( monthResult );
    }

    //generate data for year

    public void year_condition() {
        List<Year> yearList = new ArrayList<>();

        //select list of works in max year

        List<Work> selectedYear = selectWork_year();

        //this variable for sum of points for one work

        int sum = 0;

        //this variable for sum of points for every works

        int totalSum = 0;

        //searching in list of works by condition

        for (int j = 1; j <= 3; j++) {
            getType( j );
            sum = 0;
            for (int i = 0; i < selectedYear.size(); i++) {
                if (selectedYear.get( i ).getCondition().equals( this.condition )) {
                    sum += selectedYear.get( i ).getPoint();
                    totalSum += selectedYear.get( i ).getPoint();
                }
            }
            yearList.add( new Year( maxResultYear.getDateYear() ).setCondition( this.condition ).setPercent( percentCompare( sum, totalSum ) ) );
        }

        for (Year y : yearList) {
            yearResult.add( new Container().setName( y.getCondition() ).setPercent( y.getPercent() ).setDescription( maxResultYear.getDateYear() + "" ).setDescription2( "برآیند سال" ) );
        }

        setConditionName( yearResult );
    }

    //setting condition by number

    private void getType(int j) {

        switch (j) {
            case 1:
                this.condition = Conditions.Good;
                break;
            case 2:
                this.condition = Conditions.Medium;
                break;
            case 3:
                this.condition = Conditions.Bad;
                break;
        }
    }


    //getting list of result for other classes


    public List<Container> getDayResult() {
        return dayResult;
    }

    public List<Container> getMonthResult() {
        return monthResult;
    }

    public List<Container> getYearResult() {
        return yearResult;
    }


    //computing percent


    private float percentCompare(float sum, float totalSum) {
        return (sum * 100 / totalSum);
    }


    //setting name of conditions for list of result

    private void setConditionName(List<Container> result) {

        for (int i = 0; i < result.size(); i++) {
            switch (result.get( i ).getName()) {
                case Conditions.Good:
                    result.get( i ).setName( context.getResources().getString( R.string.Good ) );
                    break;
                case Conditions.Medium:
                    result.get( i ).setName( context.getResources().getString( R.string.Medium ) );
                    break;
                case Conditions.Bad:
                    result.get( i ).setName( context.getResources().getString( R.string.Bad ) );
                    break;
            }
        }
    }

}
