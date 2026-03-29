package ru.ranepa.model;

import java.util.Comparator;

public class EmployeeByNameComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee e1, Employee e2) {
        if (e1 == null && e2 == null) return 0;
        if (e1 == null) return -1;
        if (e2 == null) return 1;
        var name1 = e1.getName();
        var name2 = e2.getName();
        return name1.compareTo(name2);
    }
}
