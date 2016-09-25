package com.example.spj.p2p.common;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by spj on 2016/9/20.
 * 处理程序中出现的未被捕获的异常（单例：懒汉式）
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    //懒汉式单例
    private CrashHandler() {

    }

    private static CrashHandler crashHandler = null;

    public static CrashHandler getInstance() {
        return crashHandler == null ? new CrashHandler() : crashHandler;
    }

    private Context mContext;

    public void init(Context context){
        this.mContext = context;
        //获取系统默认的捕获未被处理的异常的处理器
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前类设置为捕获未被处理的异常的处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    //如果出现了未被捕获的异常，则自动调用如下的回调方法
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(ex == null) {
            defaultUncaughtExceptionHandler.uncaughtException(thread,ex);
        }else {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    //只有在主线程中才可以调用如下的looper的方法，将要在主线程中执行的代码放在两个代码之间
                    Looper.prepare();
                    Toast.makeText(mContext, "出现网络连接的异常了", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }.start();

            //收集出现的异常的信息
            collectionException(ex);

            //接下来，2s内退出当前的应用
            try {
                Thread.sleep(2000);
                AppManager.getInstance().removeAll();//移除栈中所有的activity
                android.os.Process.killProcess(android.os.Process.myPid());//杀死当前的进程
                System.exit(0);//关闭当前的虚拟机
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    //收集异常信息的方法，并发送给服务器
    private void collectionException(final Throwable ex) {
        final  String exMessage = ex.getMessage();//异常信息
        final  String message =Build.PRODUCT + "--" + Build.DEVICE + "--" + Build.MODEL + "--" + Build.VERSION.SDK_INT;

        //模拟将获取的异常相关的数据发送给后台
        new Thread(){
            @Override
            public void run() {
                super.run();
                Log.e("TAG", ex.getMessage());
                Log.e("TAG", message);
            }
        }.start();
    }
}
