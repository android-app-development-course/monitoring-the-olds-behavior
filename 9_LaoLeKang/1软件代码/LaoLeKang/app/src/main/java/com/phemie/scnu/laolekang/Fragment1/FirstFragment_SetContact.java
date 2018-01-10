package com.phemie.scnu.laolekang.Fragment1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.phemie.scnu.laolekang.InitialActivity;
import com.phemie.scnu.laolekang.InitialHelper;
import com.phemie.scnu.laolekang.MainActivity;
import com.phemie.scnu.laolekang.R;
import com.phemie.scnu.laolekang.Regular;

import static com.phemie.scnu.laolekang.MainActivity.username;

/**
 * Created by duhuicheng on 2018/1/5.
 */

public class FirstFragment_SetContact extends AppCompatActivity implements View.OnClickListener{
    private EditText etTitle;//联系人姓名输入框
    private EditText etContent;//联系人联系方式输入框
    private ImageButton imageButton;//返回按钮
    private Button button;//保存按钮
    private TextView tvPhoneInformation;    // 显示电话号码格式
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止输入框覆盖
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.fragment_first_set_contact);
        etTitle = (EditText)findViewById(R.id.firstFragment_setname);
        etContent = (EditText)findViewById(R.id.firstFragment_setnumber);
        imageButton=(ImageButton)findViewById(R.id.rreturn);
        button=(Button)findViewById(R.id.save);
        imageButton.setOnClickListener((View.OnClickListener) this);//返回按钮的点击事件
        button.setOnClickListener((View.OnClickListener) this);//保存按钮的点击事件

        tvPhoneInformation = (TextView)findViewById(R.id.firstFragment_tv_phone_information);
        tvPhoneInformation.setText("");

        // 当是更新操作时，显示信息给用户看
        Intent intent = getIntent();
        String operation = intent.getStringExtra("operation");
        if(operation.equals("update"))
        {
            String title = intent.getStringExtra("oldTitle");
            String content = intent.getStringExtra("oldContent");
            etContent.setText(content); etTitle.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        // 给出提示操作
        Intent intent = getIntent();
        String s = intent.getStringExtra("operation");
        switch(s){
        }


        switch (v.getId()) {
            case R.id.rreturn:
                finish();
                break;//返回上一级菜单
            case R.id.save:
                // “标题”和“内容”
                String oldTitle = intent.getStringExtra("oldTitle");
                String oldContent = intent.getStringExtra("oldContent");

                // 新的“标题”和“内容”
                String newTitle = etTitle.getText().toString().trim();
                String newContent = etContent.getText().toString();

                /*if (newTitle.equals("")) {
                    Toast.makeText(this, "请输入联系人", Toast.LENGTH_SHORT).show();
                    return;
                } else if (newContent.equals("")) {
                    Toast.makeText(this, "请输入联系方式", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                // 输入合法性检查
                /*String m1 = "^\\d{7}$"; String m2 = "^\\d{11}$";
                if(Regular.regular(m1, newContent) == false &&
                        Regular.regular(m2, newContent) == false)
                {
                    etContent.setText("");
                    Toast.makeText(this, "联系方式只能为7位或11位数字！", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                String m1 = "(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|" +
                        "(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\" +
                        "d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";
                if(Regular.regular(m1, newContent) == false)
                {
                    etContent.setText("");
                    Toast.makeText(this, "请输入正确的电话号码格式！", Toast.LENGTH_SHORT).show();

                    // 设置电话号码提示信息
                    String s0 = "（支持手机号码，3-4位区号，7-8位直播号码，1－4位分机号）";
                    tvPhoneInformation.setText(s0);
                    return;
                }

                saveMySettings(oldTitle, oldContent, newTitle, newContent);
                finish();
                break;
        }
    }

    // 从数据库中读取“标题”和“内容”信息
    private String[] readMySettingsDatabase(){
        InitialActivity.helper = new InitialHelper(this);
        SQLiteDatabase db = InitialActivity.helper.getWritableDatabase();
        // 执行查询语句
        String username = MainActivity.username;

        // 查询当前用户的的“标题信息”，并设置“标题”编辑框中的文本
        Cursor cursor = db.rawQuery("select * from mySettings where username=?", new String[] {username});
        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "数据库没有任何信息", Toast.LENGTH_SHORT).show();
            return null;
        }

        cursor.moveToFirst();
        String oldTitle;    String oldContent;
        int columnTitle = cursor.getColumnIndex("title");   int columnContent = cursor.getColumnIndex("content");
        oldTitle = cursor.getString(columnTitle);  oldContent = cursor.getString(columnContent);

        // 根据从上个界面传过来的信息，设置“标题”编辑框
        Intent intent = getIntent();
        String operation = intent.getStringExtra("operation");
        /*if(operation.equals("update")){
            etTitle.setText(title); // 如果是“更新”操作，那么把文本框设置为原先的文本
        }*/


        // 关闭相关资源
        cursor.close(); db.close();
        InitialActivity.helper = null;

        return new String[]{oldTitle, oldContent};
    }
    // 将用户设置的信息保存到数据库中
    private void saveMySettings(String oldTitle, String oldContent, String newTitle, String newContent){

        InitialActivity.helper = new InitialHelper(this);
        SQLiteDatabase db = InitialActivity.helper.getWritableDatabase();


        // 根据上个界面传过来的“操作信息”修改数据库
        Intent intent = getIntent();
        String operation = intent.getStringExtra("operation");
        switch(operation) {
            case "update":
                // 修改一条数据
                db.execSQL("update mySettings set title=?,content=? where username=? " +
                        "AND title=? AND content=?", new String[]{
                        newTitle, newContent, username, oldTitle, oldContent});
                // 给出提示信息
                Toast.makeText(this, "修改信息成功！", Toast.LENGTH_SHORT).show();
                Log.i("FirstFragment1", username+":"+newTitle+", "+newContent);
                break;

            case "insert":
                // 增加一个数据
                String username = MainActivity.username;
                String head_path = FirstFragment.head_path;
                db.execSQL("insert into mySettings (username,title,content) values (?,?,?)",
                        new Object[]{username, newTitle, newContent});
                Toast.makeText(this, "加入联系人成功", Toast.LENGTH_SHORT).show();
                Log.i("FirstFragment1", username+":"+newTitle+", "+newContent);
                break;

            default:
                Toast.makeText(this, "没有任何合法的操作", Toast.LENGTH_SHORT).show();
                break;
        }

        // 关闭相关资源
        db.close(); InitialActivity.helper = null;
    }

    /*// 检验输入是否合法
    public static boolean regular(String s1, String s2){
        Pattern p = Pattern.compile(s1);
        Matcher m = p.matcher(s2);

        return m.matches();
    }*/
}
