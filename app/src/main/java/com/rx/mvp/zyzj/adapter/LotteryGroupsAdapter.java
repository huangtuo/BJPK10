package com.rx.mvp.zyzj.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.BaseAdapter;
import com.rx.mvp.zyzj.entity.resp.LotteryGoupsEntity;
import com.rx.mvp.zyzj.entity.resp.LotteryGoupsItemEntity;
import com.rx.mvp.zyzj.widget.flowLayout.FlowLayout;
import com.rx.mvp.zyzj.widget.flowLayout.TagAdapter;
import com.rx.mvp.zyzj.widget.flowLayout.TagFlowLayout;

import java.util.List;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class LotteryGroupsAdapter extends BaseAdapter<LotteryGoupsEntity> {

    private LotteryGoupsItemEntity currentEntity;

    private CheckedItemListener checkedItemListener;
    public LotteryGroupsAdapter(Context context, LotteryGoupsItemEntity currentEntity) {
        super(context);
        this.currentEntity = currentEntity;
    }

    public void setCheckedItemListener(CheckedItemListener checkedItemListener) {
        this.checkedItemListener = checkedItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupsHolder(inflater.inflate(R.layout.item_lottery_groups,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GroupsHolder viewHolder = (GroupsHolder) holder;
        LotteryGoupsEntity datas = getList().get(position);
        if(datas != null){
            List<LotteryGoupsItemEntity> items = datas.getCPNames();
            if(items != null && items.size() > 0){
                viewHolder.tagFlowLayout.setAdapter(new TagAdapter<LotteryGoupsItemEntity>(items) {
                    @Override
                    public View getView(FlowLayout parent, int position, final LotteryGoupsItemEntity entity) {
                        TextView tv = (TextView) inflater.inflate(R.layout.item_lottery_item,
                                viewHolder.tagFlowLayout, false);
                        tv.setText(entity.getCPName());

                        if(currentEntity != null){
                            if(currentEntity.getSID() == entity.getSID()){
//                                tv.setBackground(context.getDrawable(R.drawable.checked_bg));
                                tv.setTextColor(ContextCompat.getColor(context, R.color.text_color_black03));
                            }
                        }

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(checkedItemListener != null){
                                    checkedItemListener.onCheckItem(entity);
                                }
                            }
                        });
                        return tv;
                    }
                });
                viewHolder.tagFlowLayout.setOnTagClickListener((view, position2, parent) ->{
                        if(checkedItemListener != null){
                            checkedItemListener.onCheckItem(items.get(position2));
                        }
                        return false;
                });
            }
        }
    }

    public class GroupsHolder extends RecyclerView.ViewHolder{
        private TagFlowLayout tagFlowLayout;

        public GroupsHolder(View itemView) {
            super(itemView);
            tagFlowLayout = (TagFlowLayout) itemView.findViewById(R.id.item_flowlayout);
        }
    }

    public interface CheckedItemListener{
        void onCheckItem(LotteryGoupsItemEntity entity);
    }
}
