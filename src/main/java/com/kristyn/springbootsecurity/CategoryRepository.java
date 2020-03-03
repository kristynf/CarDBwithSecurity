package com.kristyn.springbootsecurity;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    ArrayList<Category> findByNameIgnoreCase(String name);
}
