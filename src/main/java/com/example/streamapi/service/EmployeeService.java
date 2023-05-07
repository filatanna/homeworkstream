package com.example.streamapi.service;

import com.example.streamapi.Employee;
import com.example.streamapi.exception.EmployeeAlreadyAddedException;
import com.example.streamapi.exception.EmployeeNotFoundException;
import com.example.streamapi.exception.EmployeeStorageIsFullException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {
    private  final List<Employee> EMPLOYEES = new ArrayList<>(3);



    @PostConstruct
     public void name(){
         EMPLOYEES.add(new Employee("Андрей","Иванов",1, 20000));
         EMPLOYEES.add(new Employee("Игорь","Петров", 2, 25000));
         EMPLOYEES.add(new Employee("Ирина","Сидорова", 1,30000));
     }
    public Employee add(String firstName, String lastName, int department, int salary){
        Employee employees= new Employee(firstName, lastName, department, salary);
        for (int i = 0; i < EMPLOYEES.size(); i++) {
            if (EMPLOYEES.get(i)==null){
             EMPLOYEES.set(i, new Employee(firstName, lastName, department, salary));
             return  EMPLOYEES.get(i);
            }
            else if (EMPLOYEES.get(i) != null  ) {
             throw new EmployeeAlreadyAddedException();
            }
                  }
        throw new EmployeeStorageIsFullException();

    }
    public Employee find(String firstName, String lastName, int department, int salary){
        Employee employees= new Employee(firstName, lastName, department, salary);
        for (int i = 0; i < EMPLOYEES.size(); i++) {
            if (EMPLOYEES.contains(employees)){
                return employees;
            }
        }
        throw new EmployeeNotFoundException();
    }
    public Employee remove(String firstName, String lastName, int department, int salary){

        Employee employees= new Employee(firstName, lastName, department,salary);
        for (int i = 0; i < EMPLOYEES.size(); i++) {
            if (EMPLOYEES.remove(employees)){
                return employees;
            }
        }
        throw new EmployeeNotFoundException();
    }

    public List<Employee> list() {
    return Collections.unmodifiableList(EMPLOYEES);
    }
}
