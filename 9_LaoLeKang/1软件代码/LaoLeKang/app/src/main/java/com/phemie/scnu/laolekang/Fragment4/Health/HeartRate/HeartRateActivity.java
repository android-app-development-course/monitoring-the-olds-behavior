package com.phemie.scnu.laolekang.Fragment4.Health.HeartRate;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phemie.scnu.laolekang.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class HeartRateActivity extends AppCompatActivity implements View.OnClickListener {

    private static final AtomicBoolean processing = new AtomicBoolean(false);
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];
    private static final int beatsArraySize = 3;//心跳数组的大小
    private static final int[] beatsArray = new int[beatsArraySize];//心跳数组
    private static int gx;
    private static int j;
    private static double flag = 1;
    //Android手机预览控件
    private static SurfaceView preview = null;
    //预览设置信息
    private static SurfaceHolder previewHolder = null;
    //Android手机相机句柄
    private static Camera camera = null;
    private static TextView text = null;
    private static WakeLock wakeLock = null;
    private static int averageIndex = 0;
    //设置默认类型
    private static TYPE currentType = TYPE.GREEN;//网格是绿色的
    private static int beatsIndex = 0; //心跳下标值
    private static double beats = 0;//心跳脉冲
    private static long startTime = 0;//开始时间
    /**
     * 相机预览方法
     * 这个方法中实现动态更新界面UI的功能，
     * 通过获取手机摄像头的参数来实时动态计算平均像素值、脉冲数，从而实时动态计算心率值。
     */
    private static PreviewCallback previewCallback = new PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera cam) {
            if (data == null)
                throw new NullPointerException();
            Camera.Size size = cam.getParameters().getPreviewSize();
            if (size == null)
                throw new NullPointerException();
            if (!processing.compareAndSet(false, true))
                return;
            int width = size.width;
            int height = size.height;
            //图像处理
            int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), height, width);
            gx = imgAvg;
            //text1.setText("平均像素值是"+String.valueOf(imgAvg));

            if (imgAvg == 0 || imgAvg == 255) {
                processing.set(false);
                return;
            }
            //计算平均值
            int averageArrayAvg = 0;
            int averageArrayCnt = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCnt++;
                }
            }
            //计算平均值
            int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;
            TYPE newType = currentType;
            if (imgAvg < rollingAverage) {
                newType = TYPE.RED;
                if (newType != currentType) {
                    beats++;
                    flag = 0;
                    //text2.setText("脉冲数是"+String.valueOf(beats));
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN;
            }

            if (averageIndex == averageArraySize)
                averageIndex = 0;
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            // Transitioned from one state to another to the same
            if (newType != currentType) {
                currentType = newType;
            }
            //获取系统结束时间（ms）
            long endTime = System.currentTimeMillis();
            double totalTimeInSecs = (endTime - startTime) / 1000d;
            if (totalTimeInSecs >= 2) {
                double bps = (beats / totalTimeInSecs);
                int dpm = (int) (bps * 60d);
                if (dpm < 30 || dpm > 180 || imgAvg < 200) {
                    //获取系统开始时间（ms）
                    startTime = System.currentTimeMillis();
                    //beats心跳总数
                    beats = 0;
                    processing.set(false);
                    return;
                }

                if (beatsIndex == beatsArraySize)
                    beatsIndex = 0;
                beatsArray[beatsIndex] = dpm;
                beatsIndex++;
                int beatsArrayAvg = 0;
                int beatsArrayCnt = 0;
                for (int i = 0; i < beatsArray.length; i++) {
                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCnt++;
                    }
                }
                int beatsAvg = (beatsArrayAvg / beatsArrayCnt);//心率平均值
                /*
                text.setText("您的的心率是"+String.valueOf(beatsAvg)+"  zhi:"+String.valueOf(beatsArray.length)
                        +"    "+String.valueOf(beatsIndex)+"    "+String.valueOf(beatsArrayAvg)+"    "+String.valueOf(beatsArrayCnt));
                */

                text.setText(String.valueOf(beatsAvg));//心率
                //获取系统时间（ms）
                startTime = System.currentTimeMillis();
                beats = 0;
            }
            processing.set(false);
        }
    };
    /**
     * 预览回调接口
     */
    private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        //创建时调用
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(previewHolder);
                camera.setPreviewCallback(previewCallback);
            } catch (Throwable t) {
                Log.e("surfaceCallback", "Exception in setPreviewDisplay()", t);
            }
        }

        //当预览改变的时候回调此方法
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            Camera.Size size = getSmallestPreviewSize(width, height, parameters);
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
            }
            camera.setParameters(parameters);
            camera.startPreview();
        }

        //销毁的时候调用
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // Ignore
        }
    };
    ImageButton rreturn;//返回键
    double addY;
    int[] xv = new int[300];
    int[] yv = new int[300];
    int[] hua = new int[]{9, 10, 11, 12, 13, 14, 13, 12, 11, 10, 9, 8, 7, 6, 7, 8, 9, 10, 11, 10, 10};//模拟心跳的起伏
    //曲线
    private Timer timer = new Timer();
    //Timer任务，与Timer配套使用
    private TimerTask task;
    private Handler handler;
    private String title = "pulse";//血压
    private XYSeries series;
    private XYMultipleSeriesDataset mDataset;
    private GraphicalView chart;
    private XYMultipleSeriesRenderer renderer;
    private Context context;
    private int addX = -1;

    public static TYPE getCurrent() {
        return currentType;
    }//获取当前类型

    /**
     * 获取相机最小的预览尺寸
     *
     * @param width
     * @param height
     * @param parameters
     * @return
     */
    private static Camera.Size getSmallestPreviewSize(int width, int height,
                                                      Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea < resultArea)
                        result = size;
                }
            }
        }
        return result;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        initConfig();

        rreturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rreturn:
                finish();
                break;
        }
    }

    /**
     * 初始化配置
     */
    private void initConfig() {

        rreturn = (ImageButton) findViewById(R.id.rreturn);
        //曲线
        context = getApplicationContext();

        //这里获得main界面上的布局，下面会把图表画在这个布局里面
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout1);

        //这个类用来放置曲线上的所有点，是一个点的集合，根据这些点画出曲线
        series = new XYSeries(title);

        //创建一个数据集的实例，这个数据集将被用来创建图表
        mDataset = new XYMultipleSeriesDataset();

        //将点集添加到这个数据集中
        mDataset.addSeries(series);

        //以下都是曲线的样式和属性等等的设置，renderer相当于一个用来给图表做渲染的句柄
        int color = Color.GREEN;
        PointStyle style = PointStyle.CIRCLE;
        renderer = buildRenderer(color, style, true);

        //设置好图表的样式
        setChartSettings(
                renderer, //
                "X", //x轴的标题
                "Y", //y轴的标题
                0, //x轴的最小值
                300, //x轴的最大值
                4, //y轴的最小值
                16, //y轴的最大值
                Color.WHITE, //
                Color.WHITE);//

        //生成图表
        chart = ChartFactory.getLineChartView(context, mDataset, renderer);

        //将图表添加到布局中去
        layout.addView(chart, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        //这里的Handler实例将配合下面的Timer实例，完成定时更新图表的功能
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //刷新图表
                updateChart();
                super.handleMessage(msg);
            }
        };

        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };

        timer.schedule(task, 1, 20);           //曲线
        //获取SurfaceView控件
        preview = (SurfaceView) findViewById(R.id.preview);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        text = (TextView) findViewById(R.id.text);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
    }

    //	曲线
    @Override
    public void onDestroy() {
        //当结束程序时关掉Timer
        timer.cancel();
        super.onDestroy();
    }

    /**
     * 创建图表
     *
     * @param color
     * @param style
     * @param fill
     * @return
     */
    protected XYMultipleSeriesRenderer buildRenderer(int color, PointStyle style, boolean fill) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        //设置图表中曲线本身的样式，包括颜色、点的大小以及线的粗细等
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.RED);
        r.setLineWidth(1);
        renderer.addSeriesRenderer(r);
        return renderer;
    }

    /**
     * 设置图标的样式
     *
     * @param renderer
     * @param xTitle：x标题
     * @param yTitle：y标题
     * @param xMin：x最小长度
     * @param xMax：x最大长度
     * @param yMin:y最小长度
     * @param yMax：y最大长度
     * @param axesColor：颜色
     * @param labelsColor：标签
     */
    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String xTitle, String yTitle,
                                    double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
        //有关对图表的渲染可参看api文档
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GREEN);
        renderer.setXLabels(20);
        renderer.setYLabels(10);
        renderer.setXTitle("Time");
        renderer.setYTitle("mmHg");
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setPointSize((float) 3);
        renderer.setShowLegend(false);
    }

    /**
     * 更新图标信息
     */
    private void updateChart() {

        //设置好下一个需要增加的节点
        if (flag == 1)
            addY = 10;
        else {
            addY = 250;
            flag = 1;
            if (gx < 200) {
                if (hua[20] > 1) {
                    Toast.makeText(this, "请用您的指尖盖住摄像头镜头！", Toast.LENGTH_SHORT).show();
                    hua[20] = 0;
                }
                hua[20]++;
                return;
            } else
                hua[20] = 10;
            j = 0;

        }
        if (j < 20) {
            addY = hua[j];
            j++;
        }

        //移除数据集中旧的点集
        mDataset.removeSeries(series);

        //判断当前点集中到底有多少点，因为屏幕总共只能容纳100个，所以当点数超过100时，长度永远是100
        int length = series.getItemCount();
        int bz = 0;
        if (length > 300) {
            length = 300;
            bz = 1;
        }
        addX = length;
        //将旧的点集中x和y的数值取出来放入backup中，并且将x的值加1，造成曲线向右平移的效果
        for (int i = 0; i < length; i++) {
            xv[i] = (int) series.getX(i) - bz;
            yv[i] = (int) series.getY(i);
        }

        //点集先清空，为了做成新的点集而准备
        series.clear();
        mDataset.addSeries(series);
        //将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中
        //这里可以试验一下把顺序颠倒过来是什么效果，即先运行循环体，再添加新产生的点
        series.add(addX, addY);
        for (int k = 0; k < length; k++) {
            series.add(xv[k], yv[k]);
        }
        //在数据集中添加新的点集
        //mDataset.addSeries(series);

        //视图更新，没有这一步，曲线不会呈现动态
        //如果在非UI主线程中，需要调用postInvalidate()，具体参考api
        chart.invalidate();
    } //曲线

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        camera = Camera.open();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    /**
     * 类型枚举
     *
     * @author PhemieTang
     */
    public enum TYPE {
        GREEN, RED
    }
}
