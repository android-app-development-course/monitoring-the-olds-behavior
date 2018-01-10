package com.phemie.scnu.laolekang.Fragment4.Health.FootStep;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.phemie.scnu.laolekang.R;
import com.phemie.scnu.laolekang.Fragment4.UpdateUiCallBack;
import com.phemie.scnu.laolekang.Fragment4.step.service.StepService;
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

public class StepActivity extends AppCompatActivity implements View.OnClickListener {


    ImageButton rreturn;//返回键
    int animationDuration = 1500;
    int per;
    int currentStep;
    int i = 0;

    String[] times = {"3:00", "6:00", "9:00", "12:00", "15:00", "18:00", "21:00", "23:00"};//X轴的标注
    int[] steps = {500, 1000, 1452, 22754, 36340, 4210, 500, 100};//图表的数据
    private TextView tv_data;//查看历史记录
    private TextView tv_set;//设置锻炼计划
    private SharedPreferencesUtils sp;//存储数据
    private CircularProgressBar myCircularProgressBar;//进度环
    private TextView myStep;
    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            //设置初始化数据
            String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");

            currentStep = stepService.getStepCount();//获取当前步数

            myStep.setText(Integer.toString(currentStep));
            per = currentStep > Integer.parseInt(planWalk_QTY) ? 100 : 100 * currentStep / Integer.parseInt(planWalk_QTY);//求出百分比
            animationDuration = 1500;//设置动效，3秒
            myCircularProgressBar.setProgressWithAnimation(per, animationDuration); // Default duration = 1500ms


            //设置步数监听回调
            stepService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
                    int s = Integer.parseInt(planWalk_QTY);

                    per = stepCount > s ? 100 : 100 * stepCount / s;//求出百分比
                    myCircularProgressBar.setProgressWithAnimation(per, animationDuration);

                    myStep.setText(Integer.toString(stepCount));

                }
            });
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private LineChartView lineChart;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisValues = new ArrayList<AxisValue>();
    private boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        assignViews();//开始绑定各个控件的id
        initData();//初始化数据
        addListener();

        getAxisLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化

    }

    private void assignViews() {//绑定各个控件
        rreturn = (ImageButton) findViewById(R.id.rreturn);
        tv_data = (TextView) findViewById(R.id.tv_data);
        myCircularProgressBar = (CircularProgressBar) findViewById(R.id.myCicularProgressBar);
        lineChart = (LineChartView) findViewById(R.id.line_chart);
        myStep = (TextView) findViewById(R.id.myStep);
        tv_set = (TextView) findViewById(R.id.tv_set);


    }

    private void addListener() {
        tv_set.setOnClickListener(this);
        tv_data.setOnClickListener(this);
        rreturn.setOnClickListener(this);
    }

    private void initData() {
        sp = new SharedPreferencesUtils(this);
        //设置当前步数为0
        myCircularProgressBar.setProgressWithAnimation(0, animationDuration);
        myStep.setText("0");
        setupService();

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
        axisX.setName("一天内的行走情况");  //表格名称
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

    /**
     * 开启计步服务
     */
    private void setupService() {
        Intent intent = new Intent(StepActivity.this, StepService.class);
        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set:
                startActivity(new Intent(this, SetPlanActivity.class));
                break;
            case R.id.tv_data:
                startActivity(new Intent(this, HistoryActivity.class));
                break;
            case R.id.rreturn:
                finish();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            this.unbindService(conn);
        }
    }


}
