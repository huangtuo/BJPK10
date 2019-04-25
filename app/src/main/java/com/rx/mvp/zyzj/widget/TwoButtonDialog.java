package com.rx.mvp.zyzj.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.utils.LogUtils;


/**
 * 作者：${丰亚会} on 2015/9/14 18:41
 * 邮箱：feng.yahui@ucsmy.com
 */
public class TwoButtonDialog extends Dialog {

    public TwoButtonDialog(Context context) {
        super(context, R.style.alert_dialog);
    }

    private TextView contentText ;
    private TextView sureBut ,cancleBut;

    private  OnSureButtonClickListener onSureButtonClickListener;

    public interface OnSureButtonClickListener{
        public void onSuerButClisk(Dialog dialog);
    }

    private  OnCancleButtonClickListener onCancleButtonClickListener;

    public interface OnCancleButtonClickListener{
        public void onCancleButClisk(Dialog dialog);
    }



    public void setOnCancleButtonClickListener(OnCancleButtonClickListener onCancleButtonClickListener) {
        this.onCancleButtonClickListener = onCancleButtonClickListener;
    }

    public void setOnSureButtonClickListener(TwoButtonDialog.OnSureButtonClickListener onSureButtonClickListener) {
        this.onSureButtonClickListener = onSureButtonClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.two_button_dialog);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        contentText  = (TextView)findViewById(R.id.contentText);
        sureBut  = (TextView)findViewById(R.id.sureBut);
        cancleBut  = (TextView)findViewById(R.id.cancleBut);
       // contentText.setMovementMethod(new ScrollingMovementMethod());
        sureBut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(onSureButtonClickListener!=null){
                    TwoButtonDialog.this.dismiss();
                    onSureButtonClickListener.onSuerButClisk(TwoButtonDialog.this);
                }
            }
        });

        cancleBut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(onCancleButtonClickListener!=null){
                    TwoButtonDialog.this.dismiss();
                    onCancleButtonClickListener.onCancleButClisk(TwoButtonDialog.this);
                }

            }
        });
    }

    public void setContentText(String text){

        if(text!=null&&text.length()>0){
            contentText.setText(text);
        }
    }

    public void setSureButText(String text){
        if(text!=null&&text.length()>0){
            sureBut.setText(text);
        }else{
            sureBut.setText("确 定");
        }
    }

    public void setCancleButText(String text){
        if(text!=null&&text.length()>0){
            cancleBut.setText(text);
        }else{
            cancleBut.setText("取 消");
        }
    }

    public void setContentTextLeft(String text) {

        if (text != null && text.length() > 0) {
            contentText.setText(text);
            contentText.setGravity(Gravity.LEFT);
        }
    }

    public void setContentTextGravity() {
        if (contentText != null) {
            contentText.setGravity(Gravity.LEFT);
        }

    }


    private static TwoButtonDialog twoButtonDialog ;
    public static void showDialog(Context context, String text, String suerbutText,String canclebutText,TwoButtonDialog.OnSureButtonClickListener onSureButtonClickListener, TwoButtonDialog.OnCancleButtonClickListener onCancleButtonClickListener){
        twoButtonDialog=null;
        try{
            if(twoButtonDialog==null){
                twoButtonDialog= new TwoButtonDialog(context);
                if(!twoButtonDialog.isShowing()){
                    twoButtonDialog.show();
                }
                if (text != null && text.length() > 0) {
                    twoButtonDialog.setContentText(text);
                }
                if (suerbutText != null && suerbutText.length() > 0) {
                    twoButtonDialog.setSureButText(suerbutText);
                }
                if (twoButtonDialog != null && canclebutText.length() > 0) {
                    twoButtonDialog.setCancleButText(canclebutText);
                }
                if (onSureButtonClickListener != null) {
                    twoButtonDialog.setOnSureButtonClickListener(onSureButtonClickListener);
                }
                if (onSureButtonClickListener != null) {
                    twoButtonDialog.setOnCancleButtonClickListener(onCancleButtonClickListener);
                }

                twoButtonDialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        twoButtonDialog = null;
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e("一个按钮的弹框错误---->" + e.getMessage());
        }

    }

    public void showDialog(Context context, String text, String suerbutText, String canclebutText, TwoButtonDialog.OnSureButtonClickListener onSureButtonClickListener, TwoButtonDialog.OnCancleButtonClickListener onCancleButtonClickListener, boolean isLeft) {
        twoButtonDialog = null;
        try {
            if (twoButtonDialog == null) {
                twoButtonDialog = new TwoButtonDialog(context);

                if (!twoButtonDialog.isShowing()) {
                    twoButtonDialog.show();
                    if (text != null && text.length() > 0) {
                        twoButtonDialog.setContentTextLeft(text);
                    }
                    if (suerbutText != null && suerbutText.length() > 0) {
                        twoButtonDialog.setSureButText(suerbutText);
                    }
                    if (twoButtonDialog != null && canclebutText.length() > 0) {
                        twoButtonDialog.setCancleButText(canclebutText);
                    }
                    if (onSureButtonClickListener != null) {
                        twoButtonDialog.setOnSureButtonClickListener(onSureButtonClickListener);
                    }
                    if (onSureButtonClickListener != null) {
                        twoButtonDialog.setOnCancleButtonClickListener(onCancleButtonClickListener);
                    }
                }
                twoButtonDialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        twoButtonDialog = null;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("一个按钮的弹框错误---->" + e.getMessage());
        }

    }

    private static void disDialog(){
        try {
            if(twoButtonDialog!=null&&twoButtonDialog.isShowing()){
                twoButtonDialog.dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e("一个按钮的弹框dis错误---->"+e.getMessage());
        }

    }

    public void setGravity(int Gravity){
        contentText.setGravity(Gravity);
    }

    private static TwoButtonDialog twoButtonDialog2 ;
    public static void showGravityDialog(Context context, int Gravity, String text, String suerbutText,String canclebutText,TwoButtonDialog.OnSureButtonClickListener onSureButtonClickListener, TwoButtonDialog.OnCancleButtonClickListener onCancleButtonClickListener){
        try{
            if(twoButtonDialog2==null){
                twoButtonDialog2= new TwoButtonDialog(context);
                if(!twoButtonDialog2.isShowing()){
                    twoButtonDialog2.show();
                }
                twoButtonDialog2.setGravity(Gravity);
                if (text != null && text.length() > 0) {
                    twoButtonDialog2.setContentText(text);
                }
                if (suerbutText != null && suerbutText.length() > 0) {
                    twoButtonDialog2.setSureButText(suerbutText);
                }
                if (twoButtonDialog2 != null && canclebutText.length() > 0) {
                    twoButtonDialog2.setCancleButText(canclebutText);
                }
                if (onSureButtonClickListener != null) {
                    twoButtonDialog2.setOnSureButtonClickListener(onSureButtonClickListener);
                }
                if (onSureButtonClickListener != null) {
                    twoButtonDialog2.setOnCancleButtonClickListener(onCancleButtonClickListener);
                }

                twoButtonDialog2.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        twoButtonDialog2 = null;
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e("一个按钮的弹框错误---->" + e.getMessage());
        }

    }


}
