package com.alex.code.foundation.utils.um;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

/**
 * Created by bshao on 12/9/16.
 */

public abstract class Tracker {

    private static volatile Tracker sInstance;

    public static Tracker getDefaultTracker() {
        if (sInstance == null) {
            synchronized (Tracker.class) {
                if (sInstance == null) {
                    sInstance = new UMengDataTracker();
                }
            }
        }
        return sInstance;
    }

    public abstract void init(Context appContext, String appKey, String channel, boolean debug);

    public abstract void onResume(Activity activity);

    public abstract void onPause(Activity activity);

    public abstract void onPageStart(String name);

    public abstract void onPageEnd(String name);

    public abstract void trackEvent(String eventId, String label, Map<String, Object> values);

    public abstract void trackEvent(String eventId, String label);

    public abstract void trackEvent(String eventId);
}
