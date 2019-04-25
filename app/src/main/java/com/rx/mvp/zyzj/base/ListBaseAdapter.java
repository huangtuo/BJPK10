/*   
* Copyright (c) 2010-2020 UCSMY All Rights Reserved.   
*   
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/     
package com.rx.mvp.zyzj.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public abstract class ListBaseAdapter<T extends Serializable> extends BaseAdapter{
	protected Context context;
	private final List<T> mList = new LinkedList<T>();
	protected LayoutInflater inflater;
	public ListBaseAdapter(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
 
	public List<T> getList() {
		return mList;
	}
	public void appendToList(List<T> list) {
		if (list == null) {
			return;
		}
		mList.addAll(list);
		notifyDataSetChanged();
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
	public int getCount() {
		// TODO Auto-generated method stub
		return mList==null?0:mList.size();
	}

	@Override
	public Object getItem(int position) {
		if(mList==null){
			return null;
		}
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getExtView(position, convertView, parent);
	}
	
	protected abstract View getExtView(int position, View convertView,
			ViewGroup parent);


}
