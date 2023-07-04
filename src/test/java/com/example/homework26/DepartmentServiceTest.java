package com.example.homework26;

import com.example.homework26.exceptions.DepartmentNotFoundException;
import com.example.homework26.model.Employee;
import com.example.homework26.services.DepartmentService;
import com.example.homework26.services.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private DepartmentService departmentService;

    public static Stream<Arguments> findSumSalaryFromDepartmentTestParams() {
        return Stream.of(
                Arguments.of(1, 10000),
                Arguments.of(2, 20000),
                Arguments.of(3, 32000),
                Arguments.of(4, 0)
        );
    }

    public static Stream<Arguments> findMaxSalaryFromDepartmentTestParams() {
        return Stream.of(
                Arguments.of(1, 10000),
                Arguments.of(2, 20000),
                Arguments.of(3, 17000)
        );
    }

    public static Stream<Arguments> findMinSalaryFromDepartmentTestParams() {
        return Stream.of(
                Arguments.of(1, 10000),
                Arguments.of(2, 20000),
                Arguments.of(3, 15000)
        );
    }

    public static Stream<Arguments> findAllEmployeesFromDepartmentTestParams() {
        return Stream.of(
                Arguments.of(1,
                        Collections.singletonList
                                (
                                        new Employee("Семен", "Семенов", 1, 10000)
                                )
                ),
                Arguments.of(2,
                        Collections.singletonList(
                                new Employee("Петр", "Петров", 2, 20000)
                        )
                ),
                Arguments.of(3,
                        List.of(
                                new Employee("Иван", "Иванов", 3, 15000),
                                new Employee("Вася", "Васин", 3, 17000)
                        )
                ),
                Arguments.of(4,
                        Collections.emptyList()
                )
        );
    }
    @BeforeEach
    public void beforeEach() {
        Mockito.when(employeeService.findAll()).thenReturn(
                List.of(
                        new Employee("Семен", "Семенов", 1, 10000),
                        new Employee("Петр", "Петров", 2, 20000),
                        new Employee("Иван", "Иванов", 3, 15000),
                        new Employee("Вася", "Васин", 3, 17000))

        );
    }

    @ParameterizedTest
    @MethodSource("findSumSalaryFromDepartmentTestParams")
    public void findSumSalaryFromDepartmentTest(int department, int expected) {
        Assertions.assertThat(departmentService.findSumSalaryFromDepartment(department))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("findMaxSalaryFromDepartmentTestParams")
    public void findMaxSalaryFromDepartmentTest(int department, int expected) {
        Assertions.assertThat(departmentService.findMaxSalaryFromDepartment(department))
                .isEqualTo(expected);
    }

    @Test
    public void findMaxSalaryNotFoundFromDepartmentTest() {
        Assertions.assertThatExceptionOfType(DepartmentNotFoundException.class)
                .isThrownBy(() -> departmentService.findMaxSalaryFromDepartment(4));
    }

    @ParameterizedTest
    @MethodSource("findMinSalaryFromDepartmentTestParams")
    public void findMinSalaryFromDepartmentTest(int department, int expected) {
        Assertions.assertThat(departmentService.findMinSalaryFromDepartment(department))
                .isEqualTo(expected);
    }

    @Test
    public void findMinSalaryNotFoundFromDepartmentTest() {
        Assertions.assertThatExceptionOfType(DepartmentNotFoundException.class)
                .isThrownBy(() -> departmentService.findMinSalaryFromDepartment(4));
    }

    @ParameterizedTest
    @MethodSource("findAllEmployeesFromDepartmentTestParams")
    public void findAllEmployeesFromDepartmentTest(int department, List<Employee> expected) {
        Assertions.assertThat(departmentService.findAllEmployeesFromDepartment(department))
                .containsExactlyInAnyOrderElementsOf(expected);
    }
    @Test
    public void findEmployeesByDepartmentTest() {
        Map<Integer, List<Employee>> expected = Map.of(
                1,
                Collections.singletonList(
                        new Employee("Семен", "Семенов", 1, 10000)
                ),
                2,
                Collections.singletonList(
                        new Employee("Петр", "Петров", 2, 20000)
                ),
                3,
                List.of(
                        new Employee("Иван", "Иванов", 3, 15000),
                        new Employee("Вася", "Васин", 3, 17000)
                )
        );
        Assertions.assertThat(departmentService.findEmployeesByDepartment())
                .containsExactlyInAnyOrderEntriesOf(expected);
    }
}