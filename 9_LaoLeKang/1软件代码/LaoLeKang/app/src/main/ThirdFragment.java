package com.phemie.scnu.laolekang.Fragment3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.phemie.scnu.laolekang.CircleFrame;
import com.phemie.scnu.laolekang.Fragment1.FirstFragment;
import com.phemie.scnu.laolekang.Fragment2.SecondFragment;
import com.phemie.scnu.laolekang.Fragment3.Setting_Main;
import com.phemie.scnu.laolekang.Fragment4.FourthFragment;
import com.phemie.scnu.laolekang.Fragment4.Health.HeartRate.HeartRateActivity;
import com.phemie.scnu.laolekang.Fragment4.Health.FootStep.StepActivity;
import com.phemie.scnu.laolekang.Fragment4.step.utils.SharedPreferencesUtils;
import com.phemie.scnu.laolekang.Fragment5.FifthFragment;
import com.phemie.scnu.laolekang.InitialActivity;
import com.phemie.scnu.laolekang.InitialHelper;
import com.phemie.scnu.laolekang.MainActivity;
import com.phemie.scnu.laolekang.R;
import com.phemie.scnu.laolekang.Fragment4.step.service.*;


public class ThirdFragment extends Fragment {
    private SharedPreferencesUtils sp;//SharedPreferences的工具类
    public static String color;
    MainActivity mainActivity;
    private FrameLayout ly_content;

    private FirstFragment f1,f11;
    private SecondFragment f2,f22;
    private ThirdFragment f3,f33;
    private FourthFragment f4,f44;
    private FifthFragment f5,f55;
    private FragmentManager fragmentManager;
    FragmentTransaction ftransaction;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FourthFragment fourFragment;
    private FifthFragment fifthFragment;

    //
//    private ImageView home_medicine;
//    private ImageView home_health;
//    private ImageView home_heartbeat;
//    private ImageView home_foot;

    //
    //private ImageView home_call;
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
//        initial();
//        setClick();
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
        healthbutton=(CircleFrame)view.findViewById(R.id.three_headline_health);
        healthbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fourFragment =new FourthFragment();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                //此处实质是先remove再add，所以FirstFragment的UI视图会销毁，回退时会重新创建视图重走onCreateView方法，
                fragmentTransaction.replace(R.id.fragment_container,fourFragment,"four");
                //将FirstFragment放入回退栈
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
                }

        });
        medicinebutton=(CircleFrame)view.findViewById(R.id.three_headline_medicine);
        medicinebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondFragment =new SecondFragment();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                //此处实质是先remove再add，所以ThirdFragment的UI视图会销毁，回退时会重新创建视图重走onCreateView方法，
                fragmentTransaction.replace(R.id.fragment_container,secondFragment,"two");
                //将FirstFragment放入回退栈
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.hide(thirdFragment);
                fragmentTransaction.commit();
            }
        });
        callbutton=(CircleFrame)view.findViewById(R.id.three_headline_call);
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fifthFragment =new FifthFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                //此处实质是先remove再add，所以FirstFragment的UI视图会销毁，回退时会重新创建视图重走onCreateView方法，
                MainActivity.funtion="health";
                //将FirstFragment放入回退栈
                fragmentTransaction.hide(thirdFragment);
                fragmentTransaction.commit();
            }
        });
        healthlist=(TextView) view.findViewById(R.id.healthlist);
        personlist=(TextView)view.findViewById(R.id.personlist);
        healthlist.setBackgroundColor(Color.parseColor("#7CFC00"));
        personlist.setBackgroundColor(Color.argb(0, 0, 0, 0));
        healthlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthlist.setBackgroundColor(Color.parseColor("#7CFC00"));
                personlist.setBackgroundColor(Color.argb(0, 0, 0, 0));
                HealthList adapter=new HealthList();
                listView.setAdapter(adapter);

            }
        });//健康列表
        personlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personlist.setBackgroundColor(Color.parseColor("#7CFC00"));
                healthlist.setBackgroundColor(Color.argb(0, 0, 0, 0));
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


    public void onResume() {
        healthlist.setBackgroundColor(Color.parseColor("#7CFC00"));
        personlist.setBackgroundColor(Color.argb(0, 0, 0, 0));
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
            healthcontent[0]="数据";
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

//    public void initial(){
//        imageButton=(ImageButton)view.findViewById(R.id.ssetting);
//        home_medicine=(ImageView)view.findViewById(R.id.home_medicine);
//        home_heartbeat=(ImageView)view.findViewById(R.id.home_heartbeat);
//        home_health=(ImageView)view.findViewById(R.id.home_health);
//        home_foot=(ImageView)view.findViewById(R.id.home_foot);
//        home_call=(ImageView)view.findViewById(R.id.home_call);
//
//        mainActivity = (MainActivity) getActivity();
//    }
//
//    public void setClick(){
//        home_health.setOnClickListener(this);
//        home_heartbeat.setOnClickListener(this);
//        home_medicine.setOnClickListener(this);
//        home_foot.setOnClickListener(this);
//        home_call.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v){
//        switch (v.getId()){
//            case R.id.home_medicine:
//                //mainActivity. gotoSecondFragment ();
//                break;
//            case R.id.home_heartbeat:
//                Intent intent2=new Intent(getActivity(),HeartRateActivity.class);
//                startActivity(intent2);
//                break;
//            case R.id.home_health:
//                //mainActivity.gotoFourthtFragment();
//                break;
//            case R.id.home_foot:
//                Intent intent3=new Intent(getActivity(),StepActivity.class);
//                startActivity(intent3);
//                break;
//            case R.id.home_call:
//                //mainActivity.gotoFifthFragment();
//            default:
//                break;
//        }
//    }
}
