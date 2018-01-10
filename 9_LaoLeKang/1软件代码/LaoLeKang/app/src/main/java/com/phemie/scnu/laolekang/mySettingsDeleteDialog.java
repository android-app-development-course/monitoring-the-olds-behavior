package com.phemie.scnu.laolekang;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.phemie.scnu.laolekang.Fragment1.FirstFragment;
import com.phemie.scnu.laolekang.Fragment5.FifthFragment;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

// 自定义对话框，删除时数据库的元素时给出提示
public class mySettingsDeleteDialog extends Dialog {
    // 对话框中的组件
    private Button btCancel;    private Button btDelete;

    // 传输的数据
    private String[] information;

    public mySettingsDeleteDialog(@NonNull Context context, String[] information) {
        super(context);
        this.information = information;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mysettings_delete_dialog);
        btCancel = (Button)findViewById(R.id.mysettings_delete_dialog_bt_cancel);
        btDelete = (Button)findViewById(R.id.mysettings_delete_dialog_bt_delete);

        // 为按钮设置时间监听器
        deleteDialogListener listener = new deleteDialogListener(information);
        btCancel.setOnClickListener(listener);  btDelete.setOnClickListener(listener);

    }


    private class deleteDialogListener implements View.OnClickListener{
        private ActivityCompat activity;

        // 数据库中的数据，包括“用户名”，“标题”，“内容”
        String[] information;

        // 构造函数
        deleteDialogListener(String[] info){
            this.information = info;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.mysettings_delete_dialog_bt_cancel:
                    dismiss();  // 直接返回
                    break;

                case R.id.mysettings_delete_dialog_bt_delete:
                    deleteElement();
                    dismiss();
                    break;
            }
        }

        // 删除数据库中的元素
        private void deleteElement(){
            // 获取数据库
            InitialActivity.helper = new InitialHelper(getContext());
            SQLiteDatabase db = InitialActivity.helper.getWritableDatabase();

            // 直接删除数据库中的数据
            try{

                db.execSQL("delete from mySettings where username=? AND title=?" +
                        " AND content=?", information);
                Toast.makeText(getContext(), "删除数据成功！", Toast.LENGTH_SHORT).show();
            }
            catch(SQLiteDatabaseCorruptException e){
                Toast.makeText(getContext(), "删除数据库时出错！", Toast.LENGTH_SHORT).show();
            }
            finally{
                // 释放资源
                db.close(); InitialActivity.helper = null;

                // 更新FirstFragment的ListView
                try {
                    FirstFragment.fragment_class.onResume(); // 更新 ListView 组件
                    FifthFragment.fifth_frafment.onResume();    // 更新 ListView 组件
                }
                catch(NullPointerException e){
                    Toast.makeText(FirstFragment.fragment_class.getActivity(),
                            "数据库中没有任何数据", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
