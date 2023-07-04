package com.example.homework26.services;

import com.example.homework26.exceptions.DepartmentNotFoundException;
import com.example.homework26.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class DepartmentService {
    private EmployeeService employeeService;
    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public int findSumSalaryFromDepartment(int department) {
        return employeeService.findAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public int findMaxSalaryFromDepartment(int department) {
        return employeeService.findAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .map(Employee::getSalary)
                .max(Comparator.naturalOrder())
                .orElseThrow(DepartmentNotFoundException::new);
    }

    public int findMinSalaryFromDepartment(int department) {
        return employeeService.findAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .map(Employee::getSalary)
                .min(Comparator.naturalOrder())
                .orElseThrow(DepartmentNotFoundException::new);
    }

    public Map<Integer, List<Employee>> findEmployeesByDepartment() {
        return employeeService.findAll().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
    public List<Employee> findAllEmployeesFromDepartment(int department) {
        return employeeService.findAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .collect(Collectors.toList());
    }
}