package com.kristyn.springbootsecurity;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CarRepository extends CrudRepository<Car, Long>{
    ArrayList<Car> findCarByMakeIgnoreCase(String make);
    ArrayList<Car> findCarByModelIgnoreCase(String model);
    ArrayList<Car> findCarByYearIgnoreCase(String year);
}
