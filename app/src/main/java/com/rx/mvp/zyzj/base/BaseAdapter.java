package com.rx.mvp.zyzj.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mList = new LinkedList<>();
    protected  Context context;
    protected LayoutInflater inflater ;
    public BaseAdapter(Context context) {
        super();
        this.context = context ;
        inflater = LayoutInflater.from(context);
    }

    public void  appendToList(List<T> list){
        if(list!=null&&list.size()>0){
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    

    /**
     *
     * @param list
     */
    public void appendToTopList(List<T> list) {
        if (list == null) {
            return;
        }
        appendToList(list, 0);
        notifyDataSetChanged();
    }

    public void appendToList(List<T> list, int postion) {
        if (postion < 0 || postion > list.size()) {
            throw new RuntimeException("illegal postion");
        }
        mList.addAll(postion, list);
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public List<T> getList(){
        if(mList!=null){
            return  mList;
        }
        return  null;
    }
}
