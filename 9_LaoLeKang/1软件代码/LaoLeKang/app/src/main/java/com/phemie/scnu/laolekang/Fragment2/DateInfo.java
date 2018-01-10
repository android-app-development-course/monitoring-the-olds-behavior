package com.phemie.scnu.laolekang.Fragment2;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by jiangjing on 2017/12/22.
 */

public class DateInfo {
    private static String mYear;
    private static String mMonth;
    private static String mDay;
    private static String mWay;

    public  void getData(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
    }

    public  String getYear(){
        return mYear;
    }

    public  String getMonth(){
        return mMonth;
    }

    public  String getDay(){
        return mDay;
    }

    public  String getWay(){
        return mWay;
    }
}
