package com.phemie.scnu.laolekang.Fragment3;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.phemie.scnu.laolekang.R;

/**
 * Created by duhuicheng on 2017/12/20.
 */

public class Setting_Main extends Activity {
    ListView mListview;//账号的LV
    ListView mListview1;//中间行的LV
    ListView mListview2;//关于的LV
    ImageButton rreturn;//返回
    private String[] setting1 = {"账号与安全"};
    private String[] setting2 = {"换肤", "音效设置", "语言"};
    private String[] setting3 = {"关于"};
    private int[] icons1={R.drawable.six_lock};
    private int[] icons2={R.drawable.six_skin,R.drawable.six_sound,R.drawable.six_language};
    private int[] icons3={R.drawable.six_about};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止输入框覆盖
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.setting_main);
        rreturn = (ImageButton) findViewById(R.id.settingreturn);
        rreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//返回主界面
        mListview=(ListView)findViewById(R.id.setting_lv1);//第一块
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.setClass(Setting_Main.this, Setting_Password.class);
                startActivity(intent);
            }
        });
        //
        //listview点击事件
        mListview1=(ListView)findViewById(R.id.setting_lv2);//第二块
        mListview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                if(setting2[arg2]=="换肤"){
                    intent.setClass(Setting_Main.this, Setting_Skin.class);
                }else if(setting2[arg2]=="音效设置"){
                    intent.setClass(Setting_Main.this, Setting_Sound.class);
                }else if(setting2[arg2]=="语言"){
                    intent.setClass(Setting_Main.this, Setting_Language.class);
                }
                startActivity(intent);
            }
        });
        //
        //
        mListview2=(ListView)findViewById(R.id.setting_lv3);//第三块
        mListview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.setClass(Setting_Main.this, Setting_About.class);
                startActivity(intent);
            }
        });

        MyBaseAdapter1 mAdapter1=new MyBaseAdapter1();
        MyBaseAdapter2 mAdapter2=new MyBaseAdapter2();
        MyBaseAdapter3 mAdapter3=new MyBaseAdapter3();
        mListview.setAdapter(mAdapter1);//第一块
        mListview1.setAdapter(mAdapter2);//第二块
        mListview2.setAdapter(mAdapter3);//第三块
        // Inflate the layout for this fragment

    }

    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
    //
    //
    //重构BaseAdapter方法实现第一个LV
    class MyBaseAdapter1 extends BaseAdapter{

        @Override
        public int getCount() {
            //返回ListView的item的条目
            return setting1.length;
        }

        @Override
        //获得item代表的对象
        public Object getItem(int position) {
            return setting1[position];
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
            if(convertView==null) {
                convertView = LayoutInflater.from(
                        getApplicationContext()).inflate(R.layout.setting_item, parent, false);
                holder=new ViewHolder();
                holder.mTextView=(TextView)convertView.findViewById(R.id.setting_tv);
                holder.imageView=(ImageView)convertView.findViewById(R.id.setting_im);
            }else{
                holder=(ViewHolder)convertView.getTag();
            }
            holder.mTextView.setText(setting1[position]);
            holder.imageView.setBackgroundResource(icons1[position]);
            return convertView;
        }
        class ViewHolder{
            TextView mTextView;
            ImageView imageView;
        }
    }
    //
    //
    //重构BaseAdapter方法实现第二个LV
    class MyBaseAdapter2 extends BaseAdapter{

        @Override
        public int getCount() {
            //返回ListView的item的条目
            return setting2.length;
        }

        @Override
        //获得item代表的对象
        public Object getItem(int position) {
            return setting2[position];
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
            if(convertView==null) {
                convertView = LayoutInflater.from(
                        getApplicationContext()).inflate(R.layout.setting_item, parent, false);
                holder=new ViewHolder();
                holder.mTextView=(TextView)convertView.findViewById(R.id.setting_tv);
                holder.imageView=(ImageView)convertView.findViewById(R.id.setting_im);
            }else{
                holder=(ViewHolder)convertView.getTag();
            }
            holder.mTextView.setText(setting2[position]);
            holder.imageView.setBackgroundResource(icons2[position]);
            return convertView;
        }
        class ViewHolder{
            TextView mTextView;
            ImageView imageView;
        }
    }
    //
    //
    //重构BaseAdapter方法实现第一个LV
    class MyBaseAdapter3 extends BaseAdapter{

        @Override
        public int getCount() {
            //返回ListView的item的条目
            return setting3.length;
        }

        @Override
        //获得item代表的对象
        public Object getItem(int position) {
            return setting3[position];
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
            if(convertView==null) {
                convertView = LayoutInflater.from(
                        getApplicationContext()).inflate(R.layout.setting_item, parent, false);
                holder=new ViewHolder();
                holder.mTextView=(TextView)convertView.findViewById(R.id.setting_tv);
                holder.imageView=(ImageView)convertView.findViewById(R.id.setting_im);
            }else{
                holder=(ViewHolder)convertView.getTag();
                }
            holder.mTextView.setText(setting3[position]);
            holder.imageView.setBackgroundResource(icons3[position]);
            return convertView;
        }
        class ViewHolder{
            TextView mTextView;
            ImageView imageView;
        }
    }
}
