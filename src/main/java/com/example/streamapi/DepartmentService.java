package com.example.streamapi;

import org.springframework.stereotype.Service;

@Service
public class DepartmentService  extends EmployeeService {
  private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }



        private static Employee minSalary() {

        int min = Integer.MAX_VALUE;
        Employee employee = null;
        for (Employee employee1 : EMPLOYEES) {
            if (employee1.getSalary() < min) {
                min = employee1.getSalary();
                employee = employee1;
            }
        }
        return employee;
    }

    private static Employee maxSalary() {
        int max = 0;
        Employee employee = null;
        for (Employee employee1 : EMPLOYEES) {
            if (employee1.getSalary() > max) {
                max = employee1.getSalary();
                employee = employee1;
            }
        }
        return employee;
    }
    private static double averageSalary(){
        double average = summaSalary()/ (double)EMPLOYEES.length;
        return average;
    }
    private static void printName(){
        for (Employee employee: EMPLOYEES){
            System.out.println(employee.getName());
        }
    }


}
