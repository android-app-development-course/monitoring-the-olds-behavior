package com.phemie.scnu.laolekang.Fragment3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;

import com.phemie.scnu.laolekang.R;

/**
 * Created by duhuicheng on 2017/12/28.
 */

public class Setting_Language extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止输入框覆盖
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        setContentView(R.layout.setting_language);
        ImageButton imageButton=(ImageButton)findViewById(R.id.rreturn);
        Button mButton=(Button)findViewById(R.id.save);
        CheckedTextView checkedTextView=(CheckedTextView)findViewById(R.id.checkedTextView);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checkedTextView.setChecked(false);
    }
    private void restartApp(){
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        System.exit(0);
    }
}
