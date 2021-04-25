package com.abbasmoharreri.computingactions.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class Container implements Parcelable, Comparable<Container> {

    private String name;
    private int point;
    private int count;
    private float percent;
    private int maxMediumPoint;
    private int minMediumPoint;
    private String description;
    private String description2;
    private List<Work> workList;

    public Container() {
        workList = new ArrayList<>();
    }

    public Container(String name, int point, int count, int percent, int maxMediumPoint, int minMediumPoint) {
        this.name = name;
        this.point = point;
        this.count = count;
        this.percent = percent;
        this.maxMediumPoint = maxMediumPoint;
        this.minMediumPoint = minMediumPoint;
        workList = new ArrayList<>();
    }

    //this method for creating Parcelable by Container class

    public static final Parcelable.Creator<Container> CREATOR = new Parcelable.Creator<Container>() {
        @Override
        public Container createFromParcel(Parcel in) {
            return new Container( in );
        }

        @Override
        public Container[] newArray(int size) {
            return new Container[size];
        }
    };

    //this method for getting values from Parcelable

    public Container(final Parcel in) {
        name = in.readString();
        point = in.readInt();
        count = in.readInt();
        percent = in.readFloat();
        maxMediumPoint = in.readInt();
        minMediumPoint = in.readInt();
        description = in.readString();
        description2 = in.readString();
        workList = (List<Work>) in.readValue( Container.class.getClassLoader() );

    }

    public String getName() {
        return name;
    }

    public Container setName(String name) {
        this.name = name;
        return this;
    }

    public int getPoint() {
        return point;
    }

    public Container setPoint(int point) {
        this.point = point;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Container setCount(int count) {
        this.count = count;
        this.maxMediumPoint = count * 5;
        this.minMediumPoint = count * -5;
        return this;
    }

    public float getPercent() {
        return percent;
    }

    public Container setPercent(float percent) {
        this.percent = percent;
        return this;
    }

    public int getMaxMediumPoint() {
        return maxMediumPoint;
    }

    public Container setMaxMediumPoint(int maxMediumPoint) {
        this.maxMediumPoint = maxMediumPoint;
        return this;
    }

    public int getMinMediumPoint() {
        return minMediumPoint;
    }

    public Container setMinMediumPoint(int minMediumPoint) {
        this.minMediumPoint = minMediumPoint;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Container setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription2() {
        return description2;
    }

    public Container setDescription2(String description2) {
        this.description2 = description2;
        return this;
    }

    public List<Work> getWorkList() {
        return workList;
    }

    public Container setWorkList(List<Work> workList) {
        this.workList = workList;
        return this;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    //this method for setting values in parcelable for transition data with 2 activity

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString( name );
        dest.writeInt( point );
        dest.writeInt( count );
        dest.writeFloat( percent );
        dest.writeInt( maxMediumPoint );
        dest.writeInt( minMediumPoint );
        dest.writeString( description );
        dest.writeString( description2 );
        dest.writeValue( workList );

    }

    //this method for sorting data by point by asc

    @Override
    public int compareTo(@NonNull Container o) {
        int compare = o.getPoint();
        return point - compare;
    }
}
