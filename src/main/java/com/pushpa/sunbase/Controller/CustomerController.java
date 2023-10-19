package com.pushpa.sunbase.Controller;

import com.pushpa.sunbase.Service.customerService;
import com.pushpa.sunbase.entity.Customer;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private customerService customerService;

    @Autowired
    private bearerToken bearerToken;
    private static final String API_URL = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";
    private static final String AUTHORIZATION_HEADER = "Bearer dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=";




//    private  String AUTHORIZATION_HEADER1="Bearer "+bearerToken.getToken();


    public CustomerController(com.pushpa.sunbase.Controller.bearerToken bearerToken) {
        this.bearerToken = bearerToken;
    }


//handles adding new customer
    @PostMapping("/create-customer")
    public String createCustomer(@ModelAttribute @Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "customer";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", AUTHORIZATION_HEADER);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Customer> request = new HttpEntity<>(customer, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                String responseBody = response.getBody();

//                repo.save(customer);
//                List<Customer> customers = customerService.getAll();
//                model.addAttribute("customers",customer);
                List<Customer> customers=customerService.getCustomerFromApi(AUTHORIZATION_HEADER);
                model.addAttribute("customers",customers);
                return "customer-list";

            }

        } catch (HttpClientErrorException e) {
            if (e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value()) {
                model.addAttribute("message", "First Name or Last Name is missing");
            } else {
                model.addAttribute("message", "An error occurred.");
            }
            return "error";
        }

        return "customer-list";
    }
//get customer list
//    @GetMapping("/customer-list")
//    public String showCustomerList(Model model) {
////        List<Customer> customers = customerService.getAll();
////        model.addAttribute("customers", customers);
//
//        return "customer-list"; // This corresponds to the customer-list.html template
//    }
//call add new customer form
    @GetMapping("/add")
    public String add(){
        return "customer";
    }

    //get customer from api given
    @GetMapping("/getCustomer")
    public String getCustomer(Model model){
        List<Customer> customer=customerService.getCustomerFromApi(AUTHORIZATION_HEADER);
        model.addAttribute("customers",customer);
        return "customer-list";
    }

    @GetMapping("/edit")
    public String editForm(){
        return "edit.html";
    }
}



