package com.createment.microservicescustomerlibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> displayAllCustomers(){
        return customerRepository.findAll();
    }

    @GetMapping("/{customerId}")
    public Customer displayCustomerById(@PathVariable Integer customerId) {
        return customerRepository.findById(customerId).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Customer> addNewCustomer(@RequestBody Customer customer) {
        try {
            Customer createdCustomer = customerService.createCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
