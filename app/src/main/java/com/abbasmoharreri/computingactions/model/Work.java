package com.abbasmoharreri.computingactions.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.abbasmoharreri.computingactions.finalVariables.Conditions;

public class Work implements Parcelable {


    private int dateDay;
    private int dateMonth;
    private int dateYear;
    private String name;
    private String condition;
    private int point;
    private String type;
    private int id;
    private float percent;
    private String dayName;


    public Work() {
    }

    public Work(String name, int dateDay, int dateMonth, int dateYear, int point) {
        BonusAndFine bonusAndFine = new BonusAndFine();
        this.name = name;
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.point = point;
        conditionAnalyse( bonusAndFine );
    }

    public Work(int id, String name, int dateDay, int dateMonth, int dateYear, int point) {
        BonusAndFine bonusAndFine = new BonusAndFine();
        this.id = id;
        this.name = name;
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.point = point;
        conditionAnalyse( bonusAndFine );
    }

    //this method for getting values from Parcelable

    protected Work(Parcel in) {
        dateDay = in.readInt();
        dateMonth = in.readInt();
        dateYear = in.readInt();
        name = in.readString();
        condition = in.readString();
        point = in.readInt();
        type = in.readString();
        id = in.readInt();
        percent = in.readFloat();
        dayName = in.readString();
    }

    //this method for creating Parcelable by Work class

    public static final Creator<Work> CREATOR = new Creator<Work>() {
        @Override
        public Work createFromParcel(Parcel in) {
            return new Work( in );
        }

        @Override
        public Work[] newArray(int size) {
            return new Work[size];
        }
    };


    public String getDayName() {
        return dayName;
    }

    public Work setDayName(String dayName) {
        this.dayName = dayName;
        return this;
    }

    public int getDateDay() {
        return dateDay;
    }

    public int getDateMonth() {
        return dateMonth;
    }

    public int getDateYear() {
        return dateYear;
    }

    public String getName() {
        return name;
    }

    public Work setName(String name) {
        this.name = name;
        return this;
    }

    public Work setDateDay(int dateDay) {
        this.dateDay = dateDay;
        return this;
    }

    public Work setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
        return this;
    }

    public Work setDateYear(int dateYear) {
        this.dateYear = dateYear;
        return this;
    }

    public String getType() {
        return type;
    }

    public Work setType(String type) {
        this.type = type;
        return this;
    }

    public int getPoint() {
        return point;
    }

    public Work setPoint(int point) {
        BonusAndFine bonusAndFine = new BonusAndFine();
        this.point = point;
        conditionAnalyse( bonusAndFine );
        return this;
    }

    public String getCondition() {
        return condition;
    }

    public Work setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public int getId() {
        return id;
    }

    public Work setId(int id) {
        this.id = id;
        return this;
    }

    public float getPercent() {
        return percent;
    }

    public Work setPercent(float percent) {
        this.percent = percent;
        return this;
    }


    //setting condition by point

    private void conditionAnalyse(BonusAndFine bonusAndFine) {

        if (point <= bonusAndFine.getMaxFinePoint()) {
            this.condition = Conditions.Bad;
        } else if (bonusAndFine.getMaxFinePoint() < point && point < bonusAndFine.getMinBonusPoint()) {
            this.condition = Conditions.Medium;
        } else if (bonusAndFine.getMinBonusPoint() <= point) {
            this.condition = Conditions.Good;
        }

    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    //this method for setting values in Parcelable for transition data with 2 activity

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( dateDay );
        dest.writeInt( dateMonth );
        dest.writeInt( dateYear );
        dest.writeString( name );
        dest.writeString( condition );
        dest.writeInt( point );
        dest.writeString( type );
        dest.writeInt( id );
        dest.writeFloat( percent );
        dest.writeString( dayName );
    }
}
