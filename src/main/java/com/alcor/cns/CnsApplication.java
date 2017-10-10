package com.alcor.cns;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"pers.roamer.boracay","com.alcor.cns"})
@ImportResource(locations = {"classpath:boracay-config.xml"})
@EnableJpaRepositories({"pers.roamer.boracay","com.alcor.cns"})
@EntityScan({"pers.roamer.boracay.entity","com.alcor.cns.entity"})

@Log4j2

public class CnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnsApplication.class, args);
	}
}
