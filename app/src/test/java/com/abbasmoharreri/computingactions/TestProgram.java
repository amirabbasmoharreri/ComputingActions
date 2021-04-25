package com.abbasmoharreri.computingactions;

import com.abbasmoharreri.computingactions.model.Container;
import com.abbasmoharreri.computingactions.model.Work;

import java.util.ArrayList;
import java.util.List;

public class TestProgram {

    List<Container> result;

    public TestProgram() {
        result = new ArrayList<>();
    }

    public int[] computePoint(List<Work> works, Work w, int firstPoint) {
        int[] sumPoint = new int[2];
        sumPoint[0] = firstPoint;
        sumPoint[1] = 1;
        for (int i = 1; i < works.size(); i++) {
            if (works.get( i ).getName().equals( w.getName() )) {
                sumPoint[0] += works.get( i ).getPoint();
                sumPoint[1]++;
                works.remove( works.get( i ).getId() );
            }
        }
        return sumPoint;
    }


    public void generateData(List<Work> selectedWorks) {
        List<Container> result = new ArrayList<>();
        List<String> countedWorks = new ArrayList<>();
        int point = 0;
        for (int i = 0; i < selectedWorks.size(); i++) {
            if (countedWorks != null) {
                if (!countedWorks.contains( selectedWorks.get( i ).getName() )) {
                    point = computePoint( selectedWorks, selectedWorks.get( i ) );
                    result.add( new Container().setName( selectedWorks.get( i ).getName() ).setPoint( point ) );
                    countedWorks.add( selectedWorks.get( i ).getName() );
                }
            }
        }

        computePercent( result );
    }

    private void computePercent(List<Container> result) {
        float percent = 0;
        int sumPoint = 0;

        for (int i = 0; i < result.size(); i++) {
            sumPoint += result.get( i ).getPoint();
        }

        for (int i = 0; i < result.size(); i++) {
            percent = (result.get( i ).getPoint() / sumPoint) * 100;
            result.get( i ).setPercent( percent );
        }
        this.result = result;

    }

    private int computePoint(List<Work> works, Work w) {
        int sumPoint = 0;
        for (int i = 0; i < works.size(); i++) {
            if (works.get( i ).getCondition().equals( w.getCondition() )) {
                sumPoint += w.getPoint();
            }
        }
        return sumPoint;
    }

    public List<Container> getResult() {

        return result;
    }

}
