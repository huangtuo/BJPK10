package com.rx.mvp.zyzj.fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.android.base.widget.pulltorefresh.PullToRefreshBase;
import com.android.base.widget.pulltorefresh.PullToRefreshListView;
import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.adapter.HistoryAdapter;
import com.rx.mvp.zyzj.base.BaseFragment;
import com.rx.mvp.zyzj.entity.resp.HistoryEntity;
import com.rx.mvp.zyzj.iface.IHistoryDatasView;
import com.rx.mvp.zyzj.presenter.HistoryDatasPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class HistoryFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ListView>, IHistoryDatasView{
    @BindView(R.id.history_pull_listview)
    PullToRefreshListView pullToRefreshListView;

    private HistoryAdapter adapter;
    private List<HistoryEntity> list;

    private HistoryDatasPresenter historyDatasPresenter = new HistoryDatasPresenter(this, this);
    private int page;
    private String size = "10";
    private String lotteryId;
    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_history, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        adapter = new HistoryAdapter(mContext);
        list = new ArrayList<>();
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        lotteryId = ZyzjApplication.getApplication().getLotteryId();
        pullToRefreshListView.setRefreshing(true);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page = 0;
        adapter.clear();
        historyDatasPresenter.getHistoryDatas(lotteryId, page + "", size);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page += 1;
        historyDatasPresenter.getHistoryDatas(lotteryId, page + "", size);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {
        pullToRefreshListView.onRefreshComplete();
    }

    @Override
    public void onError(int code, String msg) {
        sendFail(code, msg);
    }

    @Override
    public void getHistoryDataSuccess(List<HistoryEntity> historyEntityList) {
        if(historyEntityList != null && historyEntityList.size() > 0){
            adapter.appendToList(historyEntityList);
            pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        }else{
            pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

    }
}
