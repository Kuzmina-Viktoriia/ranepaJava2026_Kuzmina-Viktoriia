package ru.ranepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ranepa.model.Employee;

import java.math.BigDecimal;

//Alt + Enter -> implement interface или создать новый класс там дописать первую строку "implements EmployeeRepository" и выбрать при ошибке "implement methods"
//public interface EmployeeRepository {
//    boolean save(Employee employee);
//    Optional<Employee> findById(long id);
//    Iterable<Employee> findAll();
//    boolean delete(long id);
//}

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Iterable<Employee> findByPosition(String position);

    Iterable<Employee> findBySalaryGreaterThanEqual(BigDecimal salary);
}