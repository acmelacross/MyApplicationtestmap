package wfzzkj.myapplicationtestmap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class Main2Activity extends AppCompatActivity {
private int REQUEST_CODE = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);




//        Intent intent = new Intent(Main2Activity.this, CaptureActivity.class);
//        startActivityForResult(intent, REQUEST_CODE);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_CODE) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(Main2Activity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }
}
