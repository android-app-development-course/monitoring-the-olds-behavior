package com.phemie.scnu.laolekang.Fragment3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phemie.scnu.laolekang.CircleFrame;
import com.phemie.scnu.laolekang.Fragment4.step.utils.SharedPreferencesUtils;
import com.phemie.scnu.laolekang.InitialActivity;
import com.phemie.scnu.laolekang.InitialHelper;
import com.phemie.scnu.laolekang.MainActivity;
import com.phemie.scnu.laolekang.R;


public class ThirdFragment extends Fragment implements View.OnClickListener{
    private SharedPreferencesUtils sp;//SharedPreferences的工具类
    public static String color;
    MainActivity mainActivity;
    private FrameLayout ly_content;
    FloatingActionButton addperson;



    private View view;
    private ImageButton imageButton;
    private ListView listView;
    int currentStep;//存放当前步数
    private TextView healthlist;
    private TextView personlist;
    private CircleFrame healthbutton;
    private CircleFrame medicinebutton;
    private CircleFrame callbutton;
    public ThirdFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_third, container, false);
        HealthList mAdapter=new HealthList();
        // Inflate the layout for this fragment
//        String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "7000");
//        StepService stepService=new StepService();
//        currentStep = stepService.getStepCount();//获取当前步数
        initial();
        setClick();
        listView=(ListView)view.findViewById(R.id.datalist);
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.ssetting);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"打开",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Setting_Main.class);
                startActivity(intent);
            }
        });
        if (MainActivity.counter == 0) {
            color = "blue";
        }



        healthlist=(TextView) view.findViewById(R.id.healthlist);
        personlist=(TextView)view.findViewById(R.id.personlist);
        healthlist.setTextColor(Color.parseColor("#088d7f"));
        personlist.setTextColor(Color.parseColor("#000000"));
        healthlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthlist.setTextColor(Color.parseColor("#088d7f"));
                personlist.setTextColor(Color.parseColor("#000000"));
                HealthList adapter=new HealthList();
                listView.setAdapter(adapter);

            }
        });//健康列表
        personlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthlist.setTextColor(Color.parseColor("#000000"));
                personlist.setTextColor(Color.parseColor("#088d7f"));
                DataList adapter1=new DataList();
                if(adapter1.getCount() == 0)
                    // 如果联系人列表中没有任何的信息，通知用户添加信息
                    Toast.makeText(getActivity(), "请尽快添加联系人", Toast.LENGTH_SHORT).show();
                listView.setAdapter(adapter1);
            }
        });
        listView.setAdapter(mAdapter);
        return view;
    }


    public void initial(){
        healthbutton=(CircleFrame)view.findViewById(R.id.three_headline_health);
        medicinebutton=(CircleFrame)view.findViewById(R.id.three_headline_medicine);
        callbutton=(CircleFrame)view.findViewById(R.id.three_headline_call);
        addperson=(FloatingActionButton)view.findViewById(R.id.addcontent);
        mainActivity = (MainActivity) getActivity();
    }


    public void setClick(){
        healthbutton.setOnClickListener(this);
        medicinebutton.setOnClickListener(this);
        callbutton.setOnClickListener(this);
        addperson.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.three_headline_health:
                mainActivity.gotoFourthtFragment();
                break;
            case R.id.three_headline_call:
                mainActivity.gotoFifthFragment();
                break;
            case R.id.three_headline_medicine:
                mainActivity.gotoSecondFragment();
                break;
            case R.id.addcontent:
                mainActivity.gotoFistFragment();
            default:
                break;
        }
    }

    public void onResume() {
        healthlist.setTextColor(Color.parseColor("#088d7f"));
        personlist.setTextColor(Color.parseColor("#000000"));
        HealthList adapter=new HealthList();
        listView.setAdapter(adapter);
//        if(color.equals("red")) {
//            view.setBackgroundResource(R.drawable.six_redbackground);
//        }else if(color.equals("gray")){
//            view.setBackgroundResource(R.drawable.six_graybackground);
//        } else
//        {
//            view.setBackgroundResource(R.drawable.background);
//        }
//        Log.i("color",color);
        super.onResume();
    }//当ThirdFrament重新回到当前页面是调用

    public void onPause() {
        super.onPause();
    }//ThirdFragemt进栈时调用

    class DataList extends BaseAdapter {
        //String s=String.valueOf(currentStep);

        String[] titles = new String[0];//联系人
        String[] contents = new String[0];//联系方式

        public DataList(){
            readMySettingsDatabase();
        }
        public int getCount() {
            //返回ListView的item的条目
            return titles.length;
        }

        @Override
        //获得item代表的对象
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        //得到item的id
        public long getItemId(int position) {
            //返回ListView item的id
            return position;
        }

        @Override
        //得到item的view视图
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(
                        getActivity().getApplicationContext()).inflate(R.layout.thirdfragment_item, parent, false);
                holder = new ViewHolder();
                holder.tvtitle = (TextView) convertView.findViewById(R.id.listtitle);
                holder.tvcontent = (TextView) convertView.findViewById(R.id.listcontent);
                holder.tvtitle.setText(titles[position]);
                holder.tvcontent.setText(contents[position]);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }
        public void readMySettingsDatabase() {
            InitialActivity.helper = new InitialHelper(getActivity());
            SQLiteDatabase db = InitialActivity.helper.getWritableDatabase();
            // 执行查询语句
            String username = MainActivity.username;

            // 查询当前用户的的“标题信息”，并设置“标题”编辑框中的文本
            Cursor cursor = db.rawQuery("select * from mySettings where username=?", new String[]{username});
            titles = new String[cursor.getCount()];
            contents = new String[cursor.getCount()];
            if (cursor.getCount() == 0) {
                Toast.makeText(getActivity(), "数据库没有任何信息", Toast.LENGTH_SHORT).show();
                return;
            }

            cursor.moveToFirst();
            for (int i = 0; cursor.isAfterLast() == false; cursor.moveToNext()) {
                int columnTitle = cursor.getColumnIndex("title");
                int columnContent = cursor.getColumnIndex("content");
                titles[i] = new String(cursor.getString(columnTitle));
                contents[i] = new String(cursor.getString(columnContent));

                // 输出信息
                Log.i("FirstFragment1", titles[i] + ":" + contents[i]);

                i++;
            }


            // 关闭相关资源
            cursor.close();
            db.close();
            InitialActivity.helper = null;
        }
    }

    class HealthList extends BaseAdapter {
        //String s=String.valueOf(currentStep);
        String[] healthtitle=new String[1];//健康标题
        String[] healthcontent=new String[1];//健康信息
        public HealthList(){
            healthtitle[0]="步数";
            healthcontent[0]="暂无数据";
        }
        @Override
        public int getCount() {
            //返回ListView的item的条目
            return healthtitle.length;
        }

        @Override
        //获得item代表的对象
        public Object getItem(int position) {
            return healthtitle[position];
        }

        @Override
        //得到item的id
        public long getItemId(int position) {
            //返回ListView item的id
            return position;
        }

        @Override
        //得到item的view视图
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(
                        getActivity().getApplicationContext()).inflate(R.layout.thirdfragment_item, parent, false);
                holder = new ViewHolder();
                holder.tvtitle = (TextView) convertView.findViewById(R.id.listtitle);
                holder.tvcontent = (TextView) convertView.findViewById(R.id.listcontent);
                holder.tvtitle.setText(healthtitle[position]);
                holder.tvcontent.setText(healthcontent[position]);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }

    }
    class ViewHolder{
        TextView tvtitle;
        TextView tvcontent;
    }

}
