package com.example.homework26.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.homework26.model.Employee;
import com.example.homework26.services.DepartmentService;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/department")
public class DepartmentController {
    private DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/employees")
    public Map<Integer, List<Employee>> findEmployeesByDepartment() {
        return departmentService.findEmployeesByDepartment();
    }

    @GetMapping(value = "/{id}/employees")
    public List<Employee> findEmployeesFromDepartment(@PathVariable int id) {
        return departmentService.findAllEmployeesFromDepartment(id);
    }

    @GetMapping("/{id}/salary/sum")
    public int findSumSalaryFromDepartment(@PathVariable int id) {
        return departmentService.findSumSalaryFromDepartment(id);
    }

    @GetMapping("/{id}/salary/max")
    public int findMaxSalaryFromDepartment(@PathVariable int id) {
        return departmentService.findMaxSalaryFromDepartment(id);
    }

    @GetMapping("/{id}/salary/min")
    public int findMinSalaryFromDepartment(@PathVariable int id) {
        return departmentService.findMinSalaryFromDepartment(id);
    }

}