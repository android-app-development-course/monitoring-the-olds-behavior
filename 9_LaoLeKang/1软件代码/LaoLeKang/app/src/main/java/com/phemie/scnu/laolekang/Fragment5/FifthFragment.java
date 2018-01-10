package com.phemie.scnu.laolekang.Fragment5;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phemie.scnu.laolekang.InitialActivity;
import com.phemie.scnu.laolekang.InitialHelper;
import com.phemie.scnu.laolekang.MainActivity;
import com.phemie.scnu.laolekang.R;


/*
* 2017.12.24 调用系统拨号功能时没有“运行时权限” TODO:已经解决
* 没有实现“联系人列表”的长按编辑、删除功能
*/

public class FifthFragment extends Fragment {

    // fragment_fifth.xml布局文件的组件
    ListView lvContact;
    ImageView ivSOS;

    // 传递给FirstFragment
    public static FifthFragment fifth_frafment;

    public FifthFragment() {
        // Required empty public constructor
        fifth_frafment = this;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 组件初始化
        View v = inflater.inflate(R.layout.fragment_fifth, container, false);

        lvContact = (ListView)v.findViewById(R.id.urgency_lv_contact);
        ivSOS = (ImageView)v.findViewById(R.id.urgency_iv_sos);
        ivSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "正在调用系统拨号功能", Toast.LENGTH_SHORT).show();
                // 调用系统拨号功能
                {
                    View v = View.inflate(getContext(), R.layout.fragment_fifth_list_item, null);
                    int counts = lvContact.getChildCount();  // 获取有多少个联系人，依次拨打电话

                    // TODO：待修改：需要从拨号界面中返回到这里并继续执行下面的循环
                    for (int i = 0; i < 1; i++) {
                        View item = lvContact.getChildAt(i);

                        /*下次需要写ListView时参考这个部分*/
                        try {
                            TextView tvName = (TextView) item.findViewById(R.id.urgency_listview_tv_name);
                            String name = tvName.getText().toString();
                            Toast.makeText(getActivity(), "正在拨打联系人" + name, Toast.LENGTH_SHORT).show();

                            TextView tvNumber = (TextView) item.findViewById(R.id.urgency_listview_tv_number);
                            String number = tvNumber.getText().toString();
                            call(number);
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "调用拨号功能时发生错误", Toast.LENGTH_SHORT).show();
                        }

                        // 设置等待时间并返回该界面中
                    }
                }
            }
        });

        // 对ListView设置Adapter
        lvContact.setAdapter(new SOSBaseAdapter(getActivity()));
        lvContact.setOnItemClickListener(new fifthFragmentListener(this));


        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onResume() {
        lvContact.setAdapter(new SOSBaseAdapter(getActivity()));
        super.onResume();
    }

    // 拨号功能
    void call(String number){
        Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    // 数据适配器
    private class SOSBaseAdapter extends BaseAdapter{
        private String[] titles;  private String[] contents = new String[0];
        private Context context;

        // 构造函数
        public SOSBaseAdapter(Context c){
            context = c;

            // 打开文件读取联系人数据
            //readFile();

            // 从数据库中读取信息
            readMySettingsDatabase();
        }

        @Override
        // 获取联系人的个数
        public int getCount() {
            return contents.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = View.inflate(context, R.layout.fragment_fifth_list_item, null);
            // 引用ListView中的组件
            TextView tvName = (TextView)v.findViewById(R.id.urgency_listview_tv_name);
            TextView tvNumber = (TextView)v.findViewById(R.id.urgency_listview_tv_number);

            // 重置信息
            String s1 = titles[position];
            tvName.setText(s1); tvNumber.setText(contents[position]);

            return v;
        }

        // 测试：自己创建一个文件，并写入数据
    /*private void readFile(){
        File file = new File(context.getFilesDir(), InitialActivity.sContact);
        // 如果文件存在的话，直接打开文件读取数据
        if(file.exists())
        {
            try {
                // 创建文件读取流，并读取文件到内存中
                FileInputStream f = context.openFileInput(InitialActivity.sContact);
                byte[] b = new byte[f.available()];
                f.read(b);
                String s = new String(b);

                // 初始化 names 和 numbers 数组
                String[] multi = s.split("\n");
                names = new String[multi.length];   numbers = new String[multi.length];
                for(int i=0; i<multi.length; i++)
                {
                    String[] s0 = multi[i].split(":");
                    names[i] = s0[0];   numbers[i] = s0[1];
                }

                // 释放资源
                f.close();
            }
            catch(IOException e)
            {
                Toast.makeText(context, "读取联系人文件出错", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        // 不存在该文件时，创建一个文件，并给出提示信息
        else
        {
            try {
                file.createNewFile();

                // 跳转到输入界面 TODO：这个需要在“个人数据”中实现


                // 测试
                FileOutputStream f = context.openFileOutput(InitialActivity.sContact, MODE_PRIVATE);
                f.write("son1:123456\n".getBytes());
                f.write("daughter1:111111\n".getBytes());
                f.write("son2:654321\n".getBytes());

                names = new String[3];  numbers = new String[3];
                names[0] = "son1";  names[1] = "daughter1"; names[2] = "son2";
                numbers[0] = "123456";  numbers[1] = "111111"; numbers[2] = "654321";


                f.close();
                Toast.makeText(context, "不存在联系人文件，已成功创建文件", Toast.LENGTH_SHORT).show();
            }
            catch(IOException e)
            {
                Toast.makeText(context, "创建文件时出错", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }*/

        // 从数据库中读取“标题”和“内容”信息
        private void readMySettingsDatabase(){
            InitialActivity.helper = new InitialHelper(getActivity());
            SQLiteDatabase db = InitialActivity.helper.getWritableDatabase();
            // 执行查询语句
            String username = MainActivity.username;

            // 查询当前用户的的“标题信息”，并设置“标题”编辑框中的文本
            Cursor cursor = db.rawQuery("select * from mySettings where username=?", new String[] {username});
            titles = new String[cursor.getCount()]; contents = new String[cursor.getCount()];
            if(cursor.getCount() == 0)
            {
                Toast.makeText(getActivity(), "数据库没有任何信息", Toast.LENGTH_SHORT).show();
                return;
            }

            cursor.moveToFirst();
            for(int i=0; cursor.isAfterLast() == false; cursor.moveToNext())
            {
                int columnTitle = cursor.getColumnIndex("title");   int columnContent = cursor.getColumnIndex("content");
                // 如果字符串中包含“联系人”的话，就把这些信息保存起来
                String s = new String(cursor.getString(columnTitle));

                /*if(s.indexOf("联系人") != -1) {
                    titles[i] = new String(cursor.getString(columnTitle));
                    contents[i] = new String(cursor.getString(columnContent));
                }   // TODO: 只有包含联系人才能加进去*/

                titles[i] = new String(cursor.getString(columnTitle));
                contents[i] = new String(cursor.getString(columnContent));

                // 输出信息
                Log.i("FirstFragment1", titles[i] + ":" + contents[i]);

                i++;
            }



            // 关闭相关资源
            cursor.close(); db.close();
            InitialActivity.helper = null;
        }
    }


    // 事件监听器
    class fifthFragmentListener implements AdapterView.OnItemClickListener
    {
        FifthFragment fragment;
        public fifthFragmentListener(Fragment f){
            fragment = (FifthFragment)f;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView tvName = (TextView) view.findViewById(R.id.urgency_listview_tv_name);
            TextView tvNumber = (TextView)view.findViewById(R.id.urgency_listview_tv_number);
            String name = tvName.getText().toString();
            String number = tvNumber.getText().toString();

            try {
                // 调用系统拨号功能
                Toast.makeText(fragment.getActivity(), "正在拨打联系人" + name, Toast.LENGTH_SHORT).show();
                fragment.call(number);
            }
            catch(Exception e)
            {
                Toast.makeText(fragment.getActivity(), "调用系统拨号功能时发生错误", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }


}

