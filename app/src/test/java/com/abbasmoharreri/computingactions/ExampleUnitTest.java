package com.abbasmoharreri.computingactions;

import android.content.Intent;
import android.test.mock.MockContext;

import com.abbasmoharreri.computingactions.database.DataBaseController;
import com.abbasmoharreri.computingactions.model.Container;
import com.abbasmoharreri.computingactions.model.Work;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals( 4, 2 + 2 );
    }

    @Test
    public void pointReport_isCorrect() {
        TestProgram testProgram = new TestProgram();
        List<Work> works = new ArrayList<>();
        Work work = new Work( 1, "ab", 3, 3, 97, 10 );
        works.add( new Work( 1, "ab", 3, 3, 97, 10 ) );
        works.add( new Work( 1, "dc", 3, 3, 97, 20 ) );
        works.add( new Work( 1, "ab", 3, 3, 97, 30 ) );
        works.add( new Work( 1, "dc", 3, 3, 97, 40 ) );
        works.add( new Work( 1, "fl", 3, 3, 97, 50 ) );
        works.add( new Work( 1, "fl", 3, 3, 97, 60 ) );

        assertEquals( testProgram.computePoint( works, work, work.getPoint() )[0], 40 );
    }

    @Test
    public void valueOfContainer_isCorrect() {
        Container c = new Container();
        c.setCount( 5 );
        assertEquals( c.getMaxMediumPoint(), 25 );
        assertEquals( c.getMinMediumPoint(), -25 );
    }

    @Test
    public void conditionReport_isCorrect() {
        TestProgram testProgram = new TestProgram();
        List<Work> works = new ArrayList<>();
        works.add( new Work( 1, "ab", 3, 3, 97, 10 ) );
        works.add( new Work( 1, "ab", 3, 3, 97, 10 ) );
        works.add( new Work( 1, "dc", 3, 3, 97, 0 ) );
        works.add( new Work( 1, "ab", 3, 3, 97, 3 ) );
        works.add( new Work( 1, "dc", 3, 3, 97, 4 ) );
        works.add( new Work( 1, "fl", 3, 3, 97, 5 ) );
        works.add( new Work( 1, "fl", 3, 3, 97, 6 ) );

        testProgram.generateData( works );
        assertEquals( testProgram.getResult().get( 0 ).getPercent(), 68.42, 68.42 );
        assertEquals( testProgram.getResult().get( 1 ).getPercent(), 31.57, 31.57 );
    }


    @Test
    public void DataBase_isCorrerct() {

        /*MockContext context = new MockContext();
        Intent intent = new Intent( String.valueOf( MainActivity.class ) );
        context.startActivity( intent );

        DataBaseController dataBaseController = new DataBaseController( context );
        dataBaseController.maxDay();
        assertEquals( dataBaseController.getMaxDay().getDateDay(), 3 );*/
    }

}