package com.phemie.scnu.laolekang.Fragment4.Health.FootStep;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.phemie.scnu.laolekang.R;
import com.phemie.scnu.laolekang.Fragment4.step.bean.StepData;
import com.phemie.scnu.laolekang.Fragment4.step.utils.DbUtils;
import com.phemie.scnu.laolekang.Fragment4.step.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

public class StepChartActivity extends AppCompatActivity {

    ImageView rreturn;//返回键
    private SharedPreferencesUtils sp;//存储数据

    private LineChartView lineChart;//折线图
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisValues = new ArrayList<AxisValue>();
    private boolean isBind = false;

    String[] times = {"3:00", "6:00", "9:00", "12:00", "15:00", "18:00", "21:00", "23:00"};//X轴的标注
    int[] steps = {500, 1000, 1452, 22754, 36340, 4210, 500, 100};//图表的数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_chart);

        rreturn=(ImageView) findViewById(R.id.rreturn);

        rreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lineChart = (LineChartView) findViewById(R.id.line_chart);
        sp = new SharedPreferencesUtils(this);

        String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
        int s = Integer.parseInt(planWalk_QTY);
        List<StepData> stepDatas = DbUtils.getQueryAll(StepData.class);
       /**
        * 获取到步数的数据times、steps
        */


        getAxisLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化

    }

    /**
     * 初始化LineChart的一些设置
     */
    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.WHITE).setCubic(false);  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(true);//曲线是否平滑
        line.setFilled(true);//是否填充曲线的面积
//      line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(Color.WHITE);  //设置字体颜色
        axisX.setName("一个月内的行走情况");  //表格名称
        axisX.setTextSize(8);//设置字体大小
        axisX.setMaxLabelChars(8);  //最多几个X轴坐标
        axisX.setValues(mAxisValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部

        Axis axisY = new Axis();  //Y轴
        axisY.setMaxLabelChars(7); //默认是3，只能看最后三个数字
        axisY.setName("步数");//y轴标注
        axisY.setTextSize(8);//设置字体大小

        data.setAxisYLeft(axisY);  //Y轴设置在左边

        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
    }


    /**
     * X 轴的显示
     */
    private void getAxisLables() {
        for (int i = 0; i < times.length; i++) {
            mAxisValues.add(new AxisValue(i).setLabel(times[i]));
        }
    }

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() {
        for (int i = 0; i < steps.length; i++) {
            mPointValues.add(new PointValue(i, steps[i]));
        }
    }

}
