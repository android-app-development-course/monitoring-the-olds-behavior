package com.phemie.scnu.laolekang.Fragment2;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.phemie.scnu.laolekang.R;

/**
 * Created by jiangjing on 2017/12/28.
 */

public class MedicineTimesPopupWindow extends PopupWindow {

    Button btn_commit;
    Button btn_cancel;
    View mMenuView;
    Spinner times;
    TimeCallBack tcb;
    int SelectedID;


    public MedicineTimesPopupWindow(final Activity context){
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.medicine_times_popupwin, null);

        btn_commit = (Button) mMenuView.findViewById(R.id.popDetialFre_commit);
        btn_cancel = (Button) mMenuView.findViewById(R.id.popDetialFre_cancel);

        times=(Spinner)mMenuView.findViewById(R.id.medicineTimes);

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tcb.setSelecteditem(SelectedID);
                dismiss();//数据回传
            }
        });

        times.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               SelectedID=position;
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

    }

    //设置初始值
    public void setData(TimeCallBack t){
        tcb=t;
        int temp=tcb.getSelecteditem();
        times.setSelection(temp-1,true);
    }


    public interface TimeCallBack{
        abstract void setSelecteditem(int id);
        abstract int getSelecteditem();
    }
}
