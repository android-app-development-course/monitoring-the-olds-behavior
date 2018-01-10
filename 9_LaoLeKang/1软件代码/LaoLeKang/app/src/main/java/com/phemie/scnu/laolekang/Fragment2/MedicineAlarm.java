package com.phemie.scnu.laolekang.Fragment2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.phemie.scnu.laolekang.R;


public class MedicineAlarm extends AppCompatActivity {

    Button btn_ok;
    private Intent mIntent; //用于Service
    private  MusicConn mconn;//用于实现连接服务
    MedicineMusicService.MusicBinder binder;//服务代理
    boolean isPlaying;//判断当前是否正在播放音乐
    TextView tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_alarm);

        Init();
        setListener();
    }

    private void Init(){
        mconn=new MusicConn();
        mIntent=new Intent(this,MedicineMusicService.class);
        bindService(mIntent,mconn,BIND_AUTO_CREATE);
        tip=(TextView)findViewById(R.id.medicinealarm_tip);
        String str=tip.getText().toString();
        Bundle b=getIntent().getExtras();
        int size=b.getInt("num");
        for (int i=0;i<size;i++){
            str+= "\n"+b.getString("num"+i);
        }

        tip.setText(str);
        btn_ok=(Button)findViewById(R.id.btn_ok);
    }

    private void setListener(){
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binder.stop();
                finish();
            }
        });
    }

    private class MusicConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder=(MedicineMusicService.MusicBinder)service;
            binder.play();
            isPlaying=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

}
