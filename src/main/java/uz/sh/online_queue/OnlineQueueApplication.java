package uz.sh.online_queue;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.properties.OpenApiProperties;
import uz.sh.online_queue.properties.ServerProperties;
import uz.sh.online_queue.repository.mongorepository.AuthRepository;

@EnableConfigurationProperties({
        OpenApiProperties.class,
        ServerProperties.class
})
@OpenAPIDefinition
@SpringBootApplication
@RequiredArgsConstructor
@EnableCaching
@EnableMongoRepositories
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OnlineQueueApplication {

    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;

    public static void main(String[] args) {
        SpringApplication.run(OnlineQueueApplication.class, args);
    }

    //    @Bean
    CommandLineRunner runner() {
        return (args) -> {
            String encode = passwordEncoder.encode("123");
            System.out.println("encode = " + encode);

            Auth admin = Auth.childBuilder()
                    .passportSerial("qwerty")
                    .password(encode)
                    .role("super admin")
                    .firstName("super")
                    .lastName("admin")
                    .phoneNumber("+998901111111")
                    .build();
            authRepository.save(admin);
        };
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasenames("i18n/messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }
}
