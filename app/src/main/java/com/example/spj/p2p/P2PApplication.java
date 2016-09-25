package com.example.spj.p2p;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by spj on 2016/9/19.
 * 提供在整个应用程序调用过程中，可能使用到的一些变量
 */
public class P2PApplication extends Application{
    //提供4个需要用到的属性
    public static Context mContext;//在整个应用中用到context的位置，都使用此变量
    public static Handler mHandler;//在整个应用中发送消息
    public static Thread currentThread;//获取当前应用的线程，主线程
    public static int currentThreadId;//获取当前线程的id

    @Override
    public void onCreate() {
        super.onCreate();
        //属性的初始化
        mContext = this.getApplicationContext();
        mHandler = new Handler();
        currentThread = Thread.currentThread();
        currentThreadId = android.os.Process.myTid();

        //初始化当前的异常处理器
//        CrashHandler.getInstance().init(this);
    }
}
