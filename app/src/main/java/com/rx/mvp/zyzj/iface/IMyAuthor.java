package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.MyAuthorEntity;

import java.util.List;

/**
 * Created by huangtuo on 2018/8/15.
 */

public interface IMyAuthor extends IBaseView {
    void getMyAuthor(List<MyAuthorEntity> listAuthor);
}
