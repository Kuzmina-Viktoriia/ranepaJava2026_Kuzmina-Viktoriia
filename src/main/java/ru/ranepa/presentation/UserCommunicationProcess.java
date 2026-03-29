package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.model.EmployeeByNameComparator;
import ru.ranepa.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class UserCommunicationProcess {
    private final EmployeeService service;

    public UserCommunicationProcess(EmployeeService service) {
        this.service = service;
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to HRM system!");
            boolean running = true;

            while (running) {
                printMenu();
                System.out.println("Choose an action: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        showAllEmployees();
                        break;

                    case "2":
                        addNewEmployee(scanner);
                        break;

                    case "3":
                        deleteEmployee(scanner);
                        break;

                    case "4":
                        findEmployeeById(scanner);
                        break;

                    case "5":
                        showStatistics();
                        break;

                    case "6":
                        findAllByPosition(scanner);
                        break;
                    case "7":
                        System.out.println("Thank you for using HRM System. Goodbye!");
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
                System.out.println();
            }
        }

    }

    private static void printMenu() {
        System.out.println("\n--- HRM System Menu ---");
        System.out.println("1. Show all employees");
        System.out.println("2. Add employee");
        System.out.println("3. Delete employee by ID");
        System.out.println("4. Find employee by ID");
        System.out.println("5. Show statistics (average salary, employee with top salary)");
        System.out.println("6. Find all employees by position");
        System.out.println("7. Exit");
        System.out.println("-------------------------");
    }

    private void showAllEmployees() {
        System.out.println("\n All Employees: ");

        Iterable<Employee> employees = service.getAllEmployees();

        ArrayList<Employee> sortedEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            sortedEmployees.add(employee);
        }

        EmployeeByNameComparator byNameComparator = new EmployeeByNameComparator();
        sortedEmployees.sort(byNameComparator);

        if (sortedEmployees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("Total employees: " + sortedEmployees.size());
            for (Employee employee : sortedEmployees) {
                System.out.println(employee);
            }
        }
    }

    private void addNewEmployee(Scanner scanner) {
        System.out.println("\n Add New Employee ");

        System.out.print("Enter name (not empty): ");
        String name = scanner.nextLine();

        System.out.print("Enter position (not empty): ");
        String position = scanner.nextLine();


        BigDecimal salary = null;
        while (salary == null) {
            System.out.print("Enter salary (number, >=0): ");
            try {
                salary = new BigDecimal(scanner.nextLine());
            } catch (Exception ignored) {
            }
        }

        LocalDate hireDate = null;
        while (hireDate == null) {
            System.out.print("Enter hire date (YYYY-MM-DD): ");
            try {
                hireDate = LocalDate.parse(scanner.nextLine());
            } catch (Exception ignored) {
            }
        }

        boolean isNewEmployeeAdded = service.addEmployee(name, position, salary, hireDate);
        System.out.println("New employee was added: " + isNewEmployeeAdded);
        if (!isNewEmployeeAdded) System.out.println("If employee wasn't added, please, check your input");
    }

    private void deleteEmployee(Scanner scanner) {
        System.out.println("\n Delete Employee");

        Long id = null;
        while (id == null) {
            System.out.print("Enter employee ID to delete: ");
            try {
                id = Long.parseLong(scanner.nextLine());
            } catch (Exception ignored) {
            }
        }

        boolean isDeleted = service.deleteEmployee(id);
        System.out.println("Employee was deleted: " + isDeleted);
        if (!isDeleted) System.out.println("If employee wasn't deleted, please, try another id");
    }

    private void findEmployeeById(Scanner scanner) {
        System.out.println("\n Find Employee by Id");

        Long id = null;
        while (id == null) {
            System.out.print("Enter employee Id: ");
            try {
                id = Long.parseLong(scanner.nextLine());
            } catch (Exception ignored) {
            }
        }

        Optional<Employee> foundEmployee = service.findEmployeeById(id);
        if (foundEmployee.isPresent()) {
            Employee employee = foundEmployee.get();
            System.out.println("Found: " + employee);
        } else {
            System.out.println("Employee with ID " + id + " not found");
        }
    }

    private void showStatistics() {
        System.out.println("\n Statistics");

        BigDecimal averageSalary = service.calculateAverageSalary();
        System.out.println("Average salary: " + averageSalary);

        Optional<Employee> topResultEmployee = service.findTopSalaryEmployee();

        if (topResultEmployee.isPresent()) {
            Employee topEmployee = topResultEmployee.get();
            System.out.println("Top employee name: " + topEmployee.getName());
            System.out.println("Position: " + topEmployee.getPosition());
            System.out.println("Salary: " + topEmployee.getSalary());
        } else {
            System.out.println("No employees found");
        }
    }

    private void findAllByPosition(Scanner scanner) {
        System.out.println("\n Find all employees by position");

        System.out.print("Enter position to find: ");
        String position = scanner.nextLine();

        List<Employee> filteredEmployees = service.findAllByPosition(position);

        if (filteredEmployees.isEmpty()) {
            System.out.println("No employees found with position: " + position);
        } else {
            System.out.println("Found " + filteredEmployees.size() + " employee(s):");
            for (Employee employee : filteredEmployees) {
                System.out.println(employee);
            }
        }
    }

}
