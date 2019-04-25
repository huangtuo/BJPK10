package com.rx.mvp.zyzj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.BaseAdapter;
import com.rx.mvp.zyzj.entity.resp.MyFeedbackEntity;

/**
 * Created by huangtuo on 2018/7/30.
 */

public class MyFeedbackAdapter extends BaseAdapter<MyFeedbackEntity> {
    private OnItemClickLinstener onItemClickLinstener;

    public MyFeedbackAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickLinstener(OnItemClickLinstener onItemClickLinstener) {
        this.onItemClickLinstener = onItemClickLinstener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new FeedbackHolder(inflater.inflate(R.layout.item_my_feedback, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        FeedbackHolder holder = (FeedbackHolder) viewHolder;
        holder.setPos(position);
        MyFeedbackEntity entity = getList().get(position);
        if(entity != null){
            holder.tvTitle.setText(entity.getTopic());
            holder.tvTime.setText(entity.getCreateTime());
            holder.tvContent.setText(entity.getBreviaryContent());
            if("1".equals(entity.getStatus())){
                holder.tvState.setText("已回复");
                holder.tvState.setTextColor(context.getResources().getColor(R.color.text_color_green));
            }else{
                holder.tvState.setText("未回复");
                holder.tvState.setTextColor(context.getResources().getColor(R.color.text_color_red));
            }

        }
    }

    class FeedbackHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvState;
        TextView tvTime;
        TextView tvContent;
        public FeedbackHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.feedback_tv_title);
            tvState = (TextView) itemView.findViewById(R.id.feedback_tv_state);
            tvTime = (TextView) itemView.findViewById(R.id.feedback_tv_time);
            tvContent = (TextView) itemView.findViewById(R.id.feedback_tv_content);

            itemView.setOnClickListener(v -> {
                if(onItemClickLinstener != null)
                    onItemClickLinstener.onItemClick(getList().get(pos));
            });
        }

        private int pos;

        public void setPos(int pos) {
            this.pos = pos;
        }
    }

    public interface OnItemClickLinstener{
        void onItemClick(MyFeedbackEntity entity);
    }
}
