package com.lee.fiftytone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * 用于处理退出程序时可以退出所有的activity，而编写的通用类。 把一些全局变量放在里面。
 *
 * @author darren
 */
public class MyApplication extends Application {
    /**
     * 全局的context
     */
    private static Context mContext;
    /**
     * 保存所有的活动
     */
    public List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        registerActivity();
    }

    /**
     * 为解决Android5.0报错“NoClassDefFoundErorr”
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @SuppressLint("NewApi")
    private void registerActivity() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.e("activitylife", "onActivityCreated:   " + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.e("activitylife", "onActivityStarted:   " + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.e("activitylife", "onActivityResumed:   " + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.e("activitylife", "onActivityPaused:   " + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity,
                                                    Bundle outState) {
                Log.e("activitylife", "onActivitySaveInstanceState:   " + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.e("activitylife", "onActivityStopped:   " + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.e("activitylife", "onActivityDestroyed:   " + activity.getClass().getSimpleName());
            }

        });
    }

    /**
     * 单例模式中获取唯一的MyApplication实例
     */
    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    /**
     * 添加Activity到容器中
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 遍历所有Activity并finish
     */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    /**
     * 在应用完全退出时，释放连接
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
