package com.phemie.scnu.laolekang.Fragment4.Health.Map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.phemie.scnu.laolekang.R;

public class MapMyWayActivity extends AppCompatActivity {

    ImageView lv_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_my_way);


        lv_left=(ImageView)findViewById(R.id.lv_left);


        lv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
