package springboot.bookstorecafe.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

import springboot.bookstorecafe.models.CustomerData;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StripePaymentControllerAPI {

	@Value("${stripe.apikey}")
	String stripeKey;
	
	@RequestMapping("/createCustomer")
	public CustomerData createCustomer(@RequestBody CustomerData data) throws StripeException {
		
		Stripe.apiKey=stripeKey;

		Map<String, Object> params = new HashMap<>();
		params.put("name", data.getName());
		params.put("email", data.getEmail());
		

		Customer customer = Customer.create(params);
		data.setCustomerId(customer.getId());
		return data;
	}
}
