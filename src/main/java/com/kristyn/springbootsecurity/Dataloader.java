package com.kristyn.springbootsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//import javax.management.relation.Role;

@Component
public class Dataloader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CarRepository carRepository;

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

        Category category = new Category();
        category.setName("Used Car");

        Car car = new Car();
        car.setMake("Subaru");
        car.setModel("Forrester");
        car.setYear("2010");
        car.setMsrp("$10000");


        Car car1 = new Car();
        car1.setMake("Chevy");
        car1.setModel("Impala");
        car1.setYear("2003");
        car1.setMsrp("$8000");

        Car car2 = new Car();
        car2.setMake("Ford");
        car2.setModel("Focus");
        car2.setYear("2009");
        car2.setMsrp("$5000");

        Set<Car> cars = new HashSet<>();
        cars.add(car);
        cars.add(car1);
        cars.add(car2);
        category.setCars(cars);
        categoryRepository.save(category);


        car.setCategory(category);
        carRepository.save(car);


        Category category1 = new Category();
        category1.setName("New Car");

        Car car3 = new Car();
        car3.setMake("Ford");
        car3.setModel("Fiesta");
        car3.setYear("2020");
        car3.setMsrp("$10000");

        Car car4 = new Car();
        car4.setMake("Subaru");
        car4.setModel("Impreza");
        car4.setYear("2020");
        car4.setMsrp("25000");

        cars = new HashSet<>();
        cars.add(car3);
        cars.add(car4);

        category.setCars(cars);
        categoryRepository.save(category1);

        car.setCategory(category);
        car1.setCategory(category);
        car2.setCategory(category);
        car3.setCategory(category1);
        car4.setCategory(category1);
        carRepository.save(car);
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        carRepository.save(car4);

    }
}
