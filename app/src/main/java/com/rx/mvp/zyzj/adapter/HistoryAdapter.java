package com.rx.mvp.zyzj.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.ListBaseAdapter;
import com.rx.mvp.zyzj.entity.resp.HistoryEntity;

import java.util.List;

/**
 * Created by huangtuo on 2018/7/20.
 */

public class HistoryAdapter extends ListBaseAdapter<HistoryEntity> {
    LinearLayout.LayoutParams params;
    public HistoryAdapter(Context context) {
        super(context);
        params = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
    }

    @Override
    protected View getExtView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_history, parent, false);
        }

        TextView tvPeriod = (TextView) convertView.findViewById(R.id.history_tv_period);
        TextView tvTime = (TextView) convertView.findViewById(R.id.history_tv_time);
        LinearLayout llNums = (LinearLayout) convertView.findViewById(R.id.history_ll_nums);

        HistoryEntity entity = getList().get(position);
        if(entity != null){
            tvPeriod.setText("第" + entity.getIssue() + "期");
            tvTime.setText(entity.getOpenTime());

            llNums.removeAllViews();
            List<String> nums = entity.getResults();
            if(nums != null && nums.size() > 0){
                for(String num : nums){
                    LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.history_num_unit, null, false);
                    TextView textView = (TextView) ll.findViewById(R.id.history_tv_num);
                    ll.setLayoutParams(params);
                    textView.setText(num);
                    llNums.addView(ll);
                }
            }
        }

        return convertView;
    }
}
