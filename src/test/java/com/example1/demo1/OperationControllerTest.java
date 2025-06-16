package com.example1.demo1;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class OperationControllerTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private OperationController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInsufficientFunds() {
        
        OperationRequest request = new OperationRequest();
        request.setId(1);
        request.setAmount(100);
        request.setOperationType("withdraw");
        //еслм будет ошибка мокито, заменить аннтцию  мок на код
        when(userRepo.findAmountById(1)).thenReturn(50);


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.handleOperation(request);
        });
        assertEquals("dont enough moneyy", exception.getMessage());
    }

    @Test
    public void testNullOperationType() {
        
        OperationRequest request = new OperationRequest();
        request.setId(1);
        request.setAmount(100);
        request.setOperationType(null);

        
        ResponseEntity<String> response = controller.handleOperation(request);

        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request parameters", response.getBody());
    }

    @Test
    public void testNonPositiveAmount() {
        
        OperationRequest request = new OperationRequest();
        request.setId(1);
        request.setAmount(0);
        request.setOperationType("deposit");

        
        ResponseEntity<String> response = controller.handleOperation(request);

        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request parameters", response.getBody());
    }

    @Test
    public void testAccountNotExist() {
        // ,,skj d jcyjdyjv rkfcct
        OperationRequest request = new OperationRequest();
        request.setId(1);
        request.setAmount(50);
        request.setOperationType("withdraw");

        when(userRepo.findAmountById(1)).thenReturn(100);
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        
        ResponseEntity<String> response = controller.handleOperation(request);

        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Account not exist", response.getBody());
    }

    @Test
    public void testSuccessful() {
        // 
        OperationRequest request = new OperationRequest();
        request.setId(1);
        request.setAmount(50);
        request.setOperationType("withdraw");

        User user = new User(); 
        when(userRepo.findAmountById(1)).thenReturn(100);

        Optional<User> optionalUser = Optional.of(user);

        when(userRepo.findById(1)).thenReturn(optionalUser);


        ResponseEntity<String> response = controller.handleOperation(request);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());

    }
}
