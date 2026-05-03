package ru.ranepa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HrmApplication {
    public static void main(String[] args) {
        SpringApplication.run(HrmApplication.class, args);
//        EmployeeRepository repository = new EmployeeRepositoryImpl();
//        EmployeeService service = new EmployeeService(repository);
//        UserCommunicationProcess userCommunicationProcess = new UserCommunicationProcess(service);
//
//        userCommunicationProcess.start();
//
//        EmployeeUploader uploader = new EmployeeUploader(repository);
//        uploader.upload();
    }
}