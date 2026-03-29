package ru.ranepa.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    private final EmployeeRepository repository = mock();

    private final EmployeeService service = new EmployeeService(repository);

    @AfterEach
    public void afterEach() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void calculateAverageSalary() {
        var name = "name";
        var position = "pos";
        var hireDate = LocalDate.parse("2026-03-10");
        var salary1 = bd("100");
        var salary2 = bd("200");
        var salary3 = bd("300");
        var employees = List.of(
                new Employee(name, position, salary1, hireDate),
                new Employee(name, position, salary2, hireDate),
                new Employee(name, position, salary3, hireDate)
        );
        BigDecimal expected = bd("200.00");

        when(repository.findAll()).thenReturn(employees);

        BigDecimal actual = service.calculateAverageSalary();

        assertThat(actual).isEqualTo(expected);
        verify(repository).findAll();
    }

    @Test
    public void findTopSalaryEmployee() {
        var name = "name";
        var position = "position";
        var salary1 = bd("1.0");
        var salary2 = bd("2.0");
        var hireDate = LocalDate.parse("2026-01-02");
        var expected = new Employee(name, position, salary2, hireDate);
        var employees = List.of(
                new Employee(name, position, salary1, hireDate),
                expected
        );

        when(repository.findAll()).thenReturn(employees);

        Optional<Employee> actual = service.findTopSalaryEmployee();

        assertThat(actual.get()).isSameAs(expected);
        verify(repository).findAll();
    }

    @Test
    public void findEmployeeById_noEmployees() {
        BigDecimal expected = bd("0.00");

        when(repository.findAll()).thenReturn(emptyList());

        BigDecimal actual = service.calculateAverageSalary();

        assertThat(actual).isEqualTo(expected);
        verify(repository).findAll();
    }

    private static BigDecimal bd(String str) {
        return new BigDecimal(str);
    }

}