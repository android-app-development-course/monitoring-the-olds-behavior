package com.phemie.scnu.laolekang.Fragment3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.phemie.scnu.laolekang.R;


/**
 * Created by duhuicheng on 2017/12/23.
 */

public class Setting_Skin extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止输入框覆盖
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        setContentView(R.layout.setting_skin);
        Button mButton=(Button)findViewById(R.id.save);
//        final CheckBox mCheckBox1=(CheckBox)findViewById(R.id.color_blue);//蓝色
//        final CheckBox mCheckBox2=(CheckBox)findViewById(R.id.color_red);//红色
//        final CheckBox mCheckBox3=(CheckBox)findViewById(R.id.color_gray);//灰色
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(mCheckBox1.isChecked())
//                {
//                    ThirdFragment.color="blue";
//                }else if(mCheckBox2.isChecked()){
//                    ThirdFragment.color="red";
//                }else if(mCheckBox3.isChecked()){
//                    ThirdFragment.color="gray";
//                }
                finish();
            }
        });//保存按钮
        ImageButton imageButton=(ImageButton)findViewById(R.id.rreturn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//返回键
//        if(ThirdFragment.color.equals("blue")) {
//            mCheckBox1.setChecked(true);
//            mCheckBox2.setChecked(false);
//            mCheckBox3.setChecked(false);
//        }else if(ThirdFragment.color.equals("red")) {
//            mCheckBox1.setChecked(false);
//            mCheckBox2.setChecked(true);
//            mCheckBox3.setChecked(false);
//        }else if(ThirdFragment.color.equals("gray")) {
//            mCheckBox1.setChecked(false);
//            mCheckBox2.setChecked(false);
//            mCheckBox3.setChecked(true);
//        }
//        mCheckBox1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCheckBox1.setChecked(true);
//                mCheckBox2.setChecked(false);
//                mCheckBox3.setChecked(false);
//
//            }
//        });
//        mCheckBox2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCheckBox1.setChecked(false);
//                mCheckBox2.setChecked(true);
//                mCheckBox3.setChecked(false);
//            }
//        });
//        mCheckBox3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCheckBox1.setChecked(false);
//                mCheckBox2.setChecked(false);
//                mCheckBox3.setChecked(true);
//            }
//        });
//        MainActivity.counter++;
    }
}
