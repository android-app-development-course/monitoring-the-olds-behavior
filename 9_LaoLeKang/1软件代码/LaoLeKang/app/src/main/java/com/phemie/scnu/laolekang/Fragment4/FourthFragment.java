package com.phemie.scnu.laolekang.Fragment4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phemie.scnu.laolekang.Fragment4.Health.HeartRate.HeartRateActivity;
import com.phemie.scnu.laolekang.Fragment4.Health.Map.MapActivity;
import com.phemie.scnu.laolekang.Fragment4.Health.FootStep.StepActivity;
import com.phemie.scnu.laolekang.R;

public class FourthFragment extends Fragment {

    private ImageView location;
    private ImageView step;
    private ImageView heartrate;

    public FourthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fourth, container, false);
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);

        location = (ImageView) view.findViewById(R.id.healthlocation);
        step = (ImageView) view.findViewById(R.id.healthfoot);
        heartrate = (ImageView) view.findViewById(R.id.healthheart);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.setClass(getActivity(), MapActivity.class);
                startActivity(in);
            }
        });

        step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.setClass(getActivity(), StepActivity.class);
                startActivity(in);
            }
        });

        heartrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.setClass(getActivity(), HeartRateActivity.class);
                startActivity(in);
            }
        });
        //给各个控件设置监听器
        return view;
    }

}
