package com.rx.mvp.zyzj.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

public class VersionUtil {

	private static final String TAG = VersionUtil.class.getSimpleName();

	/**
	 * 获取应用版本号名称
	 * 
	 * @param ctx
	 * @return
	 * @author yansu
	 */
	public static String getVersionName(Context ctx) {
		PackageManager packageManager = ctx.getPackageManager();
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
		}
		return null;
	}

	/** 包名 */
	public static String getPackgName(Context ctx) {
		PackageManager packageManager = ctx.getPackageManager();
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
			return packInfo.packageName;
		} catch (NameNotFoundException e) {
		}
		return null;
	}

	/**
	 * @MethodName: getVersionCode All rights Reserved, Designed By isoftStone
	 *              Copyright: Copyright(C) 2013 Company: isoftStone Holdings
	 *              Limited
	 * @Description 获取版本号
	 * @author: J.F.Y
	 * @return int
	 * @throws
	 * @version: V1.0
	 * @date 2013-12-30 上午11:58:06
	 */
	public static int getVersionCode(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			return packInfo.versionCode;
		} catch (NameNotFoundException e) {
		}
		return 0;

	}

	/** 获取渠道名 */
	public static String getAppMetaData(Context ctx, String key) {
		if (ctx == null || TextUtils.isEmpty(key)) {
			return null;
		}
		String resultData = null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			if (packageManager != null) {
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(ctx.getPackageName(),
								PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						resultData = applicationInfo.metaData.getString(key);
					}
				}

			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return resultData;
	}

	/**
	 * 打开安装界面
	 *
	 * @param con
	 * @param path
	 */
	public static void openInstallApk(Context con, String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.delete();
			return;
		}
		// Log.e("OpenFile", file.getName());
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		con.startActivity(intent);

	}
	//判断是否已下载
	public static boolean versionEquals(String path, String code){
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		String codeStr;
		int i = path.indexOf("ewallet");
		int ii = path.indexOf("-liuzhou.apk");
		if(i > 0 && ii > 0){
			codeStr = path.substring(i + 8,ii);
		}else{
			return false;
		}
		if(code.equals(codeStr)) {  //已下载
			return true;
		}
		return false;
	}

	/**
	 * 检查指定apk是否已经安装
	 * @param context		上下文
	 * @param packageName	apk包名
	 * @return
	 */
	public static boolean isAppInstalled(Context context,String packageName) {
		PackageManager pm = context.getPackageManager();
		boolean installed =false;
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			installed =true;
		} catch(NameNotFoundException e) {
			//捕捉到异常,说明未安装
			installed =false;
		}
		return installed;
	}
}
