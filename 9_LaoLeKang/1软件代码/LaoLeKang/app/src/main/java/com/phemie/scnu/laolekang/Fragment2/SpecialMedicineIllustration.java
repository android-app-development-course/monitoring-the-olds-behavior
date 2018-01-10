package com.phemie.scnu.laolekang.Fragment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.phemie.scnu.laolekang.R;


public class SpecialMedicineIllustration extends AppCompatActivity {

    View titleView;
    Button btn_commit;
    ImageButton imbtn_return;
    EditText inputContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_medicine_illustration);

        Init();
        setListener();
    }

    private void Init(){
        titleView=findViewById(R.id.specialMedicineIllustrationTitle);
        ((TextView)titleView.findViewById(R.id.txt_top)).setText("特殊说明");
        inputContext=(EditText) findViewById(R.id.medicineIllustrationContext);
        btn_commit=(Button)titleView.findViewById(R.id.addMedicineCommit);
        imbtn_return=(ImageButton)titleView.findViewById(R.id.addMedicineReturn);
        if (getIntent().getBooleanExtra("edit",false)){
            inputContext.setText(getIntent().getStringExtra("illu"));
        }
    }

    private void setListener(){
        imbtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//不保存回传
            }
        });
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("illustration",inputContext.getText().toString());
                setResult(3,intent);
                finish();//保存回传
            }
        });
    }
}
