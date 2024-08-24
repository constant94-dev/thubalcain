package com.awl.cert.thubalcain.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeUtilsTest {

    /**
     * @MethodSource로 선언된 convertZonedDateTimeToDate(originDateTime, expectedDate)
     * 메서드 파라미터로 사용할 값 제공 기능
     *
     * @author ethan
     * @params
     * @return LocalDateTime, LocalDateTime 기준 Date 값
     **/
    private static Object[][] provideDateTimeForConversion() {
        LocalDateTime localDateTime = LocalDateTime.parse("2024-08-24T10:21:23"); // 문자열을 LocalDateTime으로 변환
        Date expectedDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); // Date 클래스로 변환

        return new Object[][]{
                {localDateTime, expectedDate}
        };
    }

    @DisplayName("ZonedDateTime 값을 Date 변환")
    @ParameterizedTest
    @MethodSource("provideDateTimeForConversion")
    void convertZonedDateTimeToDate(LocalDateTime originDateTime, Date expectedDate) {
        Date actual = DateTimeUtils.convertLocalDateTimeToDate(originDateTime);

        assertThat(actual).isEqualTo(expectedDate);
    }

    @DisplayName("발급 시점에 한달 후 만료 시점 LocalDateTime 생성 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "2024-12-23T06:58:21, 2025-01-23T06:58:21, " +
                    "2024-01-31T06:58:21, 2024-02-29T06:58:21",
            "2024-08-23T06:58:21, 2024-09-23T06:58:21, " +
                    "2024-09-30T06:58:21, 2024-10-30T06:58:21"
    })
    void generateExpireAt(
            LocalDateTime inCompleteMonthTime, LocalDateTime completeMonthTime,
            LocalDateTime inCompleteDayTime, LocalDateTime completeDayTime
    ) {
        LocalDateTime expiredAtChangeMonth = DateTimeUtils.generateExpiredAt(inCompleteMonthTime);
        LocalDateTime expiredAtChangeDay = DateTimeUtils.generateExpiredAt(inCompleteDayTime);

        assertThat(expiredAtChangeMonth).isEqualTo(completeMonthTime);
        assertThat(expiredAtChangeDay).isEqualTo(completeDayTime);
    }

    @DisplayName("발급 시점 LocalDateTime 생성 확인")
    @Test
    void generateIssuedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        LocalDateTime issuedAt = DateTimeUtils.generateIssuedAt();
        String issuedAtTime = issuedAt.format(formatter);
        LocalDateTime now = LocalDateTime.now();

        assertThat(issuedAtTime).matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");
        assertThat(issuedAt)
                .isAfterOrEqualTo(now.minusSeconds(1)) // 발급된 시간이 현재 시간과 동일하거나 이후여야 함
                .isBefore(now.plusSeconds(1)); // 1초 이내에 시간만 허용 함
    }

    @DisplayName("유효 시간이 발급 시간 이후로 설정 되었는지 확인")
    @Test
    void generateNotBeforeDate() {
        LocalDateTime issuedAt = DateTimeUtils.generateIssuedAt();
        Date notBeforeDate = DateTimeUtils.generateNotBeforeDate();

        Date expectedDate = DateTimeUtils.convertLocalDateTimeToDate(issuedAt);

        assertThat(notBeforeDate).isAfterOrEqualTo(expectedDate);
    }

    @DisplayName("현재 JVM에서 사용할 수 있는 유효한 시간 ID 확인")
    @Test
    void getAvailableZoneIds() {
        Set<String> availableZoneIds = DateTimeUtils.getAvailableZoneIds();
        for (String zoneId : availableZoneIds) {
            System.out.println(zoneId);
        }

        assertThat(availableZoneIds).contains("Asia/Seoul");
    }
}