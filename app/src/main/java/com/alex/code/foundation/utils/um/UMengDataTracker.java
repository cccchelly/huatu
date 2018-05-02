package com.alex.code.foundation.utils.um;

import android.app.Activity;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @date 2017-04-18
 * @Description
 */

public class UMengDataTracker extends Tracker {

    private Context mAppContext;

    @Override
    public void init(Context appContext, String appKey, String channel, boolean debug) {
        mAppContext = appContext;
        MobclickAgent.UMAnalyticsConfig config
                = new MobclickAgent.UMAnalyticsConfig(mAppContext, appKey, channel);
        MobclickAgent.startWithConfigure(config);
        MobclickAgent.setDebugMode(debug);
    }

    @Override
    public void onResume(Activity activity) {
        MobclickAgent.onResume(activity);
    }

    @Override
    public void onPause(Activity activity) {
        MobclickAgent.onPause(activity);
    }

    @Override
    public void onPageStart(String name) {
        MobclickAgent.onPageStart(name);
    }

    @Override
    public void onPageEnd(String name) {
        MobclickAgent.onPageEnd(name);
    }

    @Override
    public void trackEvent(String eventId, String label, Map<String, Object> values) {

    }

    @Override
    public void trackEvent(String eventId, String label) {
        MobclickAgent.onEvent(mAppContext, eventId, label);
    }

    @Override
    public void trackEvent(String eventId) {
        MobclickAgent.onEvent(mAppContext, eventId);
    }
}
