package com.example.streamapi.service;

import com.example.streamapi.Employee;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService  extends EmployeeService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }



    public Employee minSalary (int department) {
        return employeeService.list().stream()
                .filter(employee -> employee.getDepartment() == department)
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElse(null);
        }

    public  Employee maxSalary (int department) {
        return employeeService.list().stream()
                .filter(employee -> employee.getDepartment() == department)
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElse(null);
    }
    public Map< Integer, List<Employee>> printNameByDepartment(){
        return employeeService.list().stream()
                .collect(Collectors.groupingBy(Employee ::getDepartment));
    }
    public  List<Employee> printName(int department){
        return employeeService.list().stream()
                .filter(employee -> employee.getDepartment() == department)
                .collect(Collectors.toList());
    }


}
