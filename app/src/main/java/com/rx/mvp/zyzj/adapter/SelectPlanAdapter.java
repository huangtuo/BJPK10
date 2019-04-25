package com.rx.mvp.zyzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.ListBaseAdapter;
import com.rx.mvp.zyzj.entity.resp.PlanConfigEntity;
import com.rx.mvp.zyzj.entity.resp.PlanConfigGroupEntity;
import com.rx.mvp.zyzj.widget.flowLayout.FlowLayout;
import com.rx.mvp.zyzj.widget.flowLayout.TagAdapter;
import com.rx.mvp.zyzj.widget.flowLayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by huangtuo on 2018/7/27.
 */

public class SelectPlanAdapter extends ListBaseAdapter<PlanConfigGroupEntity> {

    private List<PlanConfigEntity> checkedList = new ArrayList<>();
    private SelectCheckedListener selectCheckedListener;

    public SelectPlanAdapter(Context context) {
        super(context);
    }

    public void setCheckedList(List<PlanConfigEntity> checkedList) {
        this.checkedList = checkedList;
        notifyDataSetChanged();
    }

    public void setSelectCheckedListener(SelectCheckedListener selectCheckedListener) {
        this.selectCheckedListener = selectCheckedListener;
    }

    public List<PlanConfigEntity> getCheckedList() {
        return checkedList;
    }

    @Override
    protected View getExtView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_select_plan, parent, false);
        }

        TextView tvSeries = (TextView) convertView.findViewById(R.id.selectPlan_tv_series);
        TagFlowLayout tagFlowLayout = (TagFlowLayout) convertView.findViewById(R.id.selectPlan_flowlayout_item);

        PlanConfigGroupEntity entity = getList().get(position);
        if(entity != null){
            tvSeries.setText(entity.getName());

            TagAdapter tagAdapter;
            tagFlowLayout.setAdapter(tagAdapter = new TagAdapter<PlanConfigEntity>(entity.getValues()) {
                @Override
                public View getView(FlowLayout parent, int position, PlanConfigEntity entity) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_lottery_item,
                            tagFlowLayout, false);
                    if(entity != null)
                        tv.setText(entity.getRuleName());

                    return tv;
                }
                @Override
                public boolean setSelected(int position, PlanConfigEntity s)
                {
                    for(PlanConfigEntity checkEntity : checkedList){
                        if(s.getId().equals(checkEntity.getId()))
                            return true;
                    }
                    return false;
                }
            });

//            Set<Integer> checkedIndex = new HashSet<>();
//            for(String s : checkedList){
//                if(entity.getPlanList().contains(s))
//                    checkedIndex.add(entity.getPlanList().indexOf(s));
//            }
//            tagAdapter.setSelectedList(checkedIndex);
            tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {

                }
            });
            tagFlowLayout.setOnTagClickListener((view, position2, parent2) -> {
                if(selectCheckedListener != null){
                    selectCheckedListener.selectChecked(view.isChecked(), entity.getValues().get(position2));
                }
                return false;
            });

        }

        return convertView;
    }

    public interface SelectCheckedListener{
        void selectChecked(boolean isChecked, PlanConfigEntity value);
    }
}
