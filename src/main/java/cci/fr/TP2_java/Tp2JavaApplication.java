package cci.fr.TP2_java;

import cci.fr.TP2_java.entities.AppUser;
import cci.fr.TP2_java.entities.Employee;
import cci.fr.TP2_java.entities.MenuItem;
import cci.fr.TP2_java.repositories.EmployeeRepository;
import cci.fr.TP2_java.repositories.MenuItemRepository;
import cci.fr.TP2_java.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Tp2JavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tp2JavaApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UserRepository userRepository, MenuItemRepository menuItemRepository, EmployeeRepository employeeRepository) {
        return args -> {
            // User pour s'authentifier
            userRepository.save(new AppUser(null, "employe", new BCryptPasswordEncoder().encode("password")));

            // Quelques employés
            Employee emp1 = employeeRepository.save(new Employee(null, "Alice"));
            Employee emp2 = employeeRepository.save(new Employee(null, "Bob"));

            // La carte du restaurant
            menuItemRepository.save(new MenuItem(null, "Burger", 12.50));
            menuItemRepository.save(new MenuItem(null, "Frites", 4.00));
            menuItemRepository.save(new MenuItem(null, "Coca", 2.50));
        };
    }
}
