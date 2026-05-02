package ru.ranepa.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.math.BigDecimal.ZERO;

public class Employee {
    private Long id;
    private String name;
    private String position;
    private BigDecimal salary;
    private LocalDate hireDate;


    //alt+insert or Command+N - конструктор
    public Employee(String name, String position, BigDecimal salary, LocalDate hireDate) {
        boolean isIncorrect = isNameIncorrect(name)
                || isPositionIncorrect(position)
                || isSalaryIncorrect(salary)
                || isHireDateIncorrect(hireDate);
        if (isIncorrect) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    //alt+insert or Command+N -> toString(), предоставляется классом-родителем Object отсюда надпись Override
    @Override
    public String toString() {
        return String.format("Employee{id=%s, name='%s', position='%s', salary=%s, hireDate=%s}",
                id, name, position, salary, hireDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (isNameIncorrect(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (isPositionIncorrect(position)) {
            throw new IllegalArgumentException();
        }
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        if (isSalaryIncorrect(salary)) {
            throw new IllegalArgumentException();
        }
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        if (isHireDateIncorrect(hireDate)) {
            throw new IllegalArgumentException();
        }
        this.hireDate = hireDate;
    }

    private static boolean isNameIncorrect(String name) {
        return name == null || name.isEmpty();
    }

    private static boolean isPositionIncorrect(String position) {
        return position == null || position.isEmpty();
    }

    private static boolean isSalaryIncorrect(BigDecimal salary) {
        return salary == null || salary.compareTo(ZERO) < 0;
    }

    private static boolean isHireDateIncorrect(LocalDate hireDate) {
        return hireDate == null;
    }

//    public static void hello (){
//        System.out.println("Hello!");
//    }
}
