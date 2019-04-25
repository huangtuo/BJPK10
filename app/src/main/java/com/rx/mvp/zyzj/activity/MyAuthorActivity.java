package com.rx.mvp.zyzj.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.common.BundleKey;
import com.rx.mvp.zyzj.entity.resp.MyAuthorEntity;
import com.rx.mvp.zyzj.iface.IMyAuthor;
import com.rx.mvp.zyzj.presenter.MyAuthorPresenter;
import com.rx.mvp.zyzj.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/29.
 */

public class MyAuthorActivity extends BaseActivity implements IMyAuthor{
    @BindView(R.id.common_left_photo)
    ImageView imgBack;
    @BindView(R.id.common_title)
    TextView tvTitle;
    @BindView(R.id.author_tv_version)
    TextView tvVersion;
    @BindView(R.id.author_tv_time)
    TextView tvTime;
    @BindView(R.id.author_tv_timeLabel)
    TextView tvTimeLabel;

    private MyAuthorPresenter myAuthorPresenter = new MyAuthorPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_author;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText("我的授权");

        imgBack.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void initData() {
        myAuthorPresenter.getMyAuthor();
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
    public void getMyAuthor(List<MyAuthorEntity> listAuthor) {
        if(listAuthor != null && listAuthor.size() > 0){
            MyAuthorEntity entity = listAuthor.get(0);
            if(entity != null){
                tvTime.setText(TimeUtil.formatStrYMd2(entity.getDeadTime()));
                tvVersion.setText(entity.getGoodsName());
                tvTimeLabel.setVisibility(View.VISIBLE);
            }

        }
    }
}
