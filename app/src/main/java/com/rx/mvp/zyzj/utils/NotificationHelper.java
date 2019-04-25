package com.rx.mvp.zyzj.utils;





import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.rx.mvp.zyzj.R;


/**
 * 通知制造
 *
 * @author Juwend
 */
public class NotificationHelper {
    // 1.实例化Notification类
    // 2.设置Notification对象的icon，通知文字，声音
    // 3.实例化PendingIntent类，作为控制点击通知后显示内容的对象
    // 4.加载PendingIntent对象到Notification对象（设置 打开通知抽屉后的 标题/内容）
    // 5.获得 NotificationManager对象
    // 6.使用NotificationManager对象显示通知

    static int notifyId = 80;

    public static Notification notify(Context context, String firstcontent,
                                      String title, String content, Intent intent) {
        Notification n = genNotification(context, notifyId,
                R.mipmap.ic_launcher, content, Notification.DEFAULT_SOUND, title,
                content, intent, Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT);
        notify(context, notifyId, n);
        notifyId++;
        return n;
    }

    /**
     * 相同id的情况
     */
    public static Notification notify(Context context, String firstcontent,
                                      String title, String content, Intent intent, int notifyId) {
        return notify(context, firstcontent, title, content, Notification.DEFAULT_SOUND, intent, notifyId);
    }

    /**
     * 相同id的情况
     *
     * @sound 传-1没有声音
     */
    public static Notification notify(Context context, String firstcontent,
                                      String title, String content, int sound, Intent intent, int notifyId) {
        Notification n = genNotification(context, notifyId,
                R.mipmap.ic_launcher, content, sound, title,
                content, intent, Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT);
        notify(context, notifyId, n);
        return n;
    }

    /**
     * 相同id的情况
     * @sound 传-1没有声音
     */
    public static Notification getNotify(Context context, String firstcontent,
                                         String title, String content,int sound, Intent intent, int notifyId) {
        Notification n = genNotification(context, notifyId,
                R.mipmap.ic_launcher, content, sound, title,
                content, intent, Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT);
        return n;
    }





    /**
     * 发布通知
     *
     * @param c        上下文
     * @param notifyId 通知标识id
     * @param n        通知对象
     */
    static public void notify(Context c, int notifyId, Notification n) {
        final NotificationManager nm = (NotificationManager) c
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // 显示通知
        nm.notify(notifyId, n);
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static public Notification genNotification(Context c, int notifyId,
                                               int iconResId, String notifyShowText, int soundResId,
                                               String titleText, String contentText, Intent intent, int flag) {
        PendingIntent ip = PendingIntent.getActivity(c, 0, // requestCode
                // 现在是没有使用的，所以任意值都可以
                intent, 0 // PendingIntent的flag，在update这个通知的时候可以加特别的flag
        );
        Notification n = new Notification();
        if (Build.VERSION.SDK_INT < 11) {/**不再兼容3.0以下的安卓版本*/
//            // 控制点击通知后显示内容的类
//
//            // 设置通知图标
//            n.icon = iconResId;
//            // 通知文字
//            n.tickerText = notifyShowText;
//            // 声音
//            if (soundResId != -1)
//                n.defaults = soundResId;
//            // 通知发出的标志设置
//            n.flags = flag;
//            // 设置通知参数
//            n.setLatestEventInfo(c, titleText, contentText, ip);
        } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 16) {
            Notification.Builder builder = new Notification.Builder(c)
                    .setAutoCancel(true)
                    .setContentTitle(titleText)
                    .setContentText(contentText)
                    .setContentIntent(ip)
                    .setSmallIcon(iconResId)
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true);
            if (soundResId != -1)
                builder.setDefaults(soundResId);
            n = builder.getNotification();
        } else {
            Notification.Builder builder = new Notification.Builder(c)
                    .setAutoCancel(true)
                    .setContentTitle(titleText)
                    .setContentText(contentText)
                    .setContentIntent(ip)
                    .setSmallIcon(iconResId)
                    .setWhen(System.currentTimeMillis());
            if (soundResId != -1)
                builder.setDefaults(soundResId);
            n = builder.build();
        }
        return n;
    }

    /**
     * 取消消息
     *
     * @param c
     * @param notifyId
     * @return void
     */
    public static void cancel(Context c, int notifyId) {
        ((NotificationManager) ((Activity) c)
                .getSystemService(Context.NOTIFICATION_SERVICE))
                .cancel(notifyId);
    }

}