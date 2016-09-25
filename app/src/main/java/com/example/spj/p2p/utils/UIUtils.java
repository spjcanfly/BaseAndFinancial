package com.example.spj.p2p.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.example.spj.p2p.P2PApplication;

/**
 * Created by spj on 2016/9/19.
 * 专门提供为处理一些UI相关的问题而创建的工具类
 * 提供资源获取的通用方法，避免每次都写重复的代码获取结果
 */
public class UIUtils {

    /**
     * 返回当前应用的context的实例（返回的就是myApplication实例）
     */
    public static Context getContext() {
        return P2PApplication.mContext;
    }


    /**
     * 返回一个可以发送消息的handler的实例
     */
    public static Handler getHandler() {
        return P2PApplication.mHandler;
    }

    /**
     * 返回资源文件中指定colorId对应的颜色值
     */
    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId, null);
    }


    /**
     * 加载指定的layoutId的布局，并且返回
     */
    public static View getXmlView(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }


    /**
     * 返回对应的stirngs.xml中<string-array>对应的stirng数组资源
     */
    public static String[] getStringArray(int stringArrayId) {
        String[] stringArray = getContext().getResources().getStringArray(stringArrayId);
        return stringArray;
    }


    /**
     * 将dp转换为px
     * // px = dp * density  (160dp手机看成density = 1)
     */
    public static int dp2px(int dp) {
        //获取当前的手机密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);//通过四舍五入，获取最接近的整数
    }

    /**
     * 将px转换为dp
     */
    public static int px2dp(int px) {
        //获取当前的手机密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    //保证runnable中run（）中的操作是在主线程执行的
    public static void runOnUiThread(Runnable runnable) {
        //判断是否在主线程中
        if(isMainThread()) {
            runnable.run();
        }else {
            //发送消息到主线程中执行
            getHandler().post(runnable);
        }
    }

    //判断当前线程是否是主线程中
    private static boolean isMainThread() {
        //获取当前线程的id
        int currentThreadId = android.os.Process.myTid();
        return currentThreadId == P2PApplication.currentThreadId;
    }
}
