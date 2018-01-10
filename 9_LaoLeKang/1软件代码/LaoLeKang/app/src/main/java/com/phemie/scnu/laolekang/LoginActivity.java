package com.phemie.scnu.laolekang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
2017.12.06：
    所用到的数据库需要设置权限，以防止倍恶意读取
*/

public class LoginActivity extends AppCompatActivity {

    ImageView ivAvatar;
    EditText etUsername, etPassword;
    TextView tvWarning;
    CheckBox cbRememberPassword, cbAutoLogin;
    TextView tvCreateUser;
    Button btLogin;

    // 还要添加一个列表选择控件

    public static boolean logined = false; public static boolean autoLogin = false;
    static InitialHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止输入框覆盖
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        setContentView(R.layout.activity_login);

        ivAvatar = (ImageView)findViewById(R.id.login_iv_avatar);
        etUsername = (EditText)findViewById(R.id.login_et_username);
        etPassword = (EditText)findViewById(R.id.login_et_password);
        tvWarning = (TextView)findViewById(R.id.login_tv_warning);
        cbRememberPassword = (CheckBox)findViewById(R.id.login_cb_remember_password);
        cbAutoLogin = (CheckBox)findViewById(R.id.login_cb_autologin);
        tvCreateUser = (TextView)findViewById(R.id.login_tv_createuser);
        btLogin = (Button)findViewById(R.id.login_bt_login);

        cbRememberPassword.setChecked(logined); cbAutoLogin.setChecked(autoLogin);

        // 为按钮添加事件监听器
        loginListener listener = new loginListener(this);
        tvCreateUser.setOnClickListener(listener);
        btLogin.setOnClickListener(listener);

        // 先判断用户是否已经登陆，如果没有登陆，需要手动输入，否则，读取上次退出时的用户名和密码
        if(logined == true)
        {
            // 打开文件读取用户名和密码
            File file = new File(getFilesDir(), InitialActivity.sLastUser); // TODO 在默认路径下的last_user.txt文件中
            // 如果存在该文件，从数据库中读取用户信息
            if(file.exists()){
                try {
                    // 获取最近用户名
                    FileInputStream f = openFileInput(InitialActivity.sLastUser);
                    byte[] b = new byte[f.available()]; f.read(b);
                    String username = new String(b);
                    // 查询数据库，以找到密码
                    InitialActivity.helper = new InitialHelper(this);
                    SQLiteDatabase db = InitialActivity.helper.getWritableDatabase();
                    Cursor cursor = SearchInDatabase.search(db, username);

                    if(cursor.getCount() >0) {
                        cursor.moveToFirst();
                        String password = cursor.getString(1);

                        // 将“用户名”和“密码”设置到编辑框中
                        etPassword.setText(password);   etUsername.setText(username);

                        // 后续操作：释放资源
                        cursor.close();
                        db.close();
                        InitialActivity.helper = null;
                        f.close();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    Log.i("LoginActivity1", "”记住密码“模块发生问题");
                    Toast.makeText(this, "“记住密码”模块发生问题", Toast.LENGTH_SHORT).show();
                }
            }
        }
        // 如果选择了“自动登录”，那么自动点击“登陆”按钮
        if(autoLogin == true)
        {
            //
            btLogin.performClick();
        }

    }


    // LoginActivity的点击响应函数
    private class loginListener implements View.OnClickListener{
        private LoginActivity activity;
        // 构造函数
        public loginListener(LoginActivity a){
            this.activity = a;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.login_bt_login:
                    loginClick(v);
                break;

                case R.id.login_tv_createuser:
                    loginCreateUser(v);
                    break;
            }
        }

        // 登陆按钮事件响应
        public void loginClick(View v)
        {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            // 更新两个静态变量
            logined = cbRememberPassword.isChecked();  autoLogin = cbAutoLogin.isChecked();
            //Log.i("Login", String.valueOf(logined));    Log.i("Login", String.valueOf(autoLogin));

            if(username.equals("") && password.equals("")) {
                Toast.makeText(LoginActivity.this, "请输入用户名和密码！", Toast.LENGTH_LONG).show();
                return;
            }else
            {
                // 如果用户名和密码匹配，跳转到主界面
                if(loginMatch(username, password)){
                    tvWarning.setText("");  // 将提示信息设置为空
                    // 把状态信息写入文件中
                    try {
                        File file = new File(getFilesDir(), InitialActivity.sStatus);
                        FileOutputStream f = new FileOutputStream(file, false);  // 以追加方式写入

                        f.write("LoginActivity.logined:".getBytes());
                        f.write((String.valueOf(logined)+"\n").getBytes());
                        f.write("LoginActivity.autoLogin:".getBytes());
                        f.write((String.valueOf(autoLogin)+"\n").getBytes());   // 将状态信息和“空格”写进文件中
                        f.close(); // 关闭读写流
                    }
                    catch(IOException e)
                    {
                        Log.i("LoginActivity1", "无法写入文件");
                        e.printStackTrace();
                    }

                    // 跳转到主界面
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("username", username);
                    startActivity(i);
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                    // 关闭该界面
                    activity.finish();

                }
                // 否则，给出提示信息
                else{
                    tvWarning.setText("用户名和密码不匹配！");
                    etUsername.setText(""); etPassword.setText("");
                    return;
                }
            }
        }

        // 检查用户名和密码是否匹配
        boolean loginMatch(String username, String password)
        {
            // 从数据库中读取用户名和密码，进行匹配
            InitialActivity.helper = new InitialHelper(activity);
            SQLiteDatabase db = InitialActivity.helper.getWritableDatabase();
            Cursor cursor = SearchInDatabase.search(db, username);    // 查找游标
            if(cursor.getCount() == 0)
                return false;

            cursor.moveToFirst();
            // 如果查询到的密码与输入的密码一致，那么登陆成功，否则，登录失败
            boolean canLogin = false;
            if(cursor.getString(1).equals(password))
                canLogin = true;

            // 关闭数据库和游标
            cursor.close(); db.close(); InitialActivity.helper = null;
            return canLogin;


        /*// 用于测试的代码
        if(username.equals("laolekang") && password.equals("laolekang"))
            return true;
        else
            return false;*/
        }

        // 创建账号文本事件响应
        public void loginCreateUser(View v){
            Intent intent = new Intent(LoginActivity.this, SignActivity.class);
            startActivityForResult(intent, 1);
        }

    }

    // 重新回到前台时，执行该函数
    public void onRestart(){
        super.onRestart();
        // 重置状态
        tvWarning.setText("");
        cbRememberPassword.setChecked(logined); cbAutoLogin.setChecked(autoLogin);
    }

    // 获取从SignActi 返回的数据
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            // 获取从“注册界面”传递过来的信息
            etUsername.setText(data.getStringExtra("Username"));
            etPassword.setText(data.getStringExtra("Password"));
        }
    }

}


class SearchInDatabase{
    public static Cursor search(SQLiteDatabase db, String username){
        // 查询并获取用户名对应的一项
        Cursor cursor = db.rawQuery("select * from userBasicInformation where username=?",
                new String[]{username});
        return cursor;
    }
}
