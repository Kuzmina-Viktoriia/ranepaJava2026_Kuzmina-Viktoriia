package ru.ranepa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ranepa.model.Employee;
import ru.ranepa.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/api/employees")
    public List<Employee> allEmployees() {
        LinkedList<Employee> employees = new LinkedList<>();
        for (Employee employee : service.getAllEmployees()) {
            employees.add(employee);
        }
        return employees;
    }


    @PostMapping("/api/employees")
    public boolean addEmployee(@RequestBody EmployeeInfo employeeInfo) {
        return service.addEmployee(employeeInfo.name, employeeInfo.position, employeeInfo.salary, employeeInfo.hireDate);
    }

    public static class EmployeeInfo {
        public String name;
        public String position;
        public BigDecimal salary;
        public LocalDate hireDate;
    }
}
