package com.phemie.scnu.laolekang.Fragment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.phemie.scnu.laolekang.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class AddMedicinePage extends AppCompatActivity {

    Button btn_commit;
    ImageButton btu_return;
   // LinearLayout contextLayout;
    LayoutInflater minflater;
    View TitleView;
    View scaleView;
    View lookView;
    View DosageView;
    View startDateView;
    View endDateView;
    View frequencyView;

    View specialMedicineIllustrationView;
    private Spinner spinnerStartYear;
    private List<String> startYear_list;
    private ArrayAdapter<String> startYear_adapter;
    private Spinner spinnerEndYear;
    private List<String> endYear_list;
    private ArrayAdapter<String> endYear_adapter;
    private Spinner spinnerStartDay;
    private List<String> day30_list;
    private List<String> day31_list;
    private List<String> day28_list;
    private List<String> day29_list;
    private ArrayAdapter<String> startDay_adapter;
    private Spinner spinnerEndDay;
    private ArrayAdapter<String> endDay_adapter;
    private Spinner spinnerStartMonth;
    private Spinner spinnerEndMonth;
    DateInfo curdate;
    List<detialFrequency> detialFres;
    ListView detailFrequency_Lv;
    detialFrequencyPopupWindow detialPopupWindow;
    DetailFreLVAdapter dAdapter;
    MedicineTimesPopupWindow mtimePopupWindow;

    EditText edit_name;
    EditText edit_dosage;
    //Intent backintent;

    Medicine medicine;

    Boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine_page);

        Intent intent=getIntent();
        isEdit=intent.getBooleanExtra("edit",false);
        Init();
        setListener();
    }

    private void Init(){

        btn_commit=(Button) findViewById(R.id.addMedicineCommit);
        btu_return=(ImageButton)findViewById(R.id.addMedicineReturn);
       // contextLayout=(LinearLayout) findViewById(R.id.addMedicineContext);

        edit_name=(EditText)findViewById(R.id.medicine_name);

        minflater = getLayoutInflater();
        scaleView = findViewById(R.id.medicineScale);
        ((TextView)scaleView.findViewById(R.id.attribute_name)).setText("药物规格");

        lookView=findViewById(R.id.medicineLook);
        ((TextView)lookView.findViewById(R.id.attribute_name)).setText("药物外观");
        ((TextView)lookView.findViewById(R.id.attribute_value)).setText("");
        DosageView=findViewById(R.id.medicineDosage);
        ((TextView)DosageView.findViewById(R.id.textTitle)).setText("药物用量");
        edit_dosage=(EditText)DosageView.findViewById(R.id.medicine_val) ;
        startDateView=findViewById(R.id.startDate);
        endDateView=findViewById(R.id.endDate);
        ((TextView)startDateView.findViewById(R.id.dateName)).setText("开始日期");
        ((TextView)endDateView.findViewById(R.id.dateName)).setText("结束日期");
        frequencyView=findViewById(R.id.frequency);
        ((TextView)frequencyView.findViewById(R.id.attribute_name)).setText("用药频率");


        specialMedicineIllustrationView=findViewById(R.id.specialMedicineIllustration);
        ((TextView)specialMedicineIllustrationView.findViewById(R.id.attribute_name)).setText("特殊说明");
        ((TextView)specialMedicineIllustrationView.findViewById(R.id.attribute_value)).setText("");

        spinnerStartYear=(Spinner)startDateView.findViewById(R.id.yyear);
        spinnerStartMonth=(Spinner)startDateView.findViewById(R.id.mmonth);
        spinnerStartDay=(Spinner)startDateView.findViewById(R.id.dday);
        spinnerEndYear=(Spinner)endDateView.findViewById(R.id.yyear);
        spinnerEndMonth=(Spinner)endDateView.findViewById(R.id.mmonth);
        spinnerEndDay=(Spinner)endDateView.findViewById(R.id.dday);

        curdate=new DateInfo();
        startYear_list=new ArrayList<>();
        endYear_list=new ArrayList<>();
        int tempyear=Integer.parseInt(curdate.getYear());
        for (int i=0;i<11;i++){
            startYear_list.add((tempyear+i)+"");
        }
        day28_list = new ArrayList<String>();
        day29_list = new ArrayList<String>();
        day30_list = new ArrayList<String>();
        day31_list = new ArrayList<String>();
        for (int i=1;i<=28;i++){
            day28_list.add(i+"");
            day29_list.add(i+"");
            day30_list.add(i+"");
            day31_list.add(i+"");
        }
        day29_list.add("29");
        day30_list.add("29");
        day31_list.add("29");
        day30_list.add("30");
        day31_list.add("30");
        day31_list.add("31");

        //适配器
        startYear_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, startYear_list);
        //设置样式
        startYear_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinnerStartYear.setAdapter(startYear_adapter);

        detailFrequency_Lv=(ListView)findViewById(R.id.detailFrequencyList);


       // backintent=new Intent();


        if (isEdit){
            TitleView=findViewById(R.id.addMedicineTitle);
            ((TextView)TitleView.findViewById(R.id.txt_top)).setText("用药事项");
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                medicine = (Medicine)bundle.getParcelable("data");
            }

            ((TextView)scaleView.findViewById(R.id.attribute_value)).setText(medicine.getmSpeci());
            edit_name.setText(medicine.getmName());
            edit_dosage.setText(medicine.getmDosage());

            /*
            判断原始的开始时间是否大于当前的日期
             */
            int sy=Integer.parseInt(medicine.getmStartyear())-Integer.parseInt(curdate.getYear());
            spinnerStartYear.setSelection(sy,true);
            int m=Integer.parseInt(medicine.getmStartmonth());
            spinnerStartMonth.setSelection(m-1,true);
            if (m==2)
            {
                if (sy%4==0)
                {
                    startDay_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day29_list);
                    startDay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerStartDay.setAdapter(startDay_adapter);
                }
                else{
                    startDay_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day28_list);
                    startDay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerStartDay.setAdapter(startDay_adapter);
                }
            }
            else {
                switch (m){
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        startDay_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day31_list);
                        startDay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerStartDay.setAdapter(startDay_adapter);
                        break;
                    default:
                        startDay_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day30_list);
                        startDay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerStartDay.setAdapter(startDay_adapter);
                }
            }
            int d=Integer.parseInt(medicine.getmStartday());
            spinnerStartDay.setSelection(d-1,true);

            sy=Integer.parseInt(medicine.getmStartyear());
            for (int i=sy;i<sy+11;i++){
                endYear_list.add(""+i);
            }
            endYear_adapter= new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, endYear_list);
            endYear_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEndYear.setAdapter(endYear_adapter);

            int ey=Integer.parseInt(medicine.getmEndyear());
            spinnerEndYear.setSelection(ey-Integer.parseInt(medicine.getmStartyear()),true);
             m=Integer.parseInt(medicine.getmEndmonth());
            spinnerEndMonth.setSelection(m-1,true);
            if (m==2)
            {
                if (ey%4==0)
                {
                    endDay_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day29_list);
                    endDay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEndDay.setAdapter(endDay_adapter);
                }
                else{
                    endDay_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day28_list);
                    endDay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEndDay.setAdapter(endDay_adapter);
                }
            }
            else {
                switch (m){
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        endDay_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day31_list);
                        endDay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerEndDay.setAdapter(endDay_adapter);
                        break;
                    default:
                        endDay_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day30_list);
                        endDay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerEndDay.setAdapter(endDay_adapter);
                }
            }
             d=Integer.parseInt(medicine.getmEndday());
            spinnerEndDay.setSelection(d-1,true);
            ((TextView)frequencyView.findViewById(R.id.attribute_value)).setText("每日"+medicine.getmFrequency()+"次");
            detialFres= medicine.getMdetialFrequency();

        }
        else
        {


            endYear_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, startYear_list);
            endYear_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEndYear.setAdapter(endYear_adapter);

            endDay_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day31_list);
            endDay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEndDay.setAdapter(endYear_adapter);

            startDay_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, day31_list);
            startDay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEndDay.setAdapter(startDay_adapter);
            ((TextView)frequencyView.findViewById(R.id.attribute_value)).setText("每日3次");
            detialFres=new Vector<>();
            detialFres.add(new detialFrequency("上午","8","00","无餐饮说明"));
            detialFres.add(new detialFrequency("上午","12","00","餐后"));
            detialFres.add(new detialFrequency("下午","4","00","无餐饮说明"));

            medicine= new Medicine();
            medicine.setmFrequency("3");
            medicine.setMdetialFrequency(detialFres);
            medicine.setVaild(true);
        }

        dAdapter=new DetailFreLVAdapter();
        detailFrequency_Lv.setAdapter(dAdapter);
    }

    private void setListener(){
        scaleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(AddMedicinePage.this,Drug_specification.class);
                intent.putExtra("edit",isEdit);
                intent.putExtra("spi",medicine.getmSpeci());
                startActivityForResult(intent,2222);
            }
        });
        lookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(AddMedicinePage.this,medicineLook.class);
                intent.putExtra("edit",isEdit);
                intent.putExtra("shape",medicine.getmShape());
                intent.putExtra("color",medicine.getmColor());
                startActivityForResult(intent,2222);
            }
        });
        specialMedicineIllustrationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMedicinePage.this,SpecialMedicineIllustration.class);
                intent.putExtra("edit",isEdit);
                intent.putExtra("illu",medicine.getSpecialIllustration());
                startActivityForResult(intent,2222);
            }
        });
        spinnerStartMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                    case 2:
                    case 4:
                    case 6:
                    case 7:
                    case 9:
                    case 11:
                        startDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day31_list);
                        spinnerStartDay.setAdapter(startDay_adapter);
                        break;
                    case 3:
                    case 5:
                    case 8:
                    case 10:
                        startDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day30_list);
                        spinnerStartDay.setAdapter(startDay_adapter);
                        break;
                    case 1:
                        String temp=startYear_list.get(spinnerStartYear.getSelectedItemPosition());
                        if (Integer.parseInt(temp)%4==0){
                            startDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day29_list);//闰年
                            spinnerStartDay.setAdapter(startDay_adapter);
                        }
                        else
                        {
                            startDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day28_list);//非闰年
                            spinnerStartDay.setAdapter(startDay_adapter);
                        }
                        break;
                    default:
                        break;
                }
                medicine.setmStartmonth(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerEndMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                    case 2:
                    case 4:
                    case 6:
                    case 7:
                    case 9:
                    case 11:
                        endDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day31_list);
                        spinnerEndDay.setAdapter(endDay_adapter);
                        break;
                    case 3:
                    case 5:
                    case 8:
                    case 10:
                        endDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day30_list);
                        spinnerEndDay.setAdapter(endDay_adapter);
                        break;
                    case 1:

                        if (Integer.parseInt(spinnerEndYear.getSelectedItem().toString())%4==0){
                            endDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day29_list);//闰年
                            spinnerEndDay.setAdapter(endDay_adapter);
                        }
                        else
                        {
                            endDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day28_list);//非闰年
                            spinnerEndDay.setAdapter(endDay_adapter);
                        }
                        break;
                    default:
                        break;
                }
                medicine.setmEndmonth(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerStartYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int temp=spinnerStartMonth.getSelectedItemPosition();
                if (temp==1)
                {
                    if (Integer.parseInt(startYear_list.get(position))%4==0){
                        startDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day29_list);//闰年
                        spinnerStartDay.setAdapter(startDay_adapter);
                    }
                    else
                    {
                        startDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day28_list);//非闰年
                        spinnerStartDay.setAdapter(startDay_adapter);
                    }
                }
                String str=startYear_list.get(position);
                temp=Integer.parseInt(str);
                endYear_list.clear();
                for (int i=temp;i<temp+11;i++){
                    endYear_list.add(""+i);
                }
                endYear_adapter= new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, endYear_list);
                endYear_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerEndYear.setAdapter(endYear_adapter);
                medicine.setmStartyear(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerEndYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int temp=spinnerEndMonth.getSelectedItemPosition();
                if (temp==1)
                {
                    if (Integer.parseInt(startYear_list.get(position))%4==0){
                        endDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day29_list);//闰年
                        spinnerEndDay.setAdapter(endDay_adapter);
                    }
                    else
                    {
                        endDay_adapter=new ArrayAdapter<String>(AddMedicinePage.this, android.R.layout.simple_spinner_item, day28_list);//非闰年
                        spinnerEndDay.setAdapter(endDay_adapter);
                    }
                }
                medicine.setmEndyear(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerStartDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                medicine.setmStartday(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEndDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                medicine.setmEndday(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        detailFrequency_Lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                detialPopupWindow=new detialFrequencyPopupWindow(AddMedicinePage.this);
                detialPopupWindow.setData(new detialFrequencyPopupWindow.DetailFreDataCallback() {
                    @Override
                    public void setCallback(int[] data) {
                        detialFrequency t=detialFres.get(position);
                        int pos=data[0];
                        switch (pos){
                            case 0:
                                t.setDiningIllustrate("无餐饮说明");
                                break;
                            case 1:
                                t.setDiningIllustrate("餐前");
                                break;
                            case 2:
                                t.setDiningIllustrate("餐中");
                                break;
                            default:
                                t.setDiningIllustrate("餐后");

                        }
                        pos=data[1];
                        switch (pos){
                            case 0:
                                t.setPeriod("上午");
                                break;
                            default:
                                t.setPeriod("下午");
                        }

                        t.setHour((data[2])+"");
                        if (data[3]<9)
                        {
                            t.setMin("0"+(data[3])+"");
                        }
                        else
                            t.setMin((data[3])+"");
                        dAdapter=new DetailFreLVAdapter();
                        detailFrequency_Lv.setAdapter(dAdapter);
                    }

                    @Override
                    public int[] getSelectedData() {
                        detialFrequency t=detialFres.get(position);
                        int[] temp=new int[4];
                        if (t.getDiningIllustrate().equals("无餐饮说明"))
                            temp[0]=0;
                        else if (t.getDiningIllustrate().equals("餐前"))
                            temp[0]=1;
                        else if (t.getDiningIllustrate().equals("餐中"))
                            temp[0]=2;
                        else
                            temp[0]=3;
                        if (t.getPeriod().equals("上午"))
                            temp[1]=0;
                        else
                            temp[1]=1;
                        temp[2]=Integer.parseInt(t.getHour());
                        temp[3]=Integer.parseInt(t.getMin());
                        return temp;
                    }
                });
                detialPopupWindow.showAtLocation(AddMedicinePage.this.findViewById(R.id.addMedicineMainLayout), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

            }
        });
        setListViewHeightBasedOnChildren(detailFrequency_Lv);

        frequencyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtimePopupWindow= new MedicineTimesPopupWindow(AddMedicinePage.this);
                mtimePopupWindow.setData(new MedicineTimesPopupWindow.TimeCallBack() {
                    @Override
                    public void setSelecteditem(int id) {
                        int time=id+1;
                        ((TextView)frequencyView.findViewById(R.id.attribute_value)).setText("每日"+time+"次");
                        if (time>=detialFres.size()){
                            for (int i=detialFres.size();i<time;i++)
                                detialFres.add(new detialFrequency("上午","8","00","无餐饮说明"));
                        }
                        else
                        {
                            for (int i=detialFres.size()-1;i>=time;i--)
                                detialFres.remove(i);
                        }
                        detailFrequency_Lv.setAdapter(new DetailFreLVAdapter());
                        setListViewHeightBasedOnChildren(detailFrequency_Lv);
                        medicine.setmFrequency(""+time);
                    }

                    @Override
                    public int getSelecteditem() {
                        String t=((TextView)frequencyView.findViewById(R.id.attribute_value)).getText().toString().substring(2,3);

                        return Integer.parseInt(t);
                    }
                });
                mtimePopupWindow.showAtLocation(AddMedicinePage.this.findViewById(R.id.addMedicineMainLayout), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        btu_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicine.setmName(edit_name.getText().toString());
                medicine.setmDosage(edit_dosage.getText().toString());
                medicine.setMdetialFrequency(detialFres);
                Intent intent=new Intent();
                intent.putExtra("edit",isEdit);
                intent.putExtra("add",!isEdit);
                intent.putExtra("pos",getIntent().getIntExtra("pos",-1));
                intent.putExtra("medicine",medicine);
                setResult(1,intent);
                finish();
            }
        });
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //if (params.height>222)
        //   params.height=222;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2222) {
            if (resultCode == 1) {
                //回传的数据表示有更新则刷新列表
                String val=data.getStringExtra("specification");
                ((TextView)scaleView.findViewById(R.id.attribute_value)).setText(val);
                medicine.setmSpeci(val);
            }
            else if (resultCode==2){
                //backintent.putExtra("shapeID",data.getIntExtra("shapeID",R.drawable.pill));
                //backintent.putExtra("colorID",data.getIntExtra("colorID",R.drawable.medicine_white));
                medicine.setmColor(data.getIntExtra("colorID",R.drawable.medicine_white));
                medicine.setmShape(data.getIntExtra("shapeID",R.drawable.pill));
            }
            else if (resultCode==3){
                medicine.setSpecialIllustration(data.getStringExtra("illustration"));
            }

        }
        /*
        if (requestCode == 2222) {
            if (resultCode == 1) {
                //回传的数据表示有新增数据则刷新列表
                if (data.getBooleanExtra("save", false)) {

                }
            }
        }*/
    }


    /**
     * 构建用药频率的ListView适配器
     */
    class DetailFreLVAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return detialFres.size();
        }

        @Override
        public Object getItem(int pos) {
            return detialFres.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            //将设计好的列表项布局转换成view对象
            View view = View.inflate(AddMedicinePage.this, R.layout.detailfre_item, null);
            //显示时段
            TextView period = (TextView) view.findViewById(R.id.item_detail_period);
            period.setText(detialFres.get(pos).getPeriod());
            //title.setText(medicines.get(pos));
            //显示时刻
            TextView moment = (TextView) view.findViewById(R.id.item_detail_moment);
            moment.setText(detialFres.get(pos).getMoment());
            //显示餐饮说明
            TextView dining = (TextView) view.findViewById(R.id.item_detail_dining);
            dining.setText(detialFres.get(pos).getDiningIllustrate());

            return view;
        }
    }
}
