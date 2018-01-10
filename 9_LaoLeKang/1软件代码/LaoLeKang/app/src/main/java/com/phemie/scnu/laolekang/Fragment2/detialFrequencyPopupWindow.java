package com.phemie.scnu.laolekang.Fragment2;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.phemie.scnu.laolekang.R;

/**
 * Created by jiangjing on 2017/12/24.
 */

public class detialFrequencyPopupWindow extends PopupWindow {
    Button btn_commit;
    Button btn_cancel;

    Spinner dining;
    Spinner peroid;
    Spinner hour;
    Spinner minute;
    View mMenuView;
    int[] selected;
    DetailFreDataCallback dfdcb;
    //public Activity context;

    public detialFrequencyPopupWindow(final Activity context/*, AdapterView.OnItemSelectedListener itemSelectedListener*/){
        super(context);
        //this.context=context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_window_detialfre, null);

        btn_commit = (Button) mMenuView.findViewById(R.id.popDetialFre_commit);
        btn_cancel = (Button) mMenuView.findViewById(R.id.popDetialFre_cancel);

        dining=(Spinner)mMenuView.findViewById(R.id.diningIllu);
        peroid=(Spinner)mMenuView.findViewById(R.id.fre_period);
        hour=(Spinner)mMenuView.findViewById(R.id.fre_hour);
        minute=(Spinner)mMenuView.findViewById(R.id.fre_minute);

        selected=new int[4];

        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dfdcb.setCallback(selected);
                dismiss();//数据回传
            }
        });

        //设置按钮监听
        dining.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected[0]=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected[2]=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        peroid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selected[1]=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        minute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected[3]=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置PopupWindow可触摸
        this.setTouchable(true);//**不加此语句时，当点击内部控件时，会发生整个activity的崩溃 同时 给Spinner控件加上android:spinnerMode="dialog"属性可以解决点击崩溃的问题
        //实例化一个ColorDrawable颜色为浅灰色
        ColorDrawable dw =  new ColorDrawable(0xEAEAEAEA);
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        //ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        //this.setBackgroundDrawable(dw);


    }
/*
    private AdapterView.OnItemSelectedListener itemSelectedListener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            int curspinner=parent.getId();
            /*Resources res =getResources();
            String[] languages = res.getStringArray(R.array.languages);
            String[] reminder_methods = res.getIntArray(R.array.reminder_methods_values);
            if (curspinner==R.id.fre_period)
                Log.i("popSpinner","Period");
            else if (curspinner==R.id.fre_hour)
                Log.i("popSpinner","hour");
            else if (curspinner==R.id.fre_minute)
                Log.i("popSpinner","minute");
            else if (curspinner==R.id.diningIllu)
                Log.i("popSpinner","dining");
            else
            {
                Log.i("popSpinner","defult");
                dismiss();
            }




        }


        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };*/

    public interface DetailFreDataCallback{
        abstract void setCallback(int[] data);
        abstract int[] getSelectedData();
    }

    //设置初始值
    public void setData(DetailFreDataCallback t){
        dfdcb=t;
        selected=dfdcb.getSelectedData();
        dining.setSelection(selected[0],true);
        peroid.setSelection(selected[1],true);
        hour.setSelection(selected[2],true);
        minute.setSelection(selected[3],true);
    }

}

