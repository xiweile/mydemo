package com.weiller.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.weiller.demo.*.dao")
public class MydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydemoApplication.class, args);
	}
}
