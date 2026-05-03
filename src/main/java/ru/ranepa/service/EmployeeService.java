package ru.ranepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

@Service
public class EmployeeService { // Cmd + Shift + T - создать тест/перейти к тесту
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean addEmployee(String name, String position, BigDecimal salary, LocalDate hireDate) {
        boolean isNameIncorrect = name == null || name.isEmpty();
        boolean isPositionIncorrect = position == null || position.isEmpty();
        boolean isSalaryIncorrect = salary == null || salary.compareTo(ZERO) < 0;
        boolean isHireDateIncorrect = hireDate == null || hireDate.isAfter(LocalDate.now());
        boolean isIncorrect = isNameIncorrect || isPositionIncorrect || isSalaryIncorrect || isHireDateIncorrect;

        if (isIncorrect) {
            return false;
        }

        Employee employee = new Employee(name, position, salary, hireDate);
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee.getId() != null;
    }

    public boolean deleteEmployee(long id) {
        employeeRepository.deleteById(id);
        return true;
    }

    public Optional<Employee> findEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public BigDecimal calculateAverageSalary() {
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        BigDecimal totalSalary = ZERO;
        int countEmployee = 0;

        for (Employee employee : allEmployees) {
            totalSalary = totalSalary.add(employee.getSalary());
            countEmployee++;
        }
        if (countEmployee == 0) {
            return ZERO.setScale(2, HALF_UP);
        }
        return totalSalary.divide(BigDecimal.valueOf(countEmployee), 2, HALF_UP);
    }

    public Optional<Employee> findTopSalaryEmployee() {
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        Employee topEmployee = null;

        for (Employee employee : allEmployees) {
            if (topEmployee == null || employee.getSalary().compareTo(topEmployee.getSalary()) > 0) {
                topEmployee = employee;
            }
        }
        return Optional.ofNullable(topEmployee);
    }

    public List<Employee> findAllByPosition(String position) {
        List<Employee> employeesWithPosition = new LinkedList<>();

        for (Employee employee : employeeRepository.findByPosition(position)) {
            employeesWithPosition.add(employee);
        }

        return employeesWithPosition;
    }
}
