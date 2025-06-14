package com.example1.demo1;

import java.util.UUID;

class OperationRequest {
    private int id;
    private String operationType;
    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public int getAmount() {
        return (int) amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}