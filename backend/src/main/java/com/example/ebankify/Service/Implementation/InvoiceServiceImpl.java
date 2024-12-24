package com.example.ebankify.Service.Implementation;

import com.example.ebankify.DTO.InvoiceDTO;
import com.example.ebankify.Entity.Invoice;
import com.example.ebankify.Entity.User;
import com.example.ebankify.Repository.InvoiceRepository;
import com.example.ebankify.Repository.UserRepository;
import com.example.ebankify.Service.InvoiceService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final ModelMapper InvoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserRepository userRepository,ModelMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.InvoiceMapper=invoiceMapper;
    }

    @Override
    @Transactional
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        // Fetch the user associated with this invoice
        Optional<User> userOptional = userRepository.findById(invoiceDTO.getUserId());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found"); // Use custom exception if needed
        }

        // Map InvoiceDTO to Invoice entity
        Invoice invoice = InvoiceMapper.map(invoiceDTO,Invoice.class);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceDTO;
    }

    @Override
    public InvoiceDTO getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Invoice not found")); // Use custom exception if needed
    }

    @Override
    public List<InvoiceDTO> getInvoicesByUserId(Long userId) {
        return invoiceRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InvoiceDTO updateInvoice(Long invoiceId, InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found")); // Use custom exception if needed

        // Update invoice details
        invoice.setAmountDue(invoiceDTO.getAmountDue());
        invoice.setDueDate(invoiceDTO.getDueDate());

        Invoice updatedInvoice = invoiceRepository.save(invoice);
        return convertToDto(updatedInvoice);
    }

    @Override
    @Transactional
    public boolean deleteInvoice(Long invoiceId) {
        if (!invoiceRepository.existsById(invoiceId)) {
            throw new RuntimeException("Invoice not found"); // Use custom exception if needed
        }
        invoiceRepository.deleteById(invoiceId);
        return true;
    }

    @Override
    @Transactional
    public InvoiceDTO markInvoiceAsPaid(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found")); // Use custom exception if needed

        invoice.setAmountDue(0);
        Invoice updatedInvoice = invoiceRepository.save(invoice);

        return convertToDto(updatedInvoice);
    }

    @Override
    public List<InvoiceDTO> getOverdueInvoices() {
        LocalDate today = LocalDate.now();
        return invoiceRepository.findByDueDateBeforeAndAmountDueGreaterThan(today,0.0)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Helper method to convert Invoice entity to InvoiceDTO
    private InvoiceDTO convertToDto(Invoice invoice) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setAmountDue(invoice.getAmountDue());
        invoiceDTO.setDueDate(invoice.getDueDate());
        invoiceDTO.setUserId(invoice.getUser().getId());
        invoiceDTO.setAmountDue(invoice.getAmountDue());
        return invoiceDTO;
    }
}

