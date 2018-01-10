package com.phemie.scnu.laolekang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InitialActivity extends AppCompatActivity {

     // 文件读写
     public static String sStatus = "status.txt";  public static String sLastUser = "last_user.txt";
    public static String sContact = "contact.txt";
    public static File fileStatus;
    public static BufferedInputStream inputStatus;
    public static InitialHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止输入框覆盖
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_initial);

        fileStatus = new File(getFilesDir(), sStatus);    // TODO：需要指定路径才能创建文件
        // 如果文件存在，直接打开文件读取信息
        if(fileStatus.exists()){
            readStatus();
        }
        // 否则，创建文件
        else
        {
            try {
                fileStatus.createNewFile();    // 创建文件
                // 初始化参数
                //SignActivity.signed = false;
                LoginActivity.logined = false;
                LoginActivity.autoLogin = false;
            }
            catch(IOException e)
            {
                Log.i("InitialActivity1", "无法创建文件");
                e.printStackTrace();
            }
        }


        // 跳转到“登陆”界面，并设置“记住密码”和“自动登录”状态
        Intent i = new Intent(InitialActivity.this, LoginActivity.class);
        startActivity(i);
        this.finish();
    }


    // 读取文件的内容  TODO: 2017.12.20 待实现
    private void readStatus() {
        // 创建读写流
        try {
            FileInputStream f = openFileInput(sStatus);
            inputStatus = new BufferedInputStream(f);
        } catch (IOException e) {
            Toast.makeText(InitialActivity.this, "无法打开文件并读取信息", Toast.LENGTH_SHORT).show();
        }

        // 读取信息
        if (inputStatus != null) {
            try {
                // 进行输入
                String s;
                byte[] b;
                b = new byte[inputStatus.available()];
                inputStatus.read(b);
                // 转化为字符串
                s = new String(b);
                String[] status = s.split("\n"); // 按照“空格”划分字符串
                for (int i = 0; i < status.length; i++)
                    Log.i("InitialActivity1", "状态信息" + status[i]);   // 输出各个状态

                inputStatus.close();   // 关闭输入流

                if (status.length >= 2) {
                    try {
                        String[] s_temp = new String[status.length];
                        s_temp[0] = status[0].split(":")[1];
                        s_temp[1] = status[1].split(":")[1];
                        //s_temp[2] = status[2].split(":")[1];

                        // 初始化各个静态变量
                        //SignActivity.signed = Boolean.parseBoolean(s_temp[0]);
                        LoginActivity.logined = Boolean.parseBoolean(s_temp[0]);
                        LoginActivity.autoLogin = Boolean.parseBoolean(s_temp[1]);
                    } catch (NumberFormatException e) {
                        //SignActivity.signed = false;
                        LoginActivity.logined = false;
                        LoginActivity.autoLogin = false;
                        e.printStackTrace();
                    }
                }
                } catch(IOException e){
                    Toast.makeText(InitialActivity.this, "读取文件出错", Toast.LENGTH_SHORT).show();
                }

        }
    }
}

// 创建一个具体类以实现对数据库的操作
/*class InitialHelper extends SQLiteOpenHelper {
    public InitialHelper(Context context) {
        super(context, "monitor.db", null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        // 创建一个数据库表，表的列由两个属性值组成：用户名和密码，均为字符串形式
        db.execSQL("CREATE TABLE userBasicInformation(username VARCHAR(20) PRIMARY KEY," +
                "password VARCHAR(20))");
        // 创建一个表，保存用户数据
        db.execSQL("CREATE TABLE mySettings(username VARCHAR(20) PRIMARY KEY," +
                "head_path VARCHAR(50),content_path VARCHAR(50))");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}*/
