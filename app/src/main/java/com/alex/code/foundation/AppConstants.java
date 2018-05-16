package com.alex.code.foundation;

public interface AppConstants {

    String APP_TAG = "Foundation";

    String PREF_NAME = "Foundation_Pre";

    // OK HTTP
    int CONNECT_TIME_OUT = 15;
    int WRITE_TIME_OUT = 15;
    int READ_TIME_OUT = 15;

    // Api
//    String API_BASE_URL = "http://ht.meishifulu.cn/";
//    String API_BASE_URL = "http://llht.meishifulu.cn/";//测试
    String API_BASE_URL = "http://www.yinchuanhitech.com/";//测试
    // String PIC_BASE_URL = "http://static.meishifulu.cn/";//测试
    String PIC_BASE_URL = "http://static.yinchuanhitech.com/";//测试
    String REGISTER_AND_LOGIN_URL = "http://open.yinchuanhitech.com/";   //（登录、注册、三方登录）特殊调用跟地址，已在Iapi直接申明完整地址覆盖baseuri

    String WX_APPID = "wxfe9ec39bdab4bc87";
}
