package com.employee.repository;

import com.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    List<Employee> findByEmpName(String empName);

    java.util.Optional<Employee> findByEmpId(Integer empId);

    int deleteByEmpId(Integer empId);


}
