package com.example.employee.Controller;

import com.example.employee.Dto.EmployeeDTO;
import com.example.employee.Entity.Employee;
import com.example.employee.Exception.EmployeeAlreadyExistException;
import com.example.employee.Exception.EmployeeNotFoundException;
import com.example.employee.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Employee getEmployeeById(@PathVariable long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) throw new EmployeeNotFoundException("Employee Not Found");
        return employee;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Employee addEmployee(@RequestBody EmployeeDTO dto) {
        if (employeeService.existsByEmail(dto.getEmail())) {
            throw new EmployeeAlreadyExistException("Employee Already Exist");
        }
        return employeeService.createEmployee(dto);
    }

    @PutMapping(value = "{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee updateEmployee(@PathVariable long id, @RequestBody EmployeeDTO dto) {
        if (employeeService.getEmployeeById(id) == null) {
            throw new EmployeeNotFoundException("Employee Not Found");
        }
        return employeeService.updateEmployee(id, dto);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEmployee(@PathVariable long id) {
        if (employeeService.getEmployeeById(id) == null) {
            throw new EmployeeNotFoundException("Employee Not Found");
        }
        employeeService.deleteEmployee(id);
        return "Employee Deleted Successfully";
    }
}