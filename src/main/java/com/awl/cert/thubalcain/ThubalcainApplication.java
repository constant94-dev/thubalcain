package com.awl.cert.thubalcain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ThubalcainApplication {
	public static void main(String[] args) {
		SpringApplication.run(ThubalcainApplication.class, args);
		log.info("Start App!");
	}
}
