package com.example1.demo1;

import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api")
public class OperationController {

    @Autowired
    UserRepo userRepo;

    @Transactional
    @PostMapping("api")
    public ResponseEntity<String> handleOperation(@RequestBody OperationRequest request) {
        double a = userRepo.findAmountById(request.getId());

        if ( a < request.getAmount()) {
            throw new IllegalArgumentException("dont enough money");
            
        }
        
        //невалидность данных 
        if (request.getOperationType() == null || request.getAmount() <= 0) {
            return new ResponseEntity<>("Invalid request parameters", HttpStatus.BAD_REQUEST);
        }
        //если нет акка
        Optional<User> optionalAccount = userRepo.findById(request.getId());

        if (!optionalAccount.isPresent()) {
            return ResponseEntity.status(404).body("Account not exist");
        }

        String opType = request.getOperationType().toUpperCase();

        switch (opType) {
            case "DEPOSIT":
                userRepo.UpdateAmount(request.getId(), request.getAmount());
                return ResponseEntity.ok("Deposit of " + request.getAmount() + " processed for ID: " + request.getId());

            case "WITHDRAW":
                userRepo.UpdateAmountWithdraw(request.getId(), request.getAmount());
                return ResponseEntity.ok("Withdrawal of " + request.getAmount() + " processed for ID: " + request.getId());

            default:
                return new ResponseEntity<>("Invalid operation type", HttpStatus.BAD_REQUEST);
        }
        
    }

    @GetMapping("api/v1/wallets/{WALLET_UUID}")
    public double getAmountName(@PathVariable int param) {
        return userRepo.findAmountById(param);
    }

    @GetMapping("api/create")
    public void createUser(User user) {
        userRepo.save(user);
    }
    
}
