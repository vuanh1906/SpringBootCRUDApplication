package com.example.crudspringboot.Repository;

import com.example.crudspringboot.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public Long countById(Long id);
}
