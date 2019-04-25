package com.rx.mvp.zyzj.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.widget.RemoteViews;


import com.rx.mvp.zyzj.BuildConfig;
import com.rx.mvp.zyzj.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2016/12/13.
 */
public class UpdateUtils {

    Context c;
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private static final int NOTIFY_ID = 80;
    boolean downloadfail;
    private int fileTotalSize = 0;
    private int fileDownloadSize = 0;
    private boolean cancelled = false;
    static UpdateUtils downloadUtils2;

    public static UpdateUtils getInstance(Context c) {
        if (downloadUtils2 == null)
            downloadUtils2 = new UpdateUtils(c);
        return downloadUtils2;
    }

    public UpdateUtils(Context c) {
        this.c = c;
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                fileDir = Environment.getExternalStorageDirectory()
                        .getCanonicalPath();
            } else {
                fileDir = c.getCacheDir().getAbsolutePath();
            }
            fileName = "/zyzj/zyzj.apk";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fileDir;
    private String fileName;


    private Handler handler = new Handler() {

        private int temp_date = -1;

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int rate = msg.arg1;
                    if (rate != temp_date) {
                        if (rate < 100) {
                            RemoteViews contentView = mNotification.contentView;
                            mNotification.sound = null;
                            contentView.setTextViewText(R.id.notificationTitle,
                                    "正在下载");
                            contentView.setTextViewText(R.id.notificationPercent,
                                    rate + "%");
                            contentView.setProgressBar(R.id.notificationProgress,
                                    100, rate, false);

                            mNotificationManager.notify(NOTIFY_ID, mNotification);
                        } else {
                            /**
                             * 设置为首次启动，进入欢迎页面
                             */
                            try {
                                SPUtils.putBoolean(c, "firstopen",
                                        "firstopen", false);
                            } catch (Exception e) {
                            }
                            Intent intent2 = new Intent(Intent.ACTION_VIEW);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                intent2.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                Uri contentUri = FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID + ".fileprovider", new File(fileDir + fileName));
                                intent2.setDataAndType(contentUri, "application/vnd.android.package-archive");
                            } else {
                                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent2.setDataAndType(
                                        Uri.fromFile(new File(fileDir + fileName)),
                                        "application/vnd.android.package-archive");

                            }
                            c.startActivity(intent2);
                            String title = "下载完成";
                            String msg2 = "文件已下载完毕";
                            mNotification = NotificationHelper.notify(c, msg2, title,
                                    msg2, intent2, NOTIFY_ID);
                        }
                        if (rate >= 100) {
//                            stopSelf();
                        }
                        temp_date = rate;
                    }

                    break;
                case 0:
                    mNotificationManager.cancel(NOTIFY_ID);
                    if (downloadfail) {
                        notifyFail();
                    }
                    break;
            }
        }

        ;
    };
    public static final int CONNECT_TIMEOUT = 180 * 1000;

    public static final int READ_TIMEOUT = 180 * 1000;
    Thread downThread;

    public static void startUpdate(final Context c, final String url) {

        UpdateUtils utils2 = UpdateUtils.getInstance(c);
        if (!utils2.isStart()) {
            utils2.startUpdate(url);
        }

    }


    private void startUpdate(final String url_str) {
        mNotificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intnt = new Intent();
        String title = "开始下载";
        mNotification = NotificationHelper.getNotify(c, title, title,
                title, -1, intnt, NOTIFY_ID);
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        RemoteViews contentView = new RemoteViews(c.getPackageName(),
                R.layout.notification);
        mNotification.contentView = contentView;
        NotificationHelper.notify(c, NOTIFY_ID, mNotification);
        downThread = new Thread() {
            public void run() {
                startDownload(url_str);
            }

        };
        downThread.start();
    }

    public boolean isStart() {
        if (downThread != null) {
            if (downThread.isAlive()) {
                return true;
            }
        }
        return false;
    }


    public void startDownload(String url_str) {
        downloadfail = false;
        cancelled = false;
        int rate = 0;
        while (!cancelled && rate < 100) {
            URL url = null;
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                url = new URL(url_str);
                URLConnection conn = url.openConnection();
                conn.setReadTimeout(CONNECT_TIMEOUT);
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.connect();
                is = conn.getInputStream();
                fileTotalSize = conn.getContentLength();
                if (fileTotalSize <= 0) {
                    return;
                }
                if (is == null) {
                    return;
                }
                String path = fileDir + fileName;
                File file = new File(path);
                if (file.getParentFile() != null
                        && file.getParentFile().mkdirs()) {
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                fos = new FileOutputStream(file.getAbsolutePath());
                byte buf[] = new byte[4096];
                fileDownloadSize = 0;
                do {
                    int numread = is.read(buf);
                    if (numread == -1) {
                        break;
                    }
                    fos.write(buf, 0, numread);
                    fileDownloadSize += numread;
                    rate = (int) ((fileDownloadSize / (float) fileTotalSize) * 100);
                    if (rate % 5 == 0) {
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        msg.arg1 = rate;
                        handler.sendMessage(msg);
                    }
                } while (true);

            } catch (Exception e) {
                e.printStackTrace();
                cancelled = true;
                downloadfail = true;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        cancelled = true;
                        downloadfail = true;
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        cancelled = true;
                        downloadfail = true;
                    }
                }
            }
        }
        if (cancelled) {
            Message msg = handler.obtainMessage();
            msg.what = 0;
            handler.sendMessage(msg);
        }
    }

    public void notifyFail() {
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotification.contentView = null;
        Intent intent = new Intent();
        String title = "下载安装包失败";
        String msg = "文件下载失败，请与相关人员联系";
        mNotification = NotificationHelper.notify(c, msg, title,
                msg, intent, NOTIFY_ID);
    }


}
