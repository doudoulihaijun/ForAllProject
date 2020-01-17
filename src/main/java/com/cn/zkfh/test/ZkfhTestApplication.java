package com.cn.zkfh.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cn.*"})
public class ZkfhTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZkfhTestApplication.class, args);
	}

}
