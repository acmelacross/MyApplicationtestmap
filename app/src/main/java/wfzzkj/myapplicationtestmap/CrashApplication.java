package wfzzkj.myapplicationtestmap;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class CrashApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		initBmob();
	}

	private  void initBmob(){
		//第一：默认初始化
		Bmob.initialize(this, "0230f4953d4ec60eb7f701dda12e0359");

		//第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
		//BmobConfig config =new BmobConfig.Builder(this)
		////设置appkey
		//.setApplicationId("Your Application ID")
		////请求超时时间（单位为秒）：默认15s
		//.setConnectTimeout(30)
		////文件分片上传时每片的大小（单位字节），默认512*1024
		//.setUploadBlockSize(1024*1024)
		////文件的过期时间(单位为秒)：默认1800s
		//.setFileExpiration(2500)
		//.build();
		//Bmob.initialize(config);



//        Person p2 = new Person();
//        p2.setName("lucky");
//        p2.setAddress("北京海淀");
//        p2.save(new SaveListener<String>() {
//            @Override
//            public void done(String objectId,BmobException e) {
//                if(e==null){
//                    System.out.print("添加数据成功，返回objectId为："+objectId);
//                   // toast("添加数据成功，返回objectId为："+objectId);
//                }else{
//                    System.out.print("创建数据失败：" + e.getMessage());
//                  //  toast("创建数据失败：" + e.getMessage());
//                }
//            }
//        });
		System.out.println("initBMOB");
	}
}
