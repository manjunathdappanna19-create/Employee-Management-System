package com.employee.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    int empId;

    @Column(name = "emp_name")
    String empName;

    @Column(name = "emp_email")
    String empEmail;

    @Column(name = "emp_phone")
    String empPhone;

    @Column(name = "emp_salary")
    int empSalary;

    @Column(name = "dept_id")
    int deptId;

    @Column(name ="personal_id")
    int personalId;
}
