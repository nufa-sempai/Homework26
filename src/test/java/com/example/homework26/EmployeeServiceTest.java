package com.example.homework26;

import com.example.homework26.exceptions.EmployeeAlreadyAddedException;
import com.example.homework26.exceptions.EmployeeNotFoundException;
import com.example.homework26.exceptions.EmployeeStorageIsFullException;
import com.example.homework26.model.Employee;
import com.example.homework26.services.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeServiceTest {
    private EmployeeService employeeService;
    @BeforeEach
    public void beforeTests() {
        employeeService = new EmployeeService();
        employeeService.addEmployee("Семен", "Семенов", 1, 10000);
        employeeService.addEmployee("Сергей", "Сергеев", 2, 20000);
        employeeService.addEmployee("Иван", "Иванов", 3, 30000);
    }
    public static Stream<Arguments> addWithIncorrectFirstNameParams() {
        return Stream.of(
                Arguments.of("Семен1"),
                Arguments.of("Семен$"),
                Arguments.of("Семен!")
        );
    }
    public static Stream<Arguments> addWithIncorrectLastNameParams() {
        return Stream.of(
                Arguments.of("Семенов1"),
                Arguments.of("Семенов$"),
                Arguments.of("Семенов!")
        );
    }
    @Test
    public void addEmployeeTest() {
        int beforeCount = employeeService.findAll().size();
        Employee expected = new Employee("Петр", "Петров", 4, 40000);
        Assertions.assertThat(employeeService.addEmployee("Петр", "Петров", 4, 40000))
                .isEqualTo(expected)
                .isIn(employeeService.findAll());
        Assertions.assertThat(employeeService.findAll()).hasSize(beforeCount + 1);
        Assertions.assertThat(employeeService.findEmployee("Петр", "Петров", 4, 40000))
                .isEqualTo(expected);
    }

    @Test
    public void addWhenAlreadyAddedTest() {
        assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.addEmployee("Сергей", "Сергеев", 2, 20000));
    }
    @Test
    public void addWhenStorageIsFullTest() {
        Stream.iterate(1, i -> i + 1)
                .limit(7)
                .map(number -> new Employee(
                        "Иван" + ((char) ('а' + number)),
                        "Иванов" + ((char) ('а' + number)),
                        number, 10000 + number))
                .forEach(employee -> employeeService.addEmployee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getDepartment(),
                        employee.getSalary()));
        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.addEmployee("Петр", "Петров", 1, 10000));
    }
    @Test
    public void removeTest() {
        int beforeCount = employeeService.findAll().size();
        Employee expected = new Employee("Семен", "Семенов", 1, 10000);
        Assertions.assertThat(employeeService.removeEmployee("Семен", "Семенов", 1, 10000))
                .isEqualTo(expected)
                .isNotIn(employeeService.findAll());
        Assertions.assertThat(employeeService.findAll()).hasSize(beforeCount - 1);
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.findEmployee("Семен", "Семенов", 1, 10000));
    }
    @Test
    public void removeWhenNotFoundTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.findEmployee("Вася", "Васечкин", 2, 20000));
    }
    @Test
    public void findTest() {
        int beforeCount = employeeService.findAll().size();
        Employee expected = new Employee("Семен", "Семенов", 1, 10000);
        Assertions.assertThat(employeeService.findEmployee("Семен", "Семенов", 1, 10000))
                .isEqualTo(expected)
                .isIn(employeeService.findAll());
        Assertions.assertThat(employeeService.findAll()).hasSize(beforeCount);
    }
}