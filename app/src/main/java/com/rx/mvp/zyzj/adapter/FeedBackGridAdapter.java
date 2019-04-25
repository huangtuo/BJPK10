package com.rx.mvp.zyzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.ListBaseAdapter;


/**
 * Created by huangtuo on 2016/9/9.
 */

public class FeedBackGridAdapter extends ListBaseAdapter<String> {

    public FeedBackGridAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_feedback_img,
                    parent, false);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.item_grid_image);

        String path = getList().get(position);

        if("-1".equals(path)){
            Glide.with(context).load(R.mipmap.feedback_img_add)
                    .placeholder(R.mipmap.jiazai)
                    .error(R.mipmap.jiazai)
                    .crossFade()
                    .into(image);
        }else{
            Glide.with(context).load(path)
                    .placeholder(R.mipmap.jiazai)
                    .error(R.mipmap.jiazai)
                    .crossFade()
                    .into(image);
        }

        return convertView;
    }

    @Override
    protected View getExtView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
