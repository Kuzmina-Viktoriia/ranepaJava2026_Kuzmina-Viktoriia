package ru.ranepa.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ranepa.model.Employee;
import ru.ranepa.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @MockitoBean
    private EmployeeService service;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void afterEach() {
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldAddEmployee() throws Exception {
        var json = "{\"name\": \"Vika\",\"position\":\"Analyst\",\"salary\":11000,\"hireDate\":\"2020-01-01\"}";
        var request = post("/api/employees")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);
        when(service.addEmployee(any(), any(), any(), any())).thenReturn(true);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().string("true"));

        verify(service).addEmployee("Vika", "Analyst", new BigDecimal(11000), LocalDate.parse("2020-01-01"));
    }

    @Test
    void shouldReturnAllEmployees() throws Exception {
        var request = get("/api/employees");
        String expectedJson1 = "{\"name\":\"name\",\"position\":\"position\",\"salary\": 1,\"hireDate\":\"2020-01-01\"}";
        String expectedJson2 = "{\"name\":\"name1\",\"position\":\"position1\",\"salary\": 2,\"hireDate\":\"2020-02-02\"}";
        String expectedJson = '[' + expectedJson1 + ',' + expectedJson2 + ']';
        when(service.getAllEmployees()).thenReturn(List.of(
                new Employee("name", "position", new BigDecimal(1), LocalDate.parse("2020-01-01")),
                new Employee("name1", "position1", new BigDecimal(2), LocalDate.parse("2020-02-02"))
        ));


        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(service).getAllEmployees();
    }

    @Test
    void shouldReturnNotFoundWhenNoEmployee() throws Exception {
        var request = get("/api/employees/10");
        when(service.findEmployeeById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(service).findEmployeeById(10);
    }
}