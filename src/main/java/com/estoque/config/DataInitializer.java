package com.estoque.config;

import com.estoque.model.Role;
import com.estoque.model.User;
import com.estoque.repository.RoleRepository;
import com.estoque.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        return args -> {

            // Criar roles se não existirem
        	Role adminRole = roleRepository.findByName("ROLE_ADMIN")
        	        .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        	Role userRole = roleRepository.findByName("ROLE_USER")
        	        .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));


            // Criar admin se não existir
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setName("Administrador");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456")); // Usando bean injetado
                admin.setRoles(Set.of(adminRole));

                userRepository.save(admin);
            }
        };
    }
}
