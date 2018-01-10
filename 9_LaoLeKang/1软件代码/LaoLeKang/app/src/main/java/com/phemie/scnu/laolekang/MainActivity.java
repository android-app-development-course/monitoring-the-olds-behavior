package com.phemie.scnu.laolekang;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.phemie.scnu.laolekang.Fragment1.FirstFragment;
import com.phemie.scnu.laolekang.Fragment2.DateInfo;
import com.phemie.scnu.laolekang.Fragment2.SecondFragment;
import com.phemie.scnu.laolekang.Fragment3.ThirdFragment;
import com.phemie.scnu.laolekang.Fragment4.FourthFragment;
import com.phemie.scnu.laolekang.Fragment5.FifthFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView topBar;
    private TextView tabPerson;
    private TextView tabMedicine;
    private TextView tabHome;
    private TextView tabHealth;
    private TextView tabCall;

    private FrameLayout ly_content;

    private FirstFragment f1;   //TODO:修改
    private SecondFragment f2;
    private ThirdFragment f3;
    private FourthFragment f4;
    private FifthFragment f5;
    private FragmentManager fragmentManager;
    FragmentTransaction ftransaction;

    // 杜慧成
    public static int counter;
    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

       // setAlarm();
        bindView();
        onClick(tabHome);//将主页所在fragment设置为启动软件就可以看见

        // 康富、慧城
        // 读取当前用户名
        readUsername();
        // 申请权限
        requirePermission();

    }

    //UI组件初始化与事件绑定
    private void bindView() {
        topBar = (TextView)this.findViewById(R.id.txt_top);
        tabPerson = (TextView)this.findViewById(R.id.txt_pperson);
        tabMedicine = (TextView)this.findViewById(R.id.txt_mmedicine);
        tabHome = (TextView)this.findViewById(R.id.txt_hhome);
        tabHealth = (TextView)this.findViewById(R.id.txt_hhealth);
        tabCall=(TextView)this.findViewById(R.id.txt_ccall) ;
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);

        tabPerson.setOnClickListener(this);
        tabMedicine.setOnClickListener(this);
        tabHome.setOnClickListener(this);
        tabHealth.setOnClickListener(this);
        tabCall.setOnClickListener(this);

    }

    //重置所有文本的选中状态,按钮的选中情况
    public void selected(){
        tabPerson.setSelected(false);
        tabMedicine.setSelected(false);
        tabHome.setSelected(false);
        tabHealth.setSelected(false);
        tabCall.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }

        if(f3!=null){
            transaction.hide(f3);
        }

        if(f4!=null){
            transaction.hide(f4);
        }
        if(f5!=null){
            transaction.hide(f5);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_pperson:
                selected();
                tabPerson.setSelected(true);
                if(f1==null){
                    f1 = new FirstFragment();
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;

            case R.id.txt_mmedicine:
                selected();
                tabMedicine.setSelected(true);

                if(f2==null){
                    f2 = new SecondFragment();
                    transaction.add(R.id.fragment_container,f2);

                }else{
                    transaction.show(f2);
                }
                break;



            case R.id.txt_hhome:
                selected();
                tabHome.setSelected(true);
                if(f3==null){
                    f3 = new ThirdFragment();
                    transaction.add(R.id.fragment_container,f3);
                }else{
                    transaction.show(f3);
                }
                break;

            case R.id.txt_hhealth:
                selected();
                tabHealth.setSelected(true);
                if(f4==null){
                    f4 = new FourthFragment();
                    transaction.add(R.id.fragment_container,f4);
                }else{
                    transaction.show(f4);
                }
                break;

            case R.id.txt_ccall:
                selected();
                tabCall.setSelected(true);
                if(f5==null){
                    f5 = new FifthFragment();
                    transaction.add(R.id.fragment_container,f5);
                }else{
                    transaction.show(f5);
                }
                break;
        }

        transaction.commit();
    }

    private void setAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        DateInfo date = new DateInfo();
        calendar.set(Integer.parseInt(date.getYear()),Integer.parseInt(date.getMonth())-1,Integer.parseInt(date.getDay()));
        long curt=calendar.getTimeInMillis();
        Intent i=new Intent(this,SecondFragment.class);
        PendingIntent sender = PendingIntent.getActivity(this,22222,i,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,curt,24*60*60 * 1000,sender);
    }


    // 康富、慧城
    // 保存用户名
    public void readUsername(){
        Intent i = getIntent();
        MainActivity.username = i.getStringExtra("username");
    }

    // 销毁界面时保存相关数据
    public void onDestroy(){
        File file = new File(getFilesDir(), InitialActivity.sLastUser);
        if(!file.exists()){
            try {
                file.createNewFile();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        // 对文件读写
        Intent i = getIntent();
        String s = i.getStringExtra("username");
        try{
            FileOutputStream f = openFileOutput(InitialActivity.sLastUser, MODE_PRIVATE);
            f.write(s.getBytes());
            f.close();
        }
        catch(IOException e)
        {
            Log.i("MainActivity1", "保存用户信息时出现错误");
            e.printStackTrace();
        }
        super.onDestroy();
    }

    // 申请权限函数
    public void requirePermission(){
        // 拨号功能权限
        final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1; // 系统拨号权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }

        // 其他功能权限在下面添加
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            requestCameraPermission();
        }

    }

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }


    //跳转到Fragment

    public void gotoFistFragment() {    //去个人中心页面
        onClick(tabPerson);
    }


    public void gotoSecondFragment() {    //去用药提醒页面
        onClick(tabMedicine);
    }


    public void gotoThirdFragment() {    //去主页面
        onClick(tabHome);
    }


    public void gotoFourthtFragment() {    //去健康数据页面
        onClick(tabHealth);
    }


    public void gotoFifthFragment() {    //去紧急呼叫页面
        onClick(tabCall);
    }
}
