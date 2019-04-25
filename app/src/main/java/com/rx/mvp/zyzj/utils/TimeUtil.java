package com.rx.mvp.zyzj.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {
    /**
     * 字符串日期转换
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToDate(String str) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(str, pos);
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            return formatter2.format(strtodate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化date字符串日期到 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatStrYMd(String date) {
        try {
            if (!StringUtils.isEmpty(date)) {
                String r = "\\d{4}-\\d{1,2}-\\d{1,2}";
                Pattern p = Pattern.compile(r);
                Matcher matcher = p.matcher(date);
                if (matcher.find()) {
                    return matcher.group();
                }
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date time = df.parse(date);
                return date2StrYMd(time);
            }
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 格式化date字符串日期到 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatStrYMd2(String date) {
        try {
            if (!StringUtils.isEmpty(date)) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date time = df.parse(date);
                return date2StrYMd2(time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String date2StrYMd(Date date) {
        try {
            if (date != null) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                return df.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String date2StrYMd2(Date date) {
        try {
            if (date != null) {
                DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
                return df.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 日期转字符串日期
     *
     * @author ttarfall
     * @date 2015-7-7 下午2:51:20
     */
    public static String date2StrYMdHms(Date date) {
        try {
            if (date == null) {
                return "";
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 日期字符串转日期
     *
     * @param date
     * @return
     */
    public static Date str2Date(String date) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期字符串转日期
     *
     * @param date
     * @return
     */
    public static Date str2DateYMdHms(String date) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化日历字符到时分
     *
     * @param date
     * @return
     */
    public static String str2HM(String date) {
        try {
            Date d = str2DateYMdHms(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            return hour + ":" + (minute > 9 ? minute : "0" + minute);
        } catch (Exception e) {

        }
        return "";
    }

    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");// 设置日期格式
        return df.format(new Date());
    }

    /**
     * 日期差
     *
     * @param t1 2012-12-12
     * @param t2 2012-12-12
     * @return
     */

    public static int daysBetween(String t1, String t2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(t1);
            Date date2 = sdf.parse(t2);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(date2);
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (Exception e) {
        }
        return 0;

    }

    public static String getHMSDate(String date) {
        if (!StringUtils.isEmpty(date)) {
            String r = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";
            Pattern p = Pattern.compile(r);
            Matcher matcher = p.matcher(date);
            if (matcher.find()) {
                return matcher.group();
            }
        }
        return "";
    }

    /**
     * 获取日历
     *
     * @param date 字符串包含格式是2015-05-05的日期
     * @return 返回值类型 2015-05-05
     */
    public static String getDate(String date) {
        if (!StringUtils.isEmpty(date)) {
            String r = "\\d{4}-\\d{2}-\\d{2}";
            Pattern p = Pattern.compile(r);
            Matcher matcher = p.matcher(date);
            if (matcher.find()) {
                return matcher.group();
            }
        }
        return "";
    }

    /**
     * 获取时间
     *
     * @param time 字符串包含格式15:22:33的时间
     * @return 返回值类型 15:22:33
     */
    public static String getTime(String time) {
        if (!StringUtils.isEmpty(time)) {
            String r = "\\d{2}:\\d{2}:\\d{2}";
            Pattern p = Pattern.compile(r);
            Matcher matcher = p.matcher(time);
            if (matcher.find()) {
                return matcher.group();
            }
        }
        return "";
    }

    /**
     * 判断距离开售时间是否小于1天，如果小于返回true，否则返回false
     *
     * @param bidTime
     * @return
     */
    public static boolean isOneDay(String bidTime) {
        if (!StringUtils.isEmpty(bidTime)) {
            Date data = str2DateYMdHms(bidTime);
            long time = getCurrentDate(data);
            if (data != null && time != 0) {
                long currentTime = System.currentTimeMillis();
                return currentTime >= time && currentTime < data.getTime();
            }
        }
        return false;
    }

    /**
     * 返回当前日期的0时，0分，0秒的时间
     *
     * @return
     */
    public static long getCurrentDate(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DATE);
            calendar.set(year, month, day, 0, 0, 0);
            return calendar.getTimeInMillis();
        }
        return 0;
    }

    public static boolean isNextDay(long historyTime, long currentTime) {
        if (historyTime > 0 && currentTime > historyTime) {
            try {
                Calendar cal = Calendar.getInstance();
                Date time = new Date(historyTime);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date = df.format(time);
                cal.setTime(df.parse(date));
                cal.add(Calendar.DAY_OF_MONTH, 1);
                long nextTime = cal.getTimeInMillis();
                return currentTime >= nextTime;
            } catch (Exception e) {

            }
            return false;
        } else {
            return false;
        }
    }

}
