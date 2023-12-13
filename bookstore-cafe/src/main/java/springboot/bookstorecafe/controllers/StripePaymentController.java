package springboot.bookstorecafe.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StripePaymentController {

//	@Value("${stripe.apikey}")
//	String stripeKey;
//	
//	@RequestMapping("/createCustomer")
//	public String createCustomer() {
//		return "create-customer";
//	}
}
