package com.rx.mvp.zyzj.utils;

import android.content.Context;

/**
 * 像素和dip转换
 * 
 * @author wangshutao 2014 下午2:52:51
 */
public class PxPipConvert {

	public static int convertDip2Px(Context context, int dip) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}

	public static int convertPx2Dip(Context context, int px) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue

	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue

	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

}
