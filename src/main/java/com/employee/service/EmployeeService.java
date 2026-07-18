package com.employee.service;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class EmployeeService {


    @Autowired
    EmployeeRepo employeeRepo;



    @Autowired
    RestTemplate restTemplate;

    @Transactional
   public List<Employee> getEmployeeDetailsByName(String empName) {
        return employeeRepo.findByEmpName(empName);
    }

    @Value("${department.service.url}")
    private String departmentServiceUrl;


    @Value("${personal.url}")
    private String personServiceUrl;

    public boolean isDepartmentValid(int deptId) {
        try {
            String url = departmentServiceUrl + "/id/" + deptId;
            System.out.println("Calling URL: " + url);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println("Response status: " + response.getStatusCode());
            return true;
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getClass().getName() + " - " + e.getMessage());

            return false;
        }
    }
    public java.util.Optional<Employee> findEmployeeById(int empId) {
        return employeeRepo.findByEmpId(empId);
    }

    public Employee saveEmployee(Employee employee) {
        if(!isDepartmentValid(employee.getDeptId())) {
            throw new IllegalArgumentException("Department with id " + employee.getDeptId() + " does not exist");
        }
        if(employee.getEmpName() == null || employee.getEmpEmail() == null || employee.getEmpPhone() == null || employee.getEmpSalary() == 0 || employee.getDeptId() == 0) {
            throw new IllegalArgumentException("Employee name, email, phone, salary and department id cannot be null or zero");
        }
        if(!isEmployeeDetailsPresent(employee.getPersonalId())){
            throw new IllegalArgumentException("Emplyee peronal details not present");
        }
        return employeeRepo.save(employee);
    }

    public void deleteEmployeeById(int empId) {
        Employee emp = employeeRepo.findById(empId).orElse(null);
        System.out.println("Employee found: " + emp);
        if(emp == null) {
            throw new IllegalArgumentException("Employee not found with id: " + empId);
        }
        employeeRepo.deleteByEmpId(empId);
    }


    public boolean isEmployeeDetailsPresent(int personId) {
        try{
        String url = personServiceUrl+"/id/"+personId;
        ResponseEntity<Employee> response = restTemplate.getForEntity(url, Employee.class);
        return true;
        } catch (Exception e) {
            return false;
        }
    }
}
