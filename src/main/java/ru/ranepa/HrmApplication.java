package ru.ranepa;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.time.LocalDate;

public class HrmApplication {
    public static void main(String[] args) {
        Employee emp = new Employee(
                "Kuzmina Viktoriia Pavlovna",
                "business analyst",
                30_000.0,
                LocalDate.of(2026, 3, 1)
        );
        //sout - вывод на экран System.out.println в виде сокращения
        System.out.println(emp.getSalary());

        var repo = new EmployeeRepositoryImpl(); // или EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl() или var repo = new EmployeeRepositoryImpl()
        System.out.println("==========");
        System.out.println(repo.save(emp));
        System.out.println("==========");
        repo.findById(1L).orElseThrow();
        var emp1 = repo.findById(1L).orElseThrow();
        System.out.println("Employee was found: " + emp1);

    }
}