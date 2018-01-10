package com.phemie.scnu.laolekang.Fragment2;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.phemie.scnu.laolekang.R;

import java.io.IOException;

public class MedicineMusicService extends Service {

    public MediaPlayer mediaPlayer;

    class MusicBinder extends Binder {
        public void play(){
            if (mediaPlayer==null){
                //创建一个音乐来源在程序本身的播放器
                mediaPlayer=MediaPlayer.create(MedicineMusicService.this, R.raw.alarm);
                //设置背景音乐循环播放
                mediaPlayer.setLooping(true);
                //开始播放音乐
                /*
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                mediaPlayer.start();
            }
            else {
                //设置重新开始的位置为从头开始
                mediaPlayer.seekTo(0);
                /*
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                mediaPlayer.start();
            }
        }
        public void stop(){
            if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
                mediaPlayer.stop();//停止播放音乐
            }
        }

        public void restart(){
            try {
                mediaPlayer.prepare(); //准备播放
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start(); //开始播放音乐
        }
    }

    public void onCreate(){
        super.onCreate();

    }

    @Override
    public void onDestroy(){
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        super.onDestroy();
    }

    public MedicineMusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MusicBinder(); //返回服务代理

    }
}
