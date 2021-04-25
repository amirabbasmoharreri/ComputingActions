package com.abbasmoharreri.computingactions.ui;

import android.content.Context;
import android.content.Intent;

import com.abbasmoharreri.computingactions.SpecialWorkActivity;
import com.abbasmoharreri.computingactions.finalVariables.ReportType;
import com.abbasmoharreri.computingactions.model.Container;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;

public class ComboChart {

    private ComboLineColumnChartView chart;
    private ComboLineColumnChartData data;

    private boolean isCubic = false;
    private boolean isHasLabels = true;
    private boolean isHasLines = true;
    private boolean isHasPoints = true;
    private boolean isHasAxes = true;
    private boolean isHasAxesNames = true;

    private Context context;
    private int numOfLines;
    private int numOfColumn;
    private List<Container> values;
    private String nameX;
    private String nameY;
    private int type;

    // private LottieAnimationView lottieAnimationView;


    public ComboChart(Context context, ComboLineColumnChartView Chart, int numOfLines, int numOfColumn, List<Container> values, String nameX, String nameY, int type) {
        this.context = context;
        this.chart = Chart;
        this.numOfLines = numOfLines;
        this.numOfColumn = numOfColumn;
        this.values = values;
        this.nameX = nameX;
        this.nameY = nameY;
        this.type = type;
        Chart.setOnValueTouchListener( new SelectValue() );
        generateData();
    }

    //generate data for showing in chart

    public void generateData() {
        // Chart looks the best when line data and column data have similar maximum viewports.
        data = new ComboLineColumnChartData( generateColumnData(), generateLineData() );

        //setting String of x chart and y chart

        if (isHasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines( true );
            if (isHasAxesNames) {
                axisX.setName( nameX );
                List<AxisValue> axisValues = new ArrayList<>();
                for (int i = 0; i < values.size(); i++) {
                    AxisValue axisValue = new AxisValue( i );
                    String label = values.get( i ).getName();
                    axisValue.setLabel( label );
                    axisValues.add( axisValue );
                }
                axisX.setValues( axisValues );
                axisY.setName( nameY );
            }
            data.setAxisXBottom( axisX );
            data.setAxisYLeft( axisY );
        } else {
            data.setAxisXBottom( null );
            data.setAxisYLeft( null );
        }

        chart.setComboLineColumnChartData( data );
        chart.setZoomType( ZoomType.HORIZONTAL );

    }

    //generate data for Lines in chart

    public LineChartData generateLineData() {
        List<Line> lines = new ArrayList<>();

        for (int i = 0; i < numOfLines; i++) {

            List<PointValue> pointValues = new ArrayList<>();
            for (int j = 0; j < numOfColumn; j++) {
                if (i == 0) {
                    pointValues.add( new PointValue( j, 200 ) );
                } else {
                    pointValues.add( new PointValue( j, -200 ) );
                }
            }

            Line line = new Line( pointValues );
            line.setColor( ChartUtils.nextColor() );
            line.setCubic( isCubic );
            line.setHasLabels( isHasLabels );
            line.setHasLines( isHasLines );
            line.setHasPoints( isHasPoints );
            lines.add( line );
        }

        return new LineChartData( lines ).setBaseValue( Float.NEGATIVE_INFINITY );

    }

    //generate data for Column in chart

    private ColumnChartData generateColumnData() {
        int numSubColumns = 1;

        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> subColumnValues;
        for (int i = 0; i < numOfColumn; ++i) {

            subColumnValues = new ArrayList<>();
            for (int j = 0; j < numSubColumns; ++j) {
                subColumnValues.add( new SubcolumnValue().setLabel( values.get( i ).getName() ).setValue( 200 ).setColor( ChartUtils.nextColor() ) )
                ;
            }

            Column column = new Column( subColumnValues );
            columns.add( column );
        }

        return new ColumnChartData( columns );
    }

    //showing or hiding labels

    public void toggleLables() {

        isHasLabels = !isHasLabels;
        generateData();
    }

    //showing cubic or no in point of Lines

    public void toggleCubic() {

        isCubic = !isCubic;
        generateData();
    }

    //showing or hiding lines

    public void toggleLines() {

        isHasLines = !isHasLines;
        generateData();
    }

    //showing or hiding points in Lines

    public void togglePoint() {
        isHasPoints = !isHasPoints;
        generateData();
    }

    //showing or hiding Axes in chart

    public void toggleAxes() {
        isHasAxes = !isHasAxes;
        generateData();
    }

    //showing or hiding Axes Name in chart

    public void toggleAxesNames() {
        isHasAxesNames = !isHasAxesNames;
        generateData();
    }

    //resting all values

    public void reset() {
        isHasAxes = true;
        isHasAxesNames = true;
        isCubic = false;
        isHasLabels = false;
        isHasLines = true;
        isHasPoints = true;
    }

    //setting animation for chart


    public void prepareDataAnimation() {

        int i = 0;
        int j = 0;

        chart.cancelDataAnimation();

        // Line animations
        for (Line line : data.getLineChartData().getLines()) {
            for (PointValue value : line.getValues()) {
                // Here I modify target only for Y values but it is OK to modify X targets as well.
                if (i == 0) {
                    value.setTarget( value.getX(), getValueType( (int) value.getX() )[1] );
                } else {
                    value.setTarget( value.getX(), getValueType( (int) value.getX() )[2] );
                }
            }
            i++;
        }

        // Columns animations
        for (Column column : data.getColumnChartData().getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setTarget( getValueType( j )[0] );
            }
            j++;
        }


        chart.startDataAnimation();
    }

    //getting values for showing in chart

    private float[] getValueType(int index) {
        float[] value = new float[3];
        value[0] = 0;
        value[1] = 0;
        value[2] = 0;

        switch (type) {
            case ReportType.POINT_REPORT:
                value[0] = values.get( index ).getPoint();
                value[1] = values.get( index ).getMaxMediumPoint();
                value[2] = values.get( index ).getMinMediumPoint();
                break;
            case ReportType.FREQUENCY_OF_WORK_REPORT:
                value[0] = values.get( index ).getCount();
                value[1] = 0;
                value[2] = 0;
                break;
            case ReportType.CONDITION_REPORT:
                value[0] = values.get( index ).getPercent();
                value[1] = 0;
                value[2] = 0;
                break;
            case ReportType.ASC_AND_DESC_REPORT:
                value[0] = values.get( index ).getPoint();
                value[1] = values.get( index ).getMaxMediumPoint();
                value[2] = values.get( index ).getMinMediumPoint();
                break;
            case ReportType.SPECIAL_WORK_REPORT:
                value[0] = values.get( index ).getPoint();
                value[1] = values.get( index ).getMaxMediumPoint();
                value[2] = values.get( index ).getMinMediumPoint();
                break;
        }
        return value;
    }

    //this method for clicking on column in chart

    public class SelectValue implements ComboLineColumnChartOnValueSelectListener {

        @Override
        public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {

            Intent intent = new Intent( context, SpecialWorkActivity.class );
            intent.putExtra( "columnIndex", columnIndex );
            intent.putExtra( "works", values.get( columnIndex ) );
            context.startActivity( intent );
        }

        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {

        }

        @Override
        public void onValueDeselected() {

        }
    }
}
