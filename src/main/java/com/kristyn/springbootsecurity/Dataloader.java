package com.kristyn.springbootsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//import javax.management.relation.Role;

@Component
public class Dataloader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run (String... strings) throws Exception{
      roleRepository.save(new Role("USER"));
      roleRepository.save(new Role("ADMIN"));
       Role adminRole = roleRepository.findByRole("ADMIN");
       Role userRole = roleRepository.findByRole("USER");

       User user = new User("jim@jim.com", "password", "jim", "Jimmerson", true, "jim");
       user.setRoles(Arrays.asList(userRole));
       userRepository.save(user);

        user = new User("admin@admin.com", "password", "Admin", "User", true, "admin");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);
    }
}
