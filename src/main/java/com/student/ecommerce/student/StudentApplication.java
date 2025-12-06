package com.student.ecommerce.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.student.ecommerce.student.entity.User;
import com.student.ecommerce.student.enums.UserRole;
import com.student.ecommerce.student.enums.UserStatus;
import com.student.ecommerce.student.repository.UserRepository;

@SpringBootApplication
public class StudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Tạo admin mặc định nếu chưa tồn tại
			if (!userRepository.findByEmail("admin@gmail.com").isPresent()) {
				User admin = new User();
				admin.setEmail("admin@gmail.com");
				admin.setPasswordHash(passwordEncoder.encode("admin123"));
				admin.setFullName("Administrator");
				admin.setPhoneNumber("0123456789");
				admin.setRole(UserRole.ADMIN);
				admin.setStatus(UserStatus.ACTIVE);
				userRepository.save(admin);
				System.out.println("✅ Default admin created: admin@gmail.com / admin123");
			}
		};
	}

}
