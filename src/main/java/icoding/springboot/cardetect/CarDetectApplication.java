package icoding.springboot.cardetect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync // 启用异步功能
public class CarDetectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarDetectApplication.class, args);
    }

}
