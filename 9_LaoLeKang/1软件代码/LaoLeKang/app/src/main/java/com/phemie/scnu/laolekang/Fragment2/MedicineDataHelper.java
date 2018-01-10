package com.phemie.scnu.laolekang.Fragment2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jiangjing on 2017/12/31.
 */

public class MedicineDataHelper extends SQLiteOpenHelper {

    public MedicineDataHelper (Context context){
        super(context, "MedicineData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表Medicine，字段：识别号（id）,药名（name），规格（speci），形状（shape），颜色（color），用量（dosage），开始年（syear），开始月（smonth），开始日（sday）
        //结束年（eyear），结束月（emonth），结束日（eday），频率（frequency），特殊说明（specialill）
        db.execSQL("CREATE TABLE Medicine(id integer primary key autoincrement, name varchar(30), speci varchar(4), shape integer, color integer, dosage varchar(20),syear varchar(6),smonth varchar(3), sday varchar(3), eyear varchar(6), emonth varchar(3), eday varchar(3), frequency varchar(3), specialill varchar(200) ,vaild integer);");
        //创建表DetialFrequency，字段：识别号（id），时段（period），时（hour），分（minute），餐饮说明（dining），所属药物（mid ， 外键）
        db.execSQL("CREATE TABLE DetialFrequency(id integer primary key autoincrement, period varchar(4),hour varchar(3), minute varchar(3), dining varchar(10), mid integer not null, foreign key (mid) references Medicine(id) );");
/*
        //创建触发器
        db.execSQL("CREATE TRIGGER TID_Delete" +
                "before delete on Medicine" +
                "for each row" +
                "begin" +
                "delete from DetialFrequency where mid = old.id;" +
                "end;");

/*
        db.execSQL( "CREATE TRIGGER did_insert" +
                "before update on Student" +
                "for each row" +
                "begin" +
                "   update StuCourse" +
                "   set SID=new.SID" +
                "   where SID = old.SID;" +
                "   update StuAccount" +
                "   set SID=new.SID" +
                "   where SID = old.SID;" +
                "end; ");
*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
