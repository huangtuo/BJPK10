package com.rx.mvp.zyzj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.BaseAdapter;
import com.rx.mvp.zyzj.utils.StringUtils;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class BottomSelectAdapter extends BaseAdapter<String> {

    private CheckedItemListener checkedItemListener;
    public BottomSelectAdapter(Context context) {
        super(context);
    }

    public void setCheckedItemListener(CheckedItemListener checkedItemListener) {
        this.checkedItemListener = checkedItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupsHolder(inflater.inflate(R.layout.item_bottom_select,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GroupsHolder viewHolder = (GroupsHolder) holder;
        String name = getList().get(position);
        if(!StringUtils.isEmpty(name)){
            viewHolder.tvName.setText(name);
            viewHolder.tvName.setOnClickListener(v -> {
                if(checkedItemListener != null){
                    checkedItemListener.onCheckItem(name);
                }
            });

        }
    }

    public class GroupsHolder extends RecyclerView.ViewHolder{
        private TextView tvName;

        public GroupsHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.select_tc_name);
        }
    }

    public interface CheckedItemListener{
        void onCheckItem(String entity);
    }
}
