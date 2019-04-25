package com.rx.mvp.zyzj.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

@SuppressWarnings("static-access")
/**
 * @author fengyh
 *	保存数据的工具类--SharedPreferences
 */
public class SPUtils {

    /**
     * @param context
     * @param name    SharedPreferences的name
     * @param key     保存的key
     * @param value   保存的value
     */
    public static void putBoolean(Context context, String name, String key,
                                  boolean value) {

        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String name, String key) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    /**
     * @param context
     * @param name    SharedPreferences的name
     * @param key     保存的key
     * @param value   保存的value
     */
    public static void putString(Context context, String name, String key,
                                 String value) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String name, String key) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    /**
     * @param context
     * @param name    SharedPreferences的name
     * @param key     保存的key
     * @param value   保存的value
     */
    public static void putFloat(Context context, String name, String key,
                                Float value) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static Float getFloat(Context context, String name, String key) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        return preferences.getFloat(key, 0f);
    }

    /**
     * @param context
     * @param name    SharedPreferences的name
     * @param key     保存的key
     * @param value   保存的value
     */
    public static void putInt(Context context, String name, String key,
                              int value) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static int getInt(Context context, String name, String key) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public static int getInt(Context context, String name, String key,int value) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        return preferences.getInt(key, value);
    }

    /**
     * @param context
     * @param name    SharedPreferences的name
     * @param key     保存的key
     * @param value   保存的value
     */
    public static void putLong(Context context, String name, String key,
                               Long value) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static Long getLong(Context context, String name, String key) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    /**
     * @param context
     * @param name    SharedPreferences的name
     * @param key     保存的key
     *                保存的value
     */
    @SuppressLint("NewApi")
    public static void putStringSet(Context context, String name, String key,
                                    Set<String> values) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, values);
        editor.commit();
    }

    @SuppressLint("NewApi")
    public static Set<String> getStringSet(Context context, String name,
                                           String key) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        return preferences.getStringSet(key, null);
    }


    public static void putObject(Context context, String filename, Object object) {
        SharedPreferences preferences = context.getSharedPreferences(filename,
                context.MODE_PRIVATE);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            String base = Base64.encodeToString(baos.toByteArray(), 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(filename, base);
            editor.commit();
            oos.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanObject(Context context, String filename) {
        SharedPreferences preferences = context.getSharedPreferences(filename,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static Object getObject(Context context, String filename) {
        Object object = null;
        try {
            SharedPreferences mSharedPreferences = context
                    .getSharedPreferences(filename, context.MODE_PRIVATE);
            String base = mSharedPreferences.getString(filename, "");
            if (base != null && base.length() > 0) {
                byte[] base64Bytes = Base64.decode(base.getBytes(), 0);
                ByteArrayInputStream bais = new ByteArrayInputStream(
                        base64Bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                object = ois.readObject();
                ois.close();
                bais.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    public static boolean isHasString(Context context, String filename) {
        SharedPreferences mSharedPreferences = context
                .getSharedPreferences(filename, context.MODE_PRIVATE);
        String base = mSharedPreferences.getString(filename, "");
        if (base != null && base.length() > 0) {
            return true;
        }
        return false;
    }

}
