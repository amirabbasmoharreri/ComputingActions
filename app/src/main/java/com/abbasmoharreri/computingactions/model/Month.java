package com.abbasmoharreri.computingactions.model;

import com.abbasmoharreri.computingactions.finalVariables.Conditions;

public class Month {

    private String name;
    private int dateMonth;
    private int dateYear;
    private int point = 0;
    private int workCount = 0;
    private String condition = Conditions.Medium;
    private int id;
    private float percent = 0;


    public Month() {

    }

    public Month(int dateMonth, int dateYear) {
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        monthName();
    }

    public Month(int dateMonth, int dateYear, int point) {
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.point = point;
        monthName();
    }

    public Month(int dateMonth) {
        this.dateMonth = dateMonth;
        monthName();
    }

    public Month(int id, String name, int dateMonth, int dateYear, int point, int workCount, String condition) {
        this.id = id;
        this.name = name;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.point = point;
        this.workCount = workCount;
        this.condition = condition;
        monthName();
    }


    public int getPoint() {
        return point;
    }

    public Month setPoint(int point) {
        this.point = point;
        BonusAndFine bonusAndFine = new BonusAndFine();
        conditionAnalyze( bonusAndFine );
        return this;
    }

    public int getWorkCount() {
        return workCount;
    }

    public Month setWorkCount(int workCount) {
        this.workCount = workCount;
        BonusAndFine bonusAndFine = new BonusAndFine();
        conditionAnalyze( bonusAndFine );
        return this;
    }

    public String getCondition() {
        return condition;
    }

    public Month setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public String getName() {
        return name;
    }

    public Month setName(String name) {
        this.name = name;
        monthName();
        return this;
    }

    public int getDateMonth() {
        return dateMonth;
    }

    public Month setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
        return this;
    }

    public int getDateYear() {
        return dateYear;
    }

    public Month setDateYear(int dateYear) {
        this.dateYear = dateYear;
        return this;
    }

    public int getId() {
        return id;
    }

    public Month setId(int id) {
        this.id = id;
        return this;
    }

    public float getPercent() {
        return percent;
    }

    public Month setPercent(float percent) {
        this.percent = percent;
        return this;
    }

    private void monthName() {

        switch (dateMonth) {
            case 1:
                this.name = "فروردین";
                break;
            case 2:
                this.name = "اردیبهشت";
                break;
            case 3:
                this.name = "خرداد";
                break;
            case 4:
                this.name = "تیر";
                break;
            case 5:
                this.name = "مرداد";
                break;
            case 6:
                this.name = "شهریور";
                break;
            case 7:
                this.name = "مهر";
                break;
            case 8:
                this.name = "آبان";
                break;
            case 9:
                this.name = "آذر";
                break;
            case 10:
                this.name = "دی";
                break;
            case 11:
                this.name = "بهمن";
                break;
            case 12:
                this.name = "اسفند";
                break;

        }
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
