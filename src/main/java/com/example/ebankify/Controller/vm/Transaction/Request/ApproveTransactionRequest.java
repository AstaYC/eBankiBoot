package com.example.ebankify.Controller.vm.Transaction.Request;

import com.example.ebankify.DTO.TransactionDTO;
import com.example.ebankify.Entity.Enums.Role;
import com.example.ebankify.Entity.Transaction;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApproveTransactionRequest {
    private String role;
    private String choix;
}
