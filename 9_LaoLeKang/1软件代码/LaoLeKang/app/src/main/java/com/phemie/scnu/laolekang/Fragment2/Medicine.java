package com.phemie.scnu.laolekang.Fragment2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by jiangjing on 2017/12/22.
 */

public class Medicine implements Parcelable {
    String mName;
    String mSpeci;//药物规格
    int mShape;//形状标识(记录选择的图形id)
    int mColor;//颜色标识（记录id）
    String mDosage;//用量

    String mStartyear;
    String mStartmonth;
    String mStartday;
    String mEndyear;
    String mEndmonth;
    String mEndday;
    String mFrequency;
    List<detialFrequency> mdetialFrequency;
    String specialIllustration;//特殊说明

    boolean vaild;

    public Medicine(){

    }

    public Medicine(String name,String speci,int shape,int color,String dosage,String syear,String smonth,String sday,String eyear,String emonth,String eday,String fre,List<detialFrequency> mdetialFrequency,String specialIllustration,boolean v)
    {
        mName=name;
        mShape=shape;
        mSpeci=speci;
        mColor=color;
        mDosage=dosage;
        mStartday=sday;
        mStartyear=syear;
        mStartmonth=smonth;
        mEndday=eday;
        mEndmonth=emonth;
        mEndyear=eyear;
        mFrequency=fre;
        this.mdetialFrequency=mdetialFrequency;
        this.specialIllustration=specialIllustration;
        vaild=v;
    }

    public void reset(Medicine m){
        mName=m.mName;
        mShape=m.mShape;
        mSpeci=m.mSpeci;
        mColor=m.mColor;
        mDosage=m.mDosage;
        mStartday=m.mStartday;
        mStartyear=m.mStartyear;
        mStartmonth=m.mStartmonth;
        mEndday=m.mEndday;
        mEndmonth=m.mEndmonth;
        mEndyear=m.mEndyear;
        mFrequency=m.mFrequency;
        mdetialFrequency=m.mdetialFrequency;
        specialIllustration=m.specialIllustration;
        vaild=m.vaild;
    }

    public void setmName(String name){
        mName=name;
    }

    public void setmSpeci(String s){
        mSpeci=s;
    }

    public void setmShape(int shape){
        mShape=shape;
    }

    public void setmColor(int c){
        mColor=c;
    }

    public void setmDosage(String d){
        mDosage=d;
    }

    public void setmStartyear(String y){
        mStartyear=y;
    }

    public void setmStartmonth(String m){
        mStartmonth=m;
    }

    public void setmStartday(String d){
        mStartday=d;
    }

    public void setmEndyear(String y){
        mEndyear=y;
    }

    public void setmEndmonth(String m){
        mEndmonth=m;
    }

    public void setmEndday(String d){
        mEndday=d;
    }

    public void setmFrequency(String f){
        mFrequency=f;
    }

    public void setMdetialFrequency(List<detialFrequency> d){
        mdetialFrequency=d;
    }

    public void setVaild(boolean v){
        vaild=v;
    }

    public String getmName(){
        return mName;
    }

    public String getmSpeci(){
        return mSpeci;
    }

    public String getmDosage(){
        return mDosage;
    }

    public String getmStartyear(){
        return mStartyear;
    }

    public String getmStartmonth(){
        return mStartmonth;
    }

    public String getmStartday(){
        return mStartday;
    }

    public String getmEndyear(){
        return mEndyear;
    }

    public String getmEndmonth(){
        return mEndmonth;
    }

    public String getmEndday(){
        return mEndday;
    }

    public String getmFrequency(){
        return mFrequency;
    }

    public String getSpecialIllustration(){
        return specialIllustration;
    }

    public int getmShape(){
        return mShape;
    }

    public int getmColor(){
        return mColor;
    }

    public List<detialFrequency> getMdetialFrequency(){
        return mdetialFrequency;
    }

    public void setSpecialIllustration(String s){
        specialIllustration=s;
    }

    public boolean getVaild(){
        return vaild;
    }

    public void adddetial(detialFrequency d)
    {
        mdetialFrequency.add(d);
    }

    public void adddetial(String p,String h,String m, String d)
    {
        detialFrequency t=new detialFrequency(p,h,m,d);
        mdetialFrequency.add(t);
    }

    public int getDetialSize(){
        return mdetialFrequency.size();
    }

    public void removedetail(int pos){
        mdetialFrequency.remove(pos);
    }

    public void cleardetail(){
        mdetialFrequency.clear();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mSpeci);
        dest.writeInt(mShape);
        dest.writeInt(mColor);
        dest.writeString(mDosage);
        dest.writeString(mStartyear);
        dest.writeString(mStartmonth);
        dest.writeString(mStartday);
        dest.writeString(mEndyear);
        dest.writeString(mEndmonth);
        dest.writeString(mEndday);
        dest.writeString(mFrequency);
        dest.writeList(mdetialFrequency);
        dest.writeString(specialIllustration);
        dest.writeBooleanArray(new boolean[]{vaild});
    }

    public static final Parcelable.Creator<Medicine> CREATOR = new Parcelable.Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel source) {
            //从Parcel中读取数据
            //此处read顺序依据write顺序
            boolean[] t=new boolean[1];
            Medicine m= new Medicine(source.readString(),source.readString(), source.readInt(),source.readInt(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),source.readString(),(List<detialFrequency>)source.readArrayList(detialFrequency.class.getClassLoader()),source.readString(),true);
            source.readBooleanArray(t);
            m.setVaild(t[0]);
            return m;
        }
        @Override
        public Medicine[] newArray(int size) {

            return new Medicine[size];
        }

    };
}
