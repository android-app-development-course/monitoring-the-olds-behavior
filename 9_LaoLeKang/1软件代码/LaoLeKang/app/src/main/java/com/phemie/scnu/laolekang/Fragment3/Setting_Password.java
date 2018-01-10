package com.phemie.scnu.laolekang.Fragment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.phemie.scnu.laolekang.LoginActivity;
import com.phemie.scnu.laolekang.MainActivity;
import com.phemie.scnu.laolekang.R;
import com.phemie.scnu.laolekang.Regular;

/**
 * Created by duhuicheng on 2017/12/23.
 */

public class Setting_Password extends AppCompatActivity {
    MyHelper myHelper;//定义myHelper

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止输入框覆盖
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.setting_password);
        myHelper=new MyHelper(this);
        final EditText mEditText1=(EditText)findViewById(R.id.oldpass);//旧密码
        final EditText mEditText2=(EditText)findViewById(R.id.newpass);//新密码
        final EditText mEditText3=(EditText)findViewById(R.id.comfirmpass);//确认密码
        ImageButton imageButton=(ImageButton)findViewById(R.id.rreturn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//返回上一层
        Button mButton=(Button)findViewById(R.id.save);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db;//创建db对象
                ContentValues values;
                //ContentValues testvalues;
                //testvalues=new ContentValues();
                //db=myHelper.getWritableDatabase();
                //testvalues.put("username","84136549600");
                //testvalues.put("password","87654321");
                String username1 = MainActivity.username;//当前用户的账号
                String oldpassword;//用于获取当前用户的旧密码
                String newPassword = mEditText2.getText().toString().trim();
                //db.insert("information",null,testvalues);
                //db.close();

                String m1 = "^[A-Za-z0-9]+$";
                if(Regular.regular(m1, newPassword) == false)
                {
                    Toast.makeText(Setting_Password.this, "密码只能输入由数字和26个英文字母组成",
                            Toast.LENGTH_SHORT).show();
                    mEditText2.setText(""); mEditText3.setText("");
                    return;
                }

                String m2 = "^.{1,16}$";
                if(Regular.regular(m2, newPassword) == false){
                    mEditText2.setText(""); mEditText3.setText("");
                    Toast.makeText(Setting_Password.this, "密码长度不能超过16位", Toast.LENGTH_SHORT).show();
                    return;
                }


                db = myHelper.getWritableDatabase();
                values=new ContentValues();
                Cursor cursor=db.rawQuery("select * from userBasicInformation where username=?",new  String[]{username1});
                // query("information", new String[]{"id as _id","password","username"},
                       // "username=?", new String[]{username1}, null, null, null);
                if(cursor.getCount() ==0)
                    return;
                else {
                    int nameColumnIndex = cursor.getColumnIndex("password");
                    cursor.moveToFirst();
                    oldpassword = cursor.getString(nameColumnIndex);
                    if (!oldpassword.equals(mEditText1.getText().toString())) {
                        Toast.makeText(Setting_Password.this, "输入的原密码不正确", Toast.LENGTH_SHORT).show();
                        mEditText1.setText("");
                        mEditText2.setText("");
                        mEditText3.setText("");
                    } else if (!mEditText2.getText().toString().equals( mEditText3.getText().toString())) {
                        Toast.makeText(Setting_Password.this, "两次输入的新密码不相同", Toast.LENGTH_SHORT).show();
                        mEditText1.setText("");
                        mEditText2.setText("");
                        mEditText3.setText("");
                    } else if (mEditText2.getText().toString() .equals(oldpassword)) {
                        Toast.makeText(Setting_Password.this, "新密码与旧密码不能相同", Toast.LENGTH_SHORT).show();
                        mEditText1.setText("");
                        mEditText2.setText("");
                        mEditText3.setText("");
                    } else {
                        values.put("password", mEditText2.getText().toString());
                        db.execSQL("update userBasicInformation set password=? where username=?",
                        new Object[]{mEditText2.getText().toString(),username1});
                        Toast.makeText(Setting_Password.this, "修改密码成功！", Toast.LENGTH_SHORT).show();

                        LoginActivity.autoLogin = false;

                        cursor.close();
                        db.close();
                        finish();
                    }
                }
            }
        });//保存并返回上一层
    }
    public static void setEditTextInhibitInputSpace(EditText editText){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals(" ")){
                    return "";
                }else{
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
    class MyHelper extends SQLiteOpenHelper{
        public MyHelper(Context context) {
            super(context,"monitor.db", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE userBasicInformation(username VARCHAR(20) PRIMARY KEY," +
                    "password VARCHAR(20))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
