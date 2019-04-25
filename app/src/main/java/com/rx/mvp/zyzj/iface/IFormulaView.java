package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.PlanDetailsEntity2;

/**
 * Created by huangtuo on 2018/9/8.
 */

public interface IFormulaView extends IBaseView {
    void submitSuccess(PlanDetailsEntity2 planDetailsEntity);
}
