package com.example.homework26.services;

import org.springframework.stereotype.Service;
import com.example.homework26.exceptions.EmployeeAlreadyAddedException;
import com.example.homework26.exceptions.EmployeeNotFoundException;
import com.example.homework26.exceptions.EmployeeStorageIsFullException;
import com.example.homework26.model.Employee;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private static final int LIMIT = 10;

    private final List<Employee> employees = new ArrayList<>();

    public Employee addEmployee(String firstName, String lastName, int department, int salary) {
        Employee employee = new Employee(firstName, lastName, department, salary);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < LIMIT) {
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();
    }

    public Employee removeEmployee(String firstName, String lastName, int department, int salary) {
        Employee employee = new Employee(firstName, lastName, department, salary);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException();
        }
        employees.remove(employee);
        return employee;
    }

    public Employee findEmployee(String firstName, String lastName, int department, int salary) {
        Employee employee = new Employee(firstName, lastName, department, salary);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }
}