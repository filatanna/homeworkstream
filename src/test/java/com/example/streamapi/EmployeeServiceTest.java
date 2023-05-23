package com.example.streamapi;

import com.example.streamapi.exception.EmployeeAlreadyAddedException;
import com.example.streamapi.exception.EmployeeStorageIsFullException;
import com.example.streamapi.exception.IncorrectFirstNameException;
import com.example.streamapi.exception.IncorrectLastNameException;
import com.example.streamapi.service.EmployeeService;
import com.example.streamapi.service.ValidatorService;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.stream.Stream.*;

public class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService(new ValidatorService());
    public static Stream<Arguments> addIncorrectFirstNameTestParams (){
        return of(
                Arguments.of("Пет/"),
                Arguments.of("Петр1"),
                Arguments.of("Петр_")
        );
    }
    private static Stream<Arguments> addIncorrectLastNameTestParams () {
        return of(
                Arguments.of("Петров."),
                Arguments.of("Петров1"),
                Arguments.of("Петров_")
        );
    }
    @BeforeEach
    public void before (){
        employeeService.add("Петр", "Петров", 1, 10000);
        employeeService.add("Андрей", "Андреев", 2, 20000);
        employeeService.add("Роман", "Романов", 3, 30000);
    }
    @AfterEach
    public void after(){
            employeeService.list().stream()
                .forEach(employee -> employeeService.remove(employee.getFirstName(),
                        employee.getLastName(),
                        employee.getDepartment(),
                        employee.getSalary()));
    }
    @Test
    public void addTest(){
        int beforeCount =employeeService.list().size();
        Employee expected = new Employee("Petr", "Petrov", 1, 10000);
             Assertions.assertThat(employeeService.add("Petr", "Petrov", 1, 10000))
                .isEqualTo(expected)
                .isIn(employeeService.list());
             Assertions.assertThat(employeeService.list()).hasSize(beforeCount+1);


    }
    @ParameterizedTest
    @MethodSource("addIncorrectFirstNameTestParams")
    public void addIncorrectFirstNameTest(String incorrectFirstName){
               Assertions.assertThatExceptionOfType(IncorrectFirstNameException.class)
                       .isThrownBy(()-> employeeService.add(incorrectFirstName, "Petrov", 1, 10000));
    }
    @ParameterizedTest
    @MethodSource("addIncorrectLastNameTestParams")
    public void addIncorrectLastNameTest(String incorrectLastName){
        Assertions.assertThatExceptionOfType(IncorrectLastNameException.class)
                .isThrownBy(()->
                    employeeService.add("Petr", incorrectLastName, 1, 10000)
                );
    }
    @Test
    public void addIfAlreadyThereTest() {
        Assertions.assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.add( "Петр", "Петров", 1, 10000));
    }
    @Test
    public void addIfAlreadyAllThereTest() {
        Assertions.assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.add( "Петр", "Петров", 1, 10000));
    }
}
