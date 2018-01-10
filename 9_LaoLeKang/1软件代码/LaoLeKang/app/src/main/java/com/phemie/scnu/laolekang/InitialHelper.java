package com.phemie.scnu.laolekang;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenjinhao on 2018/1/9 0009.
 */

public class InitialHelper extends SQLiteOpenHelper {
    public InitialHelper(Context context) {
        super(context, "monitor.db", null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        // 创建一个数据库表，表的列由两个属性值组成：用户名和密码，均为字符串形式
        db.execSQL("CREATE TABLE userBasicInformation(username VARCHAR(20) PRIMARY KEY," +
                "password VARCHAR(20))");
        // 创建一个表，保存用户数据
        db.execSQL("CREATE TABLE mySettings(_id INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(20)," +
                "title VARCHAR(50),content VARCHAR(50))");
        // 创建数据库， 保存头像
        db.execSQL("CREATE TABLE myHeads(username VARCHAR(20) PRIMARY KEY," +
                "head_path VARCHAR(50))");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
