package wfzzkj.myapplicationtestmap;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;

import cn.smssdk.SMSSDK;

public class MainActivity extends Activity implements LocationSource,
        AMapLocationListener {
    MapView mapView;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;
    private AMap aMap;
    public static final int REQUEST_PERMISSION_READ_EXTERNAL = 0x18;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        SMSSDK.initSDK(this, "1b6ff869a9c09", "fbf6bcdfe2edb9b9f404af9dd62c4e2");
        initMap();

    }





    @Override
    public void onLocationChanged(AMapLocation aLocation) {
        if (mListener != null && aLocation != null) {
            mListener.onLocationChanged(aLocation);// 显示系统小蓝点
             System.out.println("onLocationChangedAMapLocation" +aLocation.getAddress());
            // 执行一次 定位打印小蓝点


        }
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
           //获取下权限
            setUpMap();


        }

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // myLocationStyle.myLocationIcon(BitmapDescriptorFactory
        // .fromResource(R.drawable.ic_launcher));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听

        // aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        // aMap.setOnInfoWindowClickListener(this);// 设置点击marker事件监听器
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
//        mListener = listener;
//        if (mAMapLocationManager == null) {
//            mAMapLocationManager = LocationManagerProxy.getInstance(this);
//            mAMapLocationManager.setGpsEnable(false);
//			/*
//			 * mAMapLocManager.setGpsEnable(false);
//			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
//			 * API定位采用GPS和网络混合定位方式
//			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
//			 */
//
//            mAMapLocationManager.requestLocationUpdates(
//                    LocationProviderProxy.AMapNetwork, 2000, 10, this);
//        }


        mListener = listener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        // TODO Auto-generated method stub
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    protected void explainApplyPermissionReason() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("提示");
        builder.setMessage("物品需要定位权限");
        builder.setPositiveButton("I Know", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                applyPermission();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                applyPermission();
            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }
    @TargetApi(23)
    public void applyPermission() {
        System.out.println("applyPermission");
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                REQUEST_PERMISSION_READ_EXTERNAL);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void applyAccessPermission() {
        System.out.println("applyAccessPermission");
        // check if already got permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // if you think it's necessary explain something to user about
            // permission,you can override
            // shouldShowRequestPermissionRationale
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                applyPermission();
            }
        } else {
            setUpMap();
        }

    }

    @TargetApi(23)
    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        System.out.println("shouldShowRequestPermissionRationale");
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE: {
                explainApplyPermissionReason();
            }
            return true;

            default:
                break;
        }

        return super.shouldShowRequestPermissionRationale(permission);
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        System.out.println("onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_PERMISSION_READ_EXTERNAL: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //getAndShowPhoto();
                    setUpMap();
                } else {
                    Toast.makeText(MainActivity.this,
                            "权限不足",
                            Toast.LENGTH_LONG).show();
                }

            }

            break;

            default:
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
