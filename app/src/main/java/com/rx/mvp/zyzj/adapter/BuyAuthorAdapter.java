package com.rx.mvp.zyzj.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.ListBaseAdapter;
import com.rx.mvp.zyzj.entity.resp.BuyAuthorEntity;

/**
 * Created by Administrator on 2018/7/29.
 */

public class BuyAuthorAdapter extends ListBaseAdapter<BuyAuthorEntity> {
    private BuyAuthorEntity buyAuthorEntity;
    private TextPaint paint;

    public BuyAuthorAdapter(Context context) {
        super(context);
    }

    public BuyAuthorEntity getBuyAuthorEntity() {
        return buyAuthorEntity;
    }

    public void setBuyAuthorEntity(BuyAuthorEntity buyAuthorEntity) {
        this.buyAuthorEntity = buyAuthorEntity;
        notifyDataSetChanged();
    }

    @Override
    protected View getExtView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_buy_author, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.buy_tv_name);
        TextView tvSpecial = (TextView) convertView.findViewById(R.id.buy_tv_special);
        TextView tvOriginal = (TextView) convertView.findViewById(R.id.buy_tv_original);
        TextView tvDays = (TextView) convertView.findViewById(R.id.buy_tv_day);
        TextView tvBuy = (TextView) convertView.findViewById(R.id.buy_tv_buy);

        BuyAuthorEntity entity = getList().get(position);
        if (entity != null) {
            tvName.setText(entity.getGoodsName());
            tvSpecial.setText("特价¥" + entity.getGoodsPrice());
            tvOriginal.setText("原价¥" + entity.getOriginalPrice());
            tvDays.setText(entity.getGoodsTime() + "天");

            tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            if (buyAuthorEntity != null && buyAuthorEntity.getGoodsId().equals(entity.getGoodsId())) {
                tvBuy.setBackground(context.getResources().getDrawable(R.drawable.red_btn_normal_bg));
                tvBuy.setTextColor(context.getResources().getColor(R.color.color_white));
                if(entity.isEnable())
                    convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.bg_page));
                else
                    convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.text_color_black04));
            }else{
                tvBuy.setBackground(context.getResources().getDrawable(R.drawable.circle_5_hollow_red));
                tvBuy.setTextColor(context.getResources().getColor(R.color.text_color_red));
                if(entity.isEnable())
                    convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.color_white));
                else
                    convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.text_color_black04));
            }
        }

        return convertView;
    }
}
