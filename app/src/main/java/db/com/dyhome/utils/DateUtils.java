

package db.com.dyhome.utils;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

@SuppressLint("SimpleDateFormat")
public class DateUtils {
    // 格式：年－月－日 小时：分钟：秒
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";

    // 格式：年－月－日 小时：分钟
    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";

    // 格式：年月日 小时分钟秒
    public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";

    // 格式：年－月－日
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";

    // 格式：月－日
    public static final String SHORT_DATE_FORMAT = "MM-dd";

    // 格式: 年月日时分秒
    public static final String SHORT_LINE_FORMAT = "yyyyMMddHHmmss";

    // 格式: 年月日时分秒
    public static final String SHORT_LINE_FORMAT_TWO = "yyyyMMddHHmm";
    // 格式：小时：分钟：秒
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";

    // 格式：年-月
    public static final String MONTG_DATE_FORMAT = "yyyy-MM";

    // 年的加减
    public static final int SUB_YEAR = Calendar.YEAR;

    // 月加减
    public static final int SUB_MONTH = Calendar.MONTH;

    // 天的加减
    public static final int SUB_DAY = Calendar.DATE;

    // 小时的加减
    public static final int SUB_HOUR = Calendar.HOUR;

    // 分钟的加减
    public static final int SUB_MINUTE = Calendar.MINUTE;

    // 秒的加减
    public static final int SUB_SECOND = Calendar.SECOND;

    public static final String[] BIG_STYLE_BASE = {
    	"一",
    	"二",
    	"三",
    	"四",
    	"五",
    	"六",
    	"七",
    	"八",
    	"九",
    	"十",
    	"十一",
    	"十二",
    };
    /**
     * 把符合日期格式的字符串转换为日期类型
     * 
     * @param dateStr
     * @return
     */
    public static Date stringtoDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
        	e.printStackTrace();
        	d = null;
        }
        return d;
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     */
    public static Date stringtoDate(String dateStr, String format,
            ParsePosition pos) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr, pos);
        } catch (Exception e) {
        	e.printStackTrace();
            d = null;
        }
        return d;
    }

    /**
     * 把日期转换为字符串
     * 
     * @param date
     * @return
     */
    public static String dateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取当前时间的指定格式
     * 
     * @param format
     * @return
     */
    public static String getCurrDate(String format) {
        return dateToString(new Date(), format);
    }

    /**
     * @param dateStr
     * @param amount
     * @return
     */
    public static String dateSub(int dateKind, String dateStr, int amount) {
        Date date = stringtoDate(dateStr, FORMAT_ONE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateKind, amount);
        return dateToString(calendar.getTime(), FORMAT_ONE);
    }

    /**
     * 两个日期相减
     * 
     * @param firstTime
     * @param secTime
     * @return 相减得到的秒数
     */
    public static long timeSub(String firstTime, String secTime) {
        long first = stringtoDate(firstTime, FORMAT_ONE).getTime();
        long second = stringtoDate(secTime, FORMAT_ONE).getTime();
        return (second - first) / 1000;
    }

    /**
     * 获得某月的天数
     * 
     * @param year int
     * @param month int
     * @return int
     */
    public static int getDaysOfMonth(String year, String month) {
        int days = 0;
        if (month.equals("1") || month.equals("3") || month.equals("5")
                || month.equals("7") || month.equals("8") || month.equals("10")
                || month.equals("12")) {
            days = 31;
        } else if (month.equals("4") || month.equals("6") || month.equals("9")
                || month.equals("11")) {
            days = 30;
        } else {
            if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
                    || Integer.parseInt(year) % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }

        return days;
    }

    /**
     * 获取某年某月的天数
     * 
     * @param year int
     * @param month int 月份[1-12]
     * @return int
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前日期(几号)
     * 
     * @return int
     */
    public static int getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得当前月份
     * 
     * @return int
     */
    public static int getToMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得当前年份
     * 
     * @return int
     */
    public static int getToYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的天
     * 
     * @param date Date
     * @return int
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }
    
    /**
     * 
     * @param milliseconds
     * @return
     */
    public static int getDay(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的年
     * 
     * @param date Date
     * @return int
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * 
     * @param milliseconds
     * @return
     */
    public static int getYear(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return calendar.get(Calendar.YEAR);    	
    }

    /**
     * 返回日期的月份，1-12
     * 
     * @param date Date
     * @return int
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 
     * @param milliseconds
     * @return
     */
    public static int getMonth(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return calendar.get(Calendar.MONTH) + 1;    	
    }
    
    public static String getBigStyleMonth(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return BIG_STYLE_BASE[calendar.get(Calendar.MONTH)];
    }

    /**
     * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
     * 
     * @param date1 Date
     * @param date2 Date
     * @return long
     */
    public static long dayDiff(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / 86400000;
    }

    /**
     * 比较两个日期的年差
     * 
     * @param befor
     * @param after
     * @return
     */
    public static int yearDiff(String before, String after) {
        Date beforeDay = stringtoDate(before, LONG_DATE_FORMAT);
        Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
        return getYear(afterDay) - getYear(beforeDay);
    }

    /**
     * 比较指定日期与当前日期的差
     * 
     * @param befor
     * @param after
     * @return
     */
    public static int yearDiffCurr(String after) {
        Date beforeDay = new Date();
        Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
        return getYear(beforeDay) - getYear(afterDay);
    }

    /**
     * 比较指定日期与当前日期的差
     * 
     * @param before
     * @return
     * @author chenyz
     */
    public static long dayDiffCurr(String before) {
        Date currDate = DateUtils.stringtoDate(currDay(), LONG_DATE_FORMAT);
        Date beforeDate = stringtoDate(before, LONG_DATE_FORMAT);
        return (currDate.getTime() - beforeDate.getTime()) / 86400000;
    }

    /**
     * 获取每月的第一周
     * 
     * @param year
     * @param month
     * @return
     * @author chenyz
     */
    public static int getFirstWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取每月的最后一周
     * 
     * @param year
     * @param month
     * @return
     * @author chenyz
     */
    public static int getLastWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, getDaysOfMonth(year, month));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
     * 
     * @return
     */
    public static String getNow() {
        Calendar today = Calendar.getInstance();
        return dateToString(today.getTime(), FORMAT_ONE);
    }

    /**
     * 根据生日获取星座
     * 
     * @param birth YYYY-mm-dd
     * @return
     */
    public static String getAstro(String birth) {
        if (!isDate(birth)) {
            birth = "2000" + birth;
        }
        if (!isDate(birth)) {
            return "";
        }
        int month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1,
                birth.lastIndexOf("-")));
        int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1));
        String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
        int[] arr = {
                20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22
        };
        int start = month * 2 - (day < arr[month - 1] ? 2 : 0);
        return s.substring(start, start + 2) + "座";
    }

    /**
     * 判断日期是否有效,包括闰年的情况
     * 
     * @param date YYYY-mm-dd
     * @return
     */
    public static boolean isDate(String date) {
        StringBuffer reg = new StringBuffer(
                "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }

    /**
     * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
     * 
     * @param date 日期 为null时表示当天
     * @param month 相加(相减)的月数
     */
    public static Date nextMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 取得指定日期过 day 天后的日期 (当 day 为负数表示指日期之前);
     * 
     * @param date 日期 为null时表示当天
     * @param month 相加(相减)的月数
     */
    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    /**
     * 取得距离今天 day 日的日期
     * 
     * @param day
     * @param format
     * @return
     * @author chenyz
     */
    public static String nextDay(int day, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, day);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 取得指定日期过 day 周后的日期 (当 day 为负数表示指定月之前)
     * 
     * @param date 日期 为null时表示当天
     */
    public static Date nextWeek(Date date, int week) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.WEEK_OF_MONTH, week);
        return cal.getTime();
    }

    /**
     * 获取当前的日期(yyyy-MM-dd)
     */
    public static String currDay() {
        return DateUtils.dateToString(new Date(), DateUtils.LONG_DATE_FORMAT);
    }

    /**
     * 获取昨天的日期
     * 
     * @return
     */
    public static String befoDay() {
        return befoDay(DateUtils.LONG_DATE_FORMAT);
    }

    /**
     * 根据时间类型获取昨天的日期
     * 
     * @param format
     * @return
     * @author chenyz
     */
    public static String befoDay(String format) {
        return DateUtils.dateToString(DateUtils.nextDay(new Date(), -1), format);
    }

    /**
     * 获取明天的日期
     */
    public static String afterDay() {
        return DateUtils.dateToString(DateUtils.nextDay(new Date(), 1),
                DateUtils.LONG_DATE_FORMAT);
    }

    /**
     * 取得当前时间距离1900/1/1的天数
     * 
     * @return
     */
    public static int getDayNum() {
        int daynum = 0;
        GregorianCalendar gd = new GregorianCalendar();
        Date dt = gd.getTime();
        GregorianCalendar gd1 = new GregorianCalendar(1900, 1, 1);
        Date dt1 = gd1.getTime();
        daynum = (int) ((dt.getTime() - dt1.getTime()) / (24 * 60 * 60 * 1000));
        return daynum;
    }

    /**
     * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
     * 
     * @param day
     * @return
     */
    public static Date getDateByNum(int day) {
        GregorianCalendar gd = new GregorianCalendar(1900, 1, 1);
        Date date = gd.getTime();
        date = nextDay(date, day);
        return date;
    }

    /** 针对yyyy-MM-dd HH:mm:ss格式,显示yyyymmdd */
    public static String getYmdDateCN(String datestr) {
        if (datestr == null)
            return "";
        if (datestr.length() < 10)
            return "";
        StringBuffer buf = new StringBuffer();
        buf.append(datestr.substring(0, 4)).append(datestr.substring(5, 7))
                .append(datestr.substring(8, 10));
        return buf.toString();
    }

    /**
     * 获取本月第一天
     * 
     * @param format
     * @return
     */
    public static String getFirstDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 获取本月最后一天
     * 
     * @param format
     * @return
     */
    public static String getLastDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 获取yyyy年mm月dd日
     * 
     * @param date
     * @return
     */
    public static String getChineDate(Date date) {
        String strDate = DateUtils.dateToString(date, DateUtils.LONG_DATE_FORMAT);
        return strDate.split("-")[0] + "年" + strDate.split("-")[1] + "月" + strDate.split("-")[2] + "日";
    }

    /**
     * 获得yyyy年MM月dd日 hh:mm:ss
     * 
     * @param date
     * @return
     */
    public static String getChineLongDate(Date date) {
        String strDate = DateUtils.dateToString(date, DateUtils.LONG_DATE_FORMAT);
        return strDate.split("-")[0] + "年" + strDate.split("-")[1] + "月" + strDate.split("-")[2] + "日"
                + " " + dateToString(date, LONG_TIME_FORMAT);
    }

    /**
     * 获取mm月dd日 hh:mm:ss
     * 
     * @param date
     * @return
     */
    public static String getChineShortDate(Date date) {
        String strDate = DateUtils.dateToString(date, DateUtils.LONG_DATE_FORMAT);
        return strDate.split("-")[1] + "月" + strDate.split("-")[2] + "日"
                + " " + dateToString(date, LONG_TIME_FORMAT);
    }

    public static String getyyyMMddHHmmss2(long ms) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date(ms);
        String string = mFormat.format(date);
        return string;
    }

    /** 得到年月日时分秒 */
    public static String getyyyyMMddHHmmss(long ms) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(ms);
        String string = mFormat.format(date);
        return string;
    }

    /** 得到年月日 */
    public static String getyyyyMMdd(long ms) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(ms);
        String string = mFormat.format(date);
        return string;
    }

    /** 得到时分秒 */
    public static String getHHmmss(long ms) {
        SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(ms);
        String string = mFormat.format(date);
        return string;
    }

    /** 用来计算两个long型之差的时分秒 */
    public static String getHHmmssBetween(long ms) {
        long second = ms / 1000 % 60;
        long minute = ms / 1000 / 60 % 60;
        long hour = ms / 1000 / 60 / 60;

        String secondStr = String.valueOf(second);
        String minuteStr = String.valueOf(minute);
        String hourStr = String.valueOf(hour);

        StringBuilder mSB = new StringBuilder();

        mSB.append(hourStr.length() < 2 ? "0" + hourStr : hourStr).append(":");
        mSB.append(minuteStr.length() < 2 ? "0" + minuteStr : minuteStr).append(":");
        mSB.append(secondStr.length() < 2 ? "0" + secondStr : secondStr);

        return mSB.toString();
    }

    public static long getLongFromyyyyMMdd(String date) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = mFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (parse != null) {
            return parse.getTime();
        }
        else {
            return -1;
        }
    }

    public static String getDateStringFromyyyyMMdd(String date) {
        // String year = date.substring(0, 4);
        // String month = date.substring(4,6);
        // String day = date.substring(6,8);

        // return year+ "-" + month + "-" + day;
        // return year + month + day;
        return date;
    }
    
    public static long getExifLongFromyyyyMMddHHmmss(String date) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Date parse = null;
        try {
            parse = mFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (parse != null) {
            return parse.getTime();
        }
        else {
            return -1;
        }
    }

    /** yyyy-MM-dd HH:mm:ss */
    public static long getLongFromyyyyMMddHHmmss(String date) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = mFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (parse != null) {
            return parse.getTime();
        }
        else {
            return -1;
        }
    }

    /** yyyy-MM-dd HH:mm:ss */
    public static long getLongFromyyyyMMddHHmmss_ext(String date) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date parse = null;
        try {
            parse = mFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (parse != null) {
            return parse.getTime();
        }
        else {
            return -1;
        }
    }

    // for winstat start
    /** 得到年月日时分秒 */
    public static String getTimeStamps() {
        long ms = System.currentTimeMillis();
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date(ms);
        String string = mFormat.format(date);
        return string;
    }

    /** 得到年月日时分秒 */
    public static String getyyyyMMddHHmmss_f1(long ms) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date(ms);
        String string = mFormat.format(date);
        return string;
    }
    
    /** yyyy-MM-dd HH:mm:ssZ */
    public static String getLongFromyyyyMMddHHmmssZ(long ms) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        Date date = new Date(ms);
        String string = mFormat.format(date);
        return string;
    }

    /** 得到年月日时分秒 */
    public static String getyyyyMMddHHmmss_f2(long ms) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date(ms);
        String string = mFormat.format(date);
        return string;
    }
    
    /**
     * 
     * @param ms
     * @return
     */
    public static long truncateHHmmss(long ms) {
    	Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(ms);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.clear();
		calendar2.set(calendar1.get(Calendar.YEAR),
				calendar1.get(Calendar.MONTH),
				calendar1.get(Calendar.DAY_OF_MONTH));
		return calendar2.getTimeInMillis();
    }

    /**
     * 时间差是否大于阈值
     * @param current 当前时间
     * @param last    上次时间
     * @param threshold 阈值
     * @return
     */
    public static boolean isReachDIF(long current,long last,int threshold){
        return (current - last)>=threshold;
    }
    /**
     * 把一个long类型的时间格式化为"02:50:44"，如果小时为0，则格式为"50:44"
     * @param duration
     * @return
     */
    public static CharSequence formatDuration(long duration) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.add(Calendar.MILLISECOND, (int) duration);
        Date date = calendar.getTime();
        int hour = (int) (duration / 1000 / 60 / 60);
        CharSequence inFormat = (hour > 0) ? "kk:mm:ss" : "mm:ss";
        return DateFormat.format(inFormat, date);
    }
}
