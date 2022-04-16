package persi.sumu.departure.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author mobai
 * @version 1.0
 * @Description 日期工具类
 * @date 2022/4/16 13:42
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /** 日期格式化字符串yyyy-MM-dd*/
    public static final String YYYYMMDD = "yyyy-MM-dd";

    /** 日期格式化字符串yyyy-MM-dd HH:mm:ss*/
    public static final String YYYYMMDDHMS = "yyyy-MM-dd HH:mm:ss";

    /** 日期格式化字符串yyyy-MM-dd HH:mm:ss SSS*/
    public static final String YYYYMMDDHMSS = "yyyy-MM-dd HH:mm:ss SSS";

    /** SimpleDateFormat类型{yyyy-MM-dd}*/
    private static SimpleDateFormat sdf1;

    /** SimpleDateFormat类型{yyyy-MM-dd HH:mm:ss}*/
    private static SimpleDateFormat sdf2;

    /** SimpleDateFormat类型{yyyy-MM-dd HH:mm:ss SSS}*/
    private static SimpleDateFormat sdf3;

    /**
     * 格式化日期字符串
     * @param date 日期
     * @param format 格式化字符串
     * @return 格式化后的日期字符串
     */
    public static String getFormatDateStr(Date date, String format) {
        return getSdfInstance(format).format(date);
    }

    /**
     * 根据指定日期字符串获取对应日期
     * @param dateStr 日期字符串
     * @param format 格式化字符串
     * @return 格式化后的日期对象
     */
    public static Date getFormatDate(String dateStr, String format) {
        Date retDate = null;
        try {
            retDate = getSdfInstance(format).parse(dateStr);
        } catch (ParseException e) {
            logger.error("根据指定日期格式["+format+"]格式化日期["+dateStr+"]出错", e);
            throw new RuntimeException("格式化日期出错");
        }
        return retDate;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static Date getNowTime() {
        return new Date();
    }

    /**
     * 在原日期的基础上增加天数（i为负数时则减天数）
     * @param date
     * @param i
     * @return
     */
    public static Date addDay(Date date, int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, i);
        return c.getTime();
    }

    /**
     * 维持单例的SimpleDateFormat对象
     * @param format
     * @return
     */
    private static SimpleDateFormat getSdfInstance(String format) {
        SimpleDateFormat retSdf = null;
        switch (format) {
            case YYYYMMDD:
                if(sdf1 == null)
                    sdf1 = new SimpleDateFormat(YYYYMMDD);
                retSdf = sdf1;
                break;

            case YYYYMMDDHMS:
                if(sdf2 == null)
                    sdf2 = new SimpleDateFormat(YYYYMMDDHMS);
                retSdf = sdf2;
                break;

            case YYYYMMDDHMSS:
                if(sdf3 == null)
                    sdf3 = new SimpleDateFormat(YYYYMMDDHMS);
                retSdf = sdf3;
                break;

            default:
                retSdf = new SimpleDateFormat(format);
                break;
        }
        return retSdf;
    }

}
