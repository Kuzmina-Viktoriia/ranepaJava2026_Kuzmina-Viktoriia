package ru.ranepa.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ranepa.dto.EmployeeRequestDto;
import ru.ranepa.dto.EmployeeResponseDto;
import ru.ranepa.dto.EmployeeStatsDto;
import ru.ranepa.model.Employee;
import ru.ranepa.service.EmployeeService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmployeeResponseDto> allEmployees() {
        LinkedList<EmployeeResponseDto> employeesDto = new LinkedList<>();
        for (Employee employee : service.getAllEmployees()) {
            EmployeeResponseDto dto = employeeToDto(employee);
            employeesDto.add(dto);
        }
        return employeesDto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> employeeById(@PathVariable("id") long id) {
        Optional<Employee> optionalEmployee = service.findEmployeeById(id);

        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(404).build();
        } else {
            EmployeeResponseDto dto = employeeToDto(optionalEmployee.get());
            return ResponseEntity.status(200).body(dto);
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> addEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        if (service.addEmployee(employeeRequestDto.name, employeeRequestDto.position, employeeRequestDto.salary, employeeRequestDto.hireDate)) {
            return ResponseEntity.status(201).body(true);
        }
        return ResponseEntity.status(400).body(false);
    }

    @DeleteMapping("/{id}")
    public boolean deleteEmployee(@PathVariable("id") long id) {
        return service.deleteEmployee(id);
    }

    @GetMapping("/position/{position}")
    public List<EmployeeResponseDto> employeeByPosition(@PathVariable("position") String position) {
        List<Employee> listEmployee = service.findAllByPosition(position);
        List<EmployeeResponseDto> listDtoEmployee = new LinkedList<>();
        for (Employee employee : listEmployee) {
            listDtoEmployee.add(employeeToDto(employee));
        }
        return listDtoEmployee;
    }

    @GetMapping("/stats")
    public EmployeeStatsDto statistics() {
        EmployeeStatsDto employeeStatsDto = new EmployeeStatsDto();
        Optional<Employee> optionalEmployee = service.findTopSalaryEmployee();
        if (optionalEmployee.isEmpty()) {
            employeeStatsDto.topSalaryEmployee = null;
        } else {
            employeeStatsDto.topSalaryEmployee = employeeToDto(optionalEmployee.get());
        }
        employeeStatsDto.averageSalary = service.calculateAverageSalary();
        return employeeStatsDto;
    }

    private EmployeeResponseDto employeeToDto(Employee employee) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.id = employee.getId();
        employeeResponseDto.name = employee.getName();
        employeeResponseDto.position = employee.getPosition();
        employeeResponseDto.salary = employee.getSalary();
        employeeResponseDto.hireDate = employee.getHireDate();
        employeeResponseDto.createdAt = employee.getCreatedAt();
        return employeeResponseDto;
    }
}
