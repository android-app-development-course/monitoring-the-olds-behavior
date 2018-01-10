package com.phemie.scnu.laolekang.Fragment2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiangjing on 2017/12/24.
 */

public class detialFrequency implements Parcelable{

    String period;//时段
    String hour;//时
    String min;//分
    String diningIllustrate;//餐饮说明

    public detialFrequency(String p, String h, String m, String d) {
        period = p;
        hour = h;
        min = m;
        diningIllustrate = d;
    }

    public String getPeriod() {
        return period;
    }

    public String getHour() {
        return hour;
    }

    public String getMin() {
        return min;
    }

    public String getMoment() {
        return hour + ":" + min;
    }

    public String getDiningIllustrate() {
        return diningIllustrate;
    }

    public void setPeriod(String p){
        period=p;
    }

    public void setHour(String h){
        hour=h;
    }

    public void setMin(String m){
        min=m;
    }

    public void setDiningIllustrate(String d){
        diningIllustrate=d;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(period);
        dest.writeString(hour);
        dest.writeString(min);
        dest.writeString(diningIllustrate);
    }

    public static final Parcelable.Creator<detialFrequency> CREATOR = new Parcelable.Creator<detialFrequency>() {
        @Override
        public detialFrequency createFromParcel(Parcel source) {
            //从Parcel中读取数据
            //此处read顺序依据write顺序
            return new detialFrequency(source.readString(),source.readString(), source.readString(),source.readString());
        }
        @Override
        public detialFrequency[] newArray(int size) {

            return new detialFrequency[size];
        }

    };

}
