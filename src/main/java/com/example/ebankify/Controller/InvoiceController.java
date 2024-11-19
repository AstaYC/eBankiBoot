package com.example.ebankify.Controller;

import com.example.ebankify.DTO.InvoiceDTO;
import com.example.ebankify.DTO.LoanDTO;
import com.example.ebankify.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/")
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceDTO request){
        try{
            InvoiceDTO invoice=invoiceService.createInvoice(request);
            return ResponseEntity.ok("the invoice created successfully");
        }   catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllInvoices(){
        List<InvoiceDTO> invoices=invoiceService.getAllInvoices();
        if (invoices.isEmpty()){
            return ResponseEntity.ok("there is no invoices");
        }else{
            return ResponseEntity.ok(invoices);
        }
    }
}
