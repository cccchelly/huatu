package com.alex.code.foundation;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.alex.code.foundation.bean.UMsg;
import com.alex.code.foundation.di.AppInjector;
import com.alex.code.foundation.utils.ACache;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.utils.um.Tracker;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.singhajit.sherlock.core.Sherlock;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends MultiDexApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mActivityDispatchingAndroidInjector;

    private static Context mAppContext;
    public static final String UM_MSG = "um_msg";

    //各平台友盟分享配置
    {
//        Config.DEBUG = BuildConfig.DEBUG;
        PlatformConfig.setWeixin("wxfe9ec39bdab4bc87","b53d9ffe1375e1f1d4b133e5c31298f4");
        PlatformConfig.setQQZone("1106568574","9rBrAx8tF0LDcU02");
        PlatformConfig.setSinaWeibo("380660964","48b22c2cacd7030b7c51f538acfe552f","xlwbUrl");// TODO: 2017/11/23  等待替换
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        initLogger();
        initDi();
        initSherlock();
        initARouter();
        initFresco();
        initUMeng();
    }

    private void initUMeng() {

        //UM分享初始化
        UMShareAPI.get(this);

        //UM统计初始化
        Tracker.getDefaultTracker().init(this,
                BuildConfig.UMENG_APPKEY,
                AnalyticsConfig.getChannel(this),
                BuildConfig.DEBUG);

        //UM推送初始化
        PushAgent mPushAgent = PushAgent.getInstance(this);

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                VLog.d("UMessage: -----" +msg.custom);
            }

            @Override
            public Notification getNotification(Context context, UMessage uMessage) {
                VLog.d("getNotification: "+uMessage.text);

                ACache aCache = ACache.get(mAppContext);

                Map<String, String> extra = uMessage.extra;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = simpleDateFormat.format(new Date());
                VLog.d("date: "+date);

                String appUrl = "";
                if (extra != null) {
                    for (Map.Entry<String, String> stringStringEntry : extra.entrySet()) {
                        String key = stringStringEntry.getKey();
                        VLog.d("key: "+key);
                        if (TextUtils.equals(key, "AppUrl")) {
                            appUrl = stringStringEntry.getValue();
                        }
                    }
                }
                UMsg uMsg = new UMsg();
                uMsg.setTitle(uMessage.title);
                uMsg.setContent(uMessage.text);
                uMsg.setDate(date);
                uMsg.setAppUrl(appUrl);

                ArrayList<UMsg> uMsgs = (ArrayList<UMsg>) aCache.getAsObject(UM_MSG);
                if (uMsgs == null) {
                    uMsgs = new ArrayList<>();
                }

                uMsgs.add(0,uMsg);
                aCache.put(UM_MSG,uMsgs);
                return super.getNotification(context, uMessage);
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
//                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                VLog.d("dealWithCustomAction: "+msg.custom);
                //需要约定推送格式，um推送自定义消息，添加自定义参数，key为AppUrl，value为 app://host/foundation/goodsDetail?goodsId=16 格式
                //AppUrl=app://host/foundation/goodsDetail?goodsId=16
                String appUrl = "";
                Map<String, String> extra = msg.extra;
                VLog.d("extra: "+extra);

                if (extra != null) {
                    for (Map.Entry<String, String> stringStringEntry : extra.entrySet()) {
                        String key = stringStringEntry.getKey();
                        if (TextUtils.equals(key, "AppUrl")) {
                            appUrl = stringStringEntry.getValue();
                        }
                    }
                }
                CommonUtils.startNativeActivity(appUrl);
            }

            @Override
            public void launchApp(Context context, UMessage uMessage) {
                super.launchApp(context, uMessage);
                VLog.d("launchApp: ");
            }

            @Override
            public void openUrl(Context context, UMessage uMessage) {
                super.openUrl(context, uMessage);
                VLog.d("openUrl: ");
            }

            @Override
            public void openActivity(Context context, UMessage uMessage) {
                super.openActivity(context, uMessage);
                VLog.d("openActivity: ");
            }
        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                VLog.d("UMengPush deviceToken: "+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

                VLog.d("UMengPush:   onFailure");
            }
        });
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy
                .newBuilder()
                .tag(AppConstants.APP_TAG)
                .build()
        ) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private void initDi() {
        AppInjector.init(this);
    }

    /**
     * https://github.com/ajitsing/Sherlock
     * 通过通知栏显示崩溃信息，帮助分析崩溃
     */
    private void initSherlock() {
        Sherlock.init(this);
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }

        ARouter.init(this);
    }

    private void initFresco() {
        Fresco.initialize(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityDispatchingAndroidInjector;
    }
}
