package com.rx.mvp.zyzj.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.utils.LogUtils;


/**
 * 作者：${丰亚会} on 2015/9/15 09:24
 * 邮箱：feng.yahui@ucsmy.com
 */
public class OneButtonDialog extends Dialog {
    public OneButtonDialog(Context context) {
        super(context, R.style.alert_dialog);
    }

    private OnSureButtonClickListener onSureButtonClickListener;

    public interface OnSureButtonClickListener {
        public void onSuerButClisk(Dialog dialog);
    }

    public void setOnSureButtonClickListener(OnSureButtonClickListener onSureButtonClickListener) {
        this.onSureButtonClickListener = onSureButtonClickListener;
    }

    private TextView contentText;
    private TextView sureBut;
    private TextView tvTitle;
    private ImageView imgTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.one_button_dialog);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        contentText = (TextView) findViewById(R.id.contentText);
        sureBut = (TextView) findViewById(R.id.sureBut);
        tvTitle = (TextView) findViewById(R.id.tv_dialog_title);
        imgTitle = (ImageView) findViewById(R.id.img_dialog_error);
        //  contentText.setMovementMethod(new ScrollingMovementMethod());
        sureBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onSureButtonClickListener != null) {
                    OneButtonDialog.this.dismiss();
                    onSureButtonClickListener.onSuerButClisk(OneButtonDialog.this);
                }
            }
        });
    }

    public void setContentText(String text) {

        if (text != null && text.length() > 0) {
            contentText.setText(text);
        }
    }

    public void setSureButText(String text) {
        if (text != null && text.length() > 0) {
            sureBut.setText(text);
        } else {
            sureBut.setText("确 定");
        }
    }

    public void setContentTextGravity() {
        if (contentText != null) {
            contentText.setGravity(Gravity.LEFT);
        }

    }

    public void setTvTitle(String title) {
        if (title != null && title.length() > 0) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
            imgTitle.setVisibility(View.GONE);
            contentText.setTextColor(getContext().getResources().getColor(R.color.text_color_black02));
        }
    }

    private static OneButtonDialog oneButtonDialog;

    public static void showDialog(Context context, String text, String suerbutText, OneButtonDialog.OnSureButtonClickListener onSureButtonClickListener) {
        oneButtonDialog = null;
        try {
            if (oneButtonDialog == null) {
                oneButtonDialog = new OneButtonDialog(context);
                if (!oneButtonDialog.isShowing()) {
                    oneButtonDialog.show();
                }

                if (text != null && text.length() > 0) {
                    oneButtonDialog.setContentText(text);
                }
                if (suerbutText != null && suerbutText.length() > 0) {
                    oneButtonDialog.setSureButText(suerbutText);
                }


                if (onSureButtonClickListener != null) {
                    oneButtonDialog.setOnSureButtonClickListener(onSureButtonClickListener);
                }
                oneButtonDialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        oneButtonDialog = null;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("一个按钮的弹框错误---->" + e.getMessage());
        }

    }

    public static void showDialog(Context context, String text, String suerbutText, String title, OneButtonDialog.OnSureButtonClickListener onSureButtonClickListener) {

        try {
            if (oneButtonDialog == null) {
                oneButtonDialog = new OneButtonDialog(context);
                if (!oneButtonDialog.isShowing()) {
                    oneButtonDialog.show();
                }

                if (text != null && text.length() > 0) {
                    oneButtonDialog.setContentText(text);
                }
                if (suerbutText != null && suerbutText.length() > 0) {
                    oneButtonDialog.setSureButText(suerbutText);
                }
                if (title != null && title.length() > 0) {
                    oneButtonDialog.setTvTitle(title);
                }

                if (onSureButtonClickListener != null) {
                    oneButtonDialog.setOnSureButtonClickListener(onSureButtonClickListener);
                }
                oneButtonDialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        oneButtonDialog = null;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("一个按钮的弹框错误---->" + e.getMessage());
        }

    }

     public static void disDialog() {
        try {
            if (oneButtonDialog != null && oneButtonDialog.isShowing()) {
                oneButtonDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("一个按钮的弹框dis错误---->" + e.getMessage());
        }
    }

    public void setGravity(int Gravity){
        contentText.setGravity(Gravity);
    }

    private static OneButtonDialog oneButtonDialog2;

    public static void showGravityDialog(Context context, int Gravity, String text, String suerbutText, OneButtonDialog.OnSureButtonClickListener onSureButtonClickListener) {
        try {
            if (oneButtonDialog2 == null) {
                oneButtonDialog2 = new OneButtonDialog(context);
                if (!oneButtonDialog2.isShowing()) {
                    oneButtonDialog2.show();
                }
                oneButtonDialog2.setGravity(Gravity);
                if (text != null && text.length() > 0) {
                    oneButtonDialog2.setContentText(text);
                }
                if (suerbutText != null && suerbutText.length() > 0) {
                    oneButtonDialog2.setSureButText(suerbutText);
                }


                if (onSureButtonClickListener != null) {
                    oneButtonDialog2.setOnSureButtonClickListener(onSureButtonClickListener);
                }
                oneButtonDialog2.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        oneButtonDialog2 = null;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("一个按钮的弹框错误---->" + e.getMessage());
        }

    }


}
