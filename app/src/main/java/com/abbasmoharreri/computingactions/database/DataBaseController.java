package com.abbasmoharreri.computingactions.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.abbasmoharreri.computingactions.model.Day;
import com.abbasmoharreri.computingactions.model.Month;
import com.abbasmoharreri.computingactions.model.Work;
import com.abbasmoharreri.computingactions.model.Year;

import java.util.ArrayList;
import java.util.List;

public class DataBaseController {

    protected SQLiteDatabase db;
    protected Cursor cursor;
    protected Day maxDay;
    protected Month maxMonth;
    protected Year maxYear;
    Context context;


    //opening DataBase

    public DataBaseController(Context context) {
        this.db = new DataBase( context ).getWritableDatabase();
        this.maxDay = new Day();
        this.maxMonth = new Month();
        this.maxYear = new Year();
    }

    //closing Date Base

    public void close() {
        db.close();
        cursor.close();
    }

    //getting variables for other classes

    public Year getMaxYear() {
        return maxYear;
    }

    public Month getMaxMonth() {
        return maxMonth;
    }

    public Day getMaxDay() {
        return maxDay;
    }

    //getting all max of date

    public void getAllMax() {
        maxYear();
        maxMonth();
        maxDay();
    }

    //select max of year

    public void maxYear() {

        String query = "SELECT "
                + DataBase.KEY_ID + ","
                + DataBase.KEY_DATE_YEAR + ","
                + DataBase.KEY_POINT + ","
                + DataBase.KEY_WORK_COUNT + ","
                + DataBase.KEY_CONDITION
                + " FROM " + DataBase.NAME_TABLE_YEAR_LIST
                + " WHERE " + DataBase.KEY_DATE_YEAR
                + "=(SELECT MAX(" + DataBase.KEY_DATE_YEAR + ") FROM " + DataBase.NAME_TABLE_YEAR_LIST + ")";
        cursor = db.rawQuery( query, null );

        if (cursor.moveToFirst())
            this.maxYear.setId( cursor.getInt( 0 ) )
                    .setDateYear( cursor.getInt( 1 ) )
                    .setPoint( cursor.getInt( 2 ) )
                    .setWorkCount( cursor.getInt( 3 ) )
                    .setCondition( cursor.getString( 4 ) );
    }

    //select max of month

    public void maxMonth() {

        String query = "SELECT "
                + DataBase.KEY_ID + ","
                + DataBase.KEY_NAME + ","
                + DataBase.KEY_DATE_MONTH + ","
                + DataBase.KEY_DATE_YEAR + ","
                + DataBase.KEY_POINT + ","
                + DataBase.KEY_WORK_COUNT + ","
                + DataBase.KEY_CONDITION
                + " FROM " + DataBase.NAME_TABLE_MONTH_LIST
                + " WHERE " + DataBase.KEY_DATE_MONTH
                + "=(SELECT MAX(" + DataBase.KEY_DATE_MONTH + ") FROM " + DataBase.NAME_TABLE_MONTH_LIST
                + " WHERE "
                + DataBase.KEY_DATE_YEAR
                + " =( SELECT " + DataBase.KEY_ID + " FROM " + DataBase.NAME_TABLE_YEAR_LIST
                + " WHERE " + DataBase.KEY_DATE_YEAR
                + " = ( SELECT MAX(" + DataBase.KEY_DATE_YEAR + ") FROM " + DataBase.NAME_TABLE_YEAR_LIST + " ) ) )";
        cursor = db.rawQuery( query, null );

        if (cursor.moveToFirst())
            this.maxMonth.setId( cursor.getInt( 0 ) )
                    .setName( cursor.getString( 1 ) )
                    .setDateMonth( cursor.getInt( 2 ) )
                    .setDateYear( cursor.getInt( 3 ) )
                    .setPoint( cursor.getInt( 4 ) )
                    .setWorkCount( cursor.getInt( 5 ) )
                    .setCondition( cursor.getString( 6 ) );
    }

    //select max of day

    public void maxDay() {

        String query = "SELECT "
                + DataBase.KEY_ID + ","
                + DataBase.KEY_NAME + ","
                + DataBase.KEY_DATE_DAY + ","
                + DataBase.KEY_DATE_MONTH + ","
                + DataBase.KEY_DATE_YEAR + ","
                + DataBase.KEY_POINT + ","
                + DataBase.KEY_WORK_COUNT + ","
                + DataBase.KEY_CONDITION
                + " FROM " + DataBase.NAME_TABLE_DAY_LIST
                + " WHERE " + DataBase.KEY_DATE_DAY
                + " = ( SELECT MAX(" + DataBase.KEY_DATE_DAY + ") FROM " + DataBase.NAME_TABLE_DAY_LIST
                + " WHERE " + DataBase.KEY_DATE_YEAR
                + " =( SELECT " + DataBase.KEY_ID + " FROM " + DataBase.NAME_TABLE_YEAR_LIST
                + " WHERE " + DataBase.KEY_DATE_YEAR
                + " = ( SELECT MAX(" + DataBase.KEY_DATE_YEAR + ") FROM " + DataBase.NAME_TABLE_YEAR_LIST + " ))"
                + " AND "
                + DataBase.KEY_DATE_MONTH + " =( SELECT " + DataBase.KEY_ID + " FROM " + DataBase.NAME_TABLE_MONTH_LIST
                + " WHERE " + DataBase.KEY_DATE_MONTH
                + " =( SELECT MAX(" + DataBase.KEY_DATE_MONTH + ") FROM " + DataBase.NAME_TABLE_MONTH_LIST
                + " WHERE " + DataBase.KEY_DATE_YEAR
                + " =( SELECT " + DataBase.KEY_ID + " FROM " + DataBase.NAME_TABLE_YEAR_LIST
                + " WHERE " + DataBase.KEY_DATE_YEAR
                + " = ( SELECT MAX(" + DataBase.KEY_DATE_YEAR + ") FROM " + DataBase.NAME_TABLE_YEAR_LIST + " ) ) ) ) )";

        cursor = db.rawQuery( query, null );

        if (cursor.moveToFirst())
            this.maxDay.setId( cursor.getInt( 0 ) )
                    .setName( cursor.getString( 1 ) )
                    .setDateDay( cursor.getInt( 2 ) )
                    .setDateMonth( cursor.getInt( 3 ) )
                    .setDateYear( cursor.getInt( 4 ) )
                    .setPoint( cursor.getInt( 5 ) )
                    .setWorkCount( cursor.getInt( 6 ) )
                    .setCondition( cursor.getString( 7 ) );
    }

    //getting id of work from db by name

    protected int work_getId(String name) {
        int id = 0;
        cursor = db.query( DataBase.NAME_TABLE_WORK_NAME
                , new String[]{DataBase.KEY_ID}
                , DataBase.KEY_NAME + " =? "
                , new String[]{String.valueOf( name )}
                , null
                , null
                , null
                , null );

        if (cursor.moveToFirst()) {
            id = cursor.getInt( 0 );
        }
        return id;
    }

    //getting id of day from db by date

    protected int day_getId(int dateDay, int idMonth, int idYear) {
        int id = 0;
        cursor = db.query( DataBase.NAME_TABLE_DAY_LIST
                , new String[]{DataBase.KEY_ID}
                , DataBase.KEY_DATE_DAY + " =? AND " + DataBase.KEY_DATE_MONTH + " =? AND " + DataBase.KEY_DATE_YEAR + " =? "
                , new String[]{String.valueOf( dateDay ), String.valueOf( idMonth ), String.valueOf( idYear )}
                , null
                , null
                , null
                , null );

        if (cursor.moveToFirst()) {
            id = cursor.getInt( 0 );
        }
        return id;
    }

    //getting id month from db by date

    protected int month_getId(int dateMonth, int idYear) {
        int id = 0;
        cursor = db.query( DataBase.NAME_TABLE_MONTH_LIST
                , new String[]{DataBase.KEY_ID}
                , DataBase.KEY_DATE_MONTH + " =? AND " + DataBase.KEY_DATE_YEAR + " =? "
                , new String[]{String.valueOf( dateMonth ), String.valueOf( idYear )}
                , null
                , null
                , null
                , null );

        if (cursor.moveToFirst()) {
            id = cursor.getInt( 0 );
        }
        return id;
    }

    //getting id year from db by date

    protected int year_getId(int dateYear) {
        int id = 0;
        cursor = db.query( DataBase.NAME_TABLE_YEAR_LIST
                , new String[]{DataBase.KEY_ID}
                , DataBase.KEY_DATE_YEAR + " =? "
                , new String[]{String.valueOf( dateYear )}
                , null
                , null
                , null
                , null );

        if (cursor.moveToFirst()) {
            id = cursor.getInt( 0 );
        }
        return id;
    }

    //getting name of work from db by id

    public String work_getName(int id) {
        String name = "";
        cursor = db.query( DataBase.NAME_TABLE_WORK_NAME
                , new String[]{DataBase.KEY_NAME}
                , DataBase.KEY_ID + " =? "
                , new String[]{String.valueOf( id )}
                , null
                , null
                , null
                , null );

        if (cursor.moveToFirst()) {
            name = cursor.getString( 0 );
        }
        return name;
    }

    //getting value of day from db by id

    public int day_value(int id) {
        int value = 0;
        cursor = db.query( DataBase.NAME_TABLE_DAY_LIST
                , new String[]{DataBase.KEY_DATE_DAY}
                , DataBase.KEY_ID + " =? "
                , new String[]{String.valueOf( id )}
                , null
                , null
                , null
                , null );

        if (cursor.moveToFirst()) {
            value = cursor.getInt( 0 );
        }
        return value;
    }

    //getting value of month from db by id

    public int month_value(int id) {
        int value = 0;
        cursor = db.query( DataBase.NAME_TABLE_MONTH_LIST
                , new String[]{DataBase.KEY_DATE_MONTH}
                , DataBase.KEY_ID + " =? "
                , new String[]{String.valueOf( id )}
                , null
                , null
                , null
                , null );

        if (cursor.moveToFirst()) {
            value = cursor.getInt( 0 );
        }
        return value;
    }

    //getting value of year from db by id

    public int year_value(int id) {
        int value = 0;
        cursor = db.query( DataBase.NAME_TABLE_YEAR_LIST
                , new String[]{DataBase.KEY_DATE_YEAR}
                , DataBase.KEY_ID + " =? "
                , new String[]{String.valueOf( id )}
                , null
                , null
                , null
                , null );

        if (cursor.moveToFirst()) {
            value = cursor.getInt( 0 );
        }
        return value;
    }

    //select list of works in date period

    public List<Work> selectPeriodWork(int idStartDay, int idStartMonth, int idStartYear, int idEndDay, int idEndMonth, int idEndYear) {

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
                        + DataBase.KEY_DATE_YEAR + " <=?"
                , new String[]{String.valueOf( idStartDay )
                        , String.valueOf( idStartMonth )
                        , String.valueOf( idStartYear )
                        , String.valueOf( idEndDay )
                        , String.valueOf( idEndMonth )
                        , String.valueOf( idEndYear )}
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
            w.setName( name );
        }

        for (Work w : selectedWorks) {
            w.setDateDay( day_value( w.getDateDay() ) )
                    .setDateMonth( month_value( w.getDateMonth() ) )
                    .setDateYear( year_value( w.getDateYear() ) );
        }

        return selectedWorks;
    }

    //select day by id

    public Day selectDay(int id) {

        cursor = db.query( DataBase.NAME_TABLE_DAY_LIST
                , new String[]{DataBase.KEY_ID, DataBase.KEY_NAME, DataBase.KEY_DATE_DAY, DataBase.KEY_DATE_MONTH, DataBase.KEY_DATE_YEAR, DataBase.KEY_POINT, DataBase.KEY_WORK_COUNT, DataBase.KEY_CONDITION}
                , DataBase.KEY_ID + " =? "
                , new String[]{String.valueOf( id )}
                , null
                , null
                , null
                , null );
        Day day = null;
        if (cursor != null && cursor.moveToFirst()) {
            day = new Day( Integer.parseInt( cursor.getString( 0 ) )
                    , cursor.getString( 1 )
                    , Integer.parseInt( cursor.getString( 2 ) )
                    , Integer.parseInt( cursor.getString( 3 ) )
                    , Integer.parseInt( cursor.getString( 4 ) )
                    , Integer.parseInt( cursor.getString( 5 ) )
                    , Integer.parseInt( cursor.getString( 6 ) )
                    , cursor.getString( 7 ) );
        }
        return day;
    }


    public Month selectMonth(int id) {

        cursor = db.query( DataBase.NAME_TABLE_MONTH_LIST
                , new String[]{DataBase.KEY_ID, DataBase.KEY_NAME, DataBase.KEY_DATE_MONTH, DataBase.KEY_DATE_YEAR, DataBase.KEY_POINT, DataBase.KEY_WORK_COUNT, DataBase.KEY_CONDITION}
                , DataBase.KEY_ID + " =? "
                , new String[]{String.valueOf( id )}
                , null
                , null
                , null
                , null );
        Month month = null;
        if (cursor != null && cursor.moveToFirst()) {
            month = new Month( Integer.parseInt( cursor.getString( 0 ) )
                    , cursor.getString( 1 )
                    , Integer.parseInt( cursor.getString( 2 ) )
                    , Integer.parseInt( cursor.getString( 3 ) )
                    , Integer.parseInt( cursor.getString( 4 ) )
                    , Integer.parseInt( cursor.getString( 5 ) )
                    , cursor.getString( 6 ) );
        }
        return month;
    }


    public Year selectYear(int id) {

        cursor = db.query( DataBase.NAME_TABLE_YEAR_LIST
                , new String[]{DataBase.KEY_ID, DataBase.KEY_DATE_YEAR, DataBase.KEY_POINT, DataBase.KEY_WORK_COUNT, DataBase.KEY_CONDITION}
                , DataBase.KEY_ID + " =? "
                , new String[]{String.valueOf( id )}
                , null
                , null
                , null
                , null );
        Year year = null;
        if (cursor != null && cursor.moveToFirst()) {
            year = new Year( Integer.parseInt( cursor.getString( 0 ) )
                    , Integer.parseInt( cursor.getString( 1 ) )
                    , Integer.parseInt( cursor.getString( 2 ) )
                    , Integer.parseInt( cursor.getString( 3 ) )
                    , cursor.getString( 4 ) );
        }
        return year;
    }

    //this method for detecting existence work name and day and month and year

    private void isContentExist(Work work) {


        if (!isWorkNameExist( work.getName() )) {
            addWorkName( work.getName() );
        }
        if (!isYearExist( work )) {
            Year year = new Year( work.getDateYear() );
            addYearList( year );
        }
        if (!isMonthExist( work )) {
            Month month = new Month( work.getDateMonth(), work.getDateYear() );
            addMonthList( month );
        }
        if (!isDayExist( work )) {
            Day day = new Day( work.getDateDay(), work.getDateMonth(), work.getDateYear() ).setName( work.getDayName() );
            addDayList( day );
        }
    }

    //insert new work name in to the db

    private void addWorkName(String name) {

        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.KEY_NAME, name );

        db.insert( DataBase.NAME_TABLE_WORK_NAME, null, contentValues );
    }

    //detecting existence work name in table

    private boolean isWorkNameExist(String name) {
        cursor = db.query( DataBase.NAME_TABLE_WORK_NAME
                , new String[]{DataBase.KEY_ID}
                , DataBase.KEY_NAME + " =? "
                , new String[]{String.valueOf( name )}
                , null
                , null
                , null
                , null );
        return cursor.moveToFirst();
    }

    //insert new work in to the db

    public void addWorkList(Work work) {

        isContentExist( work );

        int idYear = year_getId( work.getDateYear() );
        int idMonth = month_getId( work.getDateMonth(), idYear );
        int idDay = day_getId( work.getDateDay(), idMonth, idYear );

        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.KEY_NAME, work_getId( work.getName() ) );
        contentValues.put( DataBase.KEY_DATE_DAY, idDay );
        contentValues.put( DataBase.KEY_DATE_MONTH, idMonth );
        contentValues.put( DataBase.KEY_DATE_YEAR, idYear );
        contentValues.put( DataBase.KEY_CONDITION, work.getCondition() );
        contentValues.put( DataBase.KEY_POINT, work.getPoint() );
        contentValues.put( DataBase.KEY_TYPE, work.getType() );

        db.insert( DataBase.NAME_TABLE_WORK_LIST, null, contentValues );

        updateAll( work );
    }

    //detecting existence work in WorkList table

    public boolean isExistWorkList(Work work) {

        isContentExist( work );

        int idYear = year_getId( work.getDateYear() );
        int idMonth = month_getId( work.getDateMonth(), idYear );
        int idDay = day_getId( work.getDateDay(), idMonth, idYear );
        int idName = work_getId( work.getName() );

        cursor = db.query( DataBase.NAME_TABLE_WORK_LIST
                , new String[]{DataBase.KEY_ID
                        , DataBase.KEY_NAME
                        , DataBase.KEY_DATE_DAY
                        , DataBase.KEY_DATE_MONTH
                        , DataBase.KEY_DATE_YEAR
                        , DataBase.KEY_CONDITION
                        , DataBase.KEY_POINT
                        , DataBase.KEY_TYPE}
                , DataBase.KEY_NAME + "=? AND "
                        + DataBase.KEY_DATE_DAY + "=? AND "
                        + DataBase.KEY_DATE_MONTH + "=? AND "
                        + DataBase.KEY_DATE_YEAR + "=?"
                , new String[]{String.valueOf( idName )
                        , String.valueOf( idDay )
                        , String.valueOf( idMonth )
                        , String.valueOf( idYear )}
                , null
                , null
                , null
                , null );

        return cursor.moveToFirst();
    }

    //insert new day in to the db

    public void addDayList(Day day) {
        int idYear = year_getId( day.getDateYear() );
        int idMonth = month_getId( day.getDateMonth(), idYear );

        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.KEY_NAME, day.getName() );
        contentValues.put( DataBase.KEY_DATE_DAY, day.getDateDay() );
        contentValues.put( DataBase.KEY_DATE_MONTH, idMonth );
        contentValues.put( DataBase.KEY_DATE_YEAR, idYear );
        contentValues.put( DataBase.KEY_POINT, day.getPoint() );
        contentValues.put( DataBase.KEY_WORK_COUNT, day.getWorkCount() );
        contentValues.put( DataBase.KEY_CONDITION, day.getCondition() );

        db.insert( DataBase.NAME_TABLE_DAY_LIST, null, contentValues );
    }

    //detecting existence day in table

    private boolean isDayExist(Work work) {
        int idYear = year_getId( work.getDateYear() );
        int idMonth = month_getId( work.getDateMonth(), idYear );

        cursor = db.query( DataBase.NAME_TABLE_DAY_LIST
                , new String[]{DataBase.KEY_ID}
                , DataBase.KEY_DATE_DAY + " =? AND " + DataBase.KEY_DATE_MONTH + " =? AND " + DataBase.KEY_DATE_YEAR + " =? "
                , new String[]{String.valueOf( work.getDateDay() ), String.valueOf( idMonth ), String.valueOf( idYear )}
                , null
                , null
                , null
                , null );
        return cursor.moveToFirst();
    }

    //insert new month in to the db

    public void addMonthList(Month month) {
        int idYear = year_getId( month.getDateYear() );

        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.KEY_NAME, month.getName() );
        contentValues.put( DataBase.KEY_DATE_MONTH, month.getDateMonth() );
        contentValues.put( DataBase.KEY_DATE_YEAR, idYear );
        contentValues.put( DataBase.KEY_POINT, month.getPoint() );
        contentValues.put( DataBase.KEY_WORK_COUNT, month.getWorkCount() );
        contentValues.put( DataBase.KEY_CONDITION, month.getCondition() );

        db.insert( DataBase.NAME_TABLE_MONTH_LIST, null, contentValues );
    }

    //detecting existence month in table

    private boolean isMonthExist(Work work) {
        int idYear = year_getId( work.getDateYear() );
        cursor = db.query( DataBase.NAME_TABLE_MONTH_LIST
                , new String[]{DataBase.KEY_ID}
                , DataBase.KEY_DATE_MONTH + " =? AND " + DataBase.KEY_DATE_YEAR + " =? "
                , new String[]{String.valueOf( work.getDateMonth() ), String.valueOf( idYear )}
                , null
                , null
                , null
                , null );
        return cursor.moveToFirst();
    }

    //insert new year in to the db

    public void addYearList(Year year) {

        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.KEY_DATE_YEAR, year.getDateYear() );
        contentValues.put( DataBase.KEY_POINT, year.getPoint() );
        contentValues.put( DataBase.KEY_WORK_COUNT, year.getWorkCount() );
        contentValues.put( DataBase.KEY_CONDITION, year.getCondition() );

        db.insert( DataBase.NAME_TABLE_YEAR_LIST, null, contentValues );
    }

    //detecting existence year in table

    private boolean isYearExist(Work work) {
        cursor = db.query( DataBase.NAME_TABLE_YEAR_LIST
                , new String[]{DataBase.KEY_ID}
                , DataBase.KEY_DATE_YEAR + " =? "
                , new String[]{String.valueOf( work.getDateYear() )}
                , null
                , null
                , null
                , null );
        return cursor.moveToFirst();
    }

    //update values in max day and month and year

    private void updateAll(Work work) {

        getAllMax();

        int point, count;

        point = (maxDay.getPoint() + work.getPoint());
        maxDay.setPoint( point );
        count = (maxDay.getWorkCount() + 1);
        maxDay.setWorkCount( count );

        point = (maxMonth.getPoint() + work.getPoint());
        maxMonth.setPoint( point );
        count = (maxMonth.getWorkCount() + 1);
        maxMonth.setWorkCount( count );

        point = (maxYear.getPoint() + work.getPoint());
        maxYear.setPoint( point );
        count = (maxYear.getWorkCount() + 1);
        maxYear.setWorkCount( count );

        updateDay( maxDay );
        updateMonth( maxMonth );
        updateYear( maxYear );
    }

    //update values in tables when deleting item

    private void updateAfterDelete(Work work) {

        int idYear = year_getId( work.getDateYear() );
        int idMonth = month_getId( work.getDateMonth(), idYear );
        int idDay = day_getId( work.getDateDay(), idMonth, idYear );
        Day day = selectDay( idDay );
        Month month = selectMonth( idMonth );
        Year year = selectYear( idYear );

        day.setWorkCount( day.getWorkCount() - 1 );
        day.setPoint( day.getPoint() - work.getPoint() );
        month.setWorkCount( month.getWorkCount() - 1 );
        month.setPoint( month.getPoint() - work.getPoint() );
        year.setWorkCount( year.getWorkCount() - 1 );
        year.setPoint( year.getPoint() - work.getPoint() );

        updateDay( day );
        updateMonth( month );
        updateYear( year );
    }

    //update values in workList table

    public void updateWork(Work work) {

        isExistWorkList( work );

        int idYear = year_getId( work.getDateYear() );
        int idMonth = month_getId( work.getDateMonth(), idYear );
        int idDay = day_getId( work.getDateDay(), idMonth, idYear );
        int idName = work_getId( work.getName() );

        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.KEY_NAME, idName );
        contentValues.put( DataBase.KEY_DATE_DAY, idDay );
        contentValues.put( DataBase.KEY_DATE_MONTH, idMonth );
        contentValues.put( DataBase.KEY_DATE_YEAR, idYear );
        contentValues.put( DataBase.KEY_CONDITION, work.getCondition() );
        contentValues.put( DataBase.KEY_POINT, work.getPoint() );
        contentValues.put( DataBase.KEY_TYPE, work.getType() );

        db.update( DataBase.NAME_TABLE_WORK_LIST, contentValues, DataBase.KEY_ID + " =?", new String[]{String.valueOf( work.getId() )} );
    }

    //update values in dayList table

    public void updateDay(Day day) {

        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.KEY_NAME, day.getName() );
        contentValues.put( DataBase.KEY_POINT, day.getPoint() );
        contentValues.put( DataBase.KEY_WORK_COUNT, day.getWorkCount() );
        contentValues.put( DataBase.KEY_CONDITION, day.getCondition() );

        db.update( DataBase.NAME_TABLE_DAY_LIST, contentValues, DataBase.KEY_ID + " =?", new String[]{String.valueOf( day.getId() )} );
    }

    //update values in monthList table

    public void updateMonth(Month month) {

        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.KEY_NAME, month.getName() );
        contentValues.put( DataBase.KEY_POINT, month.getPoint() );
        contentValues.put( DataBase.KEY_WORK_COUNT, month.getWorkCount() );
        contentValues.put( DataBase.KEY_CONDITION, month.getCondition() );

        db.update( DataBase.NAME_TABLE_MONTH_LIST, contentValues, DataBase.KEY_ID + " =?", new String[]{String.valueOf( month.getId() )} );
    }

    //update values in yearList table

    public void updateYear(Year year) {

        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.KEY_POINT, year.getPoint() );
        contentValues.put( DataBase.KEY_WORK_COUNT, year.getWorkCount() );
        contentValues.put( DataBase.KEY_CONDITION, year.getCondition() );

        db.update( DataBase.NAME_TABLE_YEAR_LIST, contentValues, DataBase.KEY_ID + " =?", new String[]{String.valueOf( year.getId() )} );
    }

    //delete work from db

    public void deleteWork(Work work) {

        db.delete( DataBase.NAME_TABLE_WORK_LIST, DataBase.KEY_ID + " =?", new String[]{String.valueOf( work.getId() )} );
        updateAfterDelete( work );
    }

    //delete day from db

    public void deleteDay(Day day) {

        db.delete( DataBase.NAME_TABLE_DAY_LIST, DataBase.KEY_ID + " =?", new String[]{String.valueOf( day.getId() )} );
    }

    //delete month from db

    public void deleteMonth(Month month) {

        db.delete( DataBase.NAME_TABLE_MONTH_LIST, DataBase.KEY_ID + " =?", new String[]{String.valueOf( month.getId() )} );
    }

    //delete year fom db

    public void deleteYear(Year year) {

        db.delete( DataBase.NAME_TABLE_YEAR_LIST, DataBase.KEY_ID + " =?", new String[]{String.valueOf( year.getId() )} );
    }

    //select all work name

    public List<Work> getAllWorkName() {
        List<Work> workList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DataBase.NAME_TABLE_WORK_NAME;

        cursor = db.rawQuery( selectQuery, null );

        if (cursor.moveToFirst()) {
            do {
                Work work = new Work().setName( cursor.getString( 1 ) );

                workList.add( work );

            } while (cursor.moveToNext());
        }
        return workList;
    }
}
