package com.rx.mvp.zyzj.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.common.BundleKey;
import com.rx.mvp.zyzj.entity.resp.MyFeedbackEntity;
import com.rx.mvp.zyzj.entity.resp.SuggestionEntity;
import com.rx.mvp.zyzj.entity.resp.UserInfoEntity;
import com.rx.mvp.zyzj.iface.IMyFeedbackView;
import com.rx.mvp.zyzj.presenter.MyFeedbackPresenter;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.rx.mvp.zyzj.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/31.
 */

public class FeedbackDetailsActivity extends BaseActivity implements IMyFeedbackView {
    @BindView(R.id.common_left_photo)
    ImageView imgBack;
    @BindView(R.id.common_title)
    TextView tvTitle;
    @BindView(R.id.feedback_tv_opinionTitle)
    TextView tvOpinionTitle;
    @BindView(R.id.feedback_img_userHead)
    CircleImageView imgUserHead;
    @BindView(R.id.feedback_tv_userName)
    TextView tvUserName;
    @BindView(R.id.feedback_tv_createTime)
    TextView tvCreateTime;
    @BindView(R.id.feedback_tv_opinionContent)
    TextView tvOpinionContent;
    @BindView(R.id.feedback_detail_ll_img)
    LinearLayout llImg;
    @BindView(R.id.feedback_ll_service)
    LinearLayout llService;
    @BindView(R.id.feedback_img_serviceHead)
    CircleImageView imgServiceHead;
    @BindView(R.id.feedback_tv_serviceName)
    TextView tvServiceName;
    @BindView(R.id.feedback_tv_serviceTime)
    TextView tvServiceTime;
    @BindView(R.id.feedback_tv_serviceContent)
    TextView tvServiceContent;

    private String opinionId;

    private MyFeedbackPresenter myFeedbackPresenter = new MyFeedbackPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feedback_details;
    }

    @Override
    protected void initBundleData() {
        opinionId = getIntent().getStringExtra(BundleKey.OPINION_ID);
    }

    @Override
    protected void initView() {
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText("意见详情");

        imgBack.setOnClickListener(v -> {
            finish();
        });

    }

    @Override
    protected void initData() {
        llImg.removeAllViews();
        myFeedbackPresenter.getFeedbackDetails(opinionId);
    }

    private void setDatas(MyFeedbackEntity entity) {
        tvOpinionTitle.setText(entity.getTopic());
        if (entity.getDetaiList() != null && entity.getDetaiList().size() > 0) {
            SuggestionEntity suggestionEntity = entity.getDetaiList().get(0);
            if (suggestionEntity != null) {
                tvUserName.setText(suggestionEntity.getUserName());
                tvCreateTime.setText(suggestionEntity.getCreateTime());
                tvOpinionContent.setText(suggestionEntity.getContent());
                if(!StringUtils.isEmpty(suggestionEntity.getBase64String())){
                    addImge(suggestionEntity.getBase64String());
                }
            }
        }
        if ("1".equals(entity.getStatus())) {
            llService.setVisibility(View.VISIBLE);

            if (entity.getDetaiList() != null && entity.getDetaiList().size() > 1) {
                SuggestionEntity suggestionEntity = entity.getDetaiList().get(1);
                if (suggestionEntity != null) {
                    tvServiceName.setText(suggestionEntity.getUserName());
                    tvServiceTime.setText(suggestionEntity.getCreateTime());
                    tvServiceContent.setText(suggestionEntity.getContent());
                    Glide.with(this).load("")
                            .placeholder(R.mipmap.user_photo)
                            .error(R.mipmap.user_photo)
                            .crossFade()
                            .into(imgServiceHead);
                }
            }

        } else {
            llService.setVisibility(View.GONE);
        }

        UserInfoEntity userInfo = ZyzjApplication.application.getUserInfoBean();
        if (userInfo != null) {
            Glide.with(this).load(userInfo.getAvatar())
                    .placeholder(R.mipmap.user_photo)
                    .error(R.mipmap.user_photo)
                    .crossFade()
                    .into(imgUserHead);
        }
    }

    private void addImge(String base64){
        Bitmap bitmap = stringtoBitmap(base64);
        if(base64 != null){
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            imageView.setImageBitmap(bitmap);
            llImg.addView(imageView);
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

    }

    @Override
    public void getFeedbackDetails(MyFeedbackEntity myFeedbackEntity) {
        if (myFeedbackEntity != null) {
            setDatas(myFeedbackEntity);
        }
    }

    /**
     * 将字符串转换成Bitmap类型
     * @param string
     * @return
     */
    public Bitmap stringtoBitmap(String string){
        Bitmap bitmap=null;
        try {
            byte[]bitmapArray;
            bitmapArray= Base64.decode(string, Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
