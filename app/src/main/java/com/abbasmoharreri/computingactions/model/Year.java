package com.abbasmoharreri.computingactions.model;

import com.abbasmoharreri.computingactions.finalVariables.Conditions;

public class Year {

    private int dateYear;
    private int point = 0;
    private int workCount = 0;
    private String condition = Conditions.Medium;
    private int id;
    private float percent = 0;
    private BonusAndFine bonusAndFine;

    public Year() {

    }

    public Year(int dateYear) {
        this.dateYear = dateYear;
    }

    public Year(int id, int dateYear, int point, int workCount, String condition) {
        this.id = id;
        this.dateYear = dateYear;
        this.point = point;
        this.workCount = workCount;
        this.condition = condition;
    }


    public int getPoint() {
        return point;
    }

    public Year setPoint(int point) {
        this.point = point;
        BonusAndFine bonusAndFine = new BonusAndFine();
        conditionAnalyze( bonusAndFine );
        return this;
    }

    public int getWorkCount() {
        return workCount;
    }

    public Year setWorkCount(int workCount) {
        this.workCount = workCount;
        BonusAndFine bonusAndFine = new BonusAndFine();
        conditionAnalyze( bonusAndFine );
        return this;
    }

    public String getCondition() {
        return condition;
    }

    public Year setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public int getDateYear() {
        return dateYear;
    }

    public Year setDateYear(int dateYear) {
        this.dateYear = dateYear;
        return this;
    }

    public int getId() {
        return id;
    }

    public Year setId(int id) {
        this.id = id;
        return this;
    }

    public float getPercent() {
        return percent;
    }

    public Year setPercent(float percent) {
        this.percent = percent;
        return this;
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
