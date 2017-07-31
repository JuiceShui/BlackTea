package com.yeeyuntech.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by adu on 2017/5/20.
 * 日期相关工具类
 */

public class DateUtils {

    /**
     * 根据format格式化时间，自动识别10位和13位时间戳
     *
     * @param time   时间戳
     * @param format
     * @return
     */
    public static String format(long time, String format) {
        if (time < 9999999999L) {
            //  转换为13位时间戳
            time = time * 1000;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date(time));
    }

    /**
     * 格式化为越南时间
     *
     * @param time
     * @param format
     * @return
     */
    public static String formatVN(long time, String format) {
        if (time < 999999999999L) {
            //  转换为13位时间戳
            time = time * 1000;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        return df.format(new Date(time));
    }

    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatyMdHms(String time) {
        if (time != null && (time.length() == 10 || time.length() == 13)) {
            long t = Long.parseLong(time);
            return format(t, "yyyy-MM-dd HH:mm:ss");
        }
        return "";
    }

    public static String formatyMdHms(long time) {
        return format(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatyMdHm(long time) {
        return format(time, "yyyy-MM-dd HH:mm");
    }

    public static String formatMdHms(long time) {
        return format(time, "MM-dd HH:mm:ss");
    }


    public static String formatHmEyMd(long time) {
        return format(time, "HH:mm E yyyy-MM-dd");
    }

    public static String formatMdHm(long time) {
        return format(time, "MM-dd HH:mm");
    }

    public static String formatyMd(long time) {
        return format(time, "yyyy-MM-dd");
    }

    public static String formatyMdCh(long time) {
        String s = formatyMd(time);
        return s.substring(0, 4) + "年" + s.substring(5, 7) + "月" + s.substring(8, 10) + "日";
    }

    public static String formatyM(long time) {
        return format(time, "yyyy-MM");
    }

    public static String formatMd(long time) {
        return format(time, "MM-dd");
    }

    public static String formatHms(long time) {
        return format(time, "HH:mm:ss");
    }

    public static String formatHm(long time) {
        return format(time, "HH:mm");
    }

    public static String formatE(long time) {
        return format(time, "E");
    }


    /**
     * 越南时间
     *
     * @param time
     * @return
     */
    public static String formatMdHmsVN(long time) {
        return formatVN(time, "MM-dd HH:mm:ss");
    }

    public static String formatMdHmVN(long time) {
        return formatVN(time, "MM-dd HH:mm");
    }


    public static String getDaysOfWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String[] weekOfDays = {"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"};
        return weekOfDays[dayOfWeek];
    }


    /**
     * 越南时间
     *
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date stringToDateVN(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }


    /**
     * string类型转换为date类型
     * strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
     * HH时mm分ss秒，
     * strTime的时间格式必须要与formatType的时间格式相同
     */
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * long转换为Date类型
     * currentTime要转换的long类型的时间
     * formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     */
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = format(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    /**
     * string类型转换为long类型
     * strTime要转换的String类型的时间
     * formatType时间格式
     * strTime的时间格式和formatType的时间格式必须相同
     */
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        // String类型转成date类型
        Date date = stringToDate(strTime, formatType);
        if (date == null) {
            return 0;
        } else {
            // date类型转成long类型
            long currentTime = dateToLong(date);
            return currentTime;
        }
    }

    /**
     * 越南时间
     *
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static long stringToLongVN(String strTime, String formatType)
            throws ParseException {
        // String类型转成date类型
        Date date = stringToDateVN(strTime, formatType);
        if (date == null) {
            return 0;
        } else {
            // date类型转成long类型
            long currentTime = dateToLong(date);
            return currentTime;
        }
    }

    /**
     * date类型转换为long类型
     * date要转换的date类型的时间
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }


    public static String getTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        return dateFormat.format(date);//时间戳
    }


    public static String getTime(long time) {
        StringBuilder builder;
        builder = new StringBuilder();
        long sec = time / (3600 * 1000);
        builder.append(sec < 10 ? "0" : "").append(sec).append(":");
        time = time % (3600 * 1000);
        long min = time / (60 * 1000);
        builder.append(min < 10 ? "0" : "").append(min).append(":");
        time = time % (60 * 1000) / 1000;
        builder.append(time < 10 ? "0" : "").append(time);
        return builder.toString();
    }

    /**
     * @param time 当天已过分钟数
     * @return "11:21"
     */
    public static String getMinute(String time) {
        long t = (long) Float.parseFloat(time);
        t = t * 60000 + 57600000;
        return formatHm(new Date(t).getTime());
    }

    /**
     * 时间格式化
     *
     * @param time 连续数字表示的时间 20161023
     * @return -天-小时-分钟
     */
    public static String formatDay(long time) {
        return formatyMd(time);
    }

    public static String formatDay(String time) {
        return formatyMd(Long.parseLong(time));
    }

    public static String currentTime() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
