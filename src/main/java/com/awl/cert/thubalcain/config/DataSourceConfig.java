package com.awl.cert.thubalcain.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Slf4j
@Configuration
@Profile({"dev","prod"})
public class DataSourceConfig {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String ENGINE = "mysql";
    private static final String OPTIONS = "zeroDateTimeBehavior=CONVERT_TO_NULL&useUnicode=true&characterEncoding=utf8&useLegacyDatetimeCode=false&allowMultiQueries=true&serverTimezone=UTC";
    @Value("${mysql.db.host}")
    private String host;
    @Value("${mysql.db.port}")
    private String port;
    @Value("${mysql.db.username}")
    private String username;
    @Value("${mysql.db.password}")
    private String password;
    @Value("${mysql.db.database}")
    private String database;

    @Value("${spring.profiles.active}")
    private String springProfilesActive;
    
    @Bean
    public DataSource hikariDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public HikariConfig hikariConfig() {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(DRIVER_CLASS_NAME);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl(getJdbcUrl());
        hikariConfig.setAutoCommit(true);
        hikariConfig.setConnectionTimeout(30_000L); // 연결을 얻기 위해 기다릴 수 있는 최대 시간(ms) 30초
        hikariConfig.setIdleTimeout(600_000L); // 연결 풀이 비활성 상태인 연결을 유지할 수 있는 최대 시간(ms) 10분
        hikariConfig.setMaxLifetime(1_800_000L); // 연결이 풀에서 유지될 수 있는 최대 시간(ms) 30분
        hikariConfig.setMinimumIdle(10); // 풀에서 유지될 최소한의 유휴 연결 수 10개, 항상 연결 준비됨
        hikariConfig.setMaximumPoolSize(15); // 풀에서 허용될 최대 연결 수 15개, 과도한 연결 생성으로 인한 리소스 낭비를 방지
        hikariConfig.setConnectionTestQuery("SELECT 1"); // 연결이 유효한지 확인하기 위해 실행될 SQL 쿼리
        hikariConfig.setValidationTimeout(5_000L); // 연결 유효성 검사를 완료하는 데 허용되는 최대 시간(ms) 5초
        hikariConfig.setTransactionIsolation("TRANSACTION_READ_COMMITTED"); // 트랜잭션 격리 수준

        return hikariConfig;
    }

    private String getJdbcUrl() {
        return String.format("jdbc:%s://%s:%s/%s?%s", ENGINE, this.host, this.port, this.database, OPTIONS);
    }
}
