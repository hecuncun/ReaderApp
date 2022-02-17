package com.hcc.readerapp.util;

import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


/**
 * 时间工具
 * <p>
 * Created by Administrator on 2016/8/24 0024.
 */
public class DateUtils {
    public static final String ElapsedFormatHHMMSS = "%1$02d:%2$02d";

    /**
     * 将日期字符串解析成毫秒值， 北京时间
     *
     * @param date    日期字符串
     * @param pattern 日期格式，如"yyyy-MM-DD"
     * @return 时间毫秒值
     */
    public static long dateToTimeMillis(String date, String pattern) {
        if (TextUtils.isEmpty(date)) {
            return 0;
        }

        long time = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            Date parse = sdf.parse(date);
            time = parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将给定时间格式化为MM:SS格式
     *
     * @param elapsedSeconds 真实时间
     * @return
     */
    public static String formatElapsedTime(long elapsedSeconds) {
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        if (elapsedSeconds >= 3600) {
            hours = elapsedSeconds / 3600;
            elapsedSeconds -= hours * 3600;
        }
        if (elapsedSeconds >= 60) {
            minutes = elapsedSeconds / 60;
            elapsedSeconds -= minutes * 60;
        }
        seconds = elapsedSeconds;

        // Create a StringBuilder if we weren'data given one to recycle.
        // TODO: if we cared, we could have a thread-local temporary StringBuilder.
        StringBuilder sb = new StringBuilder(8);


        // Format the broken-down time in a locale-appropriate way.
        // TODO: use icu4c when http://unicode.org/cldr/trac/ticket/3407 is fixed.
        Formatter f = new Formatter(sb, Locale.getDefault());
        String s = f.format(ElapsedFormatHHMMSS, minutes, seconds).toString();
        f.close();
        return s;

    }

    /**
     * 新用户判定方法
     *
     * @param registerTime 用户注册时间
     */
    public static Boolean isFreshMan(long registerTime) {

        long end = DateUtils.getEndTimeOfDay(registerTime) - registerTime;
        long sec = end + 518400000;
        return System.currentTimeMillis() < registerTime + sec;
    }

    /**
     * 检查某个时间是否在当前时间的times的间隔内，比如检查是否在2小时内
     *
     * @param timeMills 待检查的日期时间戳
     * @param times     时间间隔
     * @param timeUnit  时间单位
     * @return
     */
    public static boolean during(long timeMills, int times, TimeUnit timeUnit) {
        return System.currentTimeMillis() - timeMills < timeUnit.toMillis(times);
    }

    public static String getDateTime() {
        return formatDatetime(System.currentTimeMillis());
    }

    /**
     * 将时间格式化为字符串
     *
     * @param timeMills 毫秒数
     * @return
     */
    public static String formatDatetime(long timeMills) {
        return formatDatetime(timeMills, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取时间戳（ms），从日期中“yyyy-MM-dd”
     * @param date
     * @return
     */
    public static long parseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date parse = sdf.parse(date);
            return parse == null ? 0 : parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将时间格式化为字符串
     *
     * @param timeMills 毫秒数
     * @return
     */
    public static String formatDatetime(long timeMills, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());

        return sdf.format(new Date(timeMills));
    }

    /**
     * 将时间格式化为字符串
     *
     * @param timeMills 毫秒数
     * @return
     */
    public static String formatDatetime(long timeMills, String pattern, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);

        return sdf.format(new Date(timeMills));
    }

    /**
     * 获取 HH:mm格式的时间
     *
     * @return
     */
    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return sdf.format(new Date());
    }

    @Deprecated
    public static boolean isSameDay(String time, String lastTime, String timeFormat) {
        boolean matches = Pattern.matches("^\\d{4}-\\d{1,2}-\\d{1,2}", lastTime);
        if (TextUtils.isEmpty(lastTime) || !matches) {
            return false;
        }

        SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat, Locale.getDefault());
        try {
            Date date1 = timeFormatter.parse(time);
            Date date2 = timeFormatter.parse(lastTime);
            int compareTo = date1.compareTo(date2);
            return compareTo == 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 是否是同一天，按北京时间计算
     * @param time1 时间1
     * @param time2 时间2
     * @return
     */
    public static boolean isSameDay(long time1, long time2) {
        if (Math.abs(time1 - time2) >= 24 * 60 * 60_000) {
            return false;
        }

        Calendar date1 = Calendar.getInstance(Locale.CHINA);
        date1.setTimeInMillis(time1);
        Calendar date2 = Calendar.getInstance(Locale.CHINA);
        date2.setTimeInMillis(time2);

        int day1 = date1.get(Calendar.DAY_OF_MONTH);
        int day2 = date2.get(Calendar.DAY_OF_MONTH);

        return day1 == day2;
    }

    public static boolean beforeToday(String date, String pattern) {
        long l = dateToTimeMillis(date, pattern);
        return new Date(l).before(Calendar.getInstance().getTime());
    }

    public static boolean beforeToday(long date, String pattern) {
        return new Date(date).before(Calendar.getInstance().getTime());
    }

    /**
     * weather target is after origin or not
     *
     * @param originDate
     * @param targetDate
     * @return
     */
    public static boolean after(String originDate, String targetDate) {
        long origin = dateToTimeMillis(originDate, "yyyy-MM-dd HH:mm:ss");
        long target = dateToTimeMillis(targetDate, "yyyy-MM-dd HH:mm:ss");
        return target > origin;
    }


    /**
     * 格式化时间长度，为--:--:--格式
     *
     * @param elapsedSeconds 时间戳（s）
     * @return
     */
    public static String formatDuring(long elapsedSeconds) {
        return formatDuring(elapsedSeconds, "%1$02dD%2$02d:%3$02d:%4$02d");
    }

    /**
     * 格式化时间长度，为--:--:--格式
     *
     * @param elapsedSeconds 时间戳（s）
     * @param pattern 格式化的格式
     * @return
     */
    public static String formatDuring(long elapsedSeconds, String pattern) {
        int days = 0;
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        if (elapsedSeconds >= 86400) {
            days = (int) (elapsedSeconds / 86400);
            elapsedSeconds -= days * 86400;
        }

        if (elapsedSeconds >= 3600) {
            hours = elapsedSeconds / 3600;
            elapsedSeconds -= hours * 3600;
        }
        if (elapsedSeconds >= 60) {
            minutes = elapsedSeconds / 60;
            elapsedSeconds -= minutes * 60;
        }
        seconds = elapsedSeconds;

        StringBuilder builder = new StringBuilder(11);

        Formatter formatter = new Formatter(builder);
        Formatter format = formatter.format(pattern,  days, hours, minutes, seconds);
        String time = format.toString();
        format.close();
        return time;
    }

    /**
     * 获取指定之间那天的起始时间(北京时间)
     *
     * @param time 时间
     * @return 当天0点的时间戳
     */
    public static long getStartTimeOfDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String s = format.format(new Date(time));
        try {
            Date parse = format.parse(s);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getEndTimeOfDay(long timeMillis) {
        return getStartTimeOfDay(timeMillis + 24 * 60 * 60_000) - 1;
    }

    /**
     * 是否是今天
     * @param when time in milliseconds
     * @param locale The locale of time
     * @return
     */
    public static boolean isToday(long when, Locale locale){
        Calendar date = Calendar.getInstance(locale);
        date.setTimeInMillis(when);

        int thenYear = date.get(Calendar.YEAR);
        int thenMonth = date.get(Calendar.MONTH);
        int thenMonthDay = date.get(Calendar.DAY_OF_MONTH);

        date.setTimeInMillis(System.currentTimeMillis());
        return (thenYear == date.get(Calendar.YEAR))
                && (thenMonth == date.get(Calendar.MONTH)
                && (thenMonthDay == date.get(Calendar.DAY_OF_MONTH)));
    }

    /**
     * 是否是今天，按北京时间计算
     * @param when 时间
     * @return
     */
    public static boolean isToday(long when){
        return isToday(when, Locale.CHINA);
    }

    /**
     * 日 月 年 时分秒
     * @param timeMills 毫秒数
     * @return 20 April, 2019 18:23:22
     */
    public static String formatDatetimeEnglish(long timeMills) {
        SimpleDateFormat sdf=new SimpleDateFormat("dd MMM, yyyy HH:mm:ss",Locale.ENGLISH);
        String sd = sdf.format(new Date(timeMills));
        return sd;
    }

    /**
     * 日 月 年 时分秒
     * @param timeMills 毫秒数
     * @return 20 April, 2019 18:23:22
     */
    public static String formatDatetimeEnglish2(long timeMills) {
        SimpleDateFormat sdf=new SimpleDateFormat("MMM dd, yyyy HH:mm:ss",Locale.ENGLISH);
        String sd = sdf.format(new Date(timeMills));
        return sd;
    }

    /**
     * 日 月 年 时分
     * @param timeMills 毫秒数
     * @return 20 April, 2019 18:23
     */
    public static String formatDatetimeEnglish3(long timeMills) {
        SimpleDateFormat sdf=new SimpleDateFormat("dd MMM, yyyy HH:mm",Locale.ENGLISH);
        String sd = sdf.format(new Date(timeMills));
        return sd;
    }

    /**
     * 日 月 年
     * @param timeMills 毫秒数
     * @return 20 April, 2019
     */
    public static String formatDatetimeEnglishShort(long timeMills) {
        SimpleDateFormat sdf=new SimpleDateFormat("d MMM, yyyy",Locale.ENGLISH);
        String sd = sdf.format(new Date(timeMills));
        return sd;
    }




}
