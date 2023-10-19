package com.pushpa.sunbase.Controller;

import com.pushpa.sunbase.Service.customerService;
import com.pushpa.sunbase.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class loginController {
    private static final String AUTHORIZATION_HEADER = "Bearer dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=";
    @Autowired
    private customerService customerService;

    private static bearerToken bToken;
    public loginController(com.pushpa.sunbase.Controller.bearerToken bToken) {
        this.bToken = bToken;
    }

    @Value("${auth.api.url}")
    private String authApiUrl;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/authenticate")
    public String login(@RequestParam String login_id, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
        // Prepare the request body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"login_id\":\"" + login_id + "\",\"password\":\"" + password + "\"}", headers);

        // Send a POST request to the authentication API
        ResponseEntity<String> response = new RestTemplate().exchange(authApiUrl, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String token = response.getBody();
            // You can store the token or use it for further API calls
//            model.addAttribute("message", "Login successful. Token: " + token);
            bToken.setToken(token);
            System.out.println(bToken.getToken());
//            model.addAttribute("customer", new Customer());
//            return "customer";

//            List<Customer> customers = customerService.getAll();
//            model.addAttribute("customers",customers);
            List<Customer> customer=customerService.getCustomerFromApi(AUTHORIZATION_HEADER);
            model.addAttribute("customers",customer);
            return "customer-list";
//            return "redirect:/customer-list";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid login credentials");
            return "redirect:/login";
        }
    }
}

