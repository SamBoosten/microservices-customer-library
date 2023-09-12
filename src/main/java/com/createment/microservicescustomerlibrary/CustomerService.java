package com.createment.microservicescustomerlibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Book> findAllBooks(){
        WebClient webClient = WebClient.create("http://localhost:8080");

        List<Book> books = webClient.get()
                .uri("/books/")
                .retrieve()
                .bodyToFlux(Book.class)
                .collectList()
                .block();

        return books;
    }
    public List<Book> findAllBooksFromCustomer(Customer customer){
        List<Book> customerBooks = findAllBooks().stream()
                .filter(book -> {
                    return book.getCustomer_id().equals(customer.getId());
                })
                .toList();
        return customerBooks;
    }
    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }
}
