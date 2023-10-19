package com.pushpa.sunbase.Service;

import com.pushpa.sunbase.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class customerService {
    private final RestTemplate restTemplate;
    private final String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";


    @Autowired
    public customerService( RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }



    public List<Customer> getCustomerFromApi(String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);

        // Specify the desired content type for the response
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        RequestEntity<Void> request = RequestEntity.get(apiUrl)
                .headers(headers)
                .build();

        ResponseEntity<Customer[]> response = restTemplate.exchange(request, Customer[].class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return Arrays.asList(response.getBody());
        } else {
            // Handle the error
            System.out.println("Error Response: " + response.getBody());
            return Collections.emptyList();
        }
    }
}
