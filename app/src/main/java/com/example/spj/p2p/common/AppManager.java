package com.example.spj.p2p.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by spj on 2016/9/19.
 * 统一应用程序中所有的Activity的栈管理，
 * 涉及到Activity的添加，删除指定，删除当前，删除所有，返回栈大小的方法
 */
public class AppManager {
    private Stack<Activity> activityStack = new Stack<>();

    //设置当前类是单例的
    private static AppManager appManager = new AppManager();

    private AppManager() {

    }

    public static AppManager getInstance() {
        return appManager;
    }

    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            if (activityStack.get(i).getClass().equals(activity.getClass())) {
                activity.finish();
                activityStack.remove(i);
                break;
            }

        }
    }

    public void removeCurrent() {
        Activity activity = activityStack.lastElement();
        activity.finish();
        activityStack.remove(activity);
    }

    public void removeAll() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            activityStack.get(i).finish();
            activityStack.remove(i);
        }
    }

    public int size() {
        return activityStack == null ? 0 : activityStack.size();
    }
}
