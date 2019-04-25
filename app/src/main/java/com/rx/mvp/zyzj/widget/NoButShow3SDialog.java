package com.rx.mvp.zyzj.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.utils.LogUtils;


/**
 * 作者：${丰亚会} on 2015/9/15 09:31
 * 邮箱：feng.yahui@ucsmy.com
 */
public class NoButShow3SDialog extends Dialog {
    private Activity activity;
    public NoButShow3SDialog(Activity context) {
        super(context, R.style.alert_dialog);
        this.activity = context ;
    }

    private TextView contentText ;
    private static OnDismissFinishListener onDismissFinishListener;
    public  interface  OnDismissFinishListener {
        void  dismissFinish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.no_button_show3s_dialog);

        setCancelable(false);
        setCanceledOnTouchOutside(true);
        contentText  = (TextView)findViewById(R.id.contentText);
    }

    public void setContentText(String text){
        if(text!=null&&text.length()>0){
            contentText.setText(text);
        }
    }


    @Override
    public void show() {
        super.show();
        /**
         *  4s后消失不见了
         */
        handler.sendEmptyMessageDelayed(1,3000);
    }

    private Handler handler   = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(!activity.isFinishing()&&NoButShow3SDialog.this!=null&&NoButShow3SDialog.this.isShowing()){
                        NoButShow3SDialog.this.dismiss();
                    }
                    break;
            }
        }
    };

    private static NoButShow3SDialog noButShow3SDialog;
    public static void  showToast(Activity context,String content){
        try {
            if(noButShow3SDialog==null && !TextUtils.isEmpty(content)){
                noButShow3SDialog = new NoButShow3SDialog(context);
                if (!noButShow3SDialog.isShowing()) {
                    noButShow3SDialog.show();
                }
                noButShow3SDialog.setContentText(content);
                noButShow3SDialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        noButShow3SDialog = null;
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e("自定义toast异常---->"+e.getMessage());
        }

    }

    public static void  showToast(Activity context,String content, OnDismissFinishListener dismissFinishListener){
        noButShow3SDialog = null;
        onDismissFinishListener = dismissFinishListener;
        try {
            if(noButShow3SDialog==null && !TextUtils.isEmpty(content)){
                noButShow3SDialog = new NoButShow3SDialog(context);
                if (!noButShow3SDialog.isShowing()) {
                    noButShow3SDialog.show();
                    noButShow3SDialog.setContentText(content);
                }
                noButShow3SDialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        noButShow3SDialog = null;
                        if(onDismissFinishListener!=null){
                            onDismissFinishListener.dismissFinish();
                            onDismissFinishListener=null;
                        }
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            noButShow3SDialog = null;
            LogUtils.e("自定义toast异常---->"+e.getMessage());
        }
    }
}
