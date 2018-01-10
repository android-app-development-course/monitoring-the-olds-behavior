package com.phemie.scnu.laolekang.Fragment3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;

import com.phemie.scnu.laolekang.R;

/**
 * Created by duhuicheng on 2017/12/23.
 */

public class Setting_Sound extends AppCompatActivity {
    SeekBar seekBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止输入框覆盖
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.setting_sound);
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        Button mButton = (Button) findViewById(R.id.save);
        final Switch mSwitch = (Switch) findViewById(R.id.silence);//静音按钮
        int sound = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (sound == 0) {
            mSwitch.setChecked(true);
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitch.isChecked()) {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true); //设置是否静音
                }
                finish();
            }
        });//保存按钮
        ImageButton imageButton = (ImageButton) findViewById(R.id.rreturn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//返回键
        seekBar = (SeekBar) findViewById(R.id.slidesound);
        //获取系统最大音量
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBar.setMax(maxVolume);
        //获取当前音量
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar.setProgress(currentVolume);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //设置系统音量
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                    int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    seekBar.setProgress(currentVolume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    private class VolumeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final AudioManager audioManager1 = (AudioManager) getSystemService(AUDIO_SERVICE);
            if(intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")){
                int currentVolume = audioManager1.getStreamVolume(AudioManager.STREAM_MUSIC);
                seekBar.setProgress(currentVolume);
            }
            VolumeReceiver receiver = new VolumeReceiver();
            IntentFilter filter = new IntentFilter() ;
            filter.addAction("android.media.VOLUME_CHANGED_ACTION") ;
            Context activity = null;
            activity.registerReceiver(receiver, filter) ;
        }
    }
}
