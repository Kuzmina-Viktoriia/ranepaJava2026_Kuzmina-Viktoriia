package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.Optional;
//Alt + Enter -> implement interface или создать новый класс там дописать первую строку "implements EmployeeRepository" и выбрать при ошибке "implement methods"
public interface EmployeeRepository {
    String save (Employee employee);
    Optional<Employee> findById (Long id);
    Iterable<Employee> findAll();
    String delete (Long id);
}
