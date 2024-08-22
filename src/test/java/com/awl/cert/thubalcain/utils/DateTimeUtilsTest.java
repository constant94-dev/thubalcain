package com.awl.cert.thubalcain.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeUtilsTest {

    @Test
    @DisplayName("발급 시점 LocalDateTime 생성 확인")
    void generateIssuedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        LocalDateTime issuedAt = DateTimeUtils.generateIssuedAt();
        String issuedAtTime = issuedAt.format(formatter);
        LocalDateTime now = LocalDateTime.now();

        assertThat(issuedAtTime).matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");
        assertThat(issuedAt).
                isAfterOrEqualTo(now.minusSeconds(1)) // 발급된 시간이 현재 시간과 동일하거나 이후여야 함
                .isBefore(now.plusSeconds(1)); // 1초 이내에 시간만 허용 함
    }

    @DisplayName("발급 시점에 한달 후 만료 시점 LocalDateTime 생성 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "2024-12-23T06:58:21, 2025-01-23T06:58:21, " +
            "2024-01-31T06:58:21, 2024-02-29T06:58:21",
            "2024-08-23T06:58:21, 2024-09-23T06:58:21, " +
            "2024-09-30T06:58:21, 2024-10-30T06:58:21"
    })
    void generateExpiration(
            LocalDateTime inCompleteMonthTime, LocalDateTime completeMonthTime,
            LocalDateTime inCompleteDayTime, LocalDateTime completeDayTime
                            ) {
        LocalDateTime expiredAtChangeMonth = DateTimeUtils.generateExpiration(inCompleteMonthTime);
        LocalDateTime expiredAtChangeDay = DateTimeUtils.generateExpiration(inCompleteDayTime);

        assertThat(expiredAtChangeMonth).isEqualTo(completeMonthTime);
        assertThat(expiredAtChangeDay).isEqualTo(completeDayTime);
    }
}