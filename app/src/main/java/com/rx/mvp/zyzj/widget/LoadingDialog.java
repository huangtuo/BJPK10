package com.rx.mvp.zyzj.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.utils.LogUtils;


/**
 * 作者：${丰亚会} on 2015/9/14 17:05
 * 邮箱：feng.yahui@ucsmy.com
 */
public class LoadingDialog extends Dialog {

    private boolean isMustshow;
    private String message;

    public LoadingDialog(Context context, boolean isMustshow, String message) {
        super(context, R.style.alert_dialog);
        this.isMustshow = isMustshow;
        this.message = message;
    }

    private ProgressBar loadingProgressBar;
    private TextView loadingtext;
    private FrameLayout mSuccessFrame;
    private View mSuccessLeftMask;
    private View mSuccessRightMask;
    private SuccessTickView mSuccessTick;


    private AnimationSet mSuccessLayoutAnimSet;
    private Animation mSuccessBowAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.loading_dialog);

        setCancelable(isMustshow);
        setCanceledOnTouchOutside(false);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
        loadingtext = (TextView) findViewById(R.id.loadingtext);
        mSuccessFrame = (FrameLayout) findViewById(R.id.success_frame);
        mSuccessLeftMask = findViewById(R.id.mask_left);
        mSuccessRightMask = findViewById(R.id.mask_right);
        mSuccessTick = (SuccessTickView) findViewById(R.id.success_tick);

        mSuccessBowAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.success_bow_roate);
        mSuccessLayoutAnimSet = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.success_mask_layout);


        mSuccessBowAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.super.cancel();
                    }
                }, 800);
            }
        });
        if (message != null && message.length() > 0) {
            loadingtext.setText(message);
        }

    }


    public void setSuccess() {
        mSuccessFrame.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.GONE);

        mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(0));
        mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(1));
        loadingtext.setText("加载成功");
        playAnimation();

    }

    private void playAnimation() {
        mSuccessTick.startTickAnim();
        mSuccessRightMask.startAnimation(mSuccessBowAnim);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private static LoadingDialog loadingDialog;

    /**
     *
     */
    public static void showDialog(Context context, boolean isMustShow, String message) {
        try {
//            if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context, isMustShow, message);
            loadingDialog.show();
//                loadingDialog.setOnDismissListener(new OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
////                        loadingDialog = null;
//                    }
//                });
//            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("loadingDialog异常---->" + e.getMessage());
        }

    }


    public static void disDialog() {
        try {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("loadingDialog-dis异常---->" + e.getMessage());
        }

    }

}
