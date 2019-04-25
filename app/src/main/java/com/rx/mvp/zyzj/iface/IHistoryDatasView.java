package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.HistoryEntity;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/4.
 */

public interface IHistoryDatasView extends IBaseView {
    void getHistoryDataSuccess(List<HistoryEntity> historyEntityList);
}
