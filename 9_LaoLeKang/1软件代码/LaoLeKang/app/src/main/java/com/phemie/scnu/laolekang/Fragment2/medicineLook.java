package com.phemie.scnu.laolekang.Fragment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.phemie.scnu.laolekang.R;

import java.util.HashMap;
import java.util.Map;


public class medicineLook extends AppCompatActivity {

    //RadioGroup shapeRG;
    //RadioGroup colorRG;
    ImageView imLook;
    ImageView imColor;
    private Map<String,Integer> mShape;
    private Map<String,Integer> mColor;
    ImageButton imbtn_return;
    Button btn_commit;
    View titleView;
    int shapeID;
    int colorID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_look);

        Init();
        setListener();
    }

    private void Init(){
        //shapeRG=(RadioGroup)findViewById(R.id.medicineShapeRG);
        //colorRG=(RadioGroup)findViewById(R.id.medicineColorRG);
        imLook=(ImageView)findViewById(R.id.im_medicinelook);
        imLook.setImageResource(R.drawable.pill);
        imColor=(ImageView)findViewById(R.id.im_medicineColor);
        mShape=new HashMap<>();
        mShape.put("默认样式",R.drawable.pill);
        mShape.put("样式1",R.drawable.medicinelook1);
        mShape.put("样式2",R.drawable.medicinelook2);
        mShape.put("样式3",R.drawable.medicinelook3);
        mShape.put("样式4",R.drawable.medicinelook4);
        mShape.put("样式5",R.drawable.medicinelook5);

        mColor=new HashMap<>();
        mColor.put("默认颜色（白色）",R.drawable.medicine_white);
        mColor.put("黑色",R.drawable.medicine_black);
        mColor.put("棕色",R.drawable.medicine_brown);
        mColor.put("绿色",R.drawable.medicine_green);
        mColor.put("橙色",R.drawable.medicine_orange);
        mColor.put("粉色",R.drawable.medicine_pink);

        titleView=findViewById(R.id.medicineLookTitle);
        ((TextView)titleView.findViewById(R.id.txt_top)).setText("药物外观");

        imbtn_return=(ImageButton)titleView.findViewById(R.id.addMedicineReturn);
        btn_commit=(Button)titleView.findViewById(R.id.addMedicineCommit);
        if (getIntent().getBooleanExtra("edit",false)) {
            shapeID = getIntent().getIntExtra("shape",R.drawable.pill);
            colorID = getIntent().getIntExtra("color",R.drawable.medicine_white);
            int shapeid=R.id.rbtn_pill;
            switch (shapeID){
                case R.drawable.pill:
                    shapeid=R.id.rbtn_pill;
                    break;
                case R.drawable.medicinelook1:
                    shapeid=R.id.rbtn_shape1;
                    break;
                case R.drawable.medicinelook2:
                    shapeid=R.id.rbtn_shape2;
                    break;
                case R.drawable.medicinelook3:
                    shapeid=R.id.rbtn_shape3;
                    break;
                case R.drawable.medicinelook4:
                    shapeid=R.id.rbtn_shape4;
                    break;
                case R.drawable.medicinelook5:
                    shapeid=R.id.rbtn_shape5;
                    break;
            }
            int colorid=R.id.rbtn_white;
            switch (colorID){
                case R.drawable.medicine_black:
                    colorid=R.id.rbtn_black;
                    break;
                case R.drawable.medicine_white:
                    colorid=R.id.rbtn_white;
                    break;
                case R.drawable.medicine_pink:
                    colorid=R.id.rbtn_pink;
                    break;
                case R.drawable.medicine_brown:
                    colorid=R.id.rbtn_brown;
                    break;
                case R.drawable.medicine_orange:
                    colorid=R.id.rbtn_orange;
                    break;
                case R.drawable.medicine_green:
                    colorid=R.id.rbtn_green;
                    break;

            }
            RadioButton shape=(RadioButton)findViewById(shapeid);
            RadioButton color=(RadioButton)findViewById(colorid);
            shape.setChecked(true);
            color.setChecked(true);
            imLook.setImageResource(shapeID);
            imColor.setImageResource(colorID);
        }
    }

    private void setListener(){
        imbtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据回传
                Intent intent=new Intent();
                intent.putExtra("shapeID",shapeID);
                intent.putExtra("colorID",colorID);
                setResult(2,intent);

                finish();
            }
        });
    }

    public void clickShape(View view){
        RadioButton radioButton= (RadioButton) view;
        //获得选中的单选框的值
        String str=radioButton.getText().toString();
        //设置图片为相对应需要的图片
        imLook.setImageResource(mShape.get(str));
        //记录选择值
        shapeID=mShape.get(str);
    }

    public void clickColor(View view){
        RadioButton radioButton= (RadioButton) view;
        //获得选中的单选框的值
        String str=radioButton.getText().toString();
        //设置图片为相对应需要的图片
        imColor.setImageResource(mColor.get(str));
        //记录选择值
        colorID=mColor.get(str);
    }

}
