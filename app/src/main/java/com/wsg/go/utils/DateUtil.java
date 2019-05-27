package com.wsg.go.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-28 09:46
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DateUtil {

    public DateUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static final SimpleDateFormat YYYYMMDD_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat HHMMSS_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat YYYYMMDDHHMMSS_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static final String[] CHINESE_ZODIAC = new String[]{"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    private static final String[] ZODIAC = new String[]{"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};

    private static final int[] ZODIAC_FLAGS = new int[]{20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};

    /**
     * 获取北京时区
     * @return
     */
    public static TimeZone getBeijingTimeZone(){
        return TimeZone.getTimeZone("GMT+8:00");
    }

    /**
     * 获取当前手机对应的系统时区
     *
     */
    public static TimeZone getPhoneTimeZone()
    {
        return TimeZone.getDefault();
    }

    /**
     * 以“GMT+8：00”形式返回当前系统对应的时区
     * @return
     */
    public static String getCurrentTimeZoneStr()
    {
        TimeZone tz = TimeZone.getDefault();
        return createGmtOffsetString(true,true,tz.getRawOffset());
    }

    public static String createGmtOffsetString(boolean includeGmt,
                                               boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }

    /**
     * 获取更改时区后的时间
     * @param date 时间
     * @param oldZone 旧时区
     * @param newZone 新时区
     * @return 时间
     */
    public static Date changeTimeZone(Date date, TimeZone oldZone, TimeZone newZone)
    {
        Date dateTmp = null;
        if (date != null)
        {
            int timeOffset = oldZone.getRawOffset() - newZone.getRawOffset();
            dateTmp = new Date(date.getTime() - timeOffset);
        }
        return dateTmp;
    }

    /**
     * 将北京时区的时间转化为当前系统对应时区的时间
     * @param beijingTime
     * @param format
     * @return
     */
    public static String beijingTime2PhoneTime(String beijingTime,String format){
        Date beijingDate = parseToDate(beijingTime, format);
        Date phoneDate = changeTimeZone(beijingDate, getBeijingTimeZone(), getPhoneTimeZone());
        String phoneTime= formatDateToStr(phoneDate,format);
        return phoneTime;
    }

    /**
     * 将日期字符串转换为Date对象
     * @param date 日期字符串，必须为"yyyy-MM-dd HH:mm:ss"
     * @param format 格式化字符串
     * @return 日期字符串的Date对象表达形式
     * */
    public static Date parseToDate(String date, String format)
    {
        Date dt = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try
        {
            dt = dateFormat.parse(date);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }

        return dt;
    }

    /**
     * 将date----->String
     * 将Date对象转换为指定格式的字符串
     * @param date Date对象
     * @param //String format 格式化字符串
     * @return Date对象的字符串表达形式
     * */
    public static String formatDateToStr(Date date, String format)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /** 格式化日期的标准字符串 */
    public final static String Detail_Format = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将本地系统对应时区的时间转换为上北京时区对应的时间
     * @param phoneTime
     * @return
     */
    public static String phoneTime2BeijingTime(String phoneTime){
        Date phoneDate = parseToDate(phoneTime, Detail_Format);
        Date beijingDate = changeTimeZone(phoneDate,getPhoneTimeZone(), getBeijingTimeZone());
        String beijingTime= formatDateToStr(beijingDate,Detail_Format);
        return beijingTime;
    }



    /**
     * 当天的年月日
     * @return
     */
    public static String todayYyyyMmDd() {
        return YYYYMMDD_FORMAT.format(new Date());
    }

    /**
     * 当天的时分秒
     * @return
     */
    public static String todayHhMmSs() {
        return HHMMSS_FORMAT.format(new Date());
    }

    /**
     * 当天的年月日时分秒
     * @return
     */
    public static String todayYyyyMmDdHhMmSs() {
        return YYYYMMDDHHMMSS_FORMAT.format(new Date());
    }

    /**
     * 获取年
     * @param dateTime
     * @return
     */
    public static int parseYyyy(String dateTime) {
        try {
            Calendar e = Calendar.getInstance();
            Date date = YYYYMMDDHHMMSS_FORMAT.parse(dateTime);
            e.setTime(date);
            return e.get(Calendar.YEAR);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取年
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static int parseYyyy(String dateTime, SimpleDateFormat simpleDateFormat) {
        try {
            Calendar e = Calendar.getInstance();
            Date date = simpleDateFormat.parse(dateTime);
            e.setTime(date);
            return e.get(Calendar.YEAR);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取年
     * @param date
     * @return
     */
    public static int parseYyyy(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取月
     * @param dateTime
     * @return
     */
    public static int parseMm(String dateTime) {
        try {
            Calendar e = Calendar.getInstance();
            Date date = YYYYMMDDHHMMSS_FORMAT.parse(dateTime);
            e.setTime(date);
            return e.get(Calendar.MONTH);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取月
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static int parseMm(String dateTime, SimpleDateFormat simpleDateFormat) {
        try {
            Calendar e = Calendar.getInstance();
            Date date = simpleDateFormat.parse(dateTime);
            e.setTime(date);
            return e.get(Calendar.MONTH);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取月
     * @param date
     * @return
     */
    public static int parseMm(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取日
     * @param dateTime
     * @return
     */
    public static int parseDd(String dateTime) {
        try {
            Calendar e = Calendar.getInstance();
            Date date = YYYYMMDDHHMMSS_FORMAT.parse(dateTime);
            e.setTime(date);
            return e.get(Calendar.MONTH);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取日
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static int parseDd(String dateTime, SimpleDateFormat simpleDateFormat) {
        try {
            Calendar e = Calendar.getInstance();
            Date date = simpleDateFormat.parse(dateTime);
            e.setTime(date);
            return e.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取日
     * @param date
     * @return
     */
    public static int parseDd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(5);
    }

    /**
     * 获取年月日
     * @param dateTime
     * @return
     */
    public static String parseYyyyMmDd(String dateTime) {
        String result = "";

        try {
            Date e = YYYYMMDDHHMMSS_FORMAT.parse(dateTime);
            result = YYYYMMDD_FORMAT.format(e);
        } catch (ParseException var3) {
            var3.printStackTrace();
        }

        return result;
    }

    /**
     * 获取年月日
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static String parseYyyyMmDd(String dateTime, SimpleDateFormat simpleDateFormat) {
        String result = "";

        try {
            Date e = simpleDateFormat.parse(dateTime);
            result = YYYYMMDD_FORMAT.format(e);
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        return result;
    }

    /**
     * 获取年月日
     * @param date
     * @return
     */
    public static String parseYyyyMmDd(Date date) {
        return YYYYMMDD_FORMAT.format(date);
    }

    /**
     * 时分秒
     * @param dateTime
     * @return
     */
    public static String parseHhMmSs(String dateTime) {
        try {
            Date e = YYYYMMDDHHMMSS_FORMAT.parse(dateTime);
            return HHMMSS_FORMAT.format(e);
        } catch (ParseException var2) {
            var2.printStackTrace();
            return "";
        }
    }

    /**
     * 时分秒
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static String parseHhMmSs(String dateTime, SimpleDateFormat simpleDateFormat) {
        try {
            Date e = simpleDateFormat.parse(dateTime);
            return HHMMSS_FORMAT.format(e);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return "";
        }
    }

    /**
     * 时分秒
     * @param date
     * @return
     */
    public static String parseHhMmSs(Date date) {
        return HHMMSS_FORMAT.format(date);
    }

    /**
     * 获取星期几
     * @param dateTime
     * @return
     */
    public static int getWeekNumber(String dateTime) {
        return getWeekNumber(string2Date(dateTime, YYYYMMDDHHMMSS_FORMAT));
    }

    /**
     * 获取星期几
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static int getWeekNumber(String dateTime, SimpleDateFormat simpleDateFormat) {
        return getWeekNumber(string2Date(dateTime, simpleDateFormat));
    }

    /**
     * 获取星期几
     * @param date
     * @return
     */
    public static int getWeekNumber(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(7);
    }

    /**
     * 日期中某个月份的第几周
     * @param dateTime
     * @return
     */
    public static int getWeekOfMonth(String dateTime) {
        return getWeekOfMonth(string2Date(dateTime, YYYYMMDDHHMMSS_FORMAT));
    }

    /**
     * 日期中某个月份的第几周
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static int getWeekOfMonth(String dateTime, SimpleDateFormat simpleDateFormat) {
        return getWeekOfMonth(string2Date(dateTime, simpleDateFormat));
    }

    /**
     * 日期中某个月份的第几周
     * @param date
     * @return
     */
    public static int getWeekOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(4);
    }

    /**
     * 日期中某个年份的第几周
     * @param time
     * @return
     */
    public static int getWeekOfYear(String time) {
        return getWeekOfYear(string2Date(time, YYYYMMDDHHMMSS_FORMAT));
    }

    /**
     * 日期中某个年份的第几周
     * @param time
     * @param simpleDateFormat
     * @return
     */
    public static int getWeekOfYear(String time, SimpleDateFormat simpleDateFormat) {
        return getWeekOfYear(string2Date(time, simpleDateFormat));
    }

    /**
     * 日期中某个年份的第几周
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(3);
    }

    /**
     * 将年月日时分秒转成Long类型
     * @param dateTime
     * @return
     */
    public static Long dateTimeToTimeStamp(String dateTime) {
        try {
            Date e = YYYYMMDDHHMMSS_FORMAT.parse(dateTime);
            return Long.valueOf(e.getTime() / 1000L);
        } catch (ParseException var2) {
            var2.printStackTrace();
            return Long.valueOf(0L);
        }
    }

    /**
     * 将Long类型转成年月日时分秒
     * @param timeStamp
     * @return
     */
    public static String timeStampToDateTime(Long timeStamp) {
        return YYYYMMDDHHMMSS_FORMAT.format(new Date(timeStamp.longValue() * 1000L));
    }

    /**
     * 将年月日时分秒转成Date类型
     * @param time
     * @return
     */
    public static Date string2Date(String time) {
        return string2Date(time, YYYYMMDDHHMMSS_FORMAT);
    }

    /**
     * 将年月日时分秒转成Date类型
     * @param time
     * @param simpleDateFormat
     * @return
     */
    public static Date string2Date(String time, SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    /**
     * 将Date类型转成年月日时分秒
     * @param date
     * @return
     */
    public static String date2String(Date date) {
        return date2String(date, YYYYMMDDHHMMSS_FORMAT);
    }

    /**
     * 将Date类型转成年月日时分秒
     * @param date
     * @param simpleDateFormat
     * @return
     */
    public static String date2String(Date date, SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(date);
    }

    /**
     * 比较日期
     * @param standDate
     * @param desDate
     * @return
     */
    public static boolean dateIsBefore(String standDate, String desDate) {
        try {
            return YYYYMMDDHHMMSS_FORMAT.parse(desDate).before(YYYYMMDDHHMMSS_FORMAT.parse(standDate));
        } catch (ParseException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    /**
     * 相差多少分钟
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long minutesBetweenTwoDate(String beginDate, String endDate) {
        long millisBegin = dateTimeToTimeStamp(beginDate).longValue();
        long millisEnd = dateTimeToTimeStamp(endDate).longValue();
        return (millisEnd - millisBegin) / 60L;
    }

    /**
     * 获取日期中的生肖
     * @param dateTime
     * @return
     */
    public static String getChineseZodiac(String dateTime) {
        int yyyy = parseYyyy(dateTime);
        return getChineseZodiac(yyyy);
    }

    /**
     * 获取日期中的生肖
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static String getChineseZodiac(String dateTime, SimpleDateFormat simpleDateFormat) {
        int yyyy = parseYyyy(dateTime, simpleDateFormat);
        return getChineseZodiac(yyyy);
    }

    /**
     * 获取日期中的生肖
     * @param date
     * @return
     */
    public static String getChineseZodiac(Date date) {
        int yyyy = parseYyyy(date);
        return getChineseZodiac(yyyy);
    }

    /**
     * 获取日期中的生肖
     * @param year
     * @return
     */
    public static String getChineseZodiac(int year) {
        return CHINESE_ZODIAC[year % 12];
    }

    /**
     * 获取日期中的星座
     * @param dateTime
     * @return
     */
    public static String getZodiac(String dateTime) {
        int dd = parseDd(dateTime);
        int month = parseMm(dateTime);
        return getZodiac(month, dd);
    }

    /**
     * 获取日期中的星座
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static String getZodiac(String dateTime, SimpleDateFormat simpleDateFormat) {
        int dd = parseDd(dateTime, simpleDateFormat);
        int month = parseMm(dateTime, simpleDateFormat);
        return getZodiac(month, dd);
    }

    /**
     * 获取日期中的星座
     * @param date
     * @return
     */
    public static String getZodiac(Date date) {
        int dd = parseDd(date);
        int month = parseMm(date);
        return getZodiac(month, dd);
    }

    /**
     * 获取日期中的星座
     * @param month
     * @param day
     * @return
     */
    public static String getZodiac(int month, int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]?month - 1:(month + 10) % 12];
    }

    /**
     * 获取日期
     *
     * @param offset 表示偏移天数
     * @return
     */
    public String getNowDayOffset(int offset) {
        Calendar m_Calendar = Calendar.getInstance();
        long time = (long) m_Calendar.getTimeInMillis();
        time = time + offset * 24 * 3600 * 1000;
        Date myDate = new Date(time);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(myDate);
    }

    /**
     * 获取日期
     *
     * @param
     * @return
     */
    public String getTime(long time) {
        Date myDate = new Date(time);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return df.format(myDate);
    }

    /**
     * 使指定日期向前走一天，变成“明天”的日期
     *
     * @param cal 等处理日期
     */
    public void forward(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);//0到11
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int days = getDaysOfMonth(year, month + 1);
        if (day == days) {//如果是本月最后一天，还要判断年份是不是要向前滚
            if (month == 11) {//如果是12月份，年份要向前滚
                cal.roll(Calendar.YEAR, true);
                cal.set(Calendar.MONTH, 0);//月份，第一月是0
                cal.set(Calendar.DAY_OF_MONTH, 1);

            } else {//如果不是12月份
                cal.roll(Calendar.MONTH, true);
                cal.set(Calendar.DAY_OF_MONTH, 1);
            }

        } else {
            cal.roll(Calendar.DAY_OF_MONTH, 1);//如果是月内，直接天数加1
        }
    }

    /**
     * 使日期倒一天
     *
     * @param cal
     */
    public void backward(Calendar cal) {
        //计算上一月有多少天
        int month = cal.get(Calendar.MONTH);//0到11
        int year = cal.get(Calendar.YEAR);
        int days = getDaysOfMonth(year, month);//上个月的天数
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1) {//如果是本月第一天，倒回上一月
            if (month == 0) {//如果是本年第一个月，年份倒一天
                cal.roll(Calendar.YEAR, false);
                cal.set(Calendar.MONTH, 11);//去年最后一个月是12月
                cal.set(Calendar.DAY_OF_MONTH, 31);//12月最后一天总是31号
            } else {//月份向前
                cal.roll(Calendar.MONTH, false);
                cal.set(Calendar.DAY_OF_MONTH, days);//上个月最后一天
            }
        } else {
            cal.roll(Calendar.DAY_OF_MONTH, false);//如果是月内，日期倒一天
        }
    }

    /**
     * 判断平年闰年
     *
     * @param year
     * @return true表示闰年，false表示平年
     */
    public boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        } else if (year % 100 != 0 && year % 4 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 计算某月的天数
     *
     * @param year
     * @param month 现实生活中的月份，不是系统存储的月份，从1到12
     * @return
     */

    public int getDaysOfMonth(int year, int month) {
        if (month < 1 || month > 12) {
            return 0;
        }
        boolean isLeapYear = isLeapYear(year);
        int daysOfMonth = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                daysOfMonth = 30;
                break;
            case 2:
                if (isLeapYear) {
                    daysOfMonth = 29;
                } else {
                    daysOfMonth = 28;
                }

        }
        return daysOfMonth;
    }

    /**
     * 获取当天凌晨的秒数
     *
     * @return
     */
    public long secondsMorning(Calendar c) {
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return tempCalendar.getTimeInMillis();
    }

    /**
     * 获取第二天凌晨的秒数
     *
     * @return
     */
    public long secondsNight(Calendar c) {
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        forward(tempCalendar);
        return tempCalendar.getTimeInMillis();
    }

    /**
     * 判断某两天是不是同一天
     *
     * @param c1
     * @param c2
     * @return
     */
    public boolean isSameDay(Calendar c1, Calendar c2) {

        if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
            return false;
        if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH))
            return false;
        if (c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH))
            return false;
        return true;
    }

    /** 日期格式：yyyy-MM-dd HH:mm:ss **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /** 日期格式：yyyy-MM-dd HH:mm **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /** 日期格式：yyyy-MM-dd **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";

    /** 日期格式：HH:mm:ss **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";

    /** 日期格式：HH:mm **/
    public static final String DF_HH_MM = "HH:mm";

    private final static long MINUTE = 60 * 1000;// 1分钟
    private final static long HOUR = 60 * MINUTE;// 1小时
    private final static long DAY = 24 * HOUR;// 1天
    private final static long MONTH = 31 * DAY;// 月
    private final static long YEAR = 12 * MONTH;// 年

    /** Log输出标识 **/
    private static final String TAG = DateUtil.class.getSimpleName();

    /**
     * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
     *
     * @param date
     * @return
     */
    public static String formatFriendly(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > YEAR) {
            r = (diff / YEAR);
            return r + "年前";
        }
        if (diff > MONTH) {
            r = (diff / MONTH);
            return r + "个月前";
        }
        if (diff > DAY) {
            r = (diff / DAY);
            return r + "天前";
        }
        if (diff > HOUR) {
            r = (diff / HOUR);
            return r + "个小时前";
        }
        if (diff > MINUTE) {
            r = (diff / MINUTE);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL
     *            日期
     * @return
     */
    public static String formatDateTime(long dateL) {
        SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
        Date date = new Date(dateL);
        return sdf.format(date);
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL
     *            日期
     * @return
     */
    public static String formatDateTime(long dateL, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(new Date(dateL));
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param formater
     *            日期
     * @return
     */
    public static String formatDateTime(Date date, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(date);
    }

    /**
     * 将日期字符串转成日期
     *
     * @param strDate
     *            字符串日期
     * @return java.util.date日期类型
     */

    public static Date parseDate(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
        Date returnDate = null;
        try {
            returnDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            LogUtils.e("parseDate failed !");

        }
        return returnDate;

    }

    /**
     * 获取系统当前日期
     *
     * @return
     */
    public static Date gainCurrentDate() {
        return new Date();
    }

    /**
     * 验证日期是否比当前日期早
     *
     * @param target1
     *            比较时间1
     * @param target2
     *            比较时间2
     * @return true 则代表target1比target2晚或等于target2，否则比target2早
     */
    public static boolean compareDate(Date target1, Date target2) {
        boolean flag = false;
        try {
            String target1DateTime = DateUtil.formatDateTime(target1,
                    DF_YYYY_MM_DD_HH_MM_SS);
            String target2DateTime = DateUtil.formatDateTime(target2,
                    DF_YYYY_MM_DD_HH_MM_SS);
            if (target1DateTime.compareTo(target2DateTime) <= 0) {
                flag = true;
            }
        } catch (Exception e1) {
            LogUtils.e("比较失败，原因：" + e1.getMessage());
        }
        return flag;
    }

    /**
     * 对日期进行增加操作
     *
     * @param target
     *            需要进行运算的日期
     * @param hour
     *            小时
     * @return
     */
    public static Date addDateTime(Date target, double hour) {
        if (null == target || hour < 0) {
            return target;
        }

        return new Date(target.getTime() + (long) (hour * 60 * 60 * 1000));
    }

    /**
     * 对日期进行相减操作
     *
     * @param target
     *            需要进行运算的日期
     * @param hour
     *            小时
     * @return
     */
    public static Date subDateTime(Date target, double hour) {
        if (null == target || hour < 0) {
            return target;
        }

        return new Date(target.getTime() - (long) (hour * 60 * 60 * 1000));
    }
    private static SimpleDateFormat second = new SimpleDateFormat(
            "yy-MM-dd hh:mm:ss");

    private static SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat detailDay = new SimpleDateFormat("yyyy年MM月dd日");
    private static SimpleDateFormat fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static SimpleDateFormat tempTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat excelDate = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * 格式化excel中的时间
     * @param date
     * @return
     */
    public static String formatDateForExcelDate(Date date) {
        return excelDate.format(date);
    }

    /**
     * 将日期格式化作为文件名
     * @param date
     * @return
     */
    public static String formatDateForFileName(Date date) {
        return fileName.format(date);
    }

    /**
     * 格式化日期(精确到秒)
     *
     * @param date
     * @return
     */
    public static String formatDateSecond(Date date) {
        return second.format(date);
    }

    /**
     * 格式化日期(精确到秒)
     *
     * @param date
     * @return
     */
    public static String tempDateSecond(Date date) {
        return tempTime.format(date);
    }

    /**
     * 格式化日期(精确到秒)
     *
     * @param str
     * @return
     */
    public static Date tempDateSecond(String str) {
        try {
            return tempTime.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
    /**
     * 格式化日期(精确到天)
     *
     * @param date
     * @return
     */
    public static String formatDateDay(Date date) {
        return day.format(date);
    }

    /**
     * 格式化日期(精确到天)
     *
     * @param date
     * @return
     */
    public static String formatDateDetailDay(Date date) {
        return detailDay.format(date);
    }

    /**
     * 将double类型的数字保留两位小数（四舍五入）
     *
     * @param number
     * @return
     */
    public static String formatNumber(double number) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#0.00");
        return df.format(number);
    }

    /**
     * 将字符串转换成日期
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static Date formateDate(String date) throws Exception {
        return day.parse(date);
    }

    /**
     * 将字符日期转换成Date
     * @param date
     * @return
     * @throws Exception
     */
    public static Date parseStringToDate(String date) throws Exception {
        return day.parse(date);
    }

    /**
     * 将double日期转换成String
     * @param number
     * @return
     */
    public static String formatDoubleNumber(double number) {
        DecimalFormat df = new DecimalFormat("#");
        return df.format(number);
    }

    /**
     * 获得指定Date类型的毫秒数
     * @param date 指定的Date
     * @return 指定Date类型的毫秒数
     */
    public static long getTimeMillis(Date date){
        return date.getTime();
    }

    /**
     * 获得当前时间的毫秒数
     * @return 当前时间的毫秒数
     */
    public static long getCurrentDayTimeMillis(){
        return System.currentTimeMillis();
    }

    /**
     * 将格式化过的时间串转换成毫秒
     * @param day 将格式化过的时间
     * @param format 格式化字符串
     * @return 毫秒
     */
    public static long convertMillisecond(String day, String format) {
        if (day == null || format == null)
            return 0;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date dt = formatter.parse(day);
            return dt.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 得到两个日期的天数
     * @param sdate1 日期一
     * @param sdate2 日期二
     * @return 天数
     */
    public static int getDateInterval(String sdate1, String sdate2) {
        Date date1 = null;
        Date date2 = null;
        long betweenDays=0;

        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sdate1);
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(sdate2);

            long beginTime = date1.getTime();
            long endTime = date2.getTime();
            betweenDays = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (int) betweenDays;
    }

    /**
     * 时间比较
     * @param format 格式化字符串
     * @param time1 时间1
     * @param time2 时间2
     * @return time1比time2早返回-1,time1与time2相同返回0,time1比time2晚返回1
     */
    public static int compareTime(String format, String time1, String time2) {
        if (format == null || time1 == null || time2 == null)
            return 0;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        try {
            c1.setTime(formatter.parse(time1));
            c2.setTime(formatter.parse(time2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return c1.compareTo(c2);
    }

    /**
     * 获得时区偏移量 相对GMT RFC 82
     *
     * @param date
     * @return
     */
    public static String time_offset(Date date) {
        return String.format("%tz", date);
    }

    /**
     * 获得下午或上午
     *
     * @param date
     * @return
     */
    public static String am_or_pm(Date date) {
        return String.format("%tp", date);
    }

    /**
     * 获得当前微妙数 9位
     *
     * @param date
     * @return
     */
    public static String subtle(Date date) {
        return String.format("%tN", date);
    }

    /**
     * 获得当前毫秒数 3位
     *
     * @param date
     * @return
     */
    public static String mill(Date date) {
        return String.format("%tL", date);
    }

    /**
     * 获得当前秒 2位
     *
     * @param date
     * @return
     */
    public static String second(Date date) {
        return String.format("%tS", date);
    }

    /**
     * 获得当前分钟 2位
     *
     * @param date
     * @return
     */
    public static String minute(Date date) {
        return String.format("%tM", date);
    }

    /**
     * 获得当前小时 1-12
     *
     * @param date
     * @return
     */
    public static String hour_l(Date date) {
        return String.format("%tl", date);
    }

    /**
     * 获得当前小时 00-23
     *
     * @param date
     * @return
     */
    public static String hour_H(Date date) {
        return String.format("%tH", date);
    }

    /**
     * 获得当前时间 15:25
     *
     * @param date
     * @return
     */
    public static String hour_minute(Date date) {
        return String.format("%tR", date);
    }

    /**
     * 获得当前时间 15:23:50
     *
     * @param date
     * @return
     */
    public static String hour_minute_second(Date date) {
        return String.format("%tT", date);
    }

    /**
     * 获得当前时间 03:22:06 下午
     *
     * @param date
     * @return
     */
    public static String hour_minute_second_pm_or_am(Date date) {
        return String.format("%tr", date);
    }

    /**
     * 获取当前时间到日 03/25/08（月/日/年）
     *
     * @param date
     * @return
     */
    public static String mdy(Date date) {
        return String.format("%tD", date);
    }

    /**
     * 获取当前时间到日 2008-03-25 年—月—日
     *
     * @param date
     * @return
     */
    public static String ymd(Date date) {
        return String.format("%tF", date);
    }

    /**
     * 获得日期天 1-31
     *
     * @param date
     * @return
     */
    public static String day_one(Date date) {
        return String.format("%te", date);
    }

    /**
     * 获得日期天 01-31
     *
     * @param date
     * @return
     */
    public static String day_two(Date date) {
        return String.format("%td", date);
    }

    /**
     * 一年中的第几天 085
     *
     * @param date
     * @return
     */
    public static String day_to_year(Date date) {
        return String.format("%tj", date);
    }

    /**
     * 获得月份简称
     */
    public static String month_referred(Date date) {
        return String.format("%tb", date);
    }

    /**
     * 获得月份全称
     *
     * @param date
     * @return
     */
    public static String month_full_name(Date date) {
        return String.format("%tB", date);
    }

    /**
     * 获得月份 01-12
     *
     * @param date
     * @return
     */
    public static String month(Date date) {
        return String.format("%tm", date);
    }

    /**
     *获得星期简称
     *
     * @param date
     * @return
     */
    public static String week_referred(Date date) {
        return String.format("%ta", date);
    }

    /**
     * 获得星期全称
     *
     * @param date
     * @return
     */
    public static String week_full_name(Date date) {
        return String.format("%tA", date);
    }

    /**
     * 获得年简称 16
     *
     * @param date
     * @return
     */
    public static String year_referred(Date date) {
        return String.format("%ty", date);
    }

    /**
     * 获得年全称 2016
     *
     * @param date
     * @return
     */
    public static String year_full_name(Date date) {
        return String.format("%tY", date);
    }

    /**
     * 星期二 三月 25 13:37:22 CST 2016
     *
     * @param date
     * @return
     */
    public static String time(Date date) {
        return String.format("%tc", date);
    }

    /**
     * 获取时间戳到秒
     *
     * @param date
     * @return
     */
    public static String time_to_second(Date date) {
        return String.format("%ts", date);
    }

    /**
     * 获取时间戳到毫秒
     *
     * @param date
     * @return
     */
    public static String time_to_mill(Date date) {
        return String.format("%tQ", date);
    }

    /**
     * 获取时间戳到毫秒
     *
     * @return
     */
    public static long time_to_mill() {
        return System.currentTimeMillis();
    }





    //    /**
//     * 身份证号转生日
//     *
//     * @param identityCard 身份证
//     * @return 生日
//     */
//    public static Date identityCard2Date(String identityCard) {
//        try {
//            String dateStr;
//            if (identityCard.length() == 18) {
//                dateStr = identityCard.substring(6, 14);// 截取18位身份证身份证中生日部分
//                return formatDateString(dateStr, "yyyyMMdd");
//            }
//            if (identityCard.length() == 15) {
//                dateStr = identityCard.substring(6, 12);// 截取15位身份证中生日部分
//                return formatDateString(dateStr, "yyMMdd");
//            }
//            return null;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    /**
//     * 格式化日期时间字符串
//     *
//     * @param dateString 日期时间字符串
//     * @param pattern    模式
//     * @return Date对象
//     */
//    public static Date formatDateString(String dateString, String pattern) {
//        try {
//            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
//            return dateTimeFormatter.parseDateTime(dateString).toDate();
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
