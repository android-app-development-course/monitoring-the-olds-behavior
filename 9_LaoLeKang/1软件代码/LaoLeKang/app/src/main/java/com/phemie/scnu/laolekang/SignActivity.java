package com.phemie.scnu.laolekang;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

/*
2017.12.20 从“登陆”界面返回时，上一次输入的用户名和密码依然存在
*/


public class SignActivity extends AppCompatActivity {
    //static boolean signed ;

    ImageView ivAdatar;
    EditText etUsername, etPassword, etComfirmPassword;
    TextView tvWarning;
    Button btSign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        // 初始化控件
        ivAdatar = (ImageView) findViewById(R.id.sign_iv_avatar);
        etUsername = (EditText) findViewById(R.id.sign_et_username);
        etPassword = (EditText) findViewById(R.id.sign_et_password);
        etComfirmPassword = (EditText)findViewById(R.id.sign_et_confirmpassword);
        tvWarning = (TextView) findViewById(R.id.sign_tv_warning);
        btSign = (Button) findViewById(R.id.sign_bt_sign);
        // 设置时间监听器
        btSign.setOnClickListener(new signListener(this));

    }

    private class signListener implements View.OnClickListener{
        SignActivity activity;
        // 构造函数
        public signListener(SignActivity a){
            activity = a;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.sign_bt_sign:
                    signClick(v);
                    break;
            }
        }

        // “注册”按钮点击函数
        public void signClick(View v) {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirm = etComfirmPassword.getText().toString().trim();

            // 如果没有输入内容，给出提示
            /*if (username.equals("")) {
                Toast.makeText(SignActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            } else if (password.equals("")) {
                Toast.makeText(SignActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }*/
            String m1 = "^[A-Za-z0-9]+$";
            if(Regular.regular(m1, password) == false)
            {
                Toast.makeText(SignActivity.this, "密码只能输入由数字和26个英文字母组成",
                        Toast.LENGTH_SHORT).show();
                etPassword.setText(""); etComfirmPassword.setText("");
                return;
            }

            String m2 = "^.{1,16}$";
            if(Regular.regular(m2, password) == false){
                etPassword.setText(""); etComfirmPassword.setText("");
                Toast.makeText(SignActivity.this, "密码长度不能超过16位", Toast.LENGTH_SHORT).show();
                return;
            }
            // 用户名只能

            // 检查输入的两次输入的密码是否相等
            if(!passwordEquals(password, confirm)){
                tvWarning.setText("两次输入的密码不一致！请重新输入");
                etPassword.setText(""); etComfirmPassword.setText("");
                return;
            }

            // 检验用户名和密码是否满足文法，满足的话，保存到数据库中；否则，给出提示信息
            //signed = true;
            // 插入到数据库中，并且把数据传给LoginActivity
            insertUserInformation(username, password);


            // 跳转到LoginActivity
            try {
                Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                intent.putExtra("Username", username);
                intent.putExtra("Password", password);
                setResult(1, intent);

                // 结束该界面
                activity.finish();

            } catch (Exception e) {
                Toast.makeText(activity, "跳转时出现错误", Toast.LENGTH_SHORT).show();
            }

        }

        // 确认密码
        boolean passwordEquals(String password, String confirm){
            if(password.equals(confirm))
                return true;
            else
                return false;
        }

    }





    // 数据库存储用户信息
    void insertUserInformation(String username, String password){
        InitialActivity.helper = new InitialHelper(this);
        InitialHelper helper = InitialActivity.helper;

        try{
            SQLiteDatabase db = helper.getWritableDatabase();
            // 把用户名和密码插入到数据库中
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);
            db.insert("userBasicInformation", null, values);
            Toast.makeText(SignActivity.this, "用户信息已经添加到数据库中", Toast.LENGTH_SHORT).show();

            // 释放相关资源
            db.close(); helper = null;

            // 创建一个新用户后，把状态信息改为默认值 TODO（即：logined = false, autoLogin = false）
            try {
                FileOutputStream f = openFileOutput(InitialActivity.sStatus, MODE_PRIVATE);
                f.write("LoginActivity.logined:".getBytes());
                f.write(("false" + "\n").getBytes());
                f.write("LoginActivity.autoLogin:".getBytes());
                f.write(("false" + "\n").getBytes());   // 将状态信息和“空格”写进文件中
                f.close(); // 关闭读写流

                LoginActivity.logined = false;
                LoginActivity.autoLogin = false;
            } catch (IOException e) {
                Log.i("SignActivity1", "更新状态信息时发生错误");
                e.printStackTrace();
            }
            return ;
        }
        catch(Exception e)
        {
            Toast.makeText(SignActivity.this, "创建数据库时发生异常", Toast.LENGTH_SHORT).show();
        }
    }

    // 重新生成“注册”界面时，执行的相关操作
    public void onRestart(){
        super.onRestart();
        try{
            etUsername.setText(""); etPassword.setText("");
        }
        catch(NullPointerException e){
            Toast.makeText(SignActivity.this, "出现空指针错误", Toast.LENGTH_SHORT).show();
        }
    }

}
