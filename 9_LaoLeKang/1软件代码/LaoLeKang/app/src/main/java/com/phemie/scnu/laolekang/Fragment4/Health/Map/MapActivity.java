package com.phemie.scnu.laolekang.Fragment4.Health.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.phemie.scnu.laolekang.R;

import cc.trity.floatingactionbutton.FloatingActionButton;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    PoiSearch   search;
    // MapView控制
    public TextView locate;
    public String locationDescribe;//当前详细地址描述
    public double Latitude;//当前位置的经度
    public double Longitude;//当前位置的纬度
    ImageButton rreturn;
    LocationClientOption option;
    MyLocationListener mListener;
    MapView mapView;
    //悬浮按钮
    FloatingActionButton Map_myLocation;
    FloatingActionButton Map_myWay;
    private LocationClient mLocationClient;
    private BaiduMap mBaiduMap;

    EditText ed_city;
    EditText ed_key;
    Button btn_start;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_layout);

        search = PoiSearch.newInstance();


        initial();
        setClick();

        getMyLocation();

        //POI检索的监听对象
        OnGetPoiSearchResultListener resultListener = new OnGetPoiSearchResultListener() {
            //获得POI的检索结果，一般检索数据都是在这里获取
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //如果搜索到的结果不为空，并且没有错误
                if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {
                    MyOverLay overlay = new MyOverLay(mBaiduMap, search);//这传入search对象，因为一般搜索到后，点击时方便发出详细搜索
                    //设置数据,这里只需要一步，
                    overlay.setData(poiResult);
                    //添加到地图
                    overlay.addToMap();
                    //将显示视图拉倒正好可以看到所有POI兴趣点的缩放等级
                    overlay.zoomToSpan();//计算工具
                    //设置标记物的点击监听事件
                    mBaiduMap.setOnMarkerClickListener(overlay);
                } else {
                    Toast.makeText(getApplication(), "搜索不到你需要的信息！", Toast.LENGTH_SHORT).show();
                }
            }
            //获得POI的详细检索结果，如果发起的是详细检索，这个方法会得到回调(需要uid)
            //详细检索一般用于单个地点的搜索，比如搜索一大堆信息后，选择其中一个地点再使用详细检索
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getApplication(), "抱歉，未找到结果",
                            Toast.LENGTH_SHORT).show();
                } else {// 正常返回结果的时候，此处可以获得很多相关信息
                    Toast.makeText(getApplication(), poiDetailResult.getName() + ": "
                                    + poiDetailResult.getAddress(),
                            Toast.LENGTH_LONG).show();
                }
            }
            //获得POI室内检索结果
            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        };

        //设置监听
        search.setOnGetPoiSearchResultListener(resultListener);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city =ed_city.getText().toString();
                String key =ed_key.getText().toString();
                //发起检索
                PoiCitySearchOption poiCity = new PoiCitySearchOption();
                poiCity.keyword(key).city(city);//这里还能设置显示的个数，默认显示10个
                search.searchInCity(poiCity);//执行搜索，搜索结束后，在搜索监听对象里面的方法会被回调
            }
        });


        /*
        //POI搜索，周边检索,

        //定义Maker坐标点,深圳大学经度和纬度113.943062,22.54069
        //设置的时候经纬度是反的 纬度在前，经度在后
        LatLng point = new LatLng(22.54069, 113.943062);
        //获得Key
        String key1 ="肯德基";//et_point.getText().toString();
        //周边检索
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
        nearbySearchOption.location(point);
        nearbySearchOption.keyword(key1);
        nearbySearchOption.radius(100);// 检索半径，单位是米
        nearbySearchOption.pageNum(1);//搜索一页
        search.searchNearby(nearbySearchOption);// 发起附近检索请求
*/




    }

    public void initial() {
        locate = (TextView) findViewById(R.id.locate);
        rreturn = (ImageButton) findViewById(R.id.rreturn);

        //悬浮按钮
        Map_myLocation = (FloatingActionButton) findViewById(R.id.Map_myLocation);
        Map_myWay = (FloatingActionButton) findViewById(R.id.Map_myWay);

        // 获取BaiduMap
        mapView = (MapView) findViewById(R.id.mapView);

        ed_city=(EditText)findViewById(R.id.ed_city);
        ed_key=(EditText)findViewById(R.id.ed_key);
        btn_start=(Button)findViewById(R.id.btn_start);

    }

    public void setClick() {
        Map_myLocation.setOnClickListener(this);
        Map_myWay.setOnClickListener(this);
        rreturn.setOnClickListener(this);

    }

    public void getMyLocation() {
        // 获取LocationClient
        mLocationClient = new LocationClient(this);

        option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);//使用location.getAddrStr();获取详细地址信息要用到
        mLocationClient.setLocOption(option);

        mBaiduMap = mapView.getMap();

        // 显示出当前位置的小图标
        mBaiduMap.setMyLocationEnabled(true);
        mListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mListener);
        mLocationClient.start();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rreturn:
                finish();
                break;
            case R.id.Map_myLocation:
                LatLng ll = new LatLng(Latitude, Longitude);
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16.0f);
                mBaiduMap.animateMapStatus(u);
                break;
            case R.id.Map_myWay:
                startActivity(new Intent(MapActivity.this, MapMyWayActivity.class));
                break;
        }
    }


    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        super.onDestroy();
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // 只是完成了定位
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            Latitude = location.getLatitude();
            Longitude = location.getLongitude();


            //设置图标在地图上的位置
            mBaiduMap.setMyLocationData(locData);

            locationDescribe = location.getAddrStr();
            locate.setText(locationDescribe);//在TextView中设置位置信息


            CircleOptions circle = new CircleOptions().center(new LatLng(Latitude,Longitude)).fillColor(0x80ff0000).radius(100);
            mBaiduMap.addOverlay(circle);

            // 开始移动百度地图的定位地点到中心位置
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16.0f);
            mBaiduMap.animateMapStatus(u);
        }

    }

}


