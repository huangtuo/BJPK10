package com.rx.mvp.zyzj.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.io.File;
import java.util.List;

public class WalleteViewUtil {
    /**
     * 设置背景（解决废弃API问题）
     *
     * @param view
     * @param drawable
     */
    @SuppressLint("NewApi")
    public static void setBackground(View view, Drawable drawable) {
        if (isOverSDK_INT(16)) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * ViewPager页面监听
     */
    public static void setOnPageChangeListener(ViewPager v, ViewPager.OnPageChangeListener p) {
        if (isOverSDK_INT(23)) {
            v.addOnPageChangeListener(p);
        } else
            v.setOnPageChangeListener(p);
    }

    /**
     * 编译版本大于等于version的版本
     */
    public static boolean isOverSDK_INT(int version) {
        if (Build.VERSION.SDK_INT >= version) {
            return true;
        } else
            return false;
    }

    /**
     * 获取颜色
     */
    public static int getColor(Context c, int id) {
        if (isOverSDK_INT(23)) {
            return c.getColor(id);
        } else
            return c.getResources().getColor(id);
    }

    /**
     * 清空背景(解决废弃的API问题)
     *
     * @param view
     */
    @SuppressLint("NewApi")
    public static void clearBackground(View... view) {
        if (view != null) {
            for (View view2 : view) {
                if (view2 != null) {
                    Drawable drawable = view2.getBackground();
                    if (drawable != null) {
                        if (isOverSDK_INT(16)) {
                            view2.setBackground(null);
                        } else {
                            view2.setBackgroundDrawable(null);
                        }
                        drawable.setCallback(null);
                    }
                }
            }
        }

    }


    /**
     * app是否在最顶端
     */
    public static boolean isTopActivity(Context activity) {
        ActivityManager activityManager = (ActivityManager) activity
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = activity.getPackageName();
        if (isOverSDK_INT(21)) {
            final List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
                if (processInfo.processName.equals(packageName))
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true;
                    }
            }
        } else {
            List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo.size() > 0) {
                if (packageName.equals(tasksInfo.get(0).topActivity
                        .getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static long event_time = 0;
    private static final long TIME_LIMIT = 500;

    /**
     * 判断是否双击<br/>
     * 放在CommHttp里面判断：当500毫秒内code相同才认为是双击
     */
    public static boolean isDoubleClick() {
        if (event_time > System.currentTimeMillis()) {
            event_time = System.currentTimeMillis() + TIME_LIMIT;
            return true;
        }
        event_time = System.currentTimeMillis() + TIME_LIMIT;
        return false;
    }

    /**
     * 获取资源图片
     */
    @SuppressLint("NewApi")
    public static Drawable getDrawable(Context c, int id) {
        if (isOverSDK_INT(21)) {
            return c.getDrawable(id);
        } else
            return c.getResources().getDrawable(id);
    }

    /**
     * 剩余空间是否大于20M
     */
    @SuppressLint("NewApi")
    public static boolean isSystemEnough20M() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize;//block大小
        long blockCount;//block数目
        long availCount;//可用大小
        if (isOverSDK_INT(18)) {
            blockSize = sf.getBlockSizeLong();
//            blockCount = sf.getBlockCountLong();
            availCount = sf.getAvailableBlocksLong();
        } else {
            blockSize = sf.getBlockSize();
//            blockCount = sf.getBlockCount();
            availCount = sf.getAvailableBlocks();
        }
        long size = availCount * blockSize / 1024 / 1024;
//		Log.d("", "block大小:"+ blockSize+",block数目:"+ blockCount+",总大小:"+blockSize*blockCount/1024+"KB");
//		Log.d("", "可用的block数目：:"+ availCount+",可用大小:"+ availCount*blockSize/1024+"KB");
        if (size > 20)//足够20M
            return true;
        else
            return false;
    }
}
