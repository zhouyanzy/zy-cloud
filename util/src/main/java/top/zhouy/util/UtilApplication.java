package top.zhouy.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

@SpringBootApplication
@ComponentScan(basePackages = {"top.zhouy"})
public class UtilApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtilApplication.class, args);
	}

}
