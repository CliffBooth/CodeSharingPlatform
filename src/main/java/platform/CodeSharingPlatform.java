package platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class CodeSharingPlatform {

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

}