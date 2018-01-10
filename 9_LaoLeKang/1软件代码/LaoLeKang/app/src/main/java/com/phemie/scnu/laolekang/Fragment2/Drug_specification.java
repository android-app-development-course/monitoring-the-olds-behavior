package com.phemie.scnu.laolekang.Fragment2;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.phemie.scnu.laolekang.R;


public class Drug_specification extends AppCompatActivity {

    View titleView;
    RadioGroup rg;
    ImageButton imbtn_return;
    Button btn_commit;
    String choosed;
    RadioButton b1;
    RadioButton b2;
    RadioButton b3;
    RadioButton b4;
    RadioButton b5;
    RadioButton b6;
    RadioButton b7;
    RadioButton b8;
    RadioButton b9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_specification);

        Init();
        setListener();
    }

    private void Init(){
        titleView=findViewById(R.id.drugSpecTitle);
        ((TextView)titleView.findViewById(R.id.txt_top)).setText("药物规格");
        rg=(RadioGroup)findViewById(R.id.specRG);
        imbtn_return=(ImageButton)titleView.findViewById(R.id.addMedicineReturn);
        btn_commit=(Button)titleView.findViewById(R.id.addMedicineCommit);
        //choosed="片";
        if (getIntent().getBooleanExtra("edit",false)) {
            b1 = (RadioButton) findViewById(R.id.rbtn_piece);

            b2 = (RadioButton) findViewById(R.id.rbtn_graininess);
            b3 = (RadioButton) findViewById(R.id.rbtn_mg);
            b4 = (RadioButton) findViewById(R.id.rbtn_ml);
            b5 = (RadioButton) findViewById(R.id.rbtn_drops);
            b6 = (RadioButton) findViewById(R.id.rbtn_bursiform);
            b7 = (RadioButton) findViewById(R.id.rbtn_spoon);
            b8 = (RadioButton) findViewById(R.id.rbtn_doliform);
            b9 = (RadioButton) findViewById(R.id.rbtn_time);

            choosed = getIntent().getStringExtra("spi");
            if (choosed.equals("片")) {
                b1.setChecked(true);
            } else if (choosed.equals("粒"))
                b2.setChecked(true);
            else if (choosed.equals("毫克"))
                b3.setChecked(true);
            else if (choosed.equals("毫升"))
                b4.setChecked(true);
            else if (choosed.equals("滴"))
                b5.setChecked(true);
            else if (choosed.equals("袋"))
                b6.setChecked(true);
            else if (choosed.equals("勺"))
                b7.setChecked(true);
            else if (choosed.equals("瓶"))
                b8.setChecked(true);
            else
                b9.setChecked(true);
        }
    }

    private void setListener(){
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId){
                    case R.id.rbtn_piece:
                        choosed="片";
                        break;
                    case R.id.rbtn_graininess:
                        choosed="粒";
                        break;
                    case R.id.rbtn_mg:
                        choosed="毫克";
                        break;
                    case R.id.rbtn_ml:
                        choosed="毫升";
                        break;
                    case R.id.rbtn_drops:
                        choosed="滴";
                        break;
                    case R.id.rbtn_bursiform:
                        choosed="袋";
                        break;
                    case R.id.rbtn_spoon:
                        choosed="勺";
                        break;
                    case R.id.rbtn_doliform:
                        choosed="瓶";
                        break;
                    case R.id.rbtn_time:
                        choosed="次";
                        break;
                }
            }
        });

        imbtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("specification",choosed);
                setResult(1,intent);
                finish();
            }
        });
    }
}
