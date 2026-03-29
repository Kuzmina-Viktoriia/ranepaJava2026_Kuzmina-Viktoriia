package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.io.FileWriter;
import java.io.IOException;

public class EmployeeUploader {
    private final EmployeeRepository repository;

    public EmployeeUploader(EmployeeRepository repository) {
        this.repository = repository;
    }

    public void upload() throws IOException {
        Iterable<Employee> employees = repository.findAll();
        try (var writer = new FileWriter("/Users/viktoriakuzmina/Desktop/file.txt")) {
            writer.write("");
            for (Employee employee : employees) {
                writer.append(employee.toString());
                writer.append("\n");
            }
        }
    }
}

