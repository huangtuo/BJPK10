package com.rx.mvp.zyzj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.BaseAdapter;
import com.rx.mvp.zyzj.entity.resp.AnalysisConfigEntity;
import com.rx.mvp.zyzj.entity.resp.AnalysisConfigGroupEntity;
import com.rx.mvp.zyzj.widget.flowLayout.FlowLayout;
import com.rx.mvp.zyzj.widget.flowLayout.TagAdapter;
import com.rx.mvp.zyzj.widget.flowLayout.TagFlowLayout;

/**
 * Created by huangtuo on 2018/7/27.
 */

public class AnalysisTargetAdapter extends BaseAdapter<AnalysisConfigGroupEntity> {

    private AnalysisConfigEntity currentChecked;
    private TargetCheckedListener targetCheckedListener;

    public void setTargetCheckedListener(TargetCheckedListener targetCheckedListener) {
        this.targetCheckedListener = targetCheckedListener;
    }

    public AnalysisTargetAdapter(Context context) {
        super(context);
    }

    public void setCurrentChecked(AnalysisConfigEntity currentChecked) {
        this.currentChecked = currentChecked;
    }

    public AnalysisConfigEntity getCurrentChecked() {
        return currentChecked;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TargetHolder(inflater.inflate(R.layout.item_analysis_target, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TargetHolder targetHolder = (TargetHolder) holder;
        AnalysisConfigGroupEntity targetEntity = getList().get(position);
        if(targetEntity != null){
            targetHolder.tvSeries.setText(targetEntity.getName());

            TagAdapter tagAdapter;

            targetHolder.tagFlowLayout.setAdapter(tagAdapter = new TagAdapter<AnalysisConfigEntity>(targetEntity.getValues()) {
                @Override
                public View getView(FlowLayout parent, int position, AnalysisConfigEntity entity) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_lottery_item,
                            targetHolder.tagFlowLayout, false);
                    if(entity != null)
                        tv.setText(entity.getRuleName());

                    return tv;
                }
            });

            if(currentChecked != null){
                for(int i = 0; i < targetEntity.getValues().size(); i++){
                    if(currentChecked.getId().equals(targetEntity.getValues().get(i).getId())){
                        tagAdapter.setSelectedList(i);
                    }
                }
            }

            targetHolder.tagFlowLayout.setOnTagClickListener((view, position2, parent) -> {
                if(targetCheckedListener != null){
                    targetCheckedListener.targetChecked(targetEntity.getValues().get(position2));
                }
                return false;
            });

        }


    }

    class TargetHolder extends RecyclerView.ViewHolder{
        private TextView tvSeries;
        private TagFlowLayout tagFlowLayout;

        public TargetHolder(View itemView) {
            super(itemView);
            tvSeries = (TextView) itemView.findViewById(R.id.target_tv_series);
            tagFlowLayout = (TagFlowLayout) itemView.findViewById(R.id.target_flowlayout);
        }
    }


    public interface TargetCheckedListener{
        void targetChecked(AnalysisConfigEntity value);
    }

}
