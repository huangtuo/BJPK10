package com.rx.mvp.zyzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.ListBaseAdapter;
import com.rx.mvp.zyzj.entity.resp.HomePlanEntity;
import com.rx.mvp.zyzj.utils.PxPipConvert;

import java.util.List;

/**
 * Created by huangtuo on 2018/7/20.
 */

public class HomePlanAdapter extends ListBaseAdapter<HomePlanEntity> {
    LinearLayout.LayoutParams params;


    public HomePlanAdapter(Context context) {
        super(context);
        params = new LinearLayout.LayoutParams(
                PxPipConvert.convertDip2Px(context, 5), PxPipConvert.convertDip2Px(context, 5));
        params.setMargins(0, 0, PxPipConvert.convertDip2Px(context, 2), 0);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_plan,
                    parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.plan_tv_codeName);
        TextView tvRanking = (TextView) convertView.findViewById(R.id.plan_tv_ranking);
        TextView tvGuessRate = (TextView) convertView.findViewById(R.id.plan_tv_guessRate);
        LinearLayout llGuessRate = (LinearLayout) convertView.findViewById(R.id.plan_ll_guessRate);
        TextView tvPeriod = (TextView) convertView.findViewById(R.id.plan_tv_period);
        TextView tvIndex = (TextView) convertView.findViewById(R.id.plan_tv_index);
        ImageView imgType = (ImageView) convertView.findViewById(R.id.plan_img_type);
        TextView tvNums = (TextView) convertView.findViewById(R.id.plan_tv_num);

        HomePlanEntity entity = getList().get(position);
        if (entity != null) {
            tvName.setText(entity.getPlayType() + "-" + entity.getLotteryPlanName());
//            viewHolder.tvRanking.setText(entity.getRanking());
            tvGuessRate.setText(entity.getCorrectRatio() * 100 + "%");
            tvPeriod.setText(entity.getStartIssue() + "-" + entity.getEndIssue() + "期");
            tvIndex.setText(entity.getCorrectIndex());


            llGuessRate.removeAllViews();
            List<Boolean> guessInfo = entity.getCorrectInfos();

            for (Boolean info : guessInfo) {
                View view = new View(context);
                view.setLayoutParams(params);
                if (info) {//正确
                    view.setBackgroundResource(R.drawable.circle_green_bg);
                } else {
                    view.setBackgroundResource(R.drawable.circle_red_bg);
                }
                llGuessRate.addView(view);
            }


            List<List<String>> groupGuess = entity.getGuessResult();
            if(groupGuess != null){
                StringBuilder sb = new StringBuilder();
                for(List<String> childGuess : groupGuess){
                    for(String unit : childGuess){
                        sb.append(unit + "-");
                    }
                    //去掉最后一个 “-”
                    if(sb.length() > 1)
                        sb.setLength(sb.length() - 1);

                    sb.append(" ");
                }
                tvNums.setText(sb.toString());
            }


//            viewHolder.llNums.removeAllViews();
//            String numberStr = entity.getPlanSection();
//            if (!StringUtils.isEmpty(numberStr)) {
//                String[] nums = numberStr.split(",");
//                for (String s : nums) {
//                    TextView textView = new TextView(context);
//                    textView.setTextSize(14);
//                    textView.setTextColor(ContextCompat.getColor(context, R.color.text_color_black03));
//                    textView.setText(s);
//                    textView.setLayoutParams(params2);
//                    viewHolder.llNums.addView(textView);
//                }
//            }

        }

        return convertView;
    }

    @Override
    protected View getExtView(int position, View convertView, ViewGroup parent) {
        return null;
    }


}
