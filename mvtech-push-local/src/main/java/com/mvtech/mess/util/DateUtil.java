package com.mvtech.mess.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 日期类
 * @author: sunsf
 * @date 2020-5-15
 */
public class DateUtil {
    public static final String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYYMMDDHHMMSS_FULL = "yyyyMMddHHmmss";

    public static void main(String[] args) {
    }

    /**
     * 获取时间增加分钟数后的日期
     *
     * @param oldDate   要修改的日期
     * @param addMinute 增加的分钟数
     * @return: 日期
     * @author: sunsf
     * @date: 2020/1/10 10:04
     */
    public static Date addMinute(Date oldDate, Integer addMinute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.MINUTE, addMinute);
        return calendar.getTime();
    }

    /**
     * @return 昨天日期yyyyMMdd
     */
    public static String yesterday() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    /**
     * @param date   传入的时间
     * @param format 返回字符串格式
     * @Description: 将时间类型转换为字符串
     * @return: 日期字符串
     * @author: sunsf
     * @date: 2020/1/10 9:55
     */
    public static String date2Str(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * @param dateStr 传入的字符串
     * @param format  对应的格式
     * @Description: 将字符串转换时间
     * @return: 日期
     * @author: sunsf
     * @date: 2020/1/10 9:56
     */
    public static Date str2Date(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);

        }
    }

    /**
     * @param date         需要转换的日期
     * @param formatBefore 转换之前日期的格式
     * @Description: 转换日期我字符串
     * @param: formatAfter  转换之后日期的格式
     * @return:
     * @author: sunsf
     * @date: 2020/1/10 9:56
     */
    public static String str2Str(String date, String formatBefore, String formatAfter) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatBefore);
        try {
            Date tmpDate = sdf.parse(date);
            sdf = new SimpleDateFormat(formatAfter);
            return sdf.format(tmpDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);

        }
    }

    /**
     * @param format 要转换的格式
     * @Description: 转换当前日期为字符串
     * @return: 日期字符串
     * @author: sunsf
     * @date: 2020/1/10 10:00
     */
    public static String getDate(String format) {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * 格式当前日期展示
     *
     * @param date
     * @param formatStyle
     * @return:
     * @author: sunsf
     * @date: 2020/3/6 10:55
     */
    public static Date formatDate(Date date, String formatStyle) {
        if (date != null) {
            SimpleDateFormat f = new SimpleDateFormat(formatStyle);
            System.out.println(f.format(date));
        }
        return date;
    }

    /**
     * 获取字符串的时间time
     *
     * @param
     * @return:
     * @author: sunsf
     * @date: 2020/5/15 10:27
     */
    public static long stringToLong(String time, String formatType) {
        Date date = str2Date(time, formatType);
        if (date == null) {
            return 0;
        } else {
            return date.getTime();
        }
    }

    /**
     * 日期addDay后天数
     *
     * @param oldDateStr 旧日期天数
     * @param addDay     添加的天数
     * @param dateFormat 格式类型
     * @return:
     * @author: sunsf
     * @date: 2020/5/19 10:07
     */
    public static String addDay(String oldDateStr, Integer addDay, String dateFormat) {
        Date oldDate = DateUtil.str2Date(oldDateStr, dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.DAY_OF_MONTH, addDay);
        Date time = calendar.getTime();
        return DateUtil.date2Str(time, dateFormat);
    }




}

