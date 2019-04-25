package com.rx.mvp.zyzj.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;

import com.rx.mvp.zyzj.base.ListBaseAdapter;
import com.rx.mvp.zyzj.entity.resp.CorrectInfoEntity;
import com.rx.mvp.zyzj.entity.resp.PlanDetailsGuessEntity2;
import com.rx.mvp.zyzj.utils.PxPipConvert;

import java.util.List;

/**
 * Created by huangtuo on 2018/7/20.
 */

public class PlanDetailsAdapter extends ListBaseAdapter<PlanDetailsGuessEntity2> {

    LinearLayout.LayoutParams params;

    public PlanDetailsAdapter(Context context) {
        super(context);
        params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, PxPipConvert.convertDip2Px(context, 5), 0);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_plan_detials, parent, false);
        }
        TextView tvPeriodArea = (TextView) convertView.findViewById(R.id.planDetails_tv_periodArea);
        TextView tvGuess = (TextView) convertView.findViewById(R.id.planDetails_tv_guess);
        TextView tvState = (TextView) convertView.findViewById(R.id.planDetails_tv_state);
        TextView tvExpectPeriod = (TextView) convertView.findViewById(R.id.planDetails_tv_expectPeriod);
        TextView tvExpectIndex = (TextView) convertView.findViewById(R.id.planDetails_tv_expectIndex);
        TextView tvOpenNums = (TextView) convertView.findViewById(R.id.planDetails_tv_openNums);

        PlanDetailsGuessEntity2 entity = getList().get(position);
        if (entity != null) {
            tvPeriodArea.setText(entity.getStartIssue() + "-" + entity.getEndIssue() + "期");
            //预期数字
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
                tvGuess.setText(sb.toString());
            }

            //对错
            CorrectInfoEntity correctInfoEntity = entity.getCorrectInfo();
            if(correctInfoEntity != null){
                if(correctInfoEntity.isCorrect()){
                    tvState.setTextColor(ContextCompat.getColor(context,R.color.text_color_green));
                }else{
                    tvState.setTextColor(ContextCompat.getColor(context,R.color.text_color_red));
                }
                tvState.setText(correctInfoEntity.getDes());
            }
            //开奖数字
            StringBuilder numStr = new StringBuilder();
            if(entity.getCorrectIssueBalls() != null){
                for(String num : entity.getCorrectIssueBalls()){
                    numStr.append(num + " ");
                }
                tvOpenNums.setText(numStr.toString());
            }


            tvExpectPeriod.setText(entity.getCorrectIssue() + "期");
            tvExpectIndex.setText(entity.getCorrectIndex());

        }

        return convertView;
    }

    @Override
    protected View getExtView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
