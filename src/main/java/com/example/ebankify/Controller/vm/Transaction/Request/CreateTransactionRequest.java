package com.example.ebankify.Controller.vm.Transaction.Request;

import com.example.ebankify.DTO.TransactionDTO;

public class CreateTransactionRequest {
        private TransactionDTO transaction;

    public TransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDTO transaction) {
        this.transaction = transaction;
    }
}
