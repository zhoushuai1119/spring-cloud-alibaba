package com.cloud.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

/**
 * @ClassName DateTimeUtils
 * @Description
 * @Date 2019/6/23 14:42
 * @Version 1.0
 **/
public class DateTimeUtils {

    private DateTimeUtils() {

    }

    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter YYYYMM = DateTimeFormatter.ofPattern("yyyyMM");
    public static final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String DATETIME_PATTERN2 = "yyyy-MM-dd HH:mm:ss.S";
    public static final String DATETIME_PATTERN3 = "yyyy/MM/dd HH:mm:ss";
    private static final DateTimeFormatter DATETIME_PATTERN4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter DATETIME_PATTERN5 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter YYYYMMDD_PATTERN5 = DateTimeFormatter.ofPattern("yyyy年MM月dd日");


    private static final String TIME_PATTERN2 = "HH:mm";
    private static final String DEFAULT_TIME2 = "00:00";

    public static final String TIME_PATTERN = "HH:mm:ss";

    //时区
    private static final String ZoneOffsetStr = "+8";

    /**
     * 获取yyyyMM格式的月份字符串
     *
     * @param dateTime
     * @return
     */
    public static String getMonthWithYYYYMM(LocalDateTime dateTime) {
        return dateTime.format(YYYYMM);
    }

    /**
     * 获取下个月最后一天
     *
     * @return
     */
    public static LocalDate nextMonthLastDay() {
        return LocalDate.now().plusMonths(2).withDayOfMonth(1).minusDays(1);
    }

    /**
     * 获取上个月最后一天
     *
     * @return
     */
    public static String preMonthLastDay() {
        return LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).format(YYYYMMDD);
    }

    public static String yyyyMMddToday() {
        return LocalDate.now().format(YYYY_MM_DD);
    }

    public static String yyyyMMddTodayByLocalDateTime(LocalDateTime time) {
        time = Optional.ofNullable(time).orElse(LocalDateTime.now());
        return time.format(YYYY_MM_DD);
    }

    public static String yyyyMMddFormatToday() {
        return LocalDate.now().format(YYYYMMDD);
    }

    public static String yyyyMMddFormatNowPatten() {
        return LocalDateTime.now().format(DATETIME_PATTERN4);
    }


    public static String getYesterdayStr() {
        LocalDate localDate = LocalDateTime.now().toLocalDate().minusDays(1);
        LocalDateTime yesterday = LocalDateTime.of(localDate, LocalTime.MIN);
        return yesterday.format(DateTimeFormatter.ofPattern(DATETIME_PATTERN));
    }

    public static String getTimeString(LocalTime time) {
        return Optional
                .ofNullable(time)
                .map(localTime -> localTime.format(DateTimeFormatter.ofPattern(TIME_PATTERN2)))
                .orElse(DEFAULT_TIME2);
    }

    public static String getTimeAfterMonth(LocalDateTime time) {
        return Optional
                .ofNullable(time)
                .map(localTime -> localTime.plusDays(30).format(DateTimeFormatter.ofPattern(DATETIME_PATTERN)))
                .orElse(DEFAULT_TIME2);
    }

    public static LocalDateTime getTimeAfterMonthForDate(LocalDateTime time) {
        return Optional
                .ofNullable(time)
                .map(localTime -> localTime.plusDays(30))
                .orElse(null);
    }

    public static LocalDateTime getTimeAfterMonthForDate2(LocalDateTime time) {
        return Optional
                .ofNullable(time)
                .map(localTime -> localTime.plusDays(30))
                .orElse(null);
    }

    public static LocalDateTime getTimeBeforeDate27(LocalDateTime time, Integer days) {
        return Optional
                .ofNullable(time)
                .map(localTime -> localTime.minusDays(days))
                .orElse(null);
    }

    public static LocalDateTime getTimeBeforeDate30(LocalDateTime time, Integer days) {
        return Optional
                .ofNullable(time)
                .map(localTime -> localTime.minusDays(days))
                .orElse(null);
    }

    public static LocalDateTime getTimeBeforeHourForDate(Integer day) {
        return LocalDateTime.now().minusHours(day);
    }


    public static LocalDateTime getLocalDateTimeForFormat(LocalDateTime dateTime) {
        return LocalDateTime.parse(dateTime.format(DATETIME_PATTERN5), DateTimeFormatter.ofPattern(DATETIME_PATTERN));
    }


    public static String getLongTimeString() {
        return getLongTime().format(DateTimeFormatter.ofPattern(DATETIME_PATTERN));
    }

    public static String getYYYYMMMDDHHMMSSTimeString(LocalDateTime time) {
        time = Optional.ofNullable(time).orElse(LocalDateTime.now());
        return time.format(DateTimeFormatter.ofPattern(DATETIME_PATTERN));
    }

    public static LocalDateTime getTimeByYYYYMMMDDHHMMSSTimeString(String time) {
        return Optional
                .ofNullable(time)
                .map(timeStr -> LocalDateTime.parse(time, DateTimeFormatter.ofPattern(DATETIME_PATTERN)))
                .orElse(LocalDateTime.now());
    }

    public static LocalDateTime getTimeByYYYYMMMDDHHMMSSTimeString2(String time) {
        return Optional
                .ofNullable(time)
                .map(timeStr -> LocalDateTime.parse(time, DateTimeFormatter.ofPattern(DATETIME_PATTERN2)))
                .orElse(LocalDateTime.now());
    }

    public static LocalDateTime getTimeByYYYYMMMDDHHMMSSTimeString3(String time) {
        return Optional
                .ofNullable(time)
                .map(timeStr -> LocalDateTime.parse(time, DateTimeFormatter.ofPattern(DATETIME_PATTERN)))
                .orElse(null);
    }


    public static LocalDateTime getTimeByString3(String time) {
        return Optional
                .ofNullable(time)
                .map(timeStr -> LocalDateTime.parse(time, DateTimeFormatter.ofPattern(DATETIME_PATTERN3)))
                .orElse(LocalDateTime.now());
    }


    /**
     * '3000-01-01 00:00:00'
     *
     * @return
     */
    public static LocalDateTime getLongTime() {
        return LocalDateTime.of(3000, 1, 1, 0, 0, 0);
    }

    //得到时间戳

    public static Long getEpoch() {
        //获取毫秒数
        return LocalDateTime.now().toInstant(ZoneOffset.of(ZoneOffsetStr)).toEpochMilli();
    }

    public static Long getEpochWithTime(LocalDateTime time) {
        //获取毫秒数
        return time.toInstant(ZoneOffset.of(ZoneOffsetStr)).toEpochMilli();
    }

    public static LocalDateTime getTimeByEpoch(Long remindTime) {
        return LocalDateTime.ofEpochSecond(remindTime, 0, ZoneOffset.of(ZoneOffsetStr));
    }


    public static LocalTime getLocalTime(String time) {
        return Optional
                .ofNullable(time)
                .map(timeStr -> LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_PATTERN)))
                .orElse(LocalTime.MIN);
    }

    /**
     * 获取每月起始时间or结束时间
     *
     * @param datetimeStr
     * @param range
     * @return
     */
    public static LocalDateTime getPreviousMonthFirstDays(String datetimeStr, LocalTime range) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date = LocalDate.parse(datetimeStr, dtf).with(TemporalAdjusters.firstDayOfMonth());
        String str = LocalDateTime.of(date, range).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return LocalDateTime.parse(str, dtf2);
    }

    public static LocalDateTime getPreviousMonthLastDays(String datetimeStr, LocalTime range) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date = LocalDate.parse(datetimeStr, dtf).with(TemporalAdjusters.lastDayOfMonth());
        String str = LocalDateTime.of(date, range).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return LocalDateTime.parse(str, dtf2);
    }

    /***
     * 功能描述：格式化日期
     *
     * @param time:
     * @return: java.time.LocalDateTime
     * @author: yangzhi
     * @date: 2020/3/30 11:41
     */
    public static LocalDateTime getStr2Date(String ofPattern, String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(ofPattern));
    }
}
