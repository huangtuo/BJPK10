package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.MyFeedbackEntity;

import java.util.List;

/**
 * Created by huangtuo on 2018/8/15.
 */

public interface IMyFeedbackView extends IBaseView {
    void getMyFeedbackSuccess(List<MyFeedbackEntity> myFeedbackList);
    void submitFeedbackSuccess(String msg);
    void getFeedbackDetails(MyFeedbackEntity myFeedbackEntity);
}
