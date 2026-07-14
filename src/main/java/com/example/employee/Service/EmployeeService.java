package com.example.employee.Service;

import com.example.employee.Dto.EmployeeDTO;
import com.example.employee.Entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(EmployeeDTO employeeDTO);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);

    boolean existsByEmail(String email);

}