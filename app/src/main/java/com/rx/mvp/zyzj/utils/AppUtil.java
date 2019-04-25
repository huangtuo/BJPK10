/**
 * 
 */
package com.rx.mvp.zyzj.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class AppUtil {

	/**
	 * 获取屏幕分辨率
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
		int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
		int result[] = { width, height };
		return result;
	}

	/**
	 * 获取应用包名
	 *
	 * @param context
	 * @return
	 */
	public static String getPackageName(Context context) {
		if (context == null) {
			return "";
		}
		try {
			String pkName = context.getPackageName();
			return pkName;
		} catch (Exception e) {
		}
		return null;
	}
	private static DisplayMetrics displayMetrics = new DisplayMetrics();

	public static int px2dip(float pxValue) {
		return (int) (pxValue / displayMetrics.density + 0.5f);
	}

	public static int dip2px(float dipValue,Context context) {
		return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
	}
}
