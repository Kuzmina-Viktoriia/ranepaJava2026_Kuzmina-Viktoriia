package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final Map<Long, Employee> employees = new HashMap<>(); //Список ключ-значение

    private static long nextId = 1;

    @Override
    public boolean save(Employee employee) {
        long id = nextId++;
        employee.setId(id);
        employees.put(id, employee);
        return true;
    }

    @Override
    public Optional<Employee> findById(long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Iterable<Employee> findAll() {
        return employees.values();
    }

    @Override
    public boolean delete(long id) {
        if (employees.containsKey(id)) {
            employees.remove(id);
            return true;
        }
        return false;
    }
}
