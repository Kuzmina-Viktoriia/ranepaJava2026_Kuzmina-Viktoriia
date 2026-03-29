package ru.ranepa;

import ru.ranepa.presentation.UserCommunicationProcess;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;
import ru.ranepa.service.EmployeeUploader;

import java.io.IOException;

public class HrmApplication {
    public static void main(String[] args) throws IOException {
        EmployeeRepository repository = new EmployeeRepositoryImpl();
        EmployeeService service = new EmployeeService(repository);
        UserCommunicationProcess userCommunicationProcess = new UserCommunicationProcess(service);

        userCommunicationProcess.start();

        EmployeeUploader uploader = new EmployeeUploader(repository);
        uploader.upload();
    }
}