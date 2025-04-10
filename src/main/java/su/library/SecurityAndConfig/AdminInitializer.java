package su.library.SecurityAndConfig;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import su.library.BookType.User;
import su.library.BookType.UserRepository;

@Configuration
public class AdminInitializer {
    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            String adminUsername = "Phanhao0410";
            if (!userRepository.findByUserName(adminUsername).isPresent()) {
                User admin = new User();
                admin.setUserName(adminUsername);
                admin.setUserPassword(encoder.encode("Phanhao0410"));
                admin.setRoles(List.of("ROLE_ADMIN"));
                userRepository.save(admin);
                System.out.println("Default admin user 'phanhao' created.");
            }
        };
    }
}
