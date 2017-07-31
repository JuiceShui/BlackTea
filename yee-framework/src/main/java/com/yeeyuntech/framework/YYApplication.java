package com.yeeyuntech.framework;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * Description:
 * Created by adu on 2017/4/21.
 */

public class YYApplication extends Application {

    private final String TAG = getClass().getSimpleName();

    /**
     * 缓存数据,跟随应用声明周期
     */
    // 用户信息
    private IUser user = null;

    // 应用前后台判断相关参数
    private int resumed, paused, started, stopped;
    // 记录最后切入后台的时间
    private long pausedTime;

    // 当前应用实例
    private static YYApplication instance = null;

    // activity栈
    private LinkedList<Activity> activities = new LinkedList<>();
    // 栈顶Activity
    private WeakReference<Activity> topActivity;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivity();
    }

    public static synchronized YYApplication getInstance() {
        return instance;
    }

    public void setYYUser(IUser u) {
        user = u;
    }

    public IUser getYYUser() {
        return user;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(user.getToken());
    }


    /**
     * 获取最近一次onPause()的时间点
     *
     * @return 13位时间戳
     */
    public long getPausedTime() {
        return pausedTime;
    }


    /**
     * 应用是否处于前台
     *
     * @return
     */
    public boolean isAppVisible() {
        return started > stopped;
    }

    public String getToken() {
        if (isLogin()) {
            return user.getToken();
        } else {
            return "";
        }
    }

    public String getUserId() {
        if (isLogin()) {
            return user.getUserId();
        } else {
            return "";
        }
    }


    /**
     * 注册监控activity的声明周期
     */
    private void registerActivity() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            // I use four separate variables here. You can, of course, just use two and
            // increment/decrement them instead of using four and incrementing them all.

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                ++resumed;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                ++paused;
                pausedTime = System.currentTimeMillis();
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                ++started;
            }

            @Override
            public void onActivityStopped(Activity activity) {
                ++stopped;
            }
        });
    }


    /**
     * 添加activity到栈中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }


    /**
     * 获得记录activity栈的List
     *
     * @return
     */
    public LinkedList<Activity> getActivities() {
        return activities;
    }


    /**
     * 从activity栈中移除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }


    /**
     * 在activity的声明周期onResume()中调用，记录栈顶activity
     *
     * @param activity
     */
    public void resumeActivity(Activity activity) {
        topActivity = new WeakReference<>(activity);
    }


    /**
     * 获取当前栈顶的activity，可能为null
     *
     * @return topActivity
     */
    public Activity getTopActivity() {
        return topActivity.get();
    }


    /**
     * 判断栈中是否存在activity的实例
     *
     * @param cls activity类
     * @return
     */
    public boolean isExist(Class<?> cls) {
        for (Activity activity : activities) {
            if (activity.getClass().getSimpleName().equals(cls.getSimpleName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 结束多个activity
     *
     * @param activities 需要结束的activity
     */
    public void finishActivity(Activity... activities) {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
    }


    /**
     * 结束栈中所有activity实例，除了except列表
     *
     * @param except 例外的activity类型
     */
    public void finishAllActivities(Class<?>... except) {
        for (Activity activity : activities) {
            if (activity != null) {
                for (Class<?> c : except) {
                    if (!activity.getClass().getSimpleName().equals(c.getSimpleName())) {
                        activity.finish();
                    }
                }
            }
        }
    }


    /**
     * 结束栈中所有activity
     */
    public void finishAllActivities() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
    }


    /**
     * 重建栈中所有activity
     */
    public void recreateActivities() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.recreate();
            }
        }
    }


    /**
     * 停止一个服务
     *
     * @param cls
     */
    public void stopService(Class<?> cls) {
        stopService(new Intent(this, cls));
    }


    /**
     * 开启一个服务
     *
     * @param cls
     */
    public void startService(Class<?> cls) {
        startService(new Intent(this, cls));
    }


    /**
     * 退出应用
     */
    public void exit() {
        finishAllActivities();
        System.exit(0);
    }
}
