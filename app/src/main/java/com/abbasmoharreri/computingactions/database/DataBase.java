package com.abbasmoharreri.computingactions.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBase extends SQLiteOpenHelper {

    //final variables for using dataBase

    private final static String DATABASE_NAME = "computingActions";
    private final static int DATABASE_VERSION = 1;

    public final static String KEY_ID = "Id";
    public final static String KEY_NAME = "Name";
    public final static String KEY_DATE_DAY = "DateDay";
    public final static String KEY_DATE_MONTH = "DateMonth";
    public final static String KEY_DATE_YEAR = "DateYear";
    public final static String KEY_POINT = "Point";
    public final static String KEY_CONDITION = "Condition";
    public final static String KEY_TYPE = "Type";
    public final static String KEY_WORK_COUNT = "WorkCount";
    public final static String NAME_TABLE_WORK_LIST = "WorkList";
    public final static String NAME_TABLE_WORK_NAME = "WorkName";
    public final static String NAME_TABLE_DAY_LIST = "DayList";
    public final static String NAME_TABLE_MONTH_LIST = "MonthList";
    public final static String NAME_TABLE_YEAR_LIST = "YearList";


    public DataBase(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    //creating tables for db

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_WORKNAME_TABLE = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE_WORK_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT" + ")";

        String CREATE_WORKLIST_TABLE = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE_WORK_LIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " INTEGER NOT NULL,"
                + KEY_DATE_DAY + " INTEGER NOT NULL,"
                + KEY_DATE_MONTH + " INTEGER NOT NULL,"
                + KEY_DATE_YEAR + " INTEGER NOT NULL,"
                + KEY_CONDITION + " TEXT,"
                + KEY_POINT + " INTEGER,"
                + KEY_TYPE + " TEXT,"
                + "FOREIGN KEY (" + KEY_NAME + ")REFERENCES " + NAME_TABLE_WORK_NAME + "(" + KEY_ID + "),"
                + "FOREIGN KEY (" + KEY_DATE_DAY + ")REFERENCES " + NAME_TABLE_DAY_LIST + "(" + KEY_ID + "),"
                + "FOREIGN KEY (" + KEY_DATE_MONTH + ")REFERENCES " + NAME_TABLE_MONTH_LIST + "(" + KEY_ID + "),"
                + "FOREIGN KEY( " + KEY_DATE_YEAR + " ) REFERENCES " + NAME_TABLE_YEAR_LIST + " (" + KEY_ID + ") ) ";


        String CREATE_DAYLIST_TABLE = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE_DAY_LIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT ,"
                + KEY_DATE_DAY + " INTEGER NOT NULL,"
                + KEY_DATE_MONTH + " INTEGER NOT NULL,"
                + KEY_DATE_YEAR + " INTEGER NOT NULL,"
                + KEY_CONDITION + " TEXT,"
                + KEY_POINT + " INTEGER,"
                + KEY_WORK_COUNT + " INTEGER,"
                + "FOREIGN KEY (" + KEY_DATE_MONTH + ")REFERENCES " + NAME_TABLE_MONTH_LIST + "(" + KEY_ID + "),"
                + "FOREIGN KEY (" + KEY_DATE_YEAR + ")REFERENCES " + NAME_TABLE_YEAR_LIST + "(" + KEY_ID + "))";


        String CREATE_MONTHLIST_TABLE = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE_MONTH_LIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT NOT NULL,"
                + KEY_DATE_MONTH + " INTEGER NOT NULL,"
                + KEY_DATE_YEAR + " INTEGER NOT NULL,"
                + KEY_CONDITION + " TEXT,"
                + KEY_POINT + " INTEGER,"
                + KEY_WORK_COUNT + " INTEGER,"
                + "FOREIGN KEY (" + KEY_DATE_YEAR + ")REFERENCES " + NAME_TABLE_YEAR_LIST + "(" + KEY_ID + "))";


        String CREATE_YEARLIST_TABLE = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE_YEAR_LIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_DATE_YEAR + " INTEGER,"
                + KEY_CONDITION + " TEXT,"
                + KEY_POINT + " INTEGER,"
                + KEY_WORK_COUNT + " INTEGER" + ")";

        db.execSQL( CREATE_WORKNAME_TABLE );
        db.execSQL( CREATE_WORKLIST_TABLE );
        db.execSQL( CREATE_DAYLIST_TABLE );
        db.execSQL( CREATE_MONTHLIST_TABLE );
        db.execSQL( CREATE_YEARLIST_TABLE );

    }

    //upgrade db

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    }