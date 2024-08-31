package com.awl.cert.thubalcain.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Slf4j
public class DateTimeUtils {

    /**
     * JWE 토큰 생성시 필요한 발급 날짜 생성 기능
     *
     * @author ethan
     * @params
     * @return 토큰 발급 날짜 반환
     **/
    public static LocalDateTime generateIssuedAt() {
        return LocalDateTime.now().withNano(0); // 날짜의 나노초를 '0'초로 설정
    }

    /**
     * JWE 토큰 생성시 필요한 만료 날짜 생성 기능
     *
     * @author ethan
     * @params 발급 날짜
     * @return 토큰 만료 날짜 반환
     **/
    public static LocalDateTime generateExpiredAt(LocalDateTime issuedAt) {
        int year = issuedAt.getYear();
        int month = issuedAt.getMonthValue();
        int day = issuedAt.getDayOfMonth();
        int hour = issuedAt.getHour();
        int min = issuedAt.getMinute();
        int sec = issuedAt.getSecond();

        int nextMonth = month + 1;
        if (nextMonth >= 13) { // 다음 달이 13월 일 경우, 1월로 변경 and 1년 증가
            nextMonth = 1;
            year = year + 1;
        }

        YearMonth yearMonth = YearMonth.of(year, nextMonth); // 특정 연도의 특정 월을 나타내는 클래스
        int daysInLast = yearMonth.lengthOfMonth(); // 해당 달의 마지막 day 확인
        /*
         * if 이번 달 day > 다음 달 마지막 day
         *     then 다음달 마지막 day가 만료시점 day로 설정
         * */
        if (day > daysInLast) {
            return LocalDateTime.of(year, nextMonth, daysInLast, hour, min, sec);
        }

        return LocalDateTime.of(year, nextMonth, day, hour, min, sec);
    }

    /**
     * LocalDateTime → Date Asia/Seoul 기준으로 변환 기능
     *
     * @author ethan
     * @params 변환 전 LocalDateTime
     * @return Asia/Seoul 기준 변환 후 Date
     **/
    public static Date convertLocalDateTimeToDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());
    }

    /**
     * 현재 시간 기준 1분 후 Date 객체 생성 기능
     *
     * @author ethan
     * @params 
     * @return 현재 시간 기준 1분 후 반환
     **/
    public static Date generateNotBeforeDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);

        return calendar.getTime();
    }

    /**
     * LocalDateTime 객체 값을 문자열 변환 기능
     *
     * @author ethan
     * @params 변환 전 LocalDateTime
     * @return yyyy-MM-dd HH:mm:ss 패턴 변환 후 String
     **/
    public static String convertLocalDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
    
    /**
     * 현재 JVM에서 사용할 수 있는 유효한 시간 ID 확인 기능
     *
     * @author ethan
     * @params 
     * @return 모든 시간대 ID의 Set 반환
     **/
    public static Set<String> getAvailableZoneIds() {
        return ZoneId.getAvailableZoneIds();
    }

    protected DateTimeUtils(){}
}
