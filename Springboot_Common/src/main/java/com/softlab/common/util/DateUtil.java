package com.softlab.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : Ar1es
 * @date : 2019/11/7
 * @since : Java 8
 */
public class DateUtil {

    /**
     * Timestamp -> String
     * 时间戳转换成日期格式字符串
     * @param timestamp	时间戳
     * @return
     */
    public static String timestampToString(Timestamp timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(timestamp);
        return dateString;
    }

    /**
     * 当前时间转化为字符串
     * 精确到秒
     *
     * @return
     */
    public static String localTimeToStringSecond() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String dateString = df.format(time);
        return dateString;
    }

    /**
     * 当前时间转化为字符串
     * 精确到天
     *
     * @return
     */
    public static String localTimeToStringDay() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime time = LocalDateTime.now();
        String dateString = df.format(time);
        return dateString;
    }

    /**
     * LocalDateTime  -> Timestamp
     * 当前时间转换成时间戳
     * @return
     */
    public static Timestamp localTimeToTimestamp() {
        LocalDateTime time = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(time);
        return timestamp;
    }

}

