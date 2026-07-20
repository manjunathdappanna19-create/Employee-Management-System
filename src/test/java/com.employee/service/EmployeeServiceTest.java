package com.employee.service;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest{

    @Mock
    EmployeeRepo employeeRepo;

    @InjectMocks
    EmployeeService employeeService;

    Employee employee;

    @BeforeEach
    void setUp(){
        employee = new Employee();
        employee.setEmpId(1);
        employee.setEmpName("John");
        employee.setEmpEmail("john@gg.com");
        employee.setEmpPhone("2849284909");
        employee.setEmpSalary(30000);
        employee.setDeptId(1);
        employee.setPersonalId(1);
    }

    @Test
    void getEmpById(){
        when(employeeRepo.findByEmpId(1)).thenReturn(Optional.of(employee));
        Optional<Employee> result = employeeService.findEmployeeById(1);

        assertTrue(result.isPresent());
        assertEquals("John",employee.getEmpName());
        verify(employeeRepo,times(1)).findByEmpId(1);
    }


    @Test
    void deleteEmpById(){
        when(employeeRepo.findById(1)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployeeById(1);
        verify(employeeRepo,times(1)).findById(1);
        verify(employeeRepo,times(1)).deleteByEmpId(1);

    }

    @Test
    void saveEmployeeTest(){
        Employee err = new Employee();
        err.setEmpName("Ramesh");
        assertThrows(IllegalArgumentException.class, ()->employeeService.saveEmployee(err));
        verify(employeeRepo,never()).save(err);
    }

}