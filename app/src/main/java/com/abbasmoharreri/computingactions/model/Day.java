package com.abbasmoharreri.computingactions.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.abbasmoharreri.computingactions.finalVariables.Conditions;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;
import java.util.Locale;

public class Day {

    private String name = " ";
    private int dateDay = 0;
    private int dateMonth = 0;
    private int dateYear = 0;
    private int point = 0;
    private int workCount = 0;
    private String condition = Conditions.Medium;
    private int id;
    private float percent = 0;
    private Calendar calendar;
    private PersianCalendar persianCalendar;


    public Day() {
    }

    public Day(int dateDay, int dateMonth, int dateYear) {
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        setNameAutomate();
    }

    public Day(int dateDay) {
        this.dateDay = dateDay;
        setNameAutomate();
    }

    public Day(int id, String name, int dateDay, int dateMonth, int dateYear, int point, int workCount, String condition) {
        this.id = id;
        this.name = name;
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.point = point;
        this.workCount = workCount;
        this.condition = condition;
        setNameAutomate();
    }


    public int getPoint() {
        return point;
    }

    public Day setPoint(int point) {
        this.point = point;
        BonusAndFine bonusAndFine = new BonusAndFine();
        conditionAnalyze( bonusAndFine );
        return this;
    }

    public int getWorkCount() {
        return workCount;
    }

    public Day setWorkCount(int workCount) {
        this.workCount = workCount;
        BonusAndFine bonusAndFine = new BonusAndFine();
        conditionAnalyze( bonusAndFine );
        return this;
    }

    public String getCondition() {
        return condition;
    }

    public Day setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public String getName() {
        return name;
    }

    public Day setName(String name) {
        this.name = name;
        return this;
    }

    public int getDateDay() {
        return dateDay;
    }

    public Day setDateDay(int dateDay) {
        this.dateDay = dateDay;
        setNameAutomate();
        return this;
    }

    public int getDateMonth() {
        return dateMonth;
    }

    public Day setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
        setNameAutomate();
        return this;
    }

    public int getDateYear() {
        return dateYear;
    }

    public Day setDateYear(int dateYear) {
        this.dateYear = dateYear;
        setNameAutomate();
        return this;
    }

    public int getId() {
        return id;
    }

    public Day setId(int id) {
        this.id = id;
        return this;
    }

    public float getPercent() {
        return percent;
    }

    public Day setPercent(float percent) {
        this.percent = percent;
        return this;
    }


    private void setNameAutomate() {
        calendar = Calendar.getInstance();
        persianCalendar = new PersianCalendar();

        calendar.set( dateYear, dateMonth, dateDay );
        persianCalendar.set( dateYear, dateMonth, dateDay );

       /* Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        //Locale locale = Locale.getDefault();

        if (configuration.locale == (new Locale( "en" ))) {

            switch (calendar.get( Calendar.DAY_OF_WEEK )) {
                case Calendar.SATURDAY:
                    this.name = "شنبه";
                    break;
                case Calendar.SUNDAY:
                    this.name = "یکشنبه";
                    break;
                case Calendar.MONDAY:
                    this.name = "دوشنبه";
                    break;
                case Calendar.TUESDAY:
                    this.name = "سه شنبه";
                    break;
                case Calendar.WEDNESDAY:
                    this.name = "چهارشنبه";
                    break;
                case Calendar.THURSDAY:
                    this.name = "پنج شنبه";
                    break;
                case Calendar.FRIDAY:
                    this.name = "جمعه";
                    break;
            }

        } else if (configuration.locale == (new Locale( "fa" ))) {

            switch (persianCalendar.get( Calendar.DAY_OF_WEEK )) {
                case PersianCalendar.SATURDAY:
                    this.name = "Saturday";
                    break;
                case PersianCalendar.SUNDAY:
                    this.name = "Sunday";
                    break;
                case PersianCalendar.MONDAY:
                    this.name = "Monday";
                    break;
                case PersianCalendar.TUESDAY:
                    this.name = "Tuesday";
                    break;
                case PersianCalendar.WEDNESDAY:
                    this.name = "Wednesday";
                    break;
                case PersianCalendar.THURSDAY:
                    this.name = "Thursday";
                    break;
                case PersianCalendar.FRIDAY:
                    this.name = "Friday";
                    break;
            }

        }


        Log.e( "Day :", name );*/

    }


    //setting condition by work count

    private void conditionAnalyze(BonusAndFine bonusAndFine) {
        if (point <= (bonusAndFine.getMaxFinePoint() * workCount)) {
            this.condition = Conditions.Bad;
        } else if ((bonusAndFine.getMaxFinePoint() * workCount) < point && point < (bonusAndFine.getMinBonusPoint() * workCount)) {
            this.condition = Conditions.Medium;
        } else if ((bonusAndFine.getMinBonusPoint() * workCount) <= point) {
            this.condition = Conditions.Good;
        }
    }
}
