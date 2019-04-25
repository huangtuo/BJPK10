package com.rx.mvp.zyzj.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.adapter.FeedBackGridAdapter;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.entity.event.RefreshFeedbackEvent;
import com.rx.mvp.zyzj.entity.resp.MyFeedbackEntity;
import com.rx.mvp.zyzj.iface.IMyFeedbackView;
import com.rx.mvp.zyzj.presenter.MyFeedbackPresenter;
import com.rx.mvp.zyzj.utils.GetPathFromUri4kitkat;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.rx.mvp.zyzj.widget.ContainsEmojiEditText;
import com.rx.mvp.zyzj.widget.NoButShow3SDialog;
import com.rx.mvp.zyzj.widget.multi_image_selector.MultiImageSelector;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

/**
 * Created by huangtuo on 2018/7/9.
 */

public class FeedbackEditActivity extends BaseActivity implements IMyFeedbackView{
    @BindView(R.id.common_left_photo)
    ImageView imgBack;
    @BindView(R.id.common_title)
    TextView tvTitle;
    @BindView(R.id.common_right_text)
    TextView tvRgihtTitle;
    @BindView(R.id.feedback_edit_title)
    ContainsEmojiEditText editTitle;
    @BindView(R.id.feedback_edit_content)
    ContainsEmojiEditText editContent;
    @BindView(R.id.feedback_gridview)
    GridView gridView; //图片

    protected static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int REQUEST_IMAGE = 2;
    private ArrayList<String> mSelectPath = new ArrayList<>(); //选中的图片
    private ArrayList<String> datas;//Adapter中的datas包括加号  path == "-1" 表示 加号
    private FeedBackGridAdapter adapter;

    public static final int imgNum = 1;
    private MyFeedbackPresenter myFeedbackPresenter = new MyFeedbackPresenter(this, this);
    private String imgBase64 = "";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feedback_edit;
    }


    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText("意见反馈");
        tvRgihtTitle.setText("提交");

        imgBack.setOnClickListener(v -> {
            finish();
        });

        tvRgihtTitle.setOnClickListener(v -> {
            submit(editTitle.getText().toString(), editContent.getText().toString());
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pickImage();
            }
        });

    }

    private void submit(String title, String content) {
        if(StringUtils.isEmpty(title)){
            showToast("请输入反馈标题");
            return;
        }else if(StringUtils.isEmpty(content)){
            showToast("请输入反馈内容");
            return;
        }

//        StringBuilder sb = new StringBuilder();
//        for(String imgUrl : mSelectPath){
//            sb.append(imgUrl + "/n");
//        }
//        showToast(sb.toString());
        //发送网络请求
        myFeedbackPresenter.submitFeedback(title, content, imgBase64);

    }

    @Override
    protected void initData() {
        datas = new ArrayList<String>();
        datas.add("-1");
        adapter = new FeedBackGridAdapter(this);
        gridView.setAdapter(adapter);
        adapter.appendToList(datas);
    }


    private void pickImage() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
        } else {
            MultiImageSelector selector = MultiImageSelector.create(FeedbackEditActivity.this);
            selector.showCamera(true);
            //可选图片数量
            selector.count(imgNum);
            selector.single();
            //多选
//            selector.multi();
            //设置已选中图片
//            ArrayList<String> checkedList = new ArrayList<>();
//            for (String path : datas) {
//                if (!addIndex.equals(path)) {
//                    checkedList.add(path);
//                }
//            }
            selector.origin(mSelectPath);
            selector.start(FeedbackEditActivity.this, REQUEST_IMAGE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showToast("需要开启权限才能使用照相机功能");
            } else if (grantResults.length > 1 && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                showToast( "需要开启存储权限才能使用相册功能");
            }else{
                pickImage();
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                datas.clear();
                if(mSelectPath != null && mSelectPath.size() > 0){
                    datas.addAll(mSelectPath);
                    if(mSelectPath.size() < imgNum)
                        datas.add("-1");
                    toCompress(mSelectPath.get(0));
                }else{
                    datas.add("-1");
                }
                adapter.clear();
                adapter.appendToList(datas);

            }
        }
    }

    @Override
    public void showLoading() {
        showLoding();
    }

    @Override
    public void closeLoading() {
        disLoding();
    }

    @Override
    public void onError(int code, String msg) {
        sendFail(code, msg);
    }

    @Override
    public void getMyFeedbackSuccess(List<MyFeedbackEntity> myFeedbackList) {

    }

    @Override
    public void submitFeedbackSuccess(String msg) {
        //上传成功 刷新
        EventBus.getDefault().post(new RefreshFeedbackEvent());
        showToastFinish("提交成功", () -> finish());
    }

    @Override
    public void getFeedbackDetails(MyFeedbackEntity myFeedbackEntity) {

    }

    private void toCompress(String path){
        //未上传 则去压缩 上传
        doCompressPic(path, new OnCompressListener() {
            @Override
            public void onStart() {
                showLoding();
            }

            @Override
            public void onSuccess(File file) {
                disLoding();
                String capturePath = GetPathFromUri4kitkat.getPath(FeedbackEditActivity.this, Uri.fromFile(file));
                imgBase64 = imageToBase64(capturePath);
            }

            @Override
            public void onError(Throwable e) {
                disLoding();
            }
        });
    }

    /**
     * 获取压缩后的图片
     *
     * @param oldPath
     * @return
     */
    @NonNull
    private void doCompressPic(String oldPath, final OnCompressListener onCompressListener) {
        File oldFile = new File(oldPath);

        Luban.compress(getApplicationContext(), oldFile)
                .setMaxSize(200)        // limit the final image size（unit：Kb）
                .putGear(Luban.CUSTOM_GEAR)      // set the compress mode, default is : THIRD_GEAR
                .launch(onCompressListener);              // use CUSTOM GEAR compression mode
    }

    /**
     * 将图片转换成Base64编码的字符串
     * @param path
     * @return base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(StringUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


}
