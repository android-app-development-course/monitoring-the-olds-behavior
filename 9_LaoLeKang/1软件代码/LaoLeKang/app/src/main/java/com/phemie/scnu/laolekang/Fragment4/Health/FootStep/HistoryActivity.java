package com.phemie.scnu.laolekang.Fragment4.Health.FootStep;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.phemie.scnu.laolekang.R;
import com.phemie.scnu.laolekang.Fragment4.adapter.CommonAdapter;
import com.phemie.scnu.laolekang.Fragment4.adapter.CommonViewHolder;
import com.phemie.scnu.laolekang.Fragment4.step.bean.StepData;
import com.phemie.scnu.laolekang.Fragment4.step.utils.DbUtils;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private LinearLayout layout_titlebar;
    private ImageView iv_left;
    private Button StepChart;
    private ListView lv;

    private void assignViews() {
        layout_titlebar = (LinearLayout) findViewById(R.id.layout_titlebar);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        StepChart = (Button) findViewById(R.id.btn_chart);
        lv = (ListView) findViewById(R.id.lv);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        assignViews();
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StepChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this,StepChartActivity.class));
            }
        });
        initData();
    }

    private void initData() {
        setEmptyView(lv);
        if (DbUtils.getLiteOrm() == null) {
            DbUtils.createDb(this, "jingzhi");
        }
        List<StepData> stepDatas = DbUtils.getQueryAll(StepData.class);
        Logger.d("stepDatas=" + stepDatas);
        lv.setAdapter(new CommonAdapter<StepData>(this, stepDatas, R.layout.step_item) {
            @Override
            protected void convertView(View item, StepData stepData) {
                TextView tv_date = CommonViewHolder.get(item, R.id.tv_date);
                TextView tv_step = CommonViewHolder.get(item, R.id.tv_step);
                tv_date.setText(stepData.getToday());
                tv_step.setText(stepData.getStep() + "步");
            }
        });
    }

    protected <T extends View> T setEmptyView(ListView listView) {
        TextView emptyView = new TextView(this);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("暂无数据！");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);
        return (T) emptyView;
    }
}
